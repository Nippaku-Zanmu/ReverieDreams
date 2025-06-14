package cc.thonly.mystias_izakaya.item;

import cc.thonly.mystias_izakaya.MystiasIzakaya;
import cc.thonly.mystias_izakaya.block.MIBlocks;
import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;

public class MiItemGroups {
    public static final RegistryKey<ItemGroup> KITCHENWARE_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), MystiasIzakaya.id("kitchenware_item_group"));
    public static final RegistryKey<ItemGroup> INGREDIENT_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), MystiasIzakaya.id("ingredients_item_group"));
    public static final RegistryKey<ItemGroup> FOOD_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), MystiasIzakaya.id("food_item_group"));
    public static final ItemGroup KITCHENWARE_ITEM_GROUP = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(MIBlocks.COOKING_POT))
            .displayName(Text.translatable("item_group.kitchenware_item_group"))
            .build();
    public static final ItemGroup INGREDIENT_ITEM_GROUP = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(MIItems.BLACK_PORK))
            .displayName(Text.translatable("item_group.Ingredients_item_group"))
            .build();
    public static final ItemGroup FOOD_ITEM_GROUP = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(Items.COOKED_BEEF))
            .displayName(Text.translatable("item_group.food_item_group"))
            .build();

    public static void registerItemGroups() {
        PolymerItemGroupUtils.registerPolymerItemGroup(KITCHENWARE_ITEM_GROUP_KEY, KITCHENWARE_ITEM_GROUP);
        PolymerItemGroupUtils.registerPolymerItemGroup(INGREDIENT_ITEM_GROUP_KEY, INGREDIENT_ITEM_GROUP);
        PolymerItemGroupUtils.registerPolymerItemGroup(FOOD_ITEM_GROUP_KEY, FOOD_ITEM_GROUP);
        ItemGroupEvents.modifyEntriesEvent(KITCHENWARE_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(MIBlocks.COOKING_POT);
            itemGroup.add(MIBlocks.CUTTING_BOARD);
            itemGroup.add(MIBlocks.FRYING_PAN);
            itemGroup.add(MIBlocks.GRILL);
            itemGroup.add(MIBlocks.STEAMER);
        });
        ItemGroupEvents.modifyEntriesEvent(INGREDIENT_ITEM_GROUP_KEY).register(itemGroup -> {
            for (Item item : MIItems.INGREDIENTS) {
                itemGroup.add(item);
            }
        });
    }
}
