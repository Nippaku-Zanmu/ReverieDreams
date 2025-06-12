package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.item.base.BasicPolymerItem;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Optional;

public class ShideItem extends BasicPolymerItem {
    public ShideItem(String path, Settings settings) {
        super(path, settings, Items.PAPER);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient()) {
            World world = context.getWorld();
            PlayerEntity player = context.getPlayer();
            BlockPos pos = context.getBlockPos();
            BlockState state = world.getBlockState(pos);
            Optional<BlockState> blockStateOptional = Optional.ofNullable(this.tryGetBlock(state));
            if (blockStateOptional.isEmpty()) {
                return ActionResult.PASS;
            }
            ItemStack itemStack = context.getStack();
            if (player instanceof ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, pos, itemStack);
            }
            blockStateOptional.ifPresent(blockState -> world.setBlockState(pos, blockState, Block.NOTIFY_ALL_AND_REDRAW));
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockStateOptional.get()));
            if(player != null) {
                itemStack.decrement(1);
                world.playSound(player, pos, SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
            return ActionResult.SUCCESS_SERVER;
        }
        return super.useOnBlock(context);
    }

    public BlockState tryGetBlock(BlockState state) {
        Optional<Block> blockOptional = Optional.ofNullable(ModBlocks.SPIRITUAL_BLOCKS.getOrDefault(state.getBlock(), null));
        return blockOptional.map(block -> block.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS))).orElse(null);
    }

}
