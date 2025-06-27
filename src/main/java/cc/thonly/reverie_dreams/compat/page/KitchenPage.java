package cc.thonly.reverie_dreams.compat.page;

import cc.thonly.mystias_izakaya.block.MIBlocks;
import cc.thonly.mystias_izakaya.block.entity.KitchenwareBlockEntity;
import cc.thonly.mystias_izakaya.recipe.entry.KitchenRecipe;
import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.compat.PolydexCompatImpl;
import cc.thonly.reverie_dreams.gui.RecipeTypeCategoryManager;
import cc.thonly.reverie_dreams.item.ModGuiItems;
import cc.thonly.reverie_dreams.recipe.entry.StrengthTableRecipe;
import eu.pb4.polydex.api.v1.recipe.*;
import eu.pb4.polydex.impl.PolydexImpl;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import lombok.Getter;
import net.minecraft.block.Block;
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
public class KitchenPage implements PolydexPage {
    public static final Identifier id = Touhou.id("recipe/kitchen");
    public static final PolydexCategory CATEGORY = PolydexCategory.of(id);
    private static final Text TEXTURE = Text.empty();
    public static final ItemStack ICON = new GuiElementBuilder(MIBlocks.COOKING_POT.asItem()).setName(Text.translatable(id.toTranslationKey())).asStack();
    public final Identifier key;
    public final KitchenRecipe value;
    private final List<PolydexIngredient<?>> ingredients;
    private final PolydexStack<?> output;

    public KitchenPage(Identifier key, KitchenRecipe value) {
        this.key = key.withPrefixedPath("recipe/");
        this.value = value;
        List<PolydexIngredient<?>> list = new ArrayList<>();

        for (var x : value.getIngredients()) {
            if (x.isEmpty()) continue;
            list.add(PolydexIngredient.of(Ingredient.ofItem(x.getItem()), x.getCount()));
        }
        this.ingredients = list;
        this.output = PolydexStack.of(this.value.getOutput().getItemStack());
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
                {"X", "X", "X", "X", "X", "X", "X", "X", "X"},
                {"X", "I", "I", "I", "I", "I", "T", "O", "X"},
                {"X", "X", "X", "X", "X", "X", "X", "X", "X"},
                {"X", "X", "X", "X", "P", "X", "X", "X", "X"},
                {"X", "X", "X", "X", "X", "X", "X", "X", "X"},
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
        } else if (s.equals("O")) {
            return this.value.getOutput().getItemStack();
        } else if (s.equals("I")) {
            int i = input.get();
            input.incrementAndGet();
            if (i + 1 <= this.value.getIngredients().size()) {
                return this.value.getIngredients().get(i).getItemStack();
            }
        } else if (s.equals("T")) {
            return ModGuiItems.PROGRESS_TO_RESULT.getDefaultStack();
        } else if (s.equals("P")) {
            Block block = KitchenwareBlockEntity.BLOCK_2_KITCHEN_TYPE.inverse().get(this.value.getType());
            if (block != null) {
                return block.asItem().getDefaultStack();
            }
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
        return polydexEntry.isPartOf(output);
    }
}
