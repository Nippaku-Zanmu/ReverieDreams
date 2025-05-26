package cc.thonly.touhoumod.gui.recipe;

import cc.thonly.touhoumod.recipe.SimpleRecipeRegistryBase;

@FunctionalInterface
public interface RecipeRegistryGetter {
    SimpleRecipeRegistryBase<?> get();
}
