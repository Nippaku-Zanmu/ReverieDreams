package cc.thonly.reverie_dreams.block.base;


import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import xyz.nucleoid.packettweaker.PacketContext;

@Getter
public class BasicPolymerSaplingBlock extends SaplingBlock implements PolymerTexturedBlock, IdentifierGetter {
    private final Identifier identifier;
    private final BlockState model;

    public BasicPolymerSaplingBlock(Identifier identifier, SaplingGenerator generator, Settings settings) {
        super(generator, settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;
        this.model = PolymerBlockResourceUtils.requestBlock(BlockModelType.PLANT_BLOCK, PolymerBlockModel.of(Identifier.of(this.getIdentifier().getNamespace(), "block/" + this.getIdentifier().getPath())));
    }

    public BasicPolymerSaplingBlock(String path, SaplingGenerator generator, Settings settings) {
        this(Touhou.id(path), generator, settings);
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
        return model;
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return model;
    }
}