package cc.thonly.reverie_dreams.entity.ai.goal;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class SleepAtNightGoal extends Goal {
    private final PathAwareEntity entity;
    private final double speed;
    private BlockPos bedPos;
    private int sleepTick = 0;

    public SleepAtNightGoal(PathAwareEntity entity, double speed) {
        this.entity = entity;
        this.speed = speed;
    }

    @Override
    public boolean canStart() {
        if (!this.entity.getWorld().isNight() || this.entity.isSleeping()) return false;

        this.bedPos = findNearbyBed();
        return this.bedPos != null;
    }

    @Override
    public void start() {
        if (bedPos != null) {
            this.entity.getNavigation().startMovingTo(bedPos.getX(), bedPos.getY(), bedPos.getZ(), this.speed);
        }
    }


    @Override
    public boolean shouldContinue() {
        return !this.entity.isSleeping() && bedPos != null && entity.squaredDistanceTo(Vec3d.ofCenter(bedPos)) > 1.5;
    }

    @Override
    public void tick() {
        if (bedPos != null && entity.squaredDistanceTo(Vec3d.ofCenter(bedPos)) < 2) {
            if (!this.entity.getWorld().isClient) {
                if (sleepTick >= 20) {
                    entity.sleep(bedPos);
                    sleepTick = 0;
                }
            }
        }
        if (sleepTick >= 20 || entity.isSleeping()) sleepTick = 0;
        else sleepTick++;
    }

    private BlockPos findNearbyBed() {
        BlockPos entityPos = entity.getBlockPos();
        for (BlockPos pos : BlockPos.iterateOutwards(entityPos, 10, 3, 10)) {
            BlockState state = entity.getWorld().getBlockState(pos);
            if (state.getBlock() instanceof BedBlock) {
                if ((!state.get(BedBlock.OCCUPIED))) {
                    if (state.get(BedBlock.PART) == BedPart.HEAD) return pos;
                }
            }
        }
        return null;
    }
}
