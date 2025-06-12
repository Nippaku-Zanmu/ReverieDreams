package cc.thonly.reverie_dreams.recipe;

import cc.thonly.reverie_dreams.Touhou;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipeTypes {
    public static void init() {

    }
    public static <T extends Recipe<?>> RecipeType<T> register(String path) {
        return Registry.register(Registries.RECIPE_TYPE, Touhou.id(path), new RecipeType<T>() {
            @Override
            public String toString() {
                return Touhou.MOD_ID + ":" + path;
            }
        });
    }
}
