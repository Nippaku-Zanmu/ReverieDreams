package cc.thonly.touhoumod.block.base;


import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.util.IdentifierGetter;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import lombok.Getter;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.HashMap;
import java.util.Map;

import static eu.pb4.polymer.blocks.api.BlockModelType.*;
import static eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils.requestBlock;

@Getter
public class BasicPolymerTrapdoorBlock extends TrapdoorBlock implements PolymerTexturedBlock, IdentifierGetter {
    private final BlockState[] TOP_TRAPDOORS = new BlockState[4];
    private final BlockState[] BOTTOM_TRAPDOORS = new BlockState[4];
    private final Map<Direction, BlockState> OPEN_TRAPDOORS = new HashMap<>();
    private final Map<Direction, BlockState> OPEN_TRAPDOORS_WATERLOGGED = new HashMap<>();
    private final Identifier identifier;

    public BasicPolymerTrapdoorBlock(Identifier identifier, Settings settings) {
        super(BlockSetType.OAK, settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;

        for (Direction dir : Direction.Type.HORIZONTAL.stream().toList()) {
            int rotation = (int) dir.getPositiveHorizontalDegrees()-180;
            BlockModelType modelType = switch (dir) {
                case NORTH -> NORTH_TRAPDOOR;
                case WEST -> WEST_TRAPDOOR;
                case SOUTH -> SOUTH_TRAPDOOR;
                default -> EAST_TRAPDOOR;
            };
            OPEN_TRAPDOORS.put(dir, requestBlock(modelType, getModel("open", 0, rotation)));

            modelType = switch (dir) {
                case NORTH -> NORTH_TRAPDOOR_WATERLOGGED;
                case WEST -> WEST_TRAPDOOR_WATERLOGGED;
                case SOUTH -> SOUTH_TRAPDOOR_WATERLOGGED;
                default -> EAST_TRAPDOOR_WATERLOGGED;
            };
            OPEN_TRAPDOORS_WATERLOGGED.put(dir, requestBlock(modelType, getModel("open", 0, rotation)));
        }

        for (int i = 0; i < 4; i++) {
            TOP_TRAPDOORS[i] = requestBlock(i < 3 ? TOP_TRAPDOOR : TOP_TRAPDOOR_WATERLOGGED, getModel("top", 0, i % 2 == 1 ? 90 : 0));
            BOTTOM_TRAPDOORS[i] = requestBlock(i < 3 ? BOTTOM_TRAPDOOR : BOTTOM_TRAPDOOR_WATERLOGGED, getModel("bottom", 0, i % 2 == 1 ? 90 : 0));
        }
    }

    public BasicPolymerTrapdoorBlock(String path, Settings settings) {
        this(Touhou.id(path), settings);
    }

    private PolymerBlockModel getModel(String type, int x, int y) {
        return PolymerBlockModel.of(Identifier.of(this.getIdentifier().getNamespace(), "block/%s_%s".formatted(this.getIdentifier().getPath(), type)), x, y);
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
        return Blocks.OAK_TRAPDOOR.getDefaultState();
    }
    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        boolean waterlogged = state.get(WATERLOGGED);

        if (state.get(Properties.OPEN)) {
            return state.get(Properties.WATERLOGGED) ? OPEN_TRAPDOORS_WATERLOGGED.get(state.get(FACING)) : OPEN_TRAPDOORS.get(state.get(FACING));
        } else {
            int modelIndex = state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH ? 0 : 1;
            if (waterlogged) modelIndex += 2;
            return state.get(Properties.BLOCK_HALF) == BlockHalf.TOP ? TOP_TRAPDOORS[modelIndex] : BOTTOM_TRAPDOORS[modelIndex];
        }
    }
}