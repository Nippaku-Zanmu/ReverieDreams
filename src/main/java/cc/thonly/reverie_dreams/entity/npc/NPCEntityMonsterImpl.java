package cc.thonly.reverie_dreams.entity.npc;

import cc.thonly.reverie_dreams.entity.ai.goal.NPCAttackWithOwnerGoal;
import cc.thonly.reverie_dreams.entity.ai.goal.SleepAtNightGoal;
import cc.thonly.reverie_dreams.entity.ai.goal.WakeUpGoal;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.world.World;

@Getter
@Setter
public class NPCEntityMonsterImpl extends NPCEntityImpl {


    public NPCEntityMonsterImpl(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public NPCEntityMonsterImpl(EntityType<? extends TameableEntity> entityType, World world, Property skin) {
        super(entityType, world, skin);
    }

    @Override
    protected void initGoals() {
        super.initGoals();

        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new WakeUpGoal(this));
        this.goalSelector.add(3, new SleepAtNightGoal(this, 1.0));

        this.goalSelector.add(4, new TemptGoal(this, 1.2, stack -> stack.isOf(Items.CAKE), false));
        //        add and remove
        //        this.goalSelector.add(4, this.bowAttackGoal);
        //        this.goalSelector.add(4, this.meleeAttackGoal);
        this.goalSelector.add(6, new FollowOwnerGoal(this, 1.0, 2.0f, 10.0f));
        this.goalSelector.add(7, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0));

        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, NPCEntityImpl.class, 8.0f));
        this.goalSelector.add(10, new LookAroundGoal(this));

        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new NPCAttackWithOwnerGoal(this));
        this.targetSelector.add(3, new RevengeGoal(this).setGroupRevenge());

    }

}
