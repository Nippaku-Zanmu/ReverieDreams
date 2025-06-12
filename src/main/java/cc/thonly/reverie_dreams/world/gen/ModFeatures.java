package cc.thonly.reverie_dreams.world.gen;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.world.gen.feature.CraterFeature;
import cc.thonly.reverie_dreams.world.gen.feature.CraterFeatureConfig;
import cc.thonly.reverie_dreams.world.gen.feature.DreamGridFeature;
import cc.thonly.reverie_dreams.world.gen.feature.DreamGridFeatureConfig;
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
