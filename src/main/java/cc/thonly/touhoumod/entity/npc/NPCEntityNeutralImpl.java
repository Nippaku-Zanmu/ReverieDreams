package cc.thonly.touhoumod.entity.npc;

import cc.thonly.touhoumod.entity.ai.goal.*;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Leashable;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
public class NPCEntityNeutralImpl extends NPCEntityImpl implements Leashable {

    public NPCEntityNeutralImpl(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public NPCEntityNeutralImpl(EntityType<? extends TameableEntity> entityType, World world, Property skin) {
        super(entityType, world, skin);
    }

    @Override
    protected void initGoals() {
        super.initGoals();

        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new WakeUpGoal(this));
        this.goalSelector.add(3, new SleepAtNightGoal(this, 1.0));

        this.goalSelector.add(4, new NpcTemptGoal(this, 1.2, stack -> stack.isOf(TAME_FOOD_ITEM), false));
        //        this.goalSelector.add(4, this.bowAttackGoal);
        //        this.goalSelector.add(4, this.meleeAttackGoal);
        this.goalSelector.add(6, new NpcFollowOwnerGoal(this, 1.0, 2.0f, 10.0f));
        this.goalSelector.add(7, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0));

        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, NPCEntityImpl.class, 8.0f));
        this.goalSelector.add(10, new LookAroundGoal(this));

        this.targetSelector.add(1, new NpcTrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new NpcAttackWithOwnerGoal(this));
        this.targetSelector.add(3, new RevengeGoal(this).setGroupRevenge());

    }

    @Override
    public boolean canBeLeashed() {
        return true;
    }

    @Override
    public Boolean canFeed() {
        return true;
    }

    @Override
    public Boolean canDamageEquipment() {
        return true;
    }

    @Override
    public Boolean consumeHunger() {
        return true;
    }
}
