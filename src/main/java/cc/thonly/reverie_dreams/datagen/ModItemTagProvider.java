package cc.thonly.reverie_dreams.datagen;

import cc.thonly.mystias_izakaya.item.MIItems;
import cc.thonly.reverie_dreams.fumo.Fumo;
import cc.thonly.reverie_dreams.fumo.Fumos;
import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.data.ModTags;
import cc.thonly.reverie_dreams.item.ModItems;
import cc.thonly.reverie_dreams.item.base.*;
import cc.thonly.reverie_dreams.util.PolymerCropCreator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Map;
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

        for (Fumo instance : Fumos.getView()) {
            fumo.add(instance.item());
        }

        FabricTagProvider<Item>.FabricTagBuilder creeperDropMusicDisc = getOrCreateTagBuilder(ItemTags.CREEPER_DROP_MUSIC_DISCS);
        for (var item : ModItems.getDiscItemView()) {
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
        FabricTagProvider<Item>.FabricTagBuilder orbBlocks = getOrCreateTagBuilder(ModTags.ItemTypeTag.ORB_BLOCK);
        orbBlocks.add(
                ModBlocks.RED_ORB_BLOCK.asItem(),
                ModBlocks.YELLOW_ORB_BLOCK.asItem(),
                ModBlocks.BLUE_ORB_BLOCK.asItem(),
                ModBlocks.GREEN_ORB_BLOCK.asItem(),
                ModBlocks.PURPLE_ORB_BLOCK.asItem()
        );
        FabricTagProvider<Item>.FabricTagBuilder powerBlocks = getOrCreateTagBuilder(ModTags.ItemTypeTag.POWER_BLOCK);
        FabricTagProvider<Item>.FabricTagBuilder pointBlocks = getOrCreateTagBuilder(ModTags.ItemTypeTag.POINT_BLOCK);
        FabricTagProvider<Item>.FabricTagBuilder silverBlocks = getOrCreateTagBuilder(ModTags.ItemTypeTag.SILVER_BLOCK);
        powerBlocks.add(ModBlocks.POWER_BLOCK.asItem());
        pointBlocks.add(ModBlocks.POINT_BLOCK.asItem());
        silverBlocks.add(ModBlocks.SILVER_BLOCK.asItem());

        FabricTagProvider<Item>.FabricTagBuilder peach = getOrCreateTagBuilder(ModTags.ItemTypeTag.PEACH);
        peach.add(MIItems.PEACH);

        FabricTagProvider<Item>.FabricTagBuilder fences = getOrCreateTagBuilder(ItemTags.FENCES).setReplace(false);
        FabricTagProvider<Item>.FabricTagBuilder fenceGates = getOrCreateTagBuilder(ItemTags.FENCE_GATES).setReplace(false);
        FabricTagProvider<Item>.FabricTagBuilder stairs = getOrCreateTagBuilder(ItemTags.STAIRS).setReplace(false);
        FabricTagProvider<Item>.FabricTagBuilder slabs = getOrCreateTagBuilder(ItemTags.SLABS).setReplace(false);
        FabricTagProvider<Item>.FabricTagBuilder buttons = getOrCreateTagBuilder(ItemTags.BUTTONS).setReplace(false);
        FabricTagProvider<Item>.FabricTagBuilder trapdoors = getOrCreateTagBuilder(ItemTags.TRAPDOORS).setReplace(false);
        FabricTagProvider<Item>.FabricTagBuilder doors = getOrCreateTagBuilder(ItemTags.DOORS).setReplace(false);
        FENCES.forEach(fences::add);
        FENCE_GATES.forEach(fenceGates::add);
        STAIRS.forEach(stairs::add);
        SLABS.forEach(slabs::add);
        BUTTONS.forEach(buttons::add);
        TRAPDOORS.forEach(trapdoors::add);
        DOORS.forEach(doors::add);

        FabricTagProvider<Item>.FabricTagBuilder seeds = getOrCreateTagBuilder(ConventionalItemTags.SEEDS);
        Set<Map.Entry<Identifier, PolymerCropCreator.Instance>> views = PolymerCropCreator.getViews();
        for (Map.Entry<Identifier, PolymerCropCreator.Instance> view : views) {
            PolymerCropCreator.Instance instance = view.getValue();
            seeds.add(instance.getSeed());
        }
    }

}
