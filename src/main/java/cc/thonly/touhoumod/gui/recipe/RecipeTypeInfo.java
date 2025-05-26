package cc.thonly.touhoumod.gui.recipe;

import cc.thonly.touhoumod.recipe.SimpleRecipeRegistryBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RecipeTypeInfo {
    private final String name;
    private final SimpleRecipeRegistryBase<?> simpleRecipeRegistryBase;
    private final GuiStackGetter getter;
}
