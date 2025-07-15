package cc.thonly.reverie_dreams.entity.ai.goal;

import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class NPCWanderAroundFarGoal extends WanderAroundGoal {
    public static final float CHANCE = 0.001f;
    protected final float probability;

    public NPCWanderAroundFarGoal(PathAwareEntity pathAwareEntity, double d) {
        this(pathAwareEntity, d, CHANCE);
    }

    public NPCWanderAroundFarGoal(PathAwareEntity mob, double speed, float probability) {
        super(mob, speed);
        this.probability = probability;
    }

    @Override
    @Nullable
    protected Vec3d getWanderTarget() {
        if (this.mob.isTouchingWater()) {
            Vec3d vec3d = FuzzyTargeting.find(this.mob, 15, 7);
            return vec3d == null ? super.getWanderTarget() : vec3d;
        }
        if (this.mob.getRandom().nextFloat() >= this.probability) {
            return FuzzyTargeting.find(this.mob, 10, 7);
        }
        return super.getWanderTarget();
    }

    @Override
    public boolean canStart() {
        return this.mob.getAttacker() == null && super.canStart();
    }
}
