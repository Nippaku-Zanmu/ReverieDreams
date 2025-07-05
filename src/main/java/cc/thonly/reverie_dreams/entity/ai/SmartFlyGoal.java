package cc.thonly.reverie_dreams.entity.ai;

import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class SmartFlyGoal extends Goal {
    private final PathAwareEntity mob;
    private final double speed;
    private Vec3d movingTarget;

    public SmartFlyGoal(PathAwareEntity mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        return this.mob.getTarget() == null && this.mob.isOnGround();
    }

    @Override
    public void start() {
        Vec3d direction = this.mob.getRotationVec(0.0f);
        Vec3d target = AboveGroundTargeting.find(
                this.mob,
                8,
                7,
                direction.x,
                direction.z,
                (float)(Math.PI / 2),
                3,
                1
        );
        if (target != null) {
            this.mob.getNavigation().startMovingTo(target.x, target.y, target.z, this.speed);
        }
        this.movingTarget = target;
    }

    @Override
    public boolean shouldContinue() {
        return !this.mob.getNavigation().isIdle();
    }
}
