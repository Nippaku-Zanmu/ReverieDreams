package cc.thonly.reverie_dreams.datagen;

import cc.thonly.reverie_dreams.block.FumoBlocks;
import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.data.ModTags;
import cc.thonly.reverie_dreams.item.ModItems;
import cc.thonly.reverie_dreams.item.base.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider<Item> {
    public static final Set<Item> FENCES = new HashSet<>();
    public static final Set<Item> FENCE_GATES = new HashSet<>();
    public static final Set<Item> STAIRS = new HashSet<>();
    public static final Set<Item> SLABS = new HashSet<>();
    public static final Set<Item> BUTTONS = new HashSet<>();
    public static final Set<Item> PRESSURE_PLATES = new HashSet<>();
    public static final Set<Item> TRAPDOORS = new HashSet<>();
    public static final Set<Item> DOORS = new HashSet<>();

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ITEM, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        FabricTagProvider<Item>.FabricTagBuilder empty = getOrCreateTagBuilder(ModTags.ItemTypeTag.EMPTY);
        empty.add(Items.BEDROCK);

        FabricTagProvider<Item>.FabricTagBuilder fumo = getOrCreateTagBuilder(ModTags.ItemTypeTag.FUMO);
        for (var item : FumoBlocks.getRegisteredFumoItems()) {
            fumo.add(item);
        }

        FabricTagProvider<Item>.FabricTagBuilder creeperDropMusicDisc = getOrCreateTagBuilder(ItemTags.CREEPER_DROP_MUSIC_DISCS);
        for (var item : ModItems.getRegisteredDiscItems()) {
            creeperDropMusicDisc.add(item);
        }

        FabricTagProvider<Item>.FabricTagBuilder swordItem = getOrCreateTagBuilder(ItemTags.SWORDS);
        BasicPolymerSwordItem.ITEMS.forEach(swordItem::add);
        FabricTagProvider<Item>.FabricTagBuilder pickaxeItem = getOrCreateTagBuilder(ItemTags.PICKAXES);
        BasicPolymerPickaxeItem.ITEMS.forEach(pickaxeItem::add);
        FabricTagProvider<Item>.FabricTagBuilder axeItem = getOrCreateTagBuilder(ItemTags.AXES);
        BasicPolymerAxeItem.ITEMS.forEach(axeItem::add);
        FabricTagProvider<Item>.FabricTagBuilder shovelItem = getOrCreateTagBuilder(ItemTags.SHOVELS);
        BasicPolymerShovelItem.ITEMS.forEach(shovelItem::add);
        FabricTagProvider<Item>.FabricTagBuilder hoeItem = getOrCreateTagBuilder(ItemTags.HOES);
        BasicPolymerHoeItem.ITEMS.forEach(hoeItem::add);
        FabricTagProvider<Item>.FabricTagBuilder headItem = getOrCreateTagBuilder(ItemTags.HEAD_ARMOR);
        BasicPolymerArmorItem.HEAD_ITEMS.forEach(headItem::add);
        FabricTagProvider<Item>.FabricTagBuilder chestItem = getOrCreateTagBuilder(ItemTags.CHEST_ARMOR);
        BasicPolymerArmorItem.CHEST_ITEMS.forEach(chestItem::add);
        FabricTagProvider<Item>.FabricTagBuilder legItem = getOrCreateTagBuilder(ItemTags.LEG_ARMOR);
        BasicPolymerArmorItem.LEG_ITEMS.forEach(legItem::add);
        FabricTagProvider<Item>.FabricTagBuilder feetItem = getOrCreateTagBuilder(ItemTags.FOOT_ARMOR);
        BasicPolymerArmorItem.FEET_ITEMS.forEach(feetItem::add);
        FabricTagProvider<Item>.FabricTagBuilder armorItem = getOrCreateTagBuilder(ModTags.ItemTypeTag.ARMOR);
        BasicPolymerArmorItem.ITEMS.forEach(armorItem::add);
        FabricTagProvider<Item>.FabricTagBuilder sliverToolMaterials = getOrCreateTagBuilder(ModTags.ItemTypeTag.SILVER_TOOL_MATERIALS);
        sliverToolMaterials.add(ModItems.SILVER_INGOT);
        FabricTagProvider<Item>.FabricTagBuilder magicIceToolMaterials = getOrCreateTagBuilder(ModTags.ItemTypeTag.MAGIC_ICE_TOOL_MATERIALS);
        magicIceToolMaterials.add(ModBlocks.MAGIC_ICE_BLOCK.asItem());

        FabricTagProvider<Item>.FabricTagBuilder fences = getOrCreateTagBuilder(ItemTags.FENCES);
        FabricTagProvider<Item>.FabricTagBuilder fenceGates = getOrCreateTagBuilder(ItemTags.FENCE_GATES);
        FabricTagProvider<Item>.FabricTagBuilder stairs = getOrCreateTagBuilder(ItemTags.STAIRS);
        FabricTagProvider<Item>.FabricTagBuilder slabs = getOrCreateTagBuilder(ItemTags.SLABS);
        FabricTagProvider<Item>.FabricTagBuilder buttons = getOrCreateTagBuilder(ItemTags.BUTTONS);
        FabricTagProvider<Item>.FabricTagBuilder trapdoors = getOrCreateTagBuilder(ItemTags.TRAPDOORS);
        FabricTagProvider<Item>.FabricTagBuilder doors = getOrCreateTagBuilder(ItemTags.DOORS);
        FENCES.forEach(fences::add);
        FENCE_GATES.forEach(fenceGates::add);
        STAIRS.forEach(stairs::add);
        SLABS.forEach(slabs::add);
        BUTTONS.forEach(buttons::add);
        TRAPDOORS.forEach(trapdoors::add);
        DOORS.forEach(doors::add);


    }

}
