package me.cjcrafter.tileentity.compatibility;


import me.deecaad.compatibility.CompatibilitySetup;

public class TileEntityCompatibilityAPI {

    private static TileEntityCompatibility compatibility;

    /**
     * Don't let anybody instantiate this class
     */
    private TileEntityCompatibilityAPI() {
    }

    public static TileEntityCompatibility getCompatibility() {
        if (compatibility == null) {
            compatibility = new CompatibilitySetup().getCompatibleVersion(TileEntityCompatibility.class, "me.cjcrafter.tileentity.compatibility");
        }

        return compatibility;
    }
}
