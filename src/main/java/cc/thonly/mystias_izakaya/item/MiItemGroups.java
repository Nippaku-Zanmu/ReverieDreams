package cc.thonly.mystias_izakaya.item;

import cc.thonly.mystias_izakaya.MystiasIzakaya;
import cc.thonly.mystias_izakaya.block.MIBlocks;
import cc.thonly.reverie_dreams.util.PolymerCropCreator;
import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Map;

public class MiItemGroups {
    public static final RegistryKey<ItemGroup> KITCHENWARE_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), MystiasIzakaya.id("kitchenware_item_group"));
    public static final RegistryKey<ItemGroup> INGREDIENT_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), MystiasIzakaya.id("ingredients_item_group"));
    public static final RegistryKey<ItemGroup> SEEDS_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), MystiasIzakaya.id("seeds_item_group"));
    public static final RegistryKey<ItemGroup> FOOD_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), MystiasIzakaya.id("food_item_group"));
    public static final ItemGroup KITCHENWARE_ITEM_GROUP = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(MIBlocks.COOKING_POT))
            .displayName(Text.translatable("item_group.kitchenware_item_group"))
            .build();
    public static final ItemGroup INGREDIENT_ITEM_GROUP = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(MIItems.BLACK_PORK))
            .displayName(Text.translatable("item_group.ingredients_item_group"))
            .build();
    public static final ItemGroup SEEDS_ITEM_GROUP = PolymerItemGroupUtils.builder()
            .icon(MiItemGroups::getSeedItemIcon)
            .displayName(Text.translatable("item_group.seed_item_group"))
            .build();
    public static final ItemGroup FOOD_ITEM_GROUP = PolymerItemGroupUtils.builder()
            .icon(MiItemGroups::getFoodItemIcon)
            .displayName(Text.translatable("item_group.food_item_group"))
            .build();

    public static void registerItemGroups() {
        PolymerItemGroupUtils.registerPolymerItemGroup(KITCHENWARE_ITEM_GROUP_KEY, KITCHENWARE_ITEM_GROUP);
        PolymerItemGroupUtils.registerPolymerItemGroup(INGREDIENT_ITEM_GROUP_KEY, INGREDIENT_ITEM_GROUP);
        PolymerItemGroupUtils.registerPolymerItemGroup(SEEDS_ITEM_GROUP_KEY, SEEDS_ITEM_GROUP);
        PolymerItemGroupUtils.registerPolymerItemGroup(FOOD_ITEM_GROUP_KEY, FOOD_ITEM_GROUP);
        ItemGroupEvents.modifyEntriesEvent(KITCHENWARE_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(MIBlocks.COOKING_POT);
            itemGroup.add(MIBlocks.CUTTING_BOARD);
            itemGroup.add(MIBlocks.FRYING_PAN);
            itemGroup.add(MIBlocks.GRILL);
            itemGroup.add(MIBlocks.STEAMER);
            itemGroup.add(MIBlocks.COOKTOP);
        });
        ItemGroupEvents.modifyEntriesEvent(INGREDIENT_ITEM_GROUP_KEY).register(itemGroup -> {
            for (Item item : MIItems.INGREDIENTS) {
                itemGroup.add(item);
            }
        });
        ItemGroupEvents.modifyEntriesEvent(SEEDS_ITEM_GROUP_KEY).register(itemGroup -> {
            for (Map.Entry<Identifier, PolymerCropCreator.Instance> view : PolymerCropCreator.getViews()) {
                PolymerCropCreator.Instance instance = view.getValue();
                Item seed = instance.getSeed();
                itemGroup.add(seed);
            }
        });
        ItemGroupEvents.modifyEntriesEvent(FOOD_ITEM_GROUP_KEY).register(itemGroup -> {
            for (Item item : MIItems.FOOD_ITEMS) {
                itemGroup.add(item);
            }
        });
    }

    public static ItemStack getSeedItemIcon() {
        for (Map.Entry<Identifier, PolymerCropCreator.Instance> view : PolymerCropCreator.getViews()) {
            PolymerCropCreator.Instance instance = view.getValue();
            return new ItemStack(instance.getSeed());
        }
        return new ItemStack(Items.WHEAT_SEEDS);
    }

    public static ItemStack getFoodItemIcon() {
        for (Item foodItem : MIItems.FOOD_ITEMS) {
            return new ItemStack(foodItem);
        }
        return new ItemStack(Items.COOKED_BEEF);
    }
}
