package cc.thonly.reverie_dreams.gui.recipe.display;

import cc.thonly.reverie_dreams.recipe.slot.CountRecipeSlot;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import net.minecraft.component.Component;
import net.minecraft.component.ComponentMap;

import java.util.Iterator;

public interface DisplayView {
    default void init() {

    }

    @SuppressWarnings("unchecked")
    default GuiElementBuilder getGuiElementBuilder(CountRecipeSlot recipeSlot) {
        GuiElementBuilder guiElementBuilder = new GuiElementBuilder()
                .setItem(recipeSlot.getItem())
                .setCount(recipeSlot.getCount());
        ComponentMap.Builder components = recipeSlot.getItemSettings().getComponents();
        ComponentMap buildComponents = components.build();
        Iterator<Component<?>> iterator = buildComponents.stream().iterator();
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
