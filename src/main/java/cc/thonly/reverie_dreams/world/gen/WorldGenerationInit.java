package cc.thonly.reverie_dreams.world.gen;

public class WorldGenerationInit {
    public static void registerWorldGeneration() {
        ConfigurationFeatureInit.init();
        ChunkGenerationInit.init();
    }
}
