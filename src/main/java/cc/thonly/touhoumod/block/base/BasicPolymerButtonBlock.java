package cc.thonly.touhoumod.block.base;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.util.IdentifierGetter;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import lombok.Getter;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import xyz.nucleoid.packettweaker.PacketContext;

@Getter
public class BasicPolymerButtonBlock extends ButtonBlock implements FactoryBlock, IdentifierGetter {
    Identifier identifier;
    Block template = Blocks.MANGROVE_BUTTON;

    public BasicPolymerButtonBlock(Identifier identifier, BlockSetType blockSetType, int pressTicks, Settings settings) {
        super(blockSetType, pressTicks, settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;
    }

    public BasicPolymerButtonBlock(String path, BlockSetType blockSetType, int pressTicks, Settings settings) {
        this(Touhou.id(path),blockSetType, pressTicks, settings);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext packetContext) {
        return template.getDefaultState().with(FACING, state.get(FACING)).with(FACE, state.get(FACE)).with(POWERED, state.get(POWERED));
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new BasicPolymerButtonBlock.Model(initialBlockState, this.getIdentifier());
    }

    public static final class Model extends BlockModel {
        public final ItemStack MODEL_UNPOWERED;
        public final ItemStack MODEL_POWERED;
        public ItemDisplayElement main;

        public Model(BlockState state, Identifier id) {
            MODEL_UNPOWERED = ItemDisplayElementUtil.getModel(Identifier.of(id.getNamespace(),"block/"+id.getPath()));
            MODEL_POWERED = ItemDisplayElementUtil.getModel(Identifier.of(id.getNamespace(), "block/"+id.getPath()+"_pressed"));

            main = ItemDisplayElementUtil.createSimple();
            this.updateItem(state);
            addElement(main);
        }
        private void updateItem(BlockState state) {
            main.setItem(state.get(POWERED) ? MODEL_POWERED : MODEL_UNPOWERED);
            if (state.get(FACE) == BlockFace.WALL) main.setRightRotation(state.get(FACING).getRotationQuaternion());
            else if (state.get(FACE) == BlockFace.CEILING) main.setRightRotation(RotationAxis.POSITIVE_Z.rotationDegrees(180).mul(RotationAxis.POSITIVE_Y.rotationDegrees(state.get(FACING).getPositiveHorizontalDegrees())));
            else  main.setRightRotation(RotationAxis.POSITIVE_Y.rotationDegrees(state.get(FACING).getPositiveHorizontalDegrees()));

            float scale = 1.00125f;
            main.setScale(new Vector3f(scale));
            float scaleOffset = (scale - 1)/2;
            if (state.get(FACE) == BlockFace.WALL) main.setTranslation(new Vector3f(scaleOffset, scaleOffset, scaleOffset).mul(state.get(FACING).getUnitVector()));
            else main.setTranslation(new Vector3f(0, state.get(FACE) == BlockFace.FLOOR ? scaleOffset : -scaleOffset, 0));
        }

        @Override
        public void notifyUpdate(HolderAttachment.UpdateType updateType) {
            if (updateType == BlockBoundAttachment.BLOCK_STATE_UPDATE){
                updateItem(this.blockState());
                this.tick();
            }
            super.notifyUpdate(updateType);
        }
    }
}
