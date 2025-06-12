package cc.thonly.reverie_dreams.gui.recipe;

import cc.thonly.reverie_dreams.recipe.SimpleRecipeRegistryBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RecipeTypeInfo {
    private final String name;
    private final SimpleRecipeRegistryBase<?> simpleRecipeRegistryBase;
    private final GuiStackGetter getter;
}
