package cc.thonly.reverie_dreams.gui.recipe;

import cc.thonly.reverie_dreams.recipe.SimpleRecipeRegistryBase;

@FunctionalInterface
public interface RecipeRegistryGetter {
    SimpleRecipeRegistryBase<?> get();
}
