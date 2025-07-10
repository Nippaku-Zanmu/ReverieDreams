package cc.thonly.reverie_dreams.entity.ai.goal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;

import java.util.EnumSet;

public class NPCLookAroundGoal extends Goal {
    private final MobEntity mob;
    private double deltaX;
    private double deltaZ;
    private int lookTime;

    public NPCLookAroundGoal(MobEntity mob) {
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        return this.mob.getAttacker() != null && this.mob.getRandom().nextFloat() < 0.02f;
    }

    @Override
    public boolean shouldContinue() {
        return this.lookTime >= 0;
    }

    @Override
    public void start() {
        double d = Math.PI * 2 * this.mob.getRandom().nextDouble();
        this.deltaX = Math.cos(d);
        this.deltaZ = Math.sin(d);
        this.lookTime = 20 + this.mob.getRandom().nextInt(20);
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        --this.lookTime;
        this.mob.getLookControl().lookAt(this.mob.getX() + this.deltaX, this.mob.getEyeY(), this.mob.getZ() + this.deltaZ);
    }
}
