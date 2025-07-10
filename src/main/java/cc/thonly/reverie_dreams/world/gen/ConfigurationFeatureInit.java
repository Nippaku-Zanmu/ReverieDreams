package cc.thonly.reverie_dreams.world.gen;

import cc.thonly.mystias_izakaya.block.MIBlocks;
import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.world.gen.feature.CraterFeature;
import cc.thonly.reverie_dreams.world.gen.feature.CraterFeatureConfig;
import cc.thonly.reverie_dreams.world.gen.feature.DreamGridFeature;
import cc.thonly.reverie_dreams.world.gen.feature.DreamGridFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.BendingTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;

public class ConfigurationFeatureInit {
    public static final Feature<CraterFeatureConfig> CRATER = registerForVanilla("crater", new CraterFeature(CraterFeatureConfig.CODEC));
    public static final Feature<DreamGridFeatureConfig> DREAM_GRID = register("dream_world_grid", new DreamGridFeature(DreamGridFeatureConfig.CODEC));
    public static final RegistryKey<ConfiguredFeature<?, ?>> SPIRITUAL_TREE_KEY = getOrCreateRegistryKey("spiritual_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LEMON_TREE_KEY = getOrCreateRegistryKey("lemon_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GINKGO_TREE_KEY = getOrCreateRegistryKey("ginkgo_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PEACH_TREE_KEY = getOrCreateRegistryKey("peach_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> OVERWORLD_SILVER_ORE_KEY = getOrCreateRegistryKey("overworld_silver_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> OVERWORLD_ORB_ORE_KEY = getOrCreateRegistryKey("overworld_orb_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NETHER_BLACK_SALT_ORE_KEY = getOrCreateRegistryKey("nether_black_salt");
    public static final RegistryKey<ConfiguredFeature<?, ?>> UDUMBARA_FLOWER_KEY = getOrCreateRegistryKey("udumbara_flower");
    public static final RegistryKey<ConfiguredFeature<?, ?>> TREMELLA_KEY = getOrCreateRegistryKey("tremella_flower");


    public static void init() {

    }

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherOreReplaceables = new TagMatchRuleTest(BlockTags.NETHER_CARVER_REPLACEABLES);
        RuleTest endOreReplaceables = new BlockMatchRuleTest(Blocks.END_STONE);

        List<OreFeatureConfig.Target> overworldSilverTargets = List.of(
                OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.SILVER_ORE.getDefaultState()),
                OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.DEEPSLATE_SILVER_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> overworldOrbTargets = List.of(
                OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.ORB_ORE.getDefaultState()),
                OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.DEEPSLATE_ORB_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> netherSaltTargets = List.of(
                OreFeatureConfig.createTarget(netherOreReplaceables, MIBlocks.BLACK_SALT_BLOCK.getDefaultState())
        );

        registerContext(context, SPIRITUAL_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.SPIRITUAL.log()),
                new StraightTrunkPlacer(4, 2, 1),
                BlockStateProvider.of(ModBlocks.SPIRITUAL.leaves()),
                new BlobFoliagePlacer(ConstantIntProvider.create(4), ConstantIntProvider.create(2), 2),
                new TwoLayersFeatureSize(1, 0, 2)
        ).build());
        registerContext(context, LEMON_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(MIBlocks.LEMON.log()),
                new BendingTrunkPlacer(2, 1, 2, 2, UniformIntProvider.create(1, 1)),
                new WeightedBlockStateProvider(Pool.<BlockState>builder()
                        .add(MIBlocks.LEMON.leaves().getDefaultState().with(LeavesBlock.PERSISTENT, true), 3)
                        .add(MIBlocks.LEMON_FRUIT_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true), 1)),
                new BlobFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(2), 2),
                new TwoLayersFeatureSize(1, 0, 2)
        ).build());
        registerContext(context, GINKGO_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(MIBlocks.LEMON.log()),
                new StraightTrunkPlacer(3, 1, 0),
                new WeightedBlockStateProvider(Pool.<BlockState>builder()
                        .add(MIBlocks.GINKGO.leaves().getDefaultState().with(LeavesBlock.PERSISTENT, true), 3)
                        .add(MIBlocks.GINKGO_FRUIT_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true), 1)),
                new BlobFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(2), 2),
                new TwoLayersFeatureSize(1, 0, 2)
        ).build());
        registerContext(context, PEACH_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(MIBlocks.PEACH.log()),
                new StraightTrunkPlacer(2, 1, 1),
                new WeightedBlockStateProvider(Pool.<BlockState>builder()
                        .add(MIBlocks.PEACH.leaves().getDefaultState(), 3)
                        .add(MIBlocks.PEACH_FRUIT_LEAVES.getDefaultState(), 1)),
                new BlobFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(2), 2),
                new TwoLayersFeatureSize(1, 0, 2)
        ).build());


        registerContext(context, OVERWORLD_SILVER_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldSilverTargets, 15, 0.3F));
        registerContext(context, OVERWORLD_ORB_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldOrbTargets, 7, 0.32F));
        registerContext(context, NETHER_BLACK_SALT_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherSaltTargets, 7, 0.22F));

        registerContext(context, UDUMBARA_FLOWER_KEY, Feature.FLOWER,
                new RandomPatchFeatureConfig(32, 4, 1,
                        PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(MIBlocks.UDUMBARA_FLOWER))
                        )));
        registerContext(context, TREMELLA_KEY, Feature.FLOWER,
                new RandomPatchFeatureConfig(32, 5, 2,
                        PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(MIBlocks.TREMELLA))
                        )));

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
