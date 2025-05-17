package cc.thonly.touhoumod.entity.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class StatusEffectTargetGoal<T extends LivingEntity> extends TrackTargetGoal {
    private static final int DEFAULT_RECIPROCAL_CHANCE = 10;
    protected final Class<T> targetClass;
    protected final int reciprocalChance;
    @Nullable
    protected LivingEntity targetEntity;
    protected TargetPredicate targetPredicate;

    @Nullable
    private final RegistryEntry<StatusEffect> requiredEffect;

    public StatusEffectTargetGoal(MobEntity mob, Class<T> targetClass, boolean checkVisibility, @Nullable RegistryEntry<StatusEffect> requiredEffect) {
        this(mob, targetClass, DEFAULT_RECIPROCAL_CHANCE, checkVisibility, false, null, requiredEffect);
    }

    public StatusEffectTargetGoal(MobEntity mob, Class<T> targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, @Nullable TargetPredicate.EntityPredicate targetPredicate, @Nullable RegistryEntry<StatusEffect> requiredEffect) {
        super(mob, checkVisibility, checkCanNavigate);
        this.targetClass = targetClass;
        this.reciprocalChance = ActiveTargetGoal.toGoalTicks(reciprocalChance);
        this.setControls(EnumSet.of(Goal.Control.TARGET));
        this.requiredEffect = requiredEffect;

        this.targetPredicate = TargetPredicate.createAttackable()
                .setBaseMaxDistance(this.getFollowRange())
                .setPredicate(targetPredicate);
    }

    @Override
    public boolean canStart() {
        if (this.reciprocalChance > 0 && this.mob.getRandom().nextInt(this.reciprocalChance) != 0) {
            return false;
        }
        this.findClosestTarget();
        return this.targetEntity != null;
    }

    protected Box getSearchBox(double distance) {
        return this.mob.getBoundingBox().expand(distance, distance, distance);
    }

    protected void findClosestTarget() {
        ServerWorld serverWorld = ActiveTargetGoal.getServerWorld(this.mob);

        if (this.targetClass == PlayerEntity.class || this.targetClass == ServerPlayerEntity.class) {
            this.targetEntity = serverWorld.getClosestPlayer(
                    this.getAndUpdateTargetPredicate().setPredicate(
                            (entity, world) -> requiredEffect == null || entity.hasStatusEffect(requiredEffect)
                    ),
                    this.mob,
                    this.mob.getX(),
                    this.mob.getEyeY(),
                    this.mob.getZ()
            );
        } else {
            this.targetEntity = serverWorld.getClosestEntity(
                    this.mob.getWorld().getEntitiesByClass(
                            this.targetClass,
                            this.getSearchBox(this.getFollowRange()),
                            entity -> {
                                if (entity.getClass() == this.mob.getClass()) return false;
                                return requiredEffect == null || entity.hasStatusEffect(requiredEffect);
                            }
                    ),
                    this.getAndUpdateTargetPredicate(),
                    this.mob,
                    this.mob.getX(),
                    this.mob.getEyeY(),
                    this.mob.getZ()
            );
        }
    }

    @Override
    public void start() {
        this.mob.setTarget(this.targetEntity);
        super.start();
    }

    public void setTargetEntity(@Nullable LivingEntity targetEntity) {
        this.targetEntity = targetEntity;
    }

    private TargetPredicate getAndUpdateTargetPredicate() {
        return this.targetPredicate.setBaseMaxDistance(this.getFollowRange());
    }
}
