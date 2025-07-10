package cc.thonly.reverie_dreams.world;

import cc.thonly.mystias_izakaya.block.MIBlocks;
import cc.thonly.mystias_izakaya.item.MIItems;
import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.world.gen.ConfigurationFeatureInit;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class PlacedFeaturesInit {
    public static final RegistryKey<PlacedFeature> SPIRITUAL_TREE_KEY = getOrCreateRegistryKey("spiritual_tree_placed");
    public static final RegistryKey<PlacedFeature> LEMON_TREE_KEY = getOrCreateRegistryKey("lemon_tree_placed");
    public static final RegistryKey<PlacedFeature> GINKGO_TREE_KEY = getOrCreateRegistryKey("ginkgo_tree_placed");
    public static final RegistryKey<PlacedFeature> PEACH_TREE_KEY = getOrCreateRegistryKey("peach_tree_placed");
    public static final RegistryKey<PlacedFeature> OVERWORLD_SILVER_ORE_KEY = getOrCreateRegistryKey("overworld_silver_ore_placed");
    public static final RegistryKey<PlacedFeature> OVERWORLD_ORB_ORE_KEY = getOrCreateRegistryKey("overworld_orb_ore_placed");
    public static final RegistryKey<PlacedFeature> UDUMBARA_FLOWER_KEY = getOrCreateRegistryKey("udumbara_flower_placed");
    public static final RegistryKey<PlacedFeature> TREMELLA_KEY = getOrCreateRegistryKey("tremella_placed");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var registryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, SPIRITUAL_TREE_KEY, registryLookup.getOrThrow(ConfigurationFeatureInit.SPIRITUAL_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
                        PlacedFeatures.createCountExtraModifier(0, 0.1f, 1),
                        ModBlocks.SPIRITUAL.sapling()
                )
        );

        register(context, LEMON_TREE_KEY, registryLookup.getOrThrow(ConfigurationFeatureInit.LEMON_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
                        PlacedFeatures.createCountExtraModifier(0, 0.2f, 1),
                        MIBlocks.LEMON.sapling()
                )
        );

        register(context, GINKGO_TREE_KEY, registryLookup.getOrThrow(ConfigurationFeatureInit.GINKGO_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
                        PlacedFeatures.createCountExtraModifier(0, 0.1f, 1),
                        MIBlocks.GINKGO.sapling()
                )
        );
        register(context, PEACH_TREE_KEY, registryLookup.getOrThrow(ConfigurationFeatureInit.PEACH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
                        PlacedFeatures.createCountExtraModifier(0, 0.1f, 1),
                        MIBlocks.PEACH.sapling()
                )
        );

        register(context, OVERWORLD_SILVER_ORE_KEY, registryLookup.getOrThrow(ConfigurationFeatureInit.OVERWORLD_SILVER_ORE_KEY),
                Modifiers.modifiersCount(9, HeightRangePlacementModifier.uniform(YOffset.aboveBottom(10), YOffset.belowTop(10)))
        );
        register(context, OVERWORLD_ORB_ORE_KEY, registryLookup.getOrThrow(ConfigurationFeatureInit.OVERWORLD_ORB_ORE_KEY),
                Modifiers.modifiersCount(7, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(80)))
        );
        register(context, UDUMBARA_FLOWER_KEY, registryLookup.getOrThrow(ConfigurationFeatureInit.UDUMBARA_FLOWER_KEY),
                RarityFilterPlacementModifier.of(5),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()
        );
        register(context, TREMELLA_KEY, registryLookup.getOrThrow(ConfigurationFeatureInit.TREMELLA_KEY),
                RarityFilterPlacementModifier.of(6),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()
        );
    }

    public static void init() {

    }

    public static RegistryKey<PlacedFeature> getOrCreateRegistryKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Touhou.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context,
                                 RegistryKey<PlacedFeature> key,
                                 RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }

    public static class Modifiers {
        public static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
            return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
        }

        public static List<PlacementModifier> modifiersCount(int count, PlacementModifier heightModifier) {
            return modifiers(CountPlacementModifier.of(count), heightModifier);
        }

        public static List<PlacementModifier> modifiersRarity(int chance, PlacementModifier heightModifier) {
            return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
        }
    }
}
