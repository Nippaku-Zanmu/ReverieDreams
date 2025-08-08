package cc.thonly.reverie_dreams.block.base;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallBlock;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import xyz.nucleoid.packettweaker.PacketContext;

@Setter
@Getter
public class BasicPolymerWallBlock extends WallBlock implements FactoryBlock, PolymerTexturedBlock, IdentifierGetter {
    private final Identifier identifier;

    public BasicPolymerWallBlock(Identifier identifier, Settings settings) {
        super(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;
    }

    public BasicPolymerWallBlock(String path, Settings settings) {
        this(Touhou.id(path), settings);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return Blocks.SANDSTONE_WALL.getStateWithProperties(state);
    }
}
