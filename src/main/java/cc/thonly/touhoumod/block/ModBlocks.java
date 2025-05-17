package cc.thonly.touhoumod.block;

import cc.thonly.touhoumod.block.base.BasicPolymerBlock;
import cc.thonly.touhoumod.block.base.BasicPolymerCopyedBlock;
import cc.thonly.touhoumod.item.BasicBlockItem;
import cc.thonly.touhoumod.item.BasicCopyedBlockItem;
import cc.thonly.touhoumod.item.ModItems;
import cc.thonly.touhoumod.util.IdentifierGetter;
import eu.pb4.polymer.blocks.api.BlockModelType;
import lombok.Getter;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ModBlocks {
    private static final List<Block> BLOCK_LIST = new ArrayList<>();
    public static final Block DANMAKU_CRAFTING_TABLE = registerBlock(new DanmakuCraftingTableBlock("danmaku_crafting_table", AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE)));
    public static final Block STRENGTH_TABLE = registerBlock(new StrengthenTableBlock("strength_table", AbstractBlock.Settings.copy(Blocks.SMITHING_TABLE)));
    public static final Block GENSOKYO_ALTAR = registerBlock(new GensokyoAltarBlock("gensokyo_altar", AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE).luminance((state) -> 7)));

    public static final Block SPIRITUAL_LOG = registerBlock(new BasicPillarBlock("spiritual_log", AbstractBlock.Settings.copy(Blocks.OAK_LOG).nonOpaque()));
    public static final Block SPIRITUAL_WOOD = registerBlock(new BasicPillarBlock("spiritual_wood", AbstractBlock.Settings.copy(Blocks.OAK_LOG).nonOpaque()));
    public static final Block STRIPPED_SPIRITUAL_LOG = registerBlock(new BasicPillarBlock("stripped_spiritual_log", AbstractBlock.Settings.copy(Blocks.OAK_LOG).nonOpaque()));
    public static final Block STRIPPED_SPIRITUAL_WOOD = registerBlock(new BasicPillarBlock("stripped_spiritual_wood", AbstractBlock.Settings.copy(Blocks.OAK_WOOD).nonOpaque()));
    public static final Block SPIRITUAL_PLANKS = registerBlock(new BasicPolymerBlock("spiritual_planks", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
    public static final Block MAGIC_ICE_BLOCK = registerCopyBlock(new MagicIceBlock("magic_ice", Blocks.ICE, AbstractBlock.Settings.copy(Blocks.BLUE_ICE)));
    public static final Block POINT_BLOCK = registerBlock(new BasicPolymerBlock("point_block", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.STONE)));
    public static final Block POWER_BLOCK = registerBlock(new BasicPolymerBlock("power_block", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.STONE)));

    public static final Block DREAM_RED_BLOCK = registerBlock(new BasicPolymerBlock("dream_world_red_line_block", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.BEDROCK)));
    public static final Block DREAM_BLUE_BLOCK = registerBlock(new BasicPolymerBlock("dream_world_blue_line_block", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.BEDROCK)));

    public static final Map<Block, Block> SPIRITUAL_BLOCKS = new HashMap<>();

    public static void registerBlocks() {
//        SPIRITUAL_BLOCKS.put(Blocks.STRIPPED_OAK_LOG, ModBlocks.SPIRITUAL_OAK_LOG);
        StrippableBlockRegistry.register(SPIRITUAL_LOG, STRIPPED_SPIRITUAL_LOG);
    }

    public static Block registerBlock(IdentifierGetter block) {
        Registry.register(Registries.BLOCK, block.getIdentifier(), (Block) block);
        ModItems.registerItem(new BasicBlockItem(block.getIdentifier(), (Block) block, new Item.Settings()));
        BLOCK_LIST.add((Block) block);
        return (Block) block;
    }

    public static Block registerCopyBlock(IdentifierGetter block) {
        Registry.register(Registries.BLOCK, block.getIdentifier(), (Block) block);
        ModItems.registerItem(new BasicCopyedBlockItem(block.getIdentifier(), (Block) block, ((BasicPolymerCopyedBlock) block).getTargetblock().asItem(), new Item.Settings()));
        BLOCK_LIST.add((Block) block);
        return (Block) block;
    }
}
