package cc.thonly.reverie_dreams.world;

import cc.thonly.reverie_dreams.entity.ModEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class BiomeModificationInit {
    public static final int BASE_WEIGHT = 80;

    public static void init() {
        addBlock();
        addFlower();
        addTree();
        addEntity();
    }

    public static void addTree() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.SAVANNA),
                GenerationStep.Feature.VEGETAL_DECORATION, PlacedFeaturesInit.SPIRITUAL_TREE_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, PlacedFeaturesInit.LEMON_TREE_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SAVANNA, BiomeKeys.JUNGLE),
                GenerationStep.Feature.VEGETAL_DECORATION, PlacedFeaturesInit.GINKGO_TREE_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.JUNGLE),
                GenerationStep.Feature.VEGETAL_DECORATION, PlacedFeaturesInit.PEACH_TREE_KEY);
    }

    public static void addBlock() {
        // 银矿石
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                PlacedFeaturesInit.OVERWORLD_SILVER_ORE_KEY
        );
        // 宝玉矿石
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                PlacedFeaturesInit.OVERWORLD_ORB_ORE_KEY
        );
    }

    public static void addFlower() {
        // 幻昙华花
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS, BiomeKeys.FLOWER_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION,
                PlacedFeaturesInit.UDUMBARA_FLOWER_KEY
        );
        // 银耳丛
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.DARK_FOREST, BiomeKeys.BIRCH_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION,
                PlacedFeaturesInit.TREMELLA_KEY
        );
    }

    public static void addEntity() {
        // 妖精
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(ConventionalBiomeTags.IS_TEMPERATE),
                SpawnGroup.MONSTER,
                ModEntities.YOUSEI_ENTITY_TYPE, 60, 1, 2
        );
        // 向日葵妖精
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(ConventionalBiomeTags.IS_TEMPERATE),
                SpawnGroup.MONSTER,
                ModEntities.SUNFLOWER_YOUSEI_ENTITY_TYPE, 6, 1, 3
        );
        // 杀人蜂
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(ConventionalBiomeTags.IS_BIRCH_FOREST),
                SpawnGroup.MONSTER,
                ModEntities.KILLER_BEE_ENTITY_TYPE, 6, 2, 3
        );
        // 毛玉
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(ConventionalBiomeTags.IS_FOREST),
                SpawnGroup.MONSTER,
                ModEntities.HAIRBALL_ENTITY_TYPE, 10, 2, 4
        );
        // 哥布林
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.MINESHAFT_HAS_STRUCTURE),
                SpawnGroup.MONSTER,
                ModEntities.GOBLIN_ENTITY_TYPE, 50 / 5, 1, 1
        );
        // 蘑菇怪
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(ConventionalBiomeTags.IS_MUSHROOM),
                SpawnGroup.MONSTER,
                ModEntities.MUSHROOM_MONSTER_ENTITY_TYPE, 8, 1, 2
        );
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(ConventionalBiomeTags.IS_DARK_FOREST),
                SpawnGroup.MONSTER,
                ModEntities.MUSHROOM_MONSTER_ENTITY_TYPE, 8, 1, 2
        );
    }
}
