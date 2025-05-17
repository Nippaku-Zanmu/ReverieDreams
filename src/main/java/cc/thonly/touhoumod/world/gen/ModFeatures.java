package cc.thonly.touhoumod.world.gen;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.world.gen.feature.CraterFeature;
import cc.thonly.touhoumod.world.gen.feature.CraterFeatureConfig;
import cc.thonly.touhoumod.world.gen.feature.DreamGridFeature;
import cc.thonly.touhoumod.world.gen.feature.DreamGridFeatureConfig;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class ModFeatures {
    public static final Feature<CraterFeatureConfig> CRATER = registerForVanilla("crater", new CraterFeature(CraterFeatureConfig.CODEC));
    public static final Feature<DreamGridFeatureConfig> DREAM_GRID = register("dream_world_grid", new DreamGridFeature(DreamGridFeatureConfig.CODEC));

    public static void init() {

    }

    private static <C extends FeatureConfig, F extends Feature<C>> F registerForVanilla(String name, F feature) {
        return (F) Registry.register(Registries.FEATURE, name, feature);
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F) Registry.register(Registries.FEATURE, Touhou.id(name), feature);
    }
}
