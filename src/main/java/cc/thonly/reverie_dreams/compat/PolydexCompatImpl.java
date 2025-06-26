package cc.thonly.reverie_dreams.compat;

import cc.thonly.mystias_izakaya.block.MIBlocks;
import cc.thonly.mystias_izakaya.recipe.entry.KitchenRecipe;
import cc.thonly.mystias_izakaya.recipe.type.KitchenRecipeType;
import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.compat.page.DanmakuPage;
import cc.thonly.reverie_dreams.compat.page.GensokyoAltarPage;
import cc.thonly.reverie_dreams.compat.page.KitchenPage;
import cc.thonly.reverie_dreams.compat.page.StrengthTablePage;
import cc.thonly.reverie_dreams.item.ModItems;
import cc.thonly.reverie_dreams.recipe.BaseRecipe;
import cc.thonly.reverie_dreams.recipe.BaseRecipeType;
import cc.thonly.reverie_dreams.recipe.RecipeManager;
import cc.thonly.reverie_dreams.recipe.entry.DanmakuRecipe;
import cc.thonly.reverie_dreams.recipe.entry.GensokyoAltarRecipe;
import cc.thonly.reverie_dreams.recipe.entry.StrengthTableRecipe;
import cc.thonly.reverie_dreams.recipe.slot.ItemStackRecipeWrapper;
import cc.thonly.reverie_dreams.recipe.type.DanmakuRecipeType;
import cc.thonly.reverie_dreams.recipe.type.GensokyoAltarRecipeType;
import cc.thonly.reverie_dreams.recipe.type.StrengthTableRecipeType;
import eu.pb4.polydex.api.v1.recipe.PolydexEntry;
import eu.pb4.polydex.api.v1.recipe.PolydexPage;
import eu.pb4.polydex.api.v1.recipe.PolydexPageUtils;
import eu.pb4.polydex.impl.PolydexImpl;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class PolydexCompatImpl {
    public static void bootstrap(
    ) {
        PolydexPage.register(PolydexCompatImpl::createPages);
    }

    public static void registerEntries() {
        registerEntries(DanmakuRecipeType.getInstance().getRegistryView());
        registerEntries(GensokyoAltarRecipeType.getInstance().getRegistryView());
        registerEntries(StrengthTableRecipeType.getInstance().getRegistryView());
        registerEntries(KitchenRecipeType.getInstance().getRegistryView());
    }

    private static <T extends BaseRecipe> void registerEntries(Map<Identifier, T> view) {
        view.forEach((key, recipe) -> {
            ItemStackRecipeWrapper wrapper = RecipeManager.getOutputReflective(recipe);
            if (wrapper != null) {
                PolydexEntry.registerEntryCreator(wrapper.getItem(), (PolydexEntry::of));
            }
        });
    }

    private static void createPages(MinecraftServer minecraftServer, Consumer<PolydexPage> pageConsumer) {
        createRecipeView(DanmakuRecipeType.getInstance().getRegistryView(), DanmakuPage::new, pageConsumer);
        createRecipeView(GensokyoAltarRecipeType.getInstance().getRegistryView(), GensokyoAltarPage::new, pageConsumer);
        createRecipeView(StrengthTableRecipeType.getInstance().getRegistryView(), StrengthTablePage::new, pageConsumer);
        createRecipeView(KitchenRecipeType.getInstance().getRegistryView(), KitchenPage::new, pageConsumer);
    }

    private static <T, R extends PolydexPage> void createRecipeView(
            Map<Identifier, T> view,
            BiFunction<Identifier, T, R> pageFactory,
            Consumer<PolydexPage> consumer) {
        view.forEach((id, recipe) -> {
            consumer.accept(pageFactory.apply(id, recipe));
        });
    }


    private static PolydexEntry ofEntry(Identifier id, ItemStack stack) {
//        Item item = stack.getItem();
//        Identifier id = Registries.ITEM.getId(item);
        return PolydexEntry.of(id, stack);
    }
}
