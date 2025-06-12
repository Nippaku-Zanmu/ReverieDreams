package cc.thonly.reverie_dreams.block.base;


import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import lombok.Getter;
import net.minecraft.block.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import xyz.nucleoid.packettweaker.PacketContext;

@Getter
public class BasicPolymerWallSignBlock extends WallSignBlock implements FactoryBlock, IdentifierGetter {
    private final Identifier identifier;
    private final Block template = Blocks.MANGROVE_WALL_SIGN;

    public BasicPolymerWallSignBlock(Identifier identifier, WoodType woodType, Settings settings) {
        super(woodType, settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;
    }

    public BasicPolymerWallSignBlock(String path, WoodType woodType, Settings settings) {
        this(Touhou.id(path), woodType, settings);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext packetContext) {
        return template.getDefaultState().with(FACING, state.get(FACING)).with(WATERLOGGED, state.get(WATERLOGGED));
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new BasicPolymerWallSignBlock.Model(initialBlockState, this.getIdentifier());
    }

    public static final class Model extends BlockModel {
        public ItemDisplayElement main;

        public Model(BlockState state, Identifier id) {
            main = ItemDisplayElementUtil.createSimple(ItemDisplayElementUtil.getModel(Identifier.of(id.getNamespace(), "block/" + id.getPath())));
            this.updateItem(state);
            addElement(main);
        }

        private void updateItem(BlockState state) {
            float scale = 1.0075f;
            main.setScale(new Vector3f(scale * 1.3333334f));
            main.setTranslation(new Vector3f(0, -0.4825f, -0.1435f));
            main.setYaw(state.get(FACING).getPositiveHorizontalDegrees());
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