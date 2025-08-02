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
import net.minecraft.data.tag.ProvidedTagBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public static final Set<Item> FENCES = new HashSet<>();
    public static final Set<Item> FENCE_GATES = new HashSet<>();
    public static final Set<Item> STAIRS = new HashSet<>();
    public static final Set<Item> SLABS = new HashSet<>();
    public static final Set<Item> BUTTONS = new HashSet<>();
    public static final Set<Item> PRESSURE_PLATES = new HashSet<>();
    public static final Set<Item> TRAPDOORS = new HashSet<>();
    public static final Set<Item> DOORS = new HashSet<>();

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        // === 基础工具方法 ===
        BiConsumer<TagKey<Item>, Collection<? extends Item>> addAll = (tag, items) -> valueLookupBuilder(tag).add(items.toArray(Item[]::new));

        // === 通用 Tag ===
        valueLookupBuilder(ModTags.ItemTypeTag.EMPTY).add(Items.BEDROCK);
        addAll.accept(ModTags.ItemTypeTag.FUMO, Fumos.getView().stream().map(Fumo::item).toList());
        addAll.accept(ItemTags.CREEPER_DROP_MUSIC_DISCS, ModItems.getDiscItemView());

        // === 工具类 Tag ===
        addAll.accept(ItemTags.SWORDS, BasicPolymerSwordItem.ITEMS);
        addAll.accept(ItemTags.PICKAXES, BasicPolymerPickaxeItem.ITEMS);
        addAll.accept(ItemTags.AXES, BasicPolymerAxeItem.ITEMS);
        addAll.accept(ItemTags.SHOVELS, BasicPolymerShovelItem.ITEMS);
        addAll.accept(ItemTags.HOES, BasicPolymerHoeItem.ITEMS);

        // === 盔甲类 Tag ===
        addAll.accept(ItemTags.HEAD_ARMOR, BasicPolymerArmorItem.HEAD_ITEMS);
        addAll.accept(ItemTags.CHEST_ARMOR, BasicPolymerArmorItem.CHEST_ITEMS);
        addAll.accept(ItemTags.LEG_ARMOR, BasicPolymerArmorItem.LEG_ITEMS);
        addAll.accept(ItemTags.FOOT_ARMOR, BasicPolymerArmorItem.FEET_ITEMS);
        addAll.accept(ModTags.ItemTypeTag.ARMOR, BasicPolymerArmorItem.ITEMS);

        // === 工具材料 ===
        valueLookupBuilder(ModTags.ItemTypeTag.SILVER_TOOL_MATERIALS).add(ModItems.SILVER_INGOT);
        valueLookupBuilder(ModTags.ItemTypeTag.MAGIC_ICE_TOOL_MATERIALS).add(ModBlocks.MAGIC_ICE_BLOCK.asItem());

        // === 自定义方块 ===
        valueLookupBuilder(ModTags.ItemTypeTag.ORB_BLOCK).add(
                ModBlocks.RED_ORB_BLOCK.asItem(),
                ModBlocks.YELLOW_ORB_BLOCK.asItem(),
                ModBlocks.BLUE_ORB_BLOCK.asItem(),
                ModBlocks.GREEN_ORB_BLOCK.asItem(),
                ModBlocks.PURPLE_ORB_BLOCK.asItem()
        );
        valueLookupBuilder(ModTags.ItemTypeTag.POWER_BLOCK).add(ModBlocks.POWER_BLOCK.asItem());
        valueLookupBuilder(ModTags.ItemTypeTag.POINT_BLOCK).add(ModBlocks.POINT_BLOCK.asItem());
        valueLookupBuilder(ModTags.ItemTypeTag.SILVER_BLOCK).add(ModBlocks.SILVER_BLOCK.asItem());
        valueLookupBuilder(ModTags.ItemTypeTag.VAISRAVANAS_PAGODA).add(Items.BLAZE_POWDER);

        // === 兼容物品 ===
        valueLookupBuilder(ConventionalItemTags.FOODS).add(MIItems.FOOD_ITEMS);
        valueLookupBuilder(ModTags.ItemTypeTag.PEACH).add(MIItems.PEACH);

        // === 方块物品分类 ===
        Map<TagKey<Item>, Collection<? extends ItemConvertible>> blockItemGroups = Map.of(
                ItemTags.FENCES, FENCES,
                ItemTags.FENCE_GATES, FENCE_GATES,
                ItemTags.STAIRS, STAIRS,
                ItemTags.SLABS, SLABS,
                ItemTags.BUTTONS, BUTTONS,
                ItemTags.TRAPDOORS, TRAPDOORS,
                ItemTags.DOORS, DOORS
        );
        blockItemGroups.forEach((tag, list) -> {
            ProvidedTagBuilder<Item, Item> builder = valueLookupBuilder(tag);
            list.forEach(item -> builder.add(item.asItem()));
        });

        // === 种子 ===
        ProvidedTagBuilder<Item, Item> seeds = valueLookupBuilder(ConventionalItemTags.SEEDS);
        ProvidedTagBuilder<Item, Item> villagerPlantableSeeds = valueLookupBuilder(ItemTags.VILLAGER_PLANTABLE_SEEDS);
        for (var entry : PolymerCropCreator.getViews()) {
            Item seed = entry.getValue().getSeed();
            villagerPlantableSeeds.add(seed);
            seeds.add(seed);
        }

        // === 模组兼容扩展 ===
        this.configureCompat(wrapperLookup);
    }


    protected void configureCompat(RegistryWrapper.WrapperLookup wrapperLookup) {
        // Farmer'delight
        ProvidedTagBuilder<Item, Item> onion = valueLookupCommon("crops/onion");
        ProvidedTagBuilder<Item, Item> tomatoCrop = valueLookupCommon("crops/tomato");
        ProvidedTagBuilder<Item, Item> cabbage = valueLookupCommon("crops/cabbage");
        ProvidedTagBuilder<Item, Item> rawSalmon = valueLookupCommon("foods/raw_salmon");
        ProvidedTagBuilder<Item, Item> rawFish = valueLookupCommon("foods/raw_fish");
        ProvidedTagBuilder<Item, Item> tomatoFood = valueLookupCommon("foods/tomato");

        onion.add(MIItems.ONION);
        tomatoCrop.add(MIItems.TOMATO);
        rawSalmon.add(MIItems.SALMON);
        rawFish.add(MIItems.SALMON, MIItems.HAGFISH, MIItems.TUNA, MIItems.SUPREME_TUNA);
        tomatoFood.add(MIItems.TOMATO);

        ProvidedTagBuilder<Item, Item> meals = valueLookupFarmerDelight("meals");
        meals.add(
                MIItems.BLACK_PORK,
                MIItems.VENISON,
                MIItems.WAGYU_BEEF,
                MIItems.WILD_BOAR_MEAT
        );
    }

    private ProvidedTagBuilder<Item, Item> valueLookupFarmerDelight(String name) {
        return valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of("farmersdelight",name)));
    }

    private ProvidedTagBuilder<Item, Item> valueLookupCommon(String name) {
        return valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of("c",name)));
    }

}
