package cc.thonly.reverie_dreams.world;

import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.gen.GenerationStep;

public class BiomeModificationInit {
    public static void init() {
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                PlacedFeaturesInit.OVERWORLD_SILVER_ORE_KEY
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                PlacedFeaturesInit.OVERWORLD_ORB_ORE_KEY
        );
    }
}
