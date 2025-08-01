package cc.thonly.reverie_dreams.entity.ai.goal.work;

import cc.thonly.reverie_dreams.entity.ai.goal.util.EntityTargetUtil;
import cc.thonly.reverie_dreams.entity.npc.NPCEntityImpl;
import cc.thonly.reverie_dreams.entity.npc.NPCWorkMode;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.Predicate;

public class NPCCloseToCropGoal extends Goal {
    protected final NPCEntityImpl maid;
    private final double speed;
    private int cooldown;
    private int moveTime = 0;
    private static final int WORKING_RANGE = 15;
    private static final Direction SEARCH_DIR = Direction.EAST;
    private static final int MAX_SEARCH_COUNT = 100;
    private BlockPos searchPos = BlockPos.ORIGIN;
    private BlockPos goalPos = BlockPos.ORIGIN;
    private Direction offDir = Direction.EAST;

    public NPCCloseToCropGoal(NPCEntityImpl maid, double speed) {
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        this.maid = maid;
        this.speed = speed;
    }

    @Override
    public boolean canStart() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        }
        if (searchPos == null||!EntityTargetUtil.isThisWorkMode(maid, NPCWorkMode.FARM)) {
            resetPos();
            return false;
        }
        return true;
    }


    @Override
    public boolean shouldContinue() {
        return this.canStart();
    }


    @Override
    public void start() {
        getNextGoal();
    }

    @Override
    public void stop() {
        this.maid.getNavigation().stop();
        this.cooldown = TemptGoal.toGoalTicks(100);
    }

    @Override
    public void tick() {
        moveTime++;



        if (goalPos ==null
                ||moveTime>100
                || goalPos.toCenterPos().squaredDistanceTo(Vec3d.of(this.maid.getWorkingPos()))>770
                ||this.maid.squaredDistanceTo(Vec3d.of(goalPos)) < 3

        ) {
            this.maid.getNavigation().stop();
            goalPos = getNextGoal();
            moveTime = 0;
        } else {
            Vec3d look = goalPos.toCenterPos().offset(Direction.DOWN, 0.5);
            this.maid.getLookControl().lookAt(look);
            this.maid.getNavigation().startMovingTo(look.x,look.y,look.z,this.speed);
        }
    }

    //----------------

    //寻路 农田按照平面考虑 应该不会有人在啥阴间的地方让女仆工作吧
    //由工作原点一角开始遍历 间距2block
    //如图所示
    //-------->|
    //         v
    //<--------
    //|
    //v------->

    private BlockPos getNextGoal(){
        nextPos();
        BlockPos nearTargetBlock = NPCFarmGoal.getNearTargetBlock(maid,searchPos, false,maid.getInventory().findHand(NPCFarmGoal.IS_SEED)==null);
        for (int searchCount = 0;searchCount<MAX_SEARCH_COUNT;searchCount++){
            if (nearTargetBlock != null) {
                System.out.println("goto "+nearTargetBlock);
                return searchPos;
            }
            nextPos();
        }
        System.out.println("err Pos "+ searchPos);
        return null;
    }

    private BlockPos nextPos(){
        BlockPos offset = offset(searchPos, offDir);
        if (offset!=null){//未碰壁
            searchPos = offset;
            return searchPos;
        }else {//碰壁了则垂直偏移并改方向
            offDir = offDir.getOpposite();
            BlockPos vertical = offset(searchPos, SEARCH_DIR.rotateYClockwise());
            if (vertical==null){//垂直方向碰壁了就到头了
                resetPos();
                return searchPos;
            }
            searchPos = vertical;
            return searchPos;
        }
    }
    private BlockPos offset(BlockPos pos,Direction dir){
        BlockPos offset3 = pos.offset(dir, 3);
        if (isInRange(offset3)){
            return offset3;
        }
        BlockPos offset2 = pos.offset(dir, 2);
        return isInRange(offset2)?offset2:null;
    }
    private boolean isInRange(BlockPos pos){
        BlockPos workingPos = maid.getWorkingPos();
        return pos.getX()>=workingPos.getX()-15&&pos.getX()<=workingPos.getX()+15
                &&pos.getZ()>=workingPos.getZ()-15&&pos.getZ()<=workingPos.getZ()+15;
    }
    //重置位置
    public void resetPos(){
        offDir = SEARCH_DIR;
        searchPos = maid.getWorkingPos().offset(offDir.rotateCounterclockwise(Direction.Axis.Y),WORKING_RANGE)
                .offset(offDir.getOpposite(),15);

    }


}

