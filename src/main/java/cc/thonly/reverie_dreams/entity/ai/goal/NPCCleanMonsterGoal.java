package cc.thonly.reverie_dreams.entity.ai.goal;

import cc.thonly.reverie_dreams.entity.npc.NPCEntityImpl;
import cc.thonly.reverie_dreams.entity.npc.NPCState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WeaponComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.EnumSet;
import java.util.List;

public class NPCCleanMonsterGoal extends TrackTargetGoal {

    public NPCCleanMonsterGoal(NPCEntityImpl maid) {
        super(maid, false);
        this.maid = maid;
        this.setControls(EnumSet.of(Goal.Control.TARGET));
    }

    private final NPCEntityImpl maid;
    private LivingEntity attacking;
    private int lastAttackTime;

    TargetPredicate targetPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(16).setPredicate((e,w)->{return  !e.hasCustomName();});
    LivingEntity targetEntity;
    @Override
    public boolean canStart() {
        if (!this.maid.isTamed() || this.maid.isSitting()) {
            return false;
        }
        NPCState state = maid.getNpcState();
        LivingEntity owner = this.maid.getOwner();
        boolean isMaidHasWeapon = this.maid.getMainHandStack().isIn(ItemTags.SWORDS);
        if (owner == null||state!=NPCState.WORKING|| !isMaidHasWeapon) {
            return false;
        }
        BlockPos workPos = maid.getWorkingPos();
        ServerWorld serverWorld = getServerWorld(maid);

        //serverWorld.getClosestEntity(HostileEntity.class,)
        List<HostileEntity> targets = this.mob.getWorld().getEntitiesByClass(HostileEntity.class, new Box(workPos).expand(16, 8, 16), EntityPredicates.VALID_ENTITY);
        //处于工作原点水平方向拓展16格内的所有怪物

        targetEntity = serverWorld.getClosestEntity(targets, targetPredicate, this.maid, this.maid.getX(), this.maid.getEyeY(), this.maid.getZ());
        //挑选离女仆最近的怪物
        return targetEntity!=null;
    }
    @Override
    public void start() {
        this.maid.setTarget(targetEntity);
    }
}
