package cc.thonly.reverie_dreams.block;

import cc.thonly.reverie_dreams.block.base.*;
import cc.thonly.reverie_dreams.datagen.ModBlockTagProvider;
import cc.thonly.reverie_dreams.datagen.ModItemTagProvider;
import cc.thonly.reverie_dreams.item.BasicBlockItem;
import cc.thonly.reverie_dreams.item.BasicCopyedBlockItem;
import cc.thonly.reverie_dreams.item.ModItems;
import cc.thonly.reverie_dreams.item.base.BasicPolymerHangingSignItem;
import cc.thonly.reverie_dreams.item.base.BasicPolymerSignItem;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import eu.pb4.polymer.blocks.api.BlockModelType;
import lombok.Getter;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.Vec3d;

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
    public static final Block MUSIC_BLOCK = registerBlock(new MusicBlock("music_block", AbstractBlock.Settings.copy(Blocks.NOTE_BLOCK)));

    public static final Block SPIRITUAL_LOG = registerBlock(new BasicPillarBlock("spiritual_log", AbstractBlock.Settings.copy(Blocks.OAK_LOG).nonOpaque()));
    public static final Block SPIRITUAL_WOOD = registerBlock(new BasicPillarBlock("spiritual_wood", AbstractBlock.Settings.copy(Blocks.OAK_LOG).nonOpaque()));
    public static final Block STRIPPED_SPIRITUAL_LOG = registerBlock(new BasicPillarBlock("stripped_spiritual_log", AbstractBlock.Settings.copy(Blocks.OAK_LOG).nonOpaque()));
    public static final Block STRIPPED_SPIRITUAL_WOOD = registerBlock(new BasicPillarBlock("stripped_spiritual_wood", AbstractBlock.Settings.copy(Blocks.OAK_WOOD).nonOpaque()));
    public static final Block SPIRITUAL_PLANKS = registerBlock(new BasicPolymerBlock("spiritual_planks", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
    public static final Block SPIRITUAL_STAIR = registerBlock(new BasicPolymerStairsBlock("spiritual_stair", SPIRITUAL_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(SPIRITUAL_PLANKS)));
    public static final Block SPIRITUAL_SLAB = registerBlock(new BasicPolymerSlabBlock("spiritual_slab", SPIRITUAL_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(SPIRITUAL_PLANKS)));
    public static final Block SPIRITUAL_DOOR = registerBlock(new BasicPolymerDoorBlock("spiritual_door", AbstractBlock.Settings.copy(SPIRITUAL_PLANKS)));
    public static final Block SPIRITUAL_TRAPDOOR = registerBlock(new BasicPolymerTrapdoorBlock("spiritual_trapdoor", AbstractBlock.Settings.copy(SPIRITUAL_PLANKS)));
    public static final Block SPIRITUAL_FENCE = registerBlock(new BasicPolymerFenceBlock("spiritual_fence", AbstractBlock.Settings.copy(SPIRITUAL_PLANKS)));
    public static final Block SPIRITUAL_FENCE_GATE = registerBlock(new BasicPolymerFenceGateBlock("spiritual_fence_gate", WoodType.OAK, AbstractBlock.Settings.copy(SPIRITUAL_PLANKS)));
    public static final Block SPIRITUAL_BUTTON = registerBlock(new BasicPolymerButtonBlock("spiritual_button", BlockSetType.OAK, 30, AbstractBlock.Settings.copy(SPIRITUAL_PLANKS)));
//    public static final Block SPIRITUAL_SIGN = registerBlock(new BasicPolymerSignBlock("spiritual_sign", ModWoodTypes.SPIRITUAL_WOOD_TYPE, AbstractBlock.Settings.copy(SPIRITUAL_PLANKS)));
//    public static final Block SPIRITUAL_WALL_SIGN = registerBlock(new BasicPolymerWallSignBlock("spiritual_wall_sign", ModWoodTypes.SPIRITUAL_WOOD_TYPE, AbstractBlock.Settings.copy(SPIRITUAL_PLANKS)));
//    public static final SignBlockGroup SPIRITUAL_SIGN_GROUP = registerSignBlock(SPIRITUAL_SIGN, SPIRITUAL_WALL_SIGN);
//    public static final Block SPIRITUAL_HANGING_SIGN = registerBlock(new BasicPolymerHangingSignBlock("spiritual_hanging_sign", ModWoodTypes.SPIRITUAL_WOOD_TYPE, AbstractBlock.Settings.copy(SPIRITUAL_PLANKS)));
//    public static final Block WALL_SPIRITUAL_HANGING_SIGN = registerBlock(new BasicPolymerWallHangingSignBlock("spiritual_wall_hanging_sign", ModWoodTypes.SPIRITUAL_WOOD_TYPE, AbstractBlock.Settings.copy(SPIRITUAL_PLANKS)));
//    public static final HangingSignBlockGroup SPIRITUAL_HANGING_SIGN_GROUP = registerHangSignBlock(SPIRITUAL_HANGING_SIGN, WALL_SPIRITUAL_HANGING_SIGN);
    public static final Block MAGIC_ICE_BLOCK = registerCopyBlock(new MagicIceBlock("magic_ice", Blocks.ICE, AbstractBlock.Settings.copy(Blocks.BLUE_ICE)));
    public static final Block POINT_BLOCK = registerBlock(new BasicPolymerBlock("point_block", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.STONE)));
    public static final Block POWER_BLOCK = registerBlock(new BasicPolymerBlock("power_block", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.STONE)));
    public static final Block SILVER_ORE = registerBlock(new BasicPolymerBlock("silver_ore", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.IRON_ORE)));
    public static final Block DEEPSLATE_SILVER_ORE = registerBlock(new BasicPolymerBlock("deepslate_silver_ore", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.DEEPSLATE_IRON_ORE)));
    public static final Block SILVER_BLOCK = registerBlock(new BasicPolymerBlock("silver_block", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
    public static final Block ORB_ORE = registerBlock(new BasicPolymerBlock("orb_ore", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.DEEPSLATE_IRON_ORE)));
    public static final Block DEEPSLATE_ORB_ORE = registerBlock(new BasicPolymerBlock("deepslate_orb_ore", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.DEEPSLATE_IRON_ORE)));
    public static final Block RED_ORB_BLOCK = registerBlock(new BasicPolymerBlock("red_orb_block", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.EMERALD_BLOCK)));
    public static final Block YELLOW_ORB_BLOCK = registerBlock(new BasicPolymerBlock("yellow_orb_block", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.EMERALD_BLOCK)));
    public static final Block BLUE_ORB_BLOCK = registerBlock(new BasicPolymerBlock("blue_orb_block", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.EMERALD_BLOCK)));
    public static final Block GREEN_ORB_BLOCK = registerBlock(new BasicPolymerBlock("green_orb_block", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.EMERALD_BLOCK)));
    public static final Block PURPLE_ORB_BLOCK = registerBlock(new BasicPolymerBlock("purple_orb_block", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.EMERALD_BLOCK)));

    public static final Block DREAM_RED_BLOCK = registerBlock(new BasicPolymerBlock("dream_world_red_line_block", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.BEDROCK)));
    public static final Block DREAM_BLUE_BLOCK = registerBlock(new BasicPolymerBlock("dream_world_blue_line_block", BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.BEDROCK)));
    public static final Block MARISA_HAT_BLOCK = registerBlock(new MarisaHatBlock("marisa_hat", new Vec3d(0,0,0), AbstractBlock.Settings.copy(Blocks.WHITE_WOOL)), new Item.Settings().maxCount(1).component(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(EquipmentSlot.HEAD).swappable(false).build()));

    public static final Map<Block, Block> SPIRITUAL_BLOCKS = new HashMap<>();

    public static void registerBlocks() {
        BlockModels.registerModels();
//        SPIRITUAL_BLOCKS.put(BlockTypeTag.STRIPPED_OAK_LOG, ModBlocks.SPIRITUAL_OAK_LOG);
        StrippableBlockRegistry.register(SPIRITUAL_LOG, STRIPPED_SPIRITUAL_LOG);
    }
    public static Block registerBlock(IdentifierGetter block) {
        return registerBlock(block, new Item.Settings());
    }

    public static Block registerBlock(IdentifierGetter block, Item.Settings settings) {
        block = (IdentifierGetter) Registry.register(Registries.BLOCK, block.getIdentifier(), (Block) block);
        Item blockItem = null;
        boolean isIgnored = block instanceof SignBlock || block instanceof WallSignBlock || block instanceof HangingSignBlock || block instanceof WallHangingSignBlock;
        if (!isIgnored) {
            blockItem = ModItems.registerItem(new BasicBlockItem(block.getIdentifier(), (Block) block, settings));
        }
        Block blk = (Block) block;
        if (blk instanceof FenceBlock) {
            ModBlockTagProvider.FENCES.add(blk);
            ModItemTagProvider.FENCES.add(blk.asItem());
        }
        if (blk instanceof FenceGateBlock) {
            ModBlockTagProvider.FENCE_GATES.add(blk);
            ModItemTagProvider.FENCE_GATES.add(blk.asItem());
        }
        if (blk instanceof StairsBlock) {
            ModBlockTagProvider.STAIRS.add(blk);
            ModItemTagProvider.STAIRS.add(blk.asItem());
        }
        if (blk instanceof SlabBlock) {
            ModBlockTagProvider.SLABS.add(blk);
            ModItemTagProvider.SLABS.add(blk.asItem());
        }
        if (blk instanceof ButtonBlock) {
            ModBlockTagProvider.BUTTONS.add(blk);
            ModItemTagProvider.BUTTONS.add(blk.asItem());
        }
        if (blk instanceof PressurePlateBlock) {
            ModBlockTagProvider.PRESSURE_PLATES.add(blk);
            ModItemTagProvider.PRESSURE_PLATES.add(blk.asItem());
        }
        if (blk instanceof TrapdoorBlock) {
            ModBlockTagProvider.TRAPDOORS.add(blk);
            ModItemTagProvider.TRAPDOORS.add(blk.asItem());
        }
        if (blk instanceof DoorBlock) {
            ModBlockTagProvider.DOORS.add(blk);
            ModItemTagProvider.DOORS.add(blk.asItem());
        }
        BLOCK_LIST.add((Block) block);
        return (Block) block;
    }


    public static Block registerSimpleBlock(IdentifierGetter block) {
        block = (IdentifierGetter) Registry.register(Registries.BLOCK, block.getIdentifier(), (Block) block);
        return (Block) block;
    }

    public static SignBlockGroup registerSignBlock(Block standingBlock, Block wallBlock) {
        return new SignBlockGroup(standingBlock, wallBlock, ModItems.registerItem(new BasicPolymerSignItem(((IdentifierGetter) standingBlock).getIdentifier(), standingBlock, wallBlock, new Item.Settings().useBlockPrefixedTranslationKey().maxCount(16))));
    }

    public static HangingSignBlockGroup registerHangSignBlock(Block standingBlock, Block wallBlock) {
        return new HangingSignBlockGroup(standingBlock, wallBlock, ModItems.registerItem(new BasicPolymerHangingSignItem(((IdentifierGetter) standingBlock).getIdentifier(), standingBlock, wallBlock, new Item.Settings().useBlockPrefixedTranslationKey().maxCount(16))));
    }

    public static Block registerCopyBlock(IdentifierGetter block) {
        Registry.register(Registries.BLOCK, block.getIdentifier(), (Block) block);
        ModItems.registerItem(new BasicCopyedBlockItem(block.getIdentifier(), (Block) block, ((BasicPolymerCopyedBlock) block).getTargetblock().asItem(), new Item.Settings()));
        BLOCK_LIST.add((Block) block);
        return (Block) block;
    }
}
