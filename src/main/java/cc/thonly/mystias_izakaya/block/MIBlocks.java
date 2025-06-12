package cc.thonly.mystias_izakaya.block;

import cc.thonly.mystias_izakaya.block.kitchenware.*;
import cc.thonly.mystias_izakaya.item.MIItems;
import cc.thonly.reverie_dreams.datagen.ModBlockTagProvider;
import cc.thonly.reverie_dreams.datagen.ModItemTagProvider;
import cc.thonly.reverie_dreams.item.BasicBlockItem;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public class MIBlocks {

    public static final Block COOKING_POT = registerBlock(new CookingPot("cooking_pot", AbstractBlock.Settings.create().strength(2.0f, 3.0f).sounds(BlockSoundGroup.STONE)));
    public static final Block CUTTING_BOARD = registerBlock(new CuttingBoard("cutting_board", AbstractBlock.Settings.create().strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)));
    public static final Block FRYING_PAN = registerBlock(new FryingPan("frying_pan", AbstractBlock.Settings.create().strength(2.0f, 3.0f).sounds(BlockSoundGroup.METAL)));
    public static final Block GRILL = registerBlock(new Grill("grill", AbstractBlock.Settings.create().strength(2.0f, 3.0f).sounds(BlockSoundGroup.METAL)));
    public static final Block STEAMER = registerBlock(new Steamer("steamer", AbstractBlock.Settings.create().strength(2.0f, 3.0f).sounds(BlockSoundGroup.STONE)));

    public static void registerBlocks() {

    }

    public static Block registerBlock(IdentifierGetter block) {
        block = (IdentifierGetter) Registry.register(Registries.BLOCK, block.getIdentifier(), (Block) block);
        Item blockItem = null;
        boolean isIgnored = block instanceof SignBlock || block instanceof WallSignBlock || block instanceof HangingSignBlock || block instanceof WallHangingSignBlock;
        if (!isIgnored) {
            blockItem = MIItems.registerItem(new BasicBlockItem(block.getIdentifier(), (Block) block, new Item.Settings()));
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
        return (Block) block;
    }

}
