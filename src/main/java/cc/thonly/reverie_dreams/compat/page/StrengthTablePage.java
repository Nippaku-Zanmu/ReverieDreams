package cc.thonly.reverie_dreams.compat.page;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.compat.PolydexCompatImpl;
import cc.thonly.reverie_dreams.gui.RecipeTypeCategoryManager;
import cc.thonly.reverie_dreams.recipe.entry.GensokyoAltarRecipe;
import cc.thonly.reverie_dreams.recipe.entry.StrengthTableRecipe;
import eu.pb4.polydex.api.v1.recipe.*;
import eu.pb4.polydex.impl.PolydexImpl;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StrengthTablePage implements PolydexPage {
    public static final Identifier id = Touhou.id("recipe/strength_table");
    public static final PolydexCategory CATEGORY = PolydexCategory.of(id);
    private static final Text TEXTURE = Text.empty();
    public static final ItemStack ICON = new GuiElementBuilder(Items.BARREL).setName(Text.translatable(id.toTranslationKey())).asStack();
    public final Identifier key;
    public final StrengthTableRecipe value;
    private final List<PolydexIngredient<?>> ingredients;

    public StrengthTablePage(Identifier key, StrengthTableRecipe value) {
        this.key = key.withPrefixedPath("recipe/");
        this.value = value;
        List<PolydexIngredient<?>> list = new ArrayList<>();

        for (var x : List.of(value.getMainItem(), value.getOffItem())) {
            if (x.isEmpty()) continue;
            list.add(PolydexIngredient.of(Ingredient.ofItem(x.getItem()), x.getCount()));
        }
        this.ingredients = list;
        PolydexCompatImpl.ID2PAGE.put(key, this);
    }

    @Override
    public Identifier identifier() {
        return key;
    }

    @Override
    public ItemStack typeIcon(ServerPlayerEntity serverPlayerEntity) {
        return ICON;
    }

    @Override
    public ItemStack entryIcon(@Nullable PolydexEntry polydexEntry, ServerPlayerEntity serverPlayerEntity) {
        return this.value.getOutput().getItemStack();
    }

    @Override
    public void createPage(@Nullable PolydexEntry polydexEntry, ServerPlayerEntity serverPlayerEntity, PageBuilder pageBuilder) {
        PolydexCategory category = PolydexImpl.CATEGORY_BY_ID.get(this.identifier());
        RecipeTypeCategoryManager.open(Touhou.id("recipe/strength_table"), this.value.getId(), serverPlayerEntity, () -> {
            PolydexPageUtils.openCategoryUi(serverPlayerEntity, category, null);
            return null;
        });
    }

    @Override
    public List<PolydexIngredient<?>> ingredients() {
        return this.getIngredients();
    }

    @Override
    public List<PolydexCategory> categories() {
        return List.of(CATEGORY);
    }

    @Override
    public boolean isOwner(MinecraftServer minecraftServer, PolydexEntry polydexEntry) {
        return false;
    }
}
