package cc.thonly.touhoumod.entity.ai.goal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;

public class WakeUpGoal extends Goal {
    private final PathAwareEntity entity;

    public WakeUpGoal(PathAwareEntity entity) {
        this.entity = entity;
    }
    @Override
    public boolean canStart() {
        boolean result = entity.isSleeping() && entity.getWorld().isDay();
//        System.out.println("WakeUpGoal.canStart: " + result + " | sleeping: " + entity.isSleeping() + " | isDay: " + entity.getWorld().isDay());
        return result;
    }

    @Override
    public void start() {
        entity.wakeUp();
    }

    @Override
    public void tick() {
        super.tick();
        if(entity.isSleeping() && entity.getWorld().isDay()) {
            entity.wakeUp();
        }
    }
}
