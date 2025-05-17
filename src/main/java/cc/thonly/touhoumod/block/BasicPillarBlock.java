package cc.thonly.touhoumod.block;

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
import lombok.Setter;
import lombok.ToString;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import xyz.nucleoid.packettweaker.PacketContext;

@Setter
@Getter
@ToString
public class BasicPillarBlock extends PillarBlock implements FactoryBlock, IdentifierGetter {
    final Identifier identifier;

    public BasicPillarBlock(Identifier identifier, Settings settings) {
        super(settings.nonOpaque().registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;
    }

    public BasicPillarBlock(String path, Settings settings) {
        this(Touhou.id(path), settings);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState blockState, PacketContext packetContext) {
        return Blocks.BARRIER.getDefaultState();
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
        return Blocks.OAK_LOG.getDefaultState();
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState state) {
        return new Model(world, pos, state);
    }

    public static class Model extends BlockModel {
        private ItemDisplayElement main;
        private World world;
        private BlockPos pos;

        public Model(World world, BlockPos pos, BlockState state) {
            this.world = world;
            this.pos = pos;
            init(state);
        }

        private void init(BlockState state) {
            main = ItemDisplayElementUtil.createSimple(state.getBlock().asItem());
            switch (state.get(Properties.AXIS)) {
                case X: {
                    main.setYaw(90);
                    main.setPitch(90);
                    break;
                }
                case Z: {
                    main.setPitch(90);
                    break;
                }
            }
            main.setScale(new Vector3f(2f));
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
