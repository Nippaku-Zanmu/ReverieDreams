package cc.thonly.reverie_dreams.mixin.client;

import cc.thonly.reverie_dreams.TouhouClient;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NoteBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoteBlock.class)
public class NoteBlockMixin {
    @Inject(method = "getStateForNeighborUpdate",cancellable = true, at = @At("HEAD"))
    public void neighborUpdateInject(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random, CallbackInfoReturnable<BlockState> cir){
        if (world.isClient()){
            if (state.getBlock() instanceof PolymerBlock)
                cir.setReturnValue(Blocks.AIR.getDefaultState());
        }
    }
    @Inject(method = "onUse", cancellable = true,at = @At("HEAD"))
    public void onUseInject(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir){
        if (world.isClient){
            if (TouhouClient.SERVER_SIDE_BLOCKS.contains(state.getBlock())) {
               cir.setReturnValue(ActionResult.FAIL);
            }

        }
    }
}
