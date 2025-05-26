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
public class BasicPolymerFenceGateBlock extends FenceGateBlock implements IdentifierGetter, FactoryBlock {
    private final Identifier identifier;
    private final Block template = Blocks.MANGROVE_FENCE_GATE;

    public BasicPolymerFenceGateBlock(Identifier identifier, WoodType type, Settings settings) {
        super(type, settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;
    }

    public BasicPolymerFenceGateBlock(String path, WoodType type, Settings settings) {
        this(Touhou.id(path), type, settings);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext packetContext) {
        return template.getDefaultState().with(FACING, state.get(FACING)).with(OPEN, state.get(OPEN)).with(IN_WALL, state.get(IN_WALL));
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new BasicPolymerFenceGateBlock.Model(initialBlockState, this.getIdentifier());
    }

    public static final class Model extends BlockModel {
        public final ItemStack MODEL_CLOSED;
        public final ItemStack MODEL_OPEN;
        public final ItemDisplayElement[] main = new ItemDisplayElement[2];

        public Model(BlockState state, Identifier id) {
            MODEL_CLOSED = ItemDisplayElementUtil.getModel(Identifier.of(id.getNamespace(), "block/" + id.getPath()));
            MODEL_OPEN = ItemDisplayElementUtil.getModel(Identifier.of(id.getNamespace(), "block/" + id.getPath() + "_open"));

            main[0] = ItemDisplayElementUtil.createSimple();
            main[1] = ItemDisplayElementUtil.createSimple();
            this.updateItem(state);
            addElement(main[0]);
            addElement(main[1]);
        }

        private void updateItem(BlockState state) {
            for (int i = 0; i < 2; i++) {
                ItemDisplayElement elem = main[i];
                elem.setItem(state.get(OPEN) ? MODEL_OPEN : MODEL_CLOSED);
                float scale = 1.0025f;
                elem.setScale(new Vector3f(state.get(OPEN) ? scale : 2 * scale));
                //float scaleOffset = (scale - 1) / 2;
                float offset = i == 0 ? 0.001f : -0.001f;
                elem.setTranslation(new Vector3f(offset, offset + (state.get(IN_WALL) ? -0.1875f : 0), offset));
                elem.setRightRotation(state.get(FACING).getRotationQuaternion().mul(RotationAxis.POSITIVE_X.rotationDegrees(-90)).mul(RotationAxis.POSITIVE_Y.rotationDegrees(180)));
            }
        }

        @Override
        public void notifyUpdate(HolderAttachment.UpdateType updateType) {
            if (updateType == BlockBoundAttachment.BLOCK_STATE_UPDATE) {
                this.updateItem(this.blockState());
                this.tick();
            }
            super.notifyUpdate(updateType);
        }
    }
}