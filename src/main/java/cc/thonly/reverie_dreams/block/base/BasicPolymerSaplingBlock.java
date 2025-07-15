package cc.thonly.reverie_dreams.block.base;


import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.block.crop.TransparentPlant;
import cc.thonly.reverie_dreams.datagen.ModBlockTagProvider;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.Random;

@Getter
public class BasicPolymerSaplingBlock extends SaplingBlock implements PolymerTexturedBlock, FactoryBlock, IdentifierGetter {
    private final Identifier identifier;

    public BasicPolymerSaplingBlock(Identifier identifier, SaplingGenerator generator, Settings settings) {
        super(generator, settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;
         ModBlockTagProvider.SAPLINGS.add(this);
    }

    public BasicPolymerSaplingBlock(String path, SaplingGenerator generator, Settings settings) {
        this(Touhou.id(path), generator, settings);
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
        return TransparentPlant.TRANSPARENT;
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

            this.main.setScale(new Vector3f(1f, 1f, 0.01f));
            this.off.setScale(new Vector3f(1f, 1f, 0.01f));

            this.main.setOffset(new Vec3d(0, 0, 0));
            this.off.setOffset(new Vec3d(0, 0, 0));
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