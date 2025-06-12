package cc.thonly.reverie_dreams.world.gen;

public class ModWorldGeneration {
    public static void registerModGen() {
        ModFeatures.init();
        ModChunkGenerators.init();
    }
}
