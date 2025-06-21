package cc.thonly.reverie_dreams.compat;

import cc.thonly.mystias_izakaya.recipe.entry.KitchenRecipe;
import cc.thonly.mystias_izakaya.recipe.type.KitchenRecipeType;
import cc.thonly.reverie_dreams.compat.page.DanmakuPage;
import cc.thonly.reverie_dreams.compat.page.GensokyoAltarPage;
import cc.thonly.reverie_dreams.compat.page.KitchenPage;
import cc.thonly.reverie_dreams.compat.page.StrengthTablePage;
import cc.thonly.reverie_dreams.item.ModItems;
import cc.thonly.reverie_dreams.recipe.entry.DanmakuRecipe;
import cc.thonly.reverie_dreams.recipe.entry.GensokyoAltarRecipe;
import cc.thonly.reverie_dreams.recipe.entry.StrengthTableRecipe;
import cc.thonly.reverie_dreams.recipe.type.DanmakuRecipeType;
import cc.thonly.reverie_dreams.recipe.type.GensokyoAltarRecipeType;
import cc.thonly.reverie_dreams.recipe.type.StrengthTableRecipeType;
import eu.pb4.polydex.api.v1.recipe.PolydexEntry;
import eu.pb4.polydex.api.v1.recipe.PolydexPage;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class PolydexCompatImpl {
    public static void bootstrap(
    ) {
        PolydexPage.register(PolydexCompatImpl::createPages);
        PolydexEntry.registerEntryCreator(ModItems.DANMAKU_ITEMS.getFirst().getValues().getFirst(), PolydexCompatImpl::createEntries);
    }

    private static void createPages(MinecraftServer minecraftServer, Consumer<PolydexPage> pageConsumer) {
        consumeRecipes(DanmakuRecipeType.getInstance().getRegistryView(), DanmakuPage::new, pageConsumer);
        consumeRecipes(GensokyoAltarRecipeType.getInstance().getRegistryView(), GensokyoAltarPage::new, pageConsumer);
        consumeRecipes(StrengthTableRecipeType.getInstance().getRegistryView(), StrengthTablePage::new, pageConsumer);
        consumeRecipes(KitchenRecipeType.getInstance().getRegistryView(), KitchenPage::new, pageConsumer);
    }

    private static <T, R extends PolydexPage> void consumeRecipes(
            Map<Identifier, T> recipeMap,
            BiFunction<Identifier, T, R> pageFactory,
            Consumer<PolydexPage> consumer) {

        recipeMap.forEach((id, recipe) -> consumer.accept(pageFactory.apply(id, recipe)));
    }


    private static PolydexEntry createEntries(ItemStack stack) {
        return PolydexEntry.of(stack);
    }
}
