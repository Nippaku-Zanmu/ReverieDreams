package cc.thonly.reverie_dreams.recipe;

import cc.thonly.reverie_dreams.Touhou;
import com.mojang.serialization.MapCodec;
import eu.pb4.factorytools.api.recipe.LazyRecipeSerializer;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipeSerializer {
    public static void init() {

    }

    public static <T extends RecipeSerializer<?>> T register(String path, T recipeSerializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, Touhou.id(path), recipeSerializer);
    }

    public static <T extends Recipe<?>> LazyRecipeSerializer<T> register(String path, MapCodec<T> codec) {
        return Registry.register(Registries.RECIPE_SERIALIZER, Touhou.id(path), new LazyRecipeSerializer<>(codec));
    }
}
