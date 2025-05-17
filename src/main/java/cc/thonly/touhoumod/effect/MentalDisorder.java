package cc.thonly.touhoumod.effect;

import eu.pb4.polymer.core.api.other.PolymerStatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;

public class MentalDisorder extends StatusEffect implements PolymerStatusEffect {

    protected MentalDisorder() {
        super(StatusEffectCategory.NEUTRAL, 16262179);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        return super.applyUpdateEffect(world, entity, amplifier);
    }
}
