package cc.thonly.touhoumod.world.gen;

public class ModWorldGeneration {
    public static void registerModGen() {
        ModFeatures.init();
        ModChunkGenerators.init();
    }
}
