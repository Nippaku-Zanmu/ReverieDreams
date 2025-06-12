package cc.thonly.reverie_dreams.block.base;


import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import xyz.nucleoid.packettweaker.PacketContext;

@Getter
public class BasicPolymerSlabBlock extends SlabBlock implements IdentifierGetter, PolymerTexturedBlock {
    private final Identifier identifier;
    private final BlockState TOP_SLAB;
    private final BlockState TOP_SLAB_WATERLOGGED;
    private final BlockState BOTTOM_SLAB;
    private final BlockState BOTTOM_SLAB_WATERLOGGED;
    private final BlockState DOUBLE;

    public BasicPolymerSlabBlock(Identifier identifier, BlockState base, Settings settings) {
        super(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        DOUBLE = base;
        this.identifier = Identifier.tryParse(this.getTranslationKey().replace("block.", "").replace(".", ":"));
        TOP_SLAB = PolymerBlockResourceUtils.requestBlock(BlockModelType.TOP_SLAB, PolymerBlockModel.of(Identifier.of(identifier.getNamespace(), "block/" + identifier.getPath() + "_top")));
        TOP_SLAB_WATERLOGGED = PolymerBlockResourceUtils.requestBlock(BlockModelType.TOP_SLAB_WATERLOGGED, PolymerBlockModel.of(Identifier.of(identifier.getNamespace(), "block/" + identifier.getPath() + "_top")));
        BOTTOM_SLAB = PolymerBlockResourceUtils.requestBlock(BlockModelType.BOTTOM_SLAB, PolymerBlockModel.of(Identifier.of(identifier.getNamespace(), "block/" + identifier.getPath())));
        BOTTOM_SLAB_WATERLOGGED = PolymerBlockResourceUtils.requestBlock(BlockModelType.BOTTOM_SLAB_WATERLOGGED, PolymerBlockModel.of(Identifier.of(identifier.getNamespace(), "block/" + identifier.getPath())));
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
            default -> DOUBLE;
        };
    }
}