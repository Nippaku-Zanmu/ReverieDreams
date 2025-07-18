package cc.thonly.reverie_dreams.effect;

import cc.thonly.reverie_dreams.Touhou;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class ModStatusEffects {
    public static final RegistryEntry<StatusEffect> EMPTY = registerEffect("empty_effect", new EmptyEffect());
    public static final RegistryEntry<StatusEffect> ELIXIR_OF_LIFE = registerEffect("elixir_of_life", new ElixirOfLifeEffect());
    public static final RegistryEntry<StatusEffect> MENTAL_DISORDER = registerEffect("mental_disorder", new MentalDisorder());
    public static final RegistryEntry<StatusEffect> BACK_OF_LIFE = registerEffect("back_of_life", new BackOfLifeEffect());
    public static final RegistryEntry<StatusEffect> KANJU_KUSURI = registerEffect("kanju_kansuri", new KanjuKusuriEffect());

    public static void init() {
        ModPotions.init();
    }

    private static RegistryEntry<StatusEffect> registerEffect(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Touhou.id(id), statusEffect);
    }
}
