package cc.thonly.reverie_dreams.entity.ai.goal;

import cc.thonly.reverie_dreams.entity.MobDanmakuShooter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

@Getter
@Setter
@ToString
public class DanmakuGoal extends Goal {
    public static final MobDanmakuShooter DEFAULT_MOB_DANMAKU_FIRE_LAUNCHER = MobDanmakuShooter.DEFAULT;
    private final LivingEntity self;
    private final MobEntity mob;
    @Nullable
    private LivingEntity attackTarget;
    private final MobDanmakuShooter launcher;

    private final int minDelayTicks;
    private final int maxDelayTicks;
    private int updateCountdownTicks = -1;

    public DanmakuGoal(LivingEntity self, @Nullable MobDanmakuShooter launcher) {
        this(self, launcher, 20, 20 * 3);
    }

    public DanmakuGoal(LivingEntity self, @Nullable MobDanmakuShooter launcher, int minDelayTicks, int maxDelayTicks) {
        this.self = self;
        this.mob = (MobEntity) self;
        this.launcher = launcher != null ? launcher : DEFAULT_MOB_DANMAKU_FIRE_LAUNCHER;
        this.minDelayTicks = minDelayTicks;
        this.maxDelayTicks = maxDelayTicks;
        this.setControls(EnumSet.of(Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity == null || !livingEntity.isAlive()) {
            return false;
        }
        if (livingEntity instanceof PlayerEntity player) {
            if (player.isInCreativeMode()) {
                return false;
            }
        }
        this.attackTarget = livingEntity;
        return true;
    }

    @Override
    public void start() {
        this.resetCooldown();
    }

    @Override
    public void stop() {
        this.attackTarget = null;
        this.updateCountdownTicks = -1;
    }

    @Override
    public boolean shouldContinue() {
        return this.attackTarget != null && this.attackTarget.isAlive();
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    private void resetCooldown() {
        this.updateCountdownTicks = minDelayTicks + this.self.getRandom().nextInt(maxDelayTicks - minDelayTicks + 1);
    }

    @Override
    public void tick() {
        if (this.attackTarget == null || !this.attackTarget.isAlive()) {
            return;
        }

        float[] pitchYaw = MobDanmakuShooter.getPitchYaw(this.self, this.attackTarget);
        this.mob.getLookControl().lookAt(this.attackTarget);
        this.mob.setPitch(pitchYaw[0]);
        this.mob.setYaw(pitchYaw[1]);

        double distanceSq = this.self.squaredDistanceTo(this.attackTarget);
        if (distanceSq > 64.0) {
            if (this.mob.getNavigation().isIdle()) {
                this.mob.getNavigation().startMovingTo(this.attackTarget, 1.5);
            }
        } else {
            this.mob.getNavigation().stop();
        }

        if (--this.updateCountdownTicks <= 0) {
            World world = this.self.getWorld();
            if (world instanceof ServerWorld serverWorld) {
                this.launcher.fire(this.self, this.attackTarget, serverWorld);
                this.launcher.sound(this.self);
            }
            this.resetCooldown();
        }
    }
}
