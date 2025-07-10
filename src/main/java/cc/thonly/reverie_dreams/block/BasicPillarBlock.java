package cc.thonly.reverie_dreams.block;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import xyz.nucleoid.packettweaker.PacketContext;

@Setter
@Getter
@ToString
public class BasicPillarBlock extends PillarBlock implements PolymerTexturedBlock, IdentifierGetter {
    private final Identifier identifier;
    private final BlockState[] model = new BlockState[3];

    public BasicPillarBlock(Identifier identifier, Settings settings) {
        super(settings.nonOpaque().registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;
        model[0] = PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(Touhou.id("block/" + identifier.getPath()), 90, 90));
        model[1] = PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(Touhou.id("block/" + identifier.getPath()), 0, 0));
        model[2] = PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(Touhou.id("block/" + identifier.getPath()), 90, 0));
    }

    public BasicPillarBlock(String path, Settings settings) {
        this(Touhou.id(path), settings);
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
        return Blocks.OAK_LOG.getDefaultState();
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return switch (state.get(PillarBlock.AXIS)) {
            case X -> model[0];
            case Y -> model[1];
            case Z -> model[2];
        };
    }

//    @Override
//    public BlockState getPolymerBlockState(BlockState blockState, PacketContext packetContext) {
//        return BlockTypeTag.BARRIER.getDefaultState();
//    }
//
//    @Override
//    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
//        return BlockTypeTag.OAK_LOG.getDefaultState();
//    }

//    @Override
//    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState state) {
//        return new Model(world, pos, state);
//    }
//
//    public static class Model extends BlockModel {
//        private ItemDisplayElement main;
//        private World world;
//        private BlockPos pos;
//
//        public Model(World world, BlockPos pos, BlockState state) {
//            this.world = world;
//            this.pos = pos;
//            init(state);
//        }
//
//        private void init(BlockState state) {
//            main = ItemDisplayElementUtil.createSimple(state.getBlock().asItem());
//            switch (state.get(Properties.AXIS)) {
//                case X: {
//                    main.setYaw(90);
//                    main.setPitch(90);
//                    break;
//                }
//                case Z: {
//                    main.setPitch(90);
//                    break;
//                }
//            }
//            main.setScale(new Vector3f(2f));
//            addElement(main);
//        }
//
//        private void updateItem(BlockState state) {
//            this.removeElement(main);
//            init(state);
//        }
//
//        @Override
//        public void notifyUpdate(HolderAttachment.UpdateType updateType) {
//            if (updateType == BlockBoundAttachment.BLOCK_STATE_UPDATE) {
//                updateItem(this.blockState());
//            }
//            super.notifyUpdate(updateType);
//        }
//    }
}
