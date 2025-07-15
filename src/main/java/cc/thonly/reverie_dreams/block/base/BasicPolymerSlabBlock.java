package cc.thonly.reverie_dreams.block.base;


import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import xyz.nucleoid.packettweaker.PacketContext;

@Getter
public class BasicPolymerSlabBlock extends SlabBlock implements IdentifierGetter, PolymerTexturedBlock, FactoryBlock {
    private final Identifier identifier;
    private static final BlockState TOP_SLAB = Blocks.MANGROVE_SLAB.getDefaultState()
            .with(SlabBlock.TYPE, SlabType.TOP);
    private static final BlockState TOP_SLAB_WATERLOGGED = Blocks.MANGROVE_SLAB.getDefaultState()
            .with(SlabBlock.TYPE, SlabType.TOP)
            .with(SlabBlock.WATERLOGGED, true);
    private static final BlockState BOTTOM_SLAB = Blocks.MANGROVE_SLAB.getDefaultState()
            .with(SlabBlock.TYPE, SlabType.BOTTOM);
    private static final BlockState BOTTOM_SLAB_WATERLOGGED = Blocks.MANGROVE_SLAB.getDefaultState()
            .with(SlabBlock.TYPE, SlabType.BOTTOM)
            .with(SlabBlock.WATERLOGGED, true);
    private final BlockState doubleState;
    private final PolymerBlockModel topSlabModel;
    private final PolymerBlockModel topSlabWaterLoggedModel;
    private final PolymerBlockModel bottomSlabModel;
    private final PolymerBlockModel bottomSlabWaterLoggedModel;

    public BasicPolymerSlabBlock(Identifier identifier, BlockState base, Settings settings) {
        super(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = Identifier.tryParse(this.getTranslationKey().replace("block.", "").replace(".", ":"));
        this.topSlabModel = PolymerBlockModel.of(Identifier.of(identifier.getNamespace(), "block/" + identifier.getPath() + "_top"));
        this.topSlabWaterLoggedModel = PolymerBlockModel.of(Identifier.of(identifier.getNamespace(), "block/" + identifier.getPath() + "_top"));
        this.bottomSlabModel = PolymerBlockModel.of(Identifier.of(identifier.getNamespace(), "block/" + identifier.getPath()));
        this.bottomSlabWaterLoggedModel = PolymerBlockModel.of(Identifier.of(identifier.getNamespace(), "block/" + identifier.getPath()));
        this.doubleState = base;
    }

    public BasicPolymerSlabBlock(String path, BlockState base, Settings settings) {
        this(Touhou.id(path), base, settings);
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
        return Blocks.OAK_SLAB.getDefaultState();
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return switch (state.get(TYPE)) {
            case TOP -> state.get(WATERLOGGED) ? TOP_SLAB_WATERLOGGED : TOP_SLAB;
            case BOTTOM -> state.get(WATERLOGGED) ? BOTTOM_SLAB_WATERLOGGED : BOTTOM_SLAB;
            default -> doubleState;
        };
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new Model(this, initialBlockState, this.getIdentifier().getPath());
    }

    @Getter
    public static final class Model extends BlockModel {
        private final BasicPolymerSlabBlock self;
        private final BlockState state;
        private final String path;
        private final ItemStack topSlabModel;
        private final ItemStack topSlabWaterLoggedModel;
        private final ItemStack bottomSlabModel;
        private final ItemStack bottomSlabWaterLoggedModel;
        public ItemDisplayElement main;

        public Model(BasicPolymerSlabBlock self, BlockState state, String path) {
            this.self = self;
            this.state = state;
            this.path = path;
            this.topSlabModel = ItemDisplayElementUtil.getModel(self.getTopSlabModel().model());
            this.topSlabWaterLoggedModel = ItemDisplayElementUtil.getModel(self.getTopSlabModel().model());
            this.bottomSlabModel = ItemDisplayElementUtil.getModel(self.getBottomSlabWaterLoggedModel().model());
            this.bottomSlabWaterLoggedModel = ItemDisplayElementUtil.getModel(self.getBottomSlabWaterLoggedModel().model());
            this.init();
        }

        public void init() {
            update(this.state);
        }

        public void update(BlockState state) {
            if (this.main != null) {
                this.removeElement(this.main);
                this.main = null;
            }

            SlabType slabType = state.get(SlabBlock.TYPE);
            boolean shouldShow = slabType == SlabType.TOP || slabType == SlabType.BOTTOM;

            if (!shouldShow) return;

            this.main = (slabType == SlabType.TOP)
                    ? new ItemDisplayElement(this.topSlabModel)
                    : new ItemDisplayElement(this.bottomSlabModel);

            final float uniformScale = 1.004f;
            if (slabType == SlabType.TOP) {
                this.main.setScale(new Vector3f(uniformScale, uniformScale + 0.001f, uniformScale));
                this.main.setOffset(new Vec3d(0, ((uniformScale - 1) / 4) - 0.002, 0));
            }
            if (slabType == SlabType.BOTTOM) {
                this.main.setScale(new Vector3f(uniformScale, uniformScale + 0.001f, uniformScale));
                this.main.setOffset(new Vec3d(0, (uniformScale - 1) / 4, 0));
            }

            this.addElement(this.main);
        }


        @Override
        public void notifyUpdate(HolderAttachment.UpdateType updateType) {
            if (updateType == BlockBoundAttachment.BLOCK_STATE_UPDATE) {
                update(this.blockState());
                this.tick();
            }
            super.notifyUpdate(updateType);
        }
    }
}