package cc.thonly.reverie_dreams.util;

import cc.thonly.reverie_dreams.registry.Key2ValueRegistry;
import cc.thonly.reverie_dreams.registry.Key2ValueRegistryManager;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.LinkedHashMap;
import java.util.Map;

public record ItemColor(Item item, Long color) {
    public static final Key2ValueRegistry<Item, ItemColor> REGISTRY_KEY = Key2ValueRegistryManager.ITEM_COLOR;
    public static final ItemColor WHITE = registerItemColor(Items.WHITE_DYE, 16777215L);
    public static final ItemColor ORANGE = registerItemColor(Items.ORANGE_DYE, 16749605L);
    public static final ItemColor MAGENTA = registerItemColor(Items.MAGENTA_DYE, 15082670L);
    public static final ItemColor LIGHT_BLUE = registerItemColor(Items.LIGHT_BLUE_DYE, 2067967L);
    public static final ItemColor YELLOW = registerItemColor(Items.YELLOW_DYE, 16776998L);
    public static final ItemColor LIME = registerItemColor(Items.LIME_DYE, 14352166L);
    public static final ItemColor PINK = registerItemColor(Items.PINK_DYE, 16724365L);
    public static final ItemColor GRAY = registerItemColor(Items.GRAY_DYE, 10197915L);
    public static final ItemColor LIGHT_GRAY = registerItemColor(Items.LIGHT_GRAY_DYE, 7434609L);
    public static final ItemColor CYAN = registerItemColor(Items.CYAN_DYE, 1099519L);
    public static final ItemColor PURPLE = registerItemColor(Items.PURPLE_DYE, 16721620L);
    public static final ItemColor BLUE = registerItemColor(Items.BLUE_DYE, 526591L);
    public static final ItemColor BROWN = registerItemColor(Items.BROWN_DYE, 10495744L);
    public static final ItemColor GREEN = registerItemColor(Items.GREEN_DYE, 3473204L);
    public static final ItemColor RED = registerItemColor(Items.RED_DYE, 16724273L);
    public static final ItemColor BLACK = registerItemColor(Items.BLACK_DYE, 2500134L);

    public ItemStack color(Item item) {
        ItemStack itemStack = item.getDefaultStack();
        return this.color(itemStack);
    }

    public ItemStack color(ItemStack itemStack) {
        itemStack.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(this.color().intValue()));
        return itemStack;
    }

    public static ItemColor registerItemColor(Item item, Long value) {
        return Key2ValueRegistryManager.register(REGISTRY_KEY, item, new ItemColor(item, value));
    }

    public static Map<Item, Long> getView() {
        Map<Item, Long> view = new LinkedHashMap<>();
        REGISTRY_KEY.keysStream().forEach(item -> {
            ItemColor color = REGISTRY_KEY.get(item);
            if (color != null) {
                view.put(item, color.color());
            }
        });
        return view;
    }
}
