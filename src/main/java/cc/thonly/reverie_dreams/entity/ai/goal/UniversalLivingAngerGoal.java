package cc.thonly.reverie_dreams.entity.ai.goal;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Box;
import net.minecraft.world.GameRules;

import java.util.List;

public class UniversalLivingAngerGoal<T extends LivingEntity>
        extends Goal {
    private static final int BOX_VERTICAL_EXPANSION = 10;
    private final T mob;
    private final boolean triggerOthers;
    private int lastAttackedTime;

    public UniversalLivingAngerGoal(T mob, boolean triggerOthers) {
        this.mob = mob;
        this.triggerOthers = triggerOthers;
    }

    @Override
    public boolean canStart() {
        return net.minecraft.entity.ai.goal.UniversalAngerGoal.getServerWorld(this.mob).getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER) && this.canStartUniversalAnger();
    }

    private boolean canStartUniversalAnger() {
        return this.mob.getAttacker() != null && this.mob.getAttacker().getType() == EntityType.PLAYER && ((LivingEntity)this.mob).getLastAttackedTime() > this.lastAttackedTime;
    }

    @Override
    public void start() {
        this.lastAttackedTime = this.mob.getLastAttackedTime();
        ((Angerable)this.mob).universallyAnger();
        if (this.triggerOthers) {
            this.getOthersInRange().stream().filter(entity -> entity != this.mob).map(entity -> (Angerable) entity).forEach(Angerable::universallyAnger);
        }
        super.start();
    }

    private List<? extends LivingEntity> getOthersInRange() {
        double d = this.mob.getAttributeValue(EntityAttributes.FOLLOW_RANGE);
        Box box = Box.from(this.mob.getPos()).expand(d, 10.0, d);
        return this.mob.getWorld().getEntitiesByClass(this.mob.getClass(), box, EntityPredicates.EXCEPT_SPECTATOR);
    }
}

