package me.cjcrafter.tileentity;

import me.cjcrafter.tileentity.compatibility.TileEntityCompatibilityAPI;
import me.deecaad.core.file.Configuration;
import me.deecaad.core.utils.Debugger;
import me.deecaad.core.utils.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TileEntityMapReplacer implements Listener {

    private static Field tileEntityMapField;

    static {
        try {
            Class<?> nmsWorldClass = ReflectionUtil.getNMSClass("world.level", "World");
            tileEntityMapField = ReflectionUtil.getField(nmsWorldClass, List.class, 1);
            tileEntityMapField.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public TileEntityMapReplacer() {
        for (World world : Bukkit.getWorlds()) {
            onWorldLoad(new WorldLoadEvent(world));
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent e) {
        World world = e.getWorld();
        Configuration config = TileEntityLagFix.getInstance().getConfiguration();

        if (!config.containsKey("Worlds." + world.getName())) {
            return;
        }

        Debugger debug = TileEntityLagFix.getInstance().getDebug();
        debug.info("Registering world " + world.getName());

        Set<String> blacklist = new HashSet<>(config.getList("Worlds." + world.getName()));
        Method getHandle = ReflectionUtil.getMethod(world.getClass(), "getHandle");
        Object nmsWorld = ReflectionUtil.invokeMethod(getHandle, world);
        List<?> arr = TileEntityCompatibilityAPI.getCompatibility().getTileList(blacklist);

        ReflectionUtil.setField(tileEntityMapField, nmsWorld, arr);
    }
}
