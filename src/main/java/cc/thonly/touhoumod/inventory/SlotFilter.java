package cc.thonly.touhoumod.inventory;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface SlotFilter {
    boolean canInsert(ItemStack stack);
}