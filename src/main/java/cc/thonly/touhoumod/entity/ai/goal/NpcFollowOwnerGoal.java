package cc.thonly.touhoumod.entity.ai.goal;

import cc.thonly.touhoumod.entity.npc.NPCEntityImpl;
import cc.thonly.touhoumod.entity.npc.NPCState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.passive.TameableEntity;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class NpcFollowOwnerGoal extends Goal {
    private final TameableEntity tameable;
    @Nullable
    private LivingEntity owner;
    private final double speed;
    private final EntityNavigation navigation;
    private int updateCountdownTicks;
    private final float maxDistance;
    private final float minDistance;
    private float oldWaterPathfindingPenalty;

    public NpcFollowOwnerGoal(TameableEntity tameable, double speed, float minDistance, float maxDistance) {
        this.tameable = tameable;
        this.speed = speed;
        this.navigation = tameable.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        if (!(tameable.getNavigation() instanceof MobNavigation) && !(tameable.getNavigation() instanceof BirdNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    @Override
    public boolean canStart() {
        LivingEntity livingEntity = this.tameable.getOwner();
        if (livingEntity == null) {
            return false;
        }
        if(this.tameable instanceof NPCEntityImpl impl) {
            NPCState state = impl.getNpcState();
            if(state == NPCState.NO_WALK || state == NPCState.SEATED || state == NPCState.WORKING) {
                return false;
            }
        }
        if(this.tameable.isSitting()) {
            return false;
        }
        if(this.tameable.getAttacking() != null) {
            if (this.tameable.getAttacking().isAlive()) {
                return false;
            }
            return false;
        }
        if(this.tameable.getTarget() != null) {
            if (this.tameable.getTarget().isAlive()) {
                return false;
            }
            return false;
        }
        if (this.tameable.cannotFollowOwner()) {
            return false;
        }
        if (this.tameable.squaredDistanceTo(livingEntity) < (double)(this.minDistance * this.minDistance)) {
            return false;
        }
        this.owner = livingEntity;
        return true;
    }

    @Override
    public boolean shouldContinue() {
        if (this.navigation.isIdle()) {
            return false;
        }
        if (this.tameable.cannotFollowOwner()) {
            return false;
        }
        return !(this.tameable.squaredDistanceTo(this.owner) <= (double)(this.maxDistance * this.maxDistance));
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathfindingPenalty = this.tameable.getPathfindingPenalty(PathNodeType.WATER);
        this.tameable.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
    }

    @Override
    public void stop() {
        this.owner = null;
        this.navigation.stop();
        this.tameable.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
    }

    @Override
    public void tick() {
        boolean bl = this.tameable.shouldTryTeleportToOwner();
        if (!bl) {
            this.tameable.getLookControl().lookAt(this.owner, 10.0f, this.tameable.getMaxLookPitchChange());
        }
        if (--this.updateCountdownTicks > 0) {
            return;
        }
        this.updateCountdownTicks = this.getTickCount(10);
        if (bl) {
            if (!this.tameable.isSitting()) {
                this.tameable.tryTeleportToOwner();
            }
        } else {
            if(this.tameable.getAttacking() == null) {
                this.navigation.startMovingTo(this.owner, this.speed);
            }
        }
    }
}

