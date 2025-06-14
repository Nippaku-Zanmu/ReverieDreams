package cc.thonly.mystias_izakaya.block;

import cc.thonly.mystias_izakaya.MystiasIzakaya;
import cc.thonly.reverie_dreams.block.crop.TransparentFlatTripWire;
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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import xyz.nucleoid.packettweaker.PacketContext;

@Setter
@Getter
@ToString
public class AbstractKitchenwareBlock extends HorizontalFacingBlock implements FactoryBlock, IdentifierGetter {
    public static final MapCodec<AbstractKitchenwareBlock> CODEC = createCodec(AbstractKitchenwareBlock::new);

    public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
    Identifier identifier;
    Vec3d offset;
    Vector3f scale;

    public AbstractKitchenwareBlock(Settings settings) {
        super(settings);
    }

    public AbstractKitchenwareBlock(String id, Vector3f scale, Vec3d offset, Settings settings) {
        this(MystiasIzakaya.id(id), scale, offset, settings);
    }

    public AbstractKitchenwareBlock(Identifier identifier, Vector3f scale, Vec3d offset, Settings settings) {
        super(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;
        this.offset = offset;
        this.scale = scale;
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return TransparentFlatTripWire.TRANSPARENT_FLAT_TRIPIWIRE;
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
