package cc.thonly.touhoumod.block;

import cc.thonly.touhoumod.block.base.BasicPolymerBlockWithEntity;
import cc.thonly.touhoumod.block.entity.DanmakuCraftingTableBlockEntity;
import cc.thonly.touhoumod.gui.recipe.entry.DanmakuCraftingTableGui;
import com.mojang.serialization.MapCodec;
import eu.pb4.polymer.blocks.api.BlockModelType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.nucleoid.packettweaker.PacketContext;

public class DanmakuCraftingTableBlock extends BasicPolymerBlockWithEntity implements BreakContainerDropper {

    public DanmakuCraftingTableBlock(String path, Settings settings) {
        super(path, BlockModelType.FULL_BLOCK, settings);
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
        return state;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient && player instanceof ServerPlayerEntity serverPlayer) {
            player.swingHand(player.getActiveHand(), true);
            DanmakuCraftingTableGui gui = new DanmakuCraftingTableGui(serverPlayer, world, pos);
            gui.open();
            return ActionResult.SUCCESS_SERVER;
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if(blockEntity instanceof DanmakuCraftingTableBlockEntity DTBE) {
            SimpleInventory inventory = DTBE.getInventory();
            for (int i = 0; i < inventory.size(); i++) {
                ItemStack stack = inventory.getStack(i);
                ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack.copy());
                world.spawnEntity(itemEntity);
            }
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec((settings) -> new DanmakuCraftingTableBlock(this.getIdentifier().toString(), settings));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DanmakuCraftingTableBlockEntity(pos, state);
    }
}
