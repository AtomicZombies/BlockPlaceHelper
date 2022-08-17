package me.cjcrafter.tileentity.compatibility;

import java.util.List;
import java.util.Set;

public interface TileEntityCompatibility {

    /**
     * Gets a map of <code>BlockPosition</code> keys and <code>TileEntity</code>
     * values that filters out specific tile entities based on <code>blacklist</code>
     *
     * Each compatibility version needs to return the proper version of
     * <code>BlockPosition</code> and <code>TileEntity</code>, else there will be
     * errors during reflection
     *
     * @see net.minecraft.server.v1_16_R1.BlockPosition
     * @see net.minecraft.server.v1_16_R1.TileEntity
     * @see net.minecraft.server.v1_16_R1.Chunk#tileEntities
     *
     * @param blacklist The blacklisted list of <code>TileEntity</code>
     * @return Map to store tile entities with filtering
     */
    List<?> getTileList(Set<String> blacklist);
}
