package cc.thonly.reverie_dreams.effect;

import cc.thonly.reverie_dreams.interfaces.ILivingEntity;
import eu.pb4.polymer.core.api.other.PolymerStatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;

public class KanjuKusuriEffect extends StatusEffect implements PolymerStatusEffect {

    protected KanjuKusuriEffect() {
        super(StatusEffectCategory.BENEFICIAL, 16262179);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        ILivingEntity iLivingEntity  = (ILivingEntity) entity;
        if (entity.getWorld() instanceof ServerWorld) {
            iLivingEntity.setKanju((ServerWorld) entity.getWorld(), entity.getBlockPos());
        }
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        return super.applyUpdateEffect(world, entity, amplifier);
    }
}
