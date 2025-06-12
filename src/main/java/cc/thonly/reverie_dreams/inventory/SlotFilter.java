package cc.thonly.reverie_dreams.inventory;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface SlotFilter {
    boolean canInsert(ItemStack stack);
}