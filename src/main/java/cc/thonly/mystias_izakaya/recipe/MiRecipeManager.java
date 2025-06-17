package cc.thonly.mystias_izakaya.recipe;

import cc.thonly.mystias_izakaya.recipe.entry.KitchenRecipe;
import cc.thonly.mystias_izakaya.recipe.type.KitchenRecipeType;
import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.recipe.BaseRecipeType;
import cc.thonly.reverie_dreams.recipe.RecipeManager;

public class MiRecipeManager extends RecipeManager {
    public static final BaseRecipeType<KitchenRecipe> KITCHEN_RECIPE = register(Touhou.id("kitchen"), new KitchenRecipeType());

    public static void bootstrap() {

    }
}
