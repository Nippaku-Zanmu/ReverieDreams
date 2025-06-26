package cc.thonly.reverie_dreams.compat.page;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.compat.PolydexCompatImpl;
import cc.thonly.reverie_dreams.gui.RecipeTypeCategoryManager;
import cc.thonly.reverie_dreams.item.ModGuiItems;
import cc.thonly.reverie_dreams.recipe.entry.DanmakuRecipe;
import cc.thonly.reverie_dreams.recipe.entry.GensokyoAltarRecipe;
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
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class GensokyoAltarPage implements PolydexPage {
    public static final Identifier id = Touhou.id("recipe/gensokyo_altar");
    public static final PolydexCategory CATEGORY = PolydexCategory.of(id);
    private static final Text TEXTURE = Text.empty();
    public static final ItemStack ICON = new GuiElementBuilder(Items.BARREL).setName(Text.translatable(id.toTranslationKey())).asStack();
    public final Identifier key;
    public final GensokyoAltarRecipe value;
    private final List<PolydexIngredient<?>> ingredients;

    public GensokyoAltarPage(Identifier key, GensokyoAltarRecipe value) {
        this.key = key.withPrefixedPath("recipe/");
        this.value = value;
        List<PolydexIngredient<?>> list = new ArrayList<>();
        if (!value.getCore().isEmpty()) {
            list.add(PolydexIngredient.of(Ingredient.ofItem(value.getCore().getItem()), value.getCore().getCount()));
        }
        for (var x : value.getSlots()) {
            if (x.isEmpty()) continue;
            list.add(PolydexIngredient.of(Ingredient.ofItem(x.getItem()), x.getCount()));
        }
        this.ingredients = list;
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
    public void createPage(@Nullable PolydexEntry polydexEntry, ServerPlayerEntity serverPlayerEntity, PageBuilder layout) {
        String[][] views = {
                {"X", "X", "I", "X", "I", "X", "I", "X", "X"},
                {"X", "X", "X", "X", "X", "X", "X", "X", "X"},
                {"X", "X", "I", "X", "C", "X", "I", "T", "O"},
                {"X", "X", "X", "X", "X", "X", "X", "X", "X"},
                {"X", "X", "I", "X", "I", "X", "I", "X", "X"},
        };
        AtomicInteger input = new AtomicInteger(0);
        for (int row = 0; row < views.length; row++) {
            for (int col = 0; col < views[row].length; col++) {
                layout.set(col, row, getViewStack(input, views[row][col]));
            }
        }
    }

    private ItemStack getViewStack(AtomicInteger input, String s) {
        if (s.equals("X")) {
            return ModGuiItems.EMPTY_SLOT.getDefaultStack();
        } else if (s.equals("C")) {
            return this.value.getCore().getItemStack().copy();
        } else if (s.equals("I")) {
            int i = input.get();
            input.incrementAndGet();
            if (i + 1 < this.value.getSlots().size()) {
                return this.value.getSlots().get(i).getItemStack().copy();
            }
        } else if (s.equals("O")) {
            return this.value.getOutput().getItemStack().copy();
        } else if (s.equals("T")) {
            return ModGuiItems.PROGRESS_TO_RESULT.getDefaultStack();
        }
        return Items.AIR.getDefaultStack();
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
