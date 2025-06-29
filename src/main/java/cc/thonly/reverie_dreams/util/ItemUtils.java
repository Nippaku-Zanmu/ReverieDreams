package cc.thonly.reverie_dreams.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;

public class ItemUtils {
    public static boolean isArmorItem(ItemStack stack) {
        return stack.getOrDefault(DataComponentTypes.EQUIPPABLE, null) != null;
    }
}
