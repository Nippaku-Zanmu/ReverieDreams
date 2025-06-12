package cc.thonly.reverie_dreams.block;

import cc.thonly.reverie_dreams.block.entity.BasicPolymerFactoryBlockWithEntity;
import cc.thonly.reverie_dreams.block.entity.GensokyoAltarBlockEntity;
import cc.thonly.reverie_dreams.block.entity.ModBlockEntities;
import cc.thonly.reverie_dreams.gui.recipe.entry.GensokyoAltarGui;
import cc.thonly.reverie_dreams.recipe.GensokyoAltarRecipes;
import cc.thonly.reverie_dreams.recipe.entry.GensokyoAltarRecipe;
import com.mojang.serialization.MapCodec;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class GensokyoAltarBlock extends BasicPolymerFactoryBlockWithEntity implements BreakContainerDropper {
    public static final Map<BlockState, AltarModel> STATE_TO_MODEL = new HashMap<>();
    public static final int[][] OFFSETS = {
            {0, -4}, {-3, -3}, {3, -3},
            {-4, 0}, {4, 0},
            {-3, 3}, {3, 3}, {0, 4}, {0, 0}
    };


    public GensokyoAltarBlock(String path, Settings settings) {
        super(path, settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient && player instanceof ServerPlayerEntity serverPlayer && world.getBlockEntity(pos) instanceof GensokyoAltarBlockEntity blockEntity) {
            boolean b = canUse(world, pos);
            player.swingHand(player.getActiveHand(), true);
            ServerWorld serverWorld = (ServerWorld) world;
            if (player.isSneaking()) {
                if (!b) {
                    return ActionResult.SUCCESS_SERVER;
                }
                SimpleInventory inventory = blockEntity.getInventory();
                GensokyoAltarRecipe.Entry craft = craft(inventory, pos);
                if (craft != null) {
                    List<ServerPlayerEntity> players = serverWorld.getPlayers();
                    for (var serverPlayerEntity : players) {
                        serverWorld.spawnParticles(serverPlayerEntity, ParticleTypes.ENCHANT, true, false, pos.getX(), pos.getY() + 1, pos.getZ(), 10000, 6, 6, 6, 0.5);
                        serverWorld.spawnParticles(serverPlayerEntity, ParticleTypes.WITCH, true, false, pos.getX(), pos.getY() + 1 , pos.getZ(), 10000, 0, 0, 0, 0.5);
                        for (var offset : OFFSETS) {
                            serverWorld.spawnParticles(serverPlayerEntity, ParticleTypes.PORTAL, true, false, offset[0], pos.getY(), offset[1], 800, 3, 5, 3, 0.5);
                        }
                    }
                    inventory.clear();
                    inventory.setStack(8, craft.getOutput().getStack());
                    blockEntity.markDirty();
                }
            } else {
                GensokyoAltarGui gui = new GensokyoAltarGui(serverPlayer, state, world, pos);
                gui.open();
            }
            return ActionResult.SUCCESS_SERVER;
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof GensokyoAltarBlockEntity GABE) {
            SimpleInventory inventory = GABE.getInventory();
            for (int i = 0; i < inventory.size(); i++) {
                ItemStack stack = inventory.getStack(i);
                ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack.copy());
                world.spawnEntity(itemEntity);
            }
        }
        return super.onBreak(world, pos, state, player);
    }

    protected GensokyoAltarRecipe.Entry craft(SimpleInventory inventory, BlockPos pos) {
        return GensokyoAltarRecipes.getRecipeRegistryRef().tryGetRecipes(inventory, pos);
    }

    public boolean canUse(World world, BlockPos center) {
        Block blockType = ModBlocks.STRIPPED_SPIRITUAL_LOG;

        for (int dy = 0; dy <= 2; dy++) {
            for (int i = 0; i < OFFSETS.length; i++) {
                int[] offset = OFFSETS[i];
                if (i == 8) continue;
                BlockPos checkPos = center.add(offset[0], dy, offset[1]);
                Block blockAtPos = world.getBlockState(checkPos).getBlock();
                if (blockAtPos != blockType) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.GENSOKYO_ALTAR_BLOCK_ENTITY, GensokyoAltarBlockEntity::tick);
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState state) {
        AltarModel altarModel = new AltarModel(world, pos, state);
        STATE_TO_MODEL.put(state, altarModel);
        return altarModel;
    }

    public static class AltarModel extends BlockModel {
        protected ItemDisplayElement main;
        public final ItemDisplayElement[] itemStackDisplay = new ItemDisplayElement[9];
        BlockState state;
        BlockPos pos;
        ServerWorld world;
        GensokyoAltarBlockEntity blockEntity;
        SimpleInventory inventory;

        public AltarModel(ServerWorld world, BlockPos pos, BlockState state) {
            init(state);
            this.world = world;
            this.pos = pos;
            this.state = state;
        }

        public GensokyoAltarBlockEntity getBlockEntityFromWorld() {
            if(this.world == null) return null;
            return (GensokyoAltarBlockEntity) this.world.getBlockEntity(this.pos);
        }


        public float angle = 0;

        public void update() {
            this.blockEntity = this.getBlockEntityFromWorld();
            if(this.blockEntity == null) return;
            this.inventory = this.blockEntity.getInventory();

            for (int i = 0; i < this.itemStackDisplay.length; i++) {
                ItemStack stack = this.inventory.getStack(i);
                ItemDisplayElement element = this.itemStackDisplay[i];

                if (stack.isEmpty()) {
                    if (element != null) {
                        this.removeElement(element);
                        this.itemStackDisplay[i] = null;
                    }
                    continue;
                }

                if (element == null || !ItemStack.areEqual(stack, element.getItem())) {
                    if (element != null) {
                        this.removeElement(element);
                    }

                    ItemDisplayElement newElement = ItemDisplayElementUtil.createSimple(stack);
                    int[] offset = OFFSETS[i];
                    newElement.setScale(new Vector3f(i != 8 ? 0.6f : 0.5f));
                    newElement.setDisplaySize(0.5f, 0.5f);
                    newElement.setOffset(new Vec3d(offset[0], i != 8 ? 3 : 0.5, offset[1]));

                    this.addElement(newElement);
                    this.itemStackDisplay[i] = newElement;
                }
            }
        }

        public void init(BlockState state) {
            main = ItemDisplayElementUtil.createSimple(state.getBlock().asItem());
            main.setScale(new Vector3f(2f));
            addElement(main);
        }

        protected void updateItem(BlockState state) {
            this.removeElement(main);
            init(state);
        }

        @Override
        public void notifyUpdate(HolderAttachment.UpdateType updateType) {
            if (updateType == BlockBoundAttachment.BLOCK_STATE_UPDATE) {
                updateItem(this.blockState());
                this.update();
            }
            super.notifyUpdate(updateType);
        }
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec((settings) -> new GensokyoAltarBlock(this.getIdentifier().toString(), settings));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GensokyoAltarBlockEntity(pos, state);
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
        return Blocks.ENCHANTING_TABLE.getDefaultState();
    }

}
