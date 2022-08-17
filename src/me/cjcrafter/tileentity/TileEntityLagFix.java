package me.cjcrafter.tileentity;

import me.deecaad.core.file.Configuration;
import me.deecaad.core.file.DuplicateKeyException;
import me.deecaad.core.file.LinkedConfig;
import me.deecaad.core.utils.Debugger;
import me.deecaad.core.utils.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TileEntityLagFix extends JavaPlugin {

    private static TileEntityLagFix inst;

    private Configuration configuration;
    private Debugger debug;

    @Override
    public void onEnable() {
        inst = this;

        if (!getDataFolder().exists() || getDataFolder().listFiles() == null || getDataFolder().listFiles().length == 0)
            FileUtil.copyResourcesTo(getClass(), getClassLoader(), "resources/TileEntityLagFix", getDataFolder());
        debug = new Debugger(getLogger(), 2);
        configuration = new LinkedConfig();
        try {
            configuration.add(getConfig());
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }

        registerListeners();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Debugger getDebug() {
        return debug;
    }

    public static TileEntityLagFix getInstance() {
        if (inst == null)
            throw new IllegalStateException("Plugin is not loaded");

        return inst;
    }

    public void registerListeners() {
        Bukkit.getServer().getPluginManager().registerEvents(new TileEntityMapReplacer(), this);
    }
}
