package cc.thonly.reverie_dreams.gui.recipe.display;

import cc.thonly.reverie_dreams.recipe.slot.ItemStackRecipeWrapper;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import net.minecraft.component.Component;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.MergedComponentMap;

import java.util.Iterator;

public interface DisplayView {
    default void init() {

    }

    @SuppressWarnings("unchecked")
    default GuiElementBuilder getGuiElementBuilder(ItemStackRecipeWrapper recipeSlot) {
        GuiElementBuilder guiElementBuilder = new GuiElementBuilder()
                .setItem(recipeSlot.getItem())
                .setCount(recipeSlot.getCount());
        MergedComponentMap components = recipeSlot.getItemStack().components;
        Iterator<Component<?>> iterator = components.stream().iterator();
        while (iterator.hasNext()) {
            Component<Object> next = (Component<Object>) iterator.next();
            guiElementBuilder.setComponent(next.type(), next.value());
        }
        return guiElementBuilder;
    }

    default String[][] getGrid() {
        return new String[][]{
                {"B", "X", "X", "X", "X", "X", "X", "X", "X"},
                {"X", "X", "X", "X", "X", "X", "X", "X", "X"},
                {"X", "X", "X", "X", "X", "X", "X", "X", "X"},
                {"X", "X", "X", "X", "X", "X", "X", "X", "X"},
                {"X", "X", "X", "X", "X", "X", "X", "X", "X"},
                {"X", "X", "X", "X", "X", "X", "X", "X", "X"},
        };
    }
}
