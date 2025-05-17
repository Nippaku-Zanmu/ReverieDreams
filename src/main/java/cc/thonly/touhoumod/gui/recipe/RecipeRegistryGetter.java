package cc.thonly.touhoumod.gui.recipe;

import cc.thonly.touhoumod.recipe.SimpleRegistryInstance;

@FunctionalInterface
public interface RecipeRegistryGetter {
    SimpleRegistryInstance<?> get();
}
