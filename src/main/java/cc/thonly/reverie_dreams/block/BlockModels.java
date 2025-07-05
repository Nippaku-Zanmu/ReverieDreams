package cc.thonly.reverie_dreams.block;

import cc.thonly.mystias_izakaya.item.MIItems;
import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.block.base.EmptyBlock;
import cc.thonly.reverie_dreams.block.crop.TransparentFlatTripWire;
import cc.thonly.reverie_dreams.block.crop.TransparentPlant;
import cc.thonly.reverie_dreams.block.crop.TransparentPlantWatterlogged;
import cc.thonly.reverie_dreams.block.crop.TransparentTripWire;
import cc.thonly.reverie_dreams.item.BasicBlockItem;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BlockModels {
    public static final Block EMPTY_TRANSPARENT_TRIPWIRE = registerEmpty(new EmptyBlock(Touhou.id("empty_transparent_tripwire"), TransparentTripWire.TRANSPARENT_TRIPWIRE, AbstractBlock.Settings.create()));
    public static final Block EMPTY_TRIPWIRE_FLAT = registerEmpty(new EmptyBlock(Touhou.id("empty_tripwire_flat"), TransparentFlatTripWire.TRANSPARENT_FLAT_TRIPIWIRE, AbstractBlock.Settings.create()));
    public static final Block EMPTY_TRANSPARENT_PLANT_WATERLOGGED = registerEmpty(new EmptyBlock(Touhou.id("empty_transparent_plant_waterlogged"), TransparentPlantWatterlogged.TRANSPARENT_WATTERLOGGED, AbstractBlock.Settings.create()));
    public static final Block EMPTY_TRANSPARENT_PLANT = registerEmpty(new EmptyBlock(Touhou.id("empty_transparent_plant"), TransparentPlant.TRANSPARENT, AbstractBlock.Settings.create()));

    public static void registerModels() {
    }

    public static Block registerEmpty(IdentifierGetter block) {
        block = (IdentifierGetter) Registry.register(Registries.BLOCK, block.getIdentifier(), (Block) block);
        Item blockItem = MIItems.registerItem(new BasicBlockItem(block.getIdentifier(), (Block) block, new Item.Settings()));;
        return (Block) block;
    }
}
