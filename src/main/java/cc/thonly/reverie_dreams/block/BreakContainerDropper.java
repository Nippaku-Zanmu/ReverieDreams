package cc.thonly.reverie_dreams.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@FunctionalInterface
public interface BreakContainerDropper {
    BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player);
}
