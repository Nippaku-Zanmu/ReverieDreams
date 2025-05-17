package cc.thonly.touhoumod.block;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.sound.ModSoundEvents;
import cc.thonly.touhoumod.util.IdentifierGetter;
import com.mojang.serialization.MapCodec;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import xyz.nucleoid.packettweaker.PacketContext;

@Getter
@Setter
@ToString
public class BasicFumoBlock extends HorizontalFacingBlock implements FactoryBlock, IdentifierGetter {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);
    public static final MapCodec<BasicFumoBlock> CODEC = createCodec(BasicFumoBlock::new);

    Identifier identifier;
    Vec3d offsets = new Vec3d(0, 0, 0);

    public BasicFumoBlock(Identifier identifier, Vec3d offsets, Settings settings) {
        super(settings.nonOpaque().registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;
        this.offsets = offsets;
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    public BasicFumoBlock(String path, Vec3d offsets, Settings settings) {
        this(Touhou.id(path), offsets, settings);
    }

    public BasicFumoBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        world.playSound(null, hit.getBlockPos(), SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS, 0.5f, 1);
        super.onProjectileHit(world, state, hit, projectile);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            world.playSound(null, pos, ModSoundEvents.randomFumo(), SoundCategory.BLOCKS, 1f, 1);
            return ActionResult.SUCCESS_SERVER;
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            world.playSound(null, pos, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS, 0.5f, 1);
        }
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return Blocks.BARRIER.getDefaultState();
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
        return Blocks.WHITE_WOOL.getDefaultState();
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new Model(initialBlockState, offsets);
    }

    public static final class Model extends ElementHolder {
        private final ItemDisplayElement main;

        public Model(BlockState state, Vec3d offsets) {
            this.main = ItemDisplayElementUtil.createSimple(state.getBlock().asItem());
            this.main.setDisplaySize(1f, 1f);
            this.main.setOffset(offsets);
            this.main.setScale(new Vector3f(1f));
            this.main.setModelTransformation(ModelTransformationMode.NONE);
            var yaw = state.get(FACING).getPositiveHorizontalDegrees();
            this.main.setYaw(yaw);
            this.addElement(this.main);
        }
    }
}
