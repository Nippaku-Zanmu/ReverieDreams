package cc.thonly.touhoumod.compat;

import cc.thonly.touhoumod.compat.page.DanmakuPage;
import cc.thonly.touhoumod.item.ModItems;
import cc.thonly.touhoumod.recipe.DanmakuRecipes;
import cc.thonly.touhoumod.recipe.entry.DanmakuRecipe;
import eu.pb4.polydex.api.v1.recipe.PolydexEntry;
import eu.pb4.polydex.api.v1.recipe.PolydexPage;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.function.Consumer;

public class PolydexCompatImpl {
    public static void bootstrap(
//            MinecraftServer server
    ) {
        PolydexPage.register(PolydexCompatImpl::createPages);
        PolydexEntry.registerEntryCreator(ModItems.DANMAKU_ITEMS.getFirst().getValues().getFirst(), PolydexCompatImpl::createEntries);
    }

    private static void createPages(MinecraftServer minecraftServer, Consumer<PolydexPage> pageConsumer) {
        DanmakuRecipes.RecipeRegistryBase<DanmakuRecipe.Entry> registryRef = DanmakuRecipes.getRecipeRegistryRef();
        Map<Identifier, DanmakuRecipe.Entry> all = registryRef.getAll();
        all.forEach((id, value) -> {
            pageConsumer.accept(new DanmakuPage(id,value));
        });
    }

    private static PolydexEntry createEntries(ItemStack stack) {
        return PolydexEntry.of(stack);
    }
}
