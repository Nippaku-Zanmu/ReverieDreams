package cc.thonly.reverie_dreams.effect;

import cc.thonly.reverie_dreams.Touhou;
import eu.pb4.polymer.core.api.other.SimplePolymerPotion;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModPotions {
    public static final Potion ELIXIR_OF_LIFE_POTION = registerPotion("elixir_of_life", new SimplePolymerPotion("elixir_of_life", new StatusEffectInstance(ModStatusEffects.ELIXIR_OF_LIFE, 3600, 0)));
    public static final Potion ELIXIR_OF_LIFE_POTION_INF = registerPotion("elixir_of_life_inf", new SimplePolymerPotion("elixir_of_life", new StatusEffectInstance(ModStatusEffects.ELIXIR_OF_LIFE, Integer.MAX_VALUE, 0)));
    public static final Potion MENTAL_DISORDER_POTION = registerPotion("mental_disorder", new SimplePolymerPotion("mental_disorder", new StatusEffectInstance(ModStatusEffects.MENTAL_DISORDER, 3600, 0)));
    public static final Potion BACK_OF_LIFE_POTION = registerPotion("back_of_life", new SimplePolymerPotion("back_of_life", new StatusEffectInstance(ModStatusEffects.BACK_OF_LIFE, 3600, 0)));

    public static void init() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {

        });
    }
    public static Potion registerPotion(String id, Potion potion) {
        Registry.register(Registries.POTION, Touhou.id(id), potion);
        return potion;
    }
}
