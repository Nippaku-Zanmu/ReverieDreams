package cc.thonly.reverie_dreams.interfaces;

import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;

public interface IItemStack {
    @SuppressWarnings("unchecked")
    public static <T> void setComponentSafe(ItemStack stack, ComponentType<T> key, Object value) {
        stack.set(key, (T) value);
    }
    boolean isFood();
}
