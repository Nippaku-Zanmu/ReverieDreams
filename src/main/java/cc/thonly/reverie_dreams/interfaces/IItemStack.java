package cc.thonly.reverie_dreams.interfaces;

import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;

@SuppressWarnings("unchecked")
public interface IItemStack {
    public static <T> void setComponentSafe(ItemStack stack, ComponentType<T> key, Object value) {
        stack.set(key, (T) value);
    }
    boolean isFood();
}
