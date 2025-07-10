package cc.thonly.reverie_dreams.world.gen;

import cc.thonly.reverie_dreams.world.PlacedFeaturesInit;

public class WorldGenerationInit {
    public static void registerWorldGeneration() {
        ConfigurationFeatureInit.init();
        PlacedFeaturesInit.init();
        ChunkGenerationInit.init();
    }
}
