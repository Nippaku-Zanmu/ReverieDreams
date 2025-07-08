package cc.thonly.reverie_dreams.gui.recipe.display;

import cc.thonly.reverie_dreams.gui.access.GuiElementBuilderAccessor;
import cc.thonly.reverie_dreams.gui.recipe.GuiOpeningPrevCallback;
import cc.thonly.reverie_dreams.recipe.view.RecipeEntryWrapper;
import cc.thonly.reverie_dreams.recipe.ItemStackRecipeWrapper;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.component.Component;
import net.minecraft.component.MergedComponentMap;
import net.minecraft.server.network.ServerPlayerEntity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

@SuppressWarnings("unchecked")
public interface DisplayView {
    default void init() {

    }

    public static SimpleGui create(Class<? extends SimpleGui> clazz, ServerPlayerEntity player, RecipeEntryWrapper<?> key2ValueEntry, GuiOpeningPrevCallback prevGuiCallback) {
        try {
            Constructor<?> constructor = clazz.getConstructor(ServerPlayerEntity.class, RecipeEntryWrapper.class, GuiOpeningPrevCallback.class);
            return (SimpleGui) constructor.newInstance(player, key2ValueEntry, prevGuiCallback);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }


    default GuiElementBuilder getGuiElementBuilder(ItemStackRecipeWrapper recipe) {
        GuiElementBuilder guiElementBuilder = new GuiElementBuilder();
        GuiElementBuilderAccessor accessor = (GuiElementBuilderAccessor) guiElementBuilder;
        accessor.setItemStack(recipe.getItemStack());
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
