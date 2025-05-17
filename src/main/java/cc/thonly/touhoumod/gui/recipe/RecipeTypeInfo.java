package cc.thonly.touhoumod.gui.recipe;

import cc.thonly.touhoumod.recipe.SimpleRegistryInstance;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RecipeTypeInfo {
    private final String name;
    private final SimpleRegistryInstance<?> simpleRegistryInstance;
    private final GuiStackGetter getter;
}
