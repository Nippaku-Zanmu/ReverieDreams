package cc.thonly.mystias_izakaya.block;

import cc.thonly.mystias_izakaya.MystiasIzakaya;
import cc.thonly.mystias_izakaya.block.entity.KitchenwareBlockEntity;
import cc.thonly.mystias_izakaya.gui.recipe.block.KitchenBlockGui;
import cc.thonly.reverie_dreams.block.crop.TransparentFlatTripWire;
import cc.thonly.reverie_dreams.recipe.BaseRecipe;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import com.mojang.serialization.MapCodec;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@ToString
public class AbstractKitchenwareBlock extends BlockWithEntity implements FactoryBlock, IdentifierGetter, TransparentFlatTripWire {
    public static final Set<AbstractKitchenwareBlock> KITCHENWARE_BLOCKS = new HashSet<>();
    public static final MapCodec<AbstractKitchenwareBlock> CODEC = createCodec(AbstractKitchenwareBlock::new);

    public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
    private Identifier identifier;
    private Vec3d offset = new Vec3d(0, 0, 0);
    private Vector3f scale = new Vector3f(0, 0, 0);
    private final Boolean requiredEnergy;
    private final Double tickBonus;

    public AbstractKitchenwareBlock(Settings settings) {
        super(settings);
        this.tickBonus = 0.0;
        this.requiredEnergy = true;
        KITCHENWARE_BLOCKS.add(this);
    }

    public AbstractKitchenwareBlock(String id, Double tickBonus, Boolean requiredEnergy, Vector3f scale, Vec3d offset, Settings settings) {
        this(MystiasIzakaya.id(id), tickBonus, requiredEnergy, scale, offset, settings);
    }

    public AbstractKitchenwareBlock(Identifier identifier, Double tickBonus, Boolean requiredEnergy, Vector3f scale, Vec3d offset, Settings settings) {
        super(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;
        this.offset = offset;
        this.scale = scale;
        this.tickBonus = tickBonus;
        this.requiredEnergy = requiredEnergy;
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
        KITCHENWARE_BLOCKS.add(this);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos belowPos = pos.down();
        BlockState belowState = world.getBlockState(belowPos);
        boolean pass = false;
        BlockState downBlockState = world.getBlockState(pos.down());
        Block upBlock = downBlockState.getBlock();
        if (upBlock instanceof FenceBlock || upBlock instanceof WallBlock || upBlock instanceof LeavesBlock) {
            pass = true;
        }
        return pass || belowState.isSideSolidFullSquare(world, belowPos, Direction.UP);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient() && world instanceof ServerWorld) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof KitchenwareBlockEntity kitchenwareBlockEntity) {
                if (kitchenwareBlockEntity.isWorking()) {
                    return ActionResult.FAIL;
                }
                UUID uuid = kitchenwareBlockEntity.getUuid();
                Set<KitchenBlockGui<?>> kitchenBlockGuis = KitchenwareBlockEntity.SESSIONS.computeIfAbsent(uuid, (map) -> new HashSet<>());
                KitchenBlockGui<BaseRecipe> simpleGui = new KitchenBlockGui<>(this, kitchenwareBlockEntity, serverPlayer);
                kitchenBlockGuis.add(simpleGui);
                simpleGui.open();

                return ActionResult.SUCCESS_SERVER;
            }
            return ActionResult.FAIL;
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient() && world instanceof ServerWorld serverWorld) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof KitchenwareBlockEntity kitchenwareBlockEntity) {
                SimpleInventory inventory = kitchenwareBlockEntity.getInventory();
                for (int i = 0; i < inventory.size(); i++) {
                    ItemStack stack = inventory.getStack(i);
                    if (stack.isEmpty()) {
                        continue;
                    }
                    ItemEntity itemEntity = new ItemEntity(serverWorld, pos.getX(), pos.getY(), pos.getZ(), stack);
                    serverWorld.spawnEntity(itemEntity);
                }
            }
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected MapCodec<? extends AbstractKitchenwareBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState) state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing();
        return this.getDefaultState().with(FACING, direction);
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState state) {
        return new Model(world, pos, state);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new KitchenwareBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, MiBlockEntities.KITCHENWARE_BLOCK_ENTITY, KitchenwareBlockEntity::tick);
    }

    public class Model extends BlockModel {

        private ItemDisplayElement main;

        public Model(ServerWorld world, BlockPos pos, BlockState state) {
            init(state);
        }

        public void init(BlockState state) {
            main = ItemDisplayElementUtil.createSimple(state.getBlock().asItem());
            Direction facing = state.get(FACING);
            float yaw = switch (facing) {
                case NORTH -> 180f;
                case EAST -> -90f;
                case SOUTH -> 0f;
                case WEST -> 90f;
                default -> 0f;
            };
            main.setOffset(getOffset());
            main.setScale(getScale());
            main.setYaw(yaw);
            addElement(main);
        }

        private void updateItem(BlockState state) {
            this.removeElement(main);
            init(state);
        }

        @Override
        public void notifyUpdate(HolderAttachment.UpdateType updateType) {
            if (updateType == BlockBoundAttachment.BLOCK_STATE_UPDATE) {
                updateItem(this.blockState());
            }
            super.notifyUpdate(updateType);
        }
    }
}
