package cc.thonly.reverie_dreams.recipe;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.recipe.entry.DanmakuRecipe;
import cc.thonly.reverie_dreams.recipe.entry.GensokyoAltarRecipe;
import cc.thonly.reverie_dreams.recipe.entry.StrengthTableRecipe;
import cc.thonly.reverie_dreams.recipe.slot.ItemStackRecipeWrapper;
import cc.thonly.reverie_dreams.recipe.type.DanmakuRecipeType;
import cc.thonly.reverie_dreams.recipe.type.GensokyoAltarRecipeType;
import cc.thonly.reverie_dreams.recipe.type.StrengthTableRecipeType;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
public class RecipeManager {
    public static final Map<Identifier, BaseRecipeType<?>> RECIPE_TYPES = new Object2ObjectOpenHashMap<>();
    public static final BaseRecipeType<DanmakuRecipe> DANMAKU_TYPE = register(Touhou.id("danmaku"), new DanmakuRecipeType());
    public static final BaseRecipeType<GensokyoAltarRecipe> GENSOKYO_ALTAR = register(Touhou.id("gensokyo_altar"), new GensokyoAltarRecipeType());
    public static final BaseRecipeType<StrengthTableRecipe> STRENGTH_TABLE = register(Touhou.id("strength_table"), new StrengthTableRecipeType());

    public static void bootstrap() {

    }

    public static BaseRecipe getFromOutput(Item item) {
        for (Map.Entry<Identifier, BaseRecipeType<?>> recipeTypeEntry : RECIPE_TYPES.entrySet()) {
            Map<Identifier, ?> registryView = recipeTypeEntry.getValue().getRegistryView();
            for (Map.Entry<Identifier, ?> recipeEntry : registryView.entrySet()) {
                Object recipeObj = recipeEntry.getValue();
                ItemStackRecipeWrapper wrapper = getOutputReflective(recipeObj);
                if (wrapper != null && wrapper.getItem() == item) {
                    return (BaseRecipe) recipeObj;
                }
            }
        }
        return null;
    }

    public static ItemStackRecipeWrapper getOutputReflective(Object recipeObj) {
        try {
            Method method = recipeObj.getClass().getMethod("getOutput");
            Object result = method.invoke(recipeObj);

            if (result instanceof ItemStackRecipeWrapper wrapper) {
                return wrapper;
            }
        } catch (Exception e) {
            log.error("Can't invoke getOutput: {}", recipeObj.getClass());
        }
        return null;
    }


    public static void onReload(ResourceManager manager) {
        RECIPE_TYPES.forEach((key, recipeType) -> {
            try {
                recipeType.removeAll();
                recipeType.reload(manager);
                log.info("Reloaded Recipe Type {}", key.toString());
            } catch (Exception e) {
                log.error("Can't reload recipes {}, {}", key, e);
            }
        });
    }

    public static<R extends BaseRecipe> BaseRecipeType<R> register(Identifier id, BaseRecipeType<R> recipeType) {
        RECIPE_TYPES.put(id, recipeType);
        recipeType.bootstrap();
        assert id == recipeType.getId();
        return recipeType;
    }
}
