package cc.thonly.reverie_dreams.world;

import cc.thonly.reverie_dreams.Touhou;
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
    public static final RegistryKey<PlacedFeature> OVERWORLD_SILVER_ORE_KEY = getOrCreateRegistryKey("overworld_silver_ore_placed");
    public static final RegistryKey<PlacedFeature> OVERWORLD_ORB_ORE_KEY = getOrCreateRegistryKey("overworld_orb_ore_placed");
    public static final RegistryKey<PlacedFeature> UDUMBARA_FLOWER_KEY = getOrCreateRegistryKey("udumbara_flower_placed");
    public static final RegistryKey<PlacedFeature> TREMELLA_KEY = getOrCreateRegistryKey("tremella_placed");
    public static void bootstrap(Registerable<PlacedFeature> context) {
        var registryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

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
