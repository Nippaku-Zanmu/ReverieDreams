package cc.thonly.touhoumod.block.base;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.util.IdentifierGetter;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import xyz.nucleoid.packettweaker.PacketContext;

@Setter
@Getter
@ToString
public abstract class BasicPolymerBlockWithEntity extends BlockWithEntity implements PolymerBlock, PolymerTexturedBlock, IdentifierGetter {
    Identifier identifier;
    BlockState polymerBlockState;

    public BasicPolymerBlockWithEntity(String path, BlockModelType blockModelType, Settings settings) {
        this(Touhou.id(path), blockModelType, settings);
    }

    public BasicPolymerBlockWithEntity(Identifier identifier, BlockModelType blockModelType, Settings settings) {
        super(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;
        this.polymerBlockState = PolymerBlockResourceUtils.requestBlock(blockModelType, PolymerBlockModel.of(Identifier.of(identifier.getNamespace(), "block/" + identifier.getPath())));
    }

    @Override
    public BlockState getPolymerBlockState(BlockState blockState, PacketContext packetContext) {
        return this.polymerBlockState;
    }
}
