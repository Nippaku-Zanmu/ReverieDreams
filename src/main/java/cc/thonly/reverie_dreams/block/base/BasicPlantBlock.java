package cc.thonly.reverie_dreams.block.base;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.block.crop.TransparentPlant;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import com.mojang.serialization.MapCodec;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.Random;

@Setter
@Getter
public class BasicPlantBlock extends PlantBlock implements Fertilizable, PolymerBlock, PolymerTexturedBlock, FactoryBlock, IdentifierGetter {
    private Identifier identifier;
    public static final MapCodec<BasicPlantBlock> CODEC = BasicPlantBlock.createCodec(BasicPlantBlock::new);

    protected BasicPlantBlock(Settings settings) {
        super(settings);
    }

    public BasicPlantBlock(String name, Settings settings) {
        this(Touhou.id(name), settings);
    }

    public BasicPlantBlock(Identifier identifier, Settings settings) {
        super(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World world, net.minecraft.util.math.random.Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, net.minecraft.util.math.random.Random random, BlockPos pos, BlockState state) {
        Block.dropStack((World) world, pos, new ItemStack(this));
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return TransparentPlant.TRANSPARENT;
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new Model(world, pos, initialBlockState);
    }

    @Getter
    public static class Model extends BlockModel {
        private final ServerWorld world;
        private final BlockPos blockPos;
        private final BlockState blockState;
        private ItemDisplayElement main;
        private ItemDisplayElement off;

        public Model(ServerWorld world, BlockPos blockPos, BlockState blockState) {
            this.world = world;
            this.blockPos = blockPos;
            this.blockState = blockState;
            this.init(this.blockState);
        }

        public void setState() {

            this.main.setScale(new Vector3f(0.5f));
            this.off.setScale(new Vector3f(0.5f));
            this.main.setRotation(0.0f, 45.0f);
            this.off.setRotation(0.0f, 135.0f);
            int seed = this.blockPos.getX() + this.blockPos.getY() + this.blockPos.getZ();
            Random random = new Random(seed);
            double offsetX = getRandomOffset(random);
            double offsetZ = getRandomOffset(random);

            this.main.setOffset(new Vec3d(offsetX, -0.29, offsetZ));
            this.off.setOffset(new Vec3d(offsetX, -0.29, offsetZ));
        }

        private double getRandomOffset(Random random) {
            double base = 0.1 + random.nextDouble() * 0.1;
            return random.nextBoolean() ? base : -base;
        }

        public void init(BlockState blockState) {
            this.main = ItemDisplayElementUtil.createSimple(blockState.getBlock().asItem());
            this.off = ItemDisplayElementUtil.createSimple(blockState.getBlock().asItem());
            this.setState();
            this.updateItem(blockState);
            this.addElement(this.main);
            this.addElement(this.off);
        }

        protected void updateItem(BlockState state) {
            this.removeElement(this.main);
            this.removeElement(this.off);
            this.main = ItemDisplayElementUtil.createSimple(state.getBlock().asItem());
            this.off = ItemDisplayElementUtil.createSimple(state.getBlock().asItem());
            this.setState();
            this.addElement(this.off);
        }

        @Override
        public void notifyUpdate(HolderAttachment.UpdateType updateType) {
            if (updateType == BlockBoundAttachment.BLOCK_STATE_UPDATE) {
                updateItem(this.blockState());
                this.tick();
            }
            super.notifyUpdate(updateType);
        }
    }
}
