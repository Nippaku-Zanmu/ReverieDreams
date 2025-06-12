package cc.thonly.reverie_dreams.block.base;


import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import lombok.Getter;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import xyz.nucleoid.packettweaker.PacketContext;

@Getter
public class BasicPolymerDoorBlock extends DoorBlock implements FactoryBlock, PolymerTexturedBlock, IdentifierGetter {
    private final BlockState NORTH_DOOR;
    private final BlockState EAST_DOOR;
    private final BlockState SOUTH_DOOR;
    private final BlockState WEST_DOOR;
    protected final ItemStack MODEL_TOP_RIGHT;
    protected final ItemStack MODEL_TOP_LEFT;
    protected final ItemStack MODEL_BOTTOM_RIGHT;
    protected final ItemStack MODEL_BOTTOM_LEFT;
    private final Identifier identifier;

    public BasicPolymerDoorBlock(Identifier identifier, Settings settings) {
        super(BlockSetType.OAK, settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;

        NORTH_DOOR = PolymerBlockResourceUtils.requestEmpty(BlockModelType.NORTH_DOOR);
        EAST_DOOR = PolymerBlockResourceUtils.requestEmpty(BlockModelType.EAST_DOOR);
        SOUTH_DOOR = PolymerBlockResourceUtils.requestEmpty(BlockModelType.SOUTH_DOOR);
        WEST_DOOR = PolymerBlockResourceUtils.requestEmpty(BlockModelType.WEST_DOOR);

        MODEL_TOP_RIGHT = ItemDisplayElementUtil.getModel(Identifier.of(identifier.getNamespace(), "block/%s_top_left".formatted(identifier.getPath())));
        MODEL_TOP_LEFT = ItemDisplayElementUtil.getModel(Identifier.of(identifier.getNamespace(), "block/%s_top_right".formatted(identifier.getPath())));
        MODEL_BOTTOM_RIGHT = ItemDisplayElementUtil.getModel(Identifier.of(identifier.getNamespace(), "block/%s_bottom_left".formatted(identifier.getPath())));
        MODEL_BOTTOM_LEFT = ItemDisplayElementUtil.getModel(Identifier.of(identifier.getNamespace(), "block/%s_bottom_right".formatted(identifier.getPath())));
    }

    public BasicPolymerDoorBlock(String path, Settings settings) {
        this(Touhou.id(path), settings);
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
        return Blocks.OAK_DOOR.getDefaultState();
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        if (!state.get(DoorBlock.OPEN)) {
            return switch (state.get(DoorBlock.FACING)) {
                case EAST -> EAST_DOOR;
                case WEST -> WEST_DOOR;
                case SOUTH -> SOUTH_DOOR;
                default -> NORTH_DOOR;
            };
        } else {
            if (state.get(DoorBlock.HINGE) == DoorHinge.LEFT) {
                return switch (state.get(DoorBlock.FACING)) {
                    case EAST -> SOUTH_DOOR;
                    case WEST -> NORTH_DOOR;
                    case SOUTH -> WEST_DOOR;
                    default -> EAST_DOOR;
                };
            } else {
                return switch (state.get(DoorBlock.FACING)) {
                    case EAST -> NORTH_DOOR;
                    case WEST -> SOUTH_DOOR;
                    case SOUTH -> EAST_DOOR;
                    default -> WEST_DOOR;
                };
            }
        }
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new Model(initialBlockState);
    }

    public static final class Model extends BlockModel {
        public ItemDisplayElement main;

        public Model(BlockState state) {
            main = ItemDisplayElementUtil.createSimple();
            main.setTeleportDuration(0);
            main.setInterpolationDuration(0);
            this.updateItem(state);
            updateStatePos(state);
            addElement(main);
        }

        private void updateStatePos(BlockState state) {
            var rotation = state.get(FACING).getPositiveHorizontalDegrees() + 270;
            var open = state.get(OPEN);
            if (state.get(BasicPolymerDoorBlock.HINGE).equals(DoorHinge.LEFT)) {
                rotation += open ? 90 : 0;
            } else {
                rotation += open ? 270 : 0;
            }
            main.setYaw(rotation);
        }

        private void updateItem(BlockState state) {
            BasicPolymerDoorBlock door = (BasicPolymerDoorBlock) state.getBlock();
            boolean useRightModel = state.get(HINGE).equals(DoorHinge.LEFT) ^ state.get(OPEN);

            if (state.get(HALF) == DoubleBlockHalf.UPPER)
                main.setItem(useRightModel ? door.MODEL_TOP_RIGHT : door.MODEL_TOP_LEFT);
            else
                main.setItem(useRightModel ? door.MODEL_BOTTOM_RIGHT : door.MODEL_BOTTOM_LEFT);
        }

        @Override
        public void notifyUpdate(HolderAttachment.UpdateType updateType) {
            if (updateType == BlockBoundAttachment.BLOCK_STATE_UPDATE) {
                updateStatePos(this.blockState());
                updateItem(this.blockState());
                this.tick();
            }
            super.notifyUpdate(updateType);
        }
    }
}