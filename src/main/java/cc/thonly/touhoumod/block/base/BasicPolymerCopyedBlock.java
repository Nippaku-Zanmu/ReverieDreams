package cc.thonly.touhoumod.block.base;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.util.IdentifierGetter;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import xyz.nucleoid.packettweaker.PacketContext;

@Setter
@Getter
@ToString
public class BasicPolymerCopyedBlock extends Block implements PolymerBlock, PolymerTexturedBlock, IdentifierGetter {
    Identifier identifier;
    Block targetblock;
    BlockState polymerBlockState;

    public BasicPolymerCopyedBlock(String path, Block block, Settings settings) {
        this(Touhou.id(path), block, settings);
    }

    public BasicPolymerCopyedBlock(Identifier identifier, Block targetblock, Settings settings) {
        super(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;
        this.targetblock = targetblock;
        this.polymerBlockState = targetblock.getDefaultState();
    }

    @Override
    public BlockState getPolymerBlockState(BlockState blockState, PacketContext packetContext) {
        return this.polymerBlockState;
    }
}
