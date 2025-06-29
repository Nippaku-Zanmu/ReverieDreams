package cc.thonly.reverie_dreams.world.gen;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.world.gen.feature.CraterFeature;
import cc.thonly.reverie_dreams.world.gen.feature.CraterFeatureConfig;
import cc.thonly.reverie_dreams.world.gen.feature.DreamGridFeature;
import cc.thonly.reverie_dreams.world.gen.feature.DreamGridFeatureConfig;
import net.minecraft.block.Blocks;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

public class ConfigurationFeatureInit {
    public static final Feature<CraterFeatureConfig> CRATER = registerForVanilla("crater", new CraterFeature(CraterFeatureConfig.CODEC));
    public static final Feature<DreamGridFeatureConfig> DREAM_GRID = register("dream_world_grid", new DreamGridFeature(DreamGridFeatureConfig.CODEC));

    public static final RegistryKey<ConfiguredFeature<?, ?>> OVERWORLD_SILVER_ORE_KEY = getOrCreateRegistryKey("overworld_silver_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> OVERWORLD_ORB_ORE_KEY = getOrCreateRegistryKey("overworld_orb_ore");

    public static void init() {

    }

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherOreReplaceables = new BlockMatchRuleTest(Blocks.END_STONE);

        List<OreFeatureConfig.Target> overworldSilverTargets = List.of(
                OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.SILVER_ORE.getDefaultState()),
                OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.DEEPSLATE_SILVER_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> overworldOrbTargets = List.of(
                OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.ORB_ORE.getDefaultState()),
                OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.DEEPSLATE_ORB_ORE.getDefaultState()));

        registerContext(context, OVERWORLD_SILVER_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldSilverTargets, 15, 0.5F));
        registerContext(context, OVERWORLD_ORB_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldOrbTargets, 7, 0.32F));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void registerContext(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                          RegistryKey<ConfiguredFeature<?, ?>> key,
                                                                                          F feature,
                                                                                          FC featureConfig
    ) {
        context.register(key, new ConfiguredFeature<>(feature, featureConfig));
    }

    private static RegistryKey<ConfiguredFeature<?, ?>> getOrCreateRegistryKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Touhou.id(name));
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F registerForVanilla(String name, F feature) {
        return (F) Registry.register(Registries.FEATURE, name, feature);
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F) Registry.register(Registries.FEATURE, Touhou.id(name), feature);
    }
}
