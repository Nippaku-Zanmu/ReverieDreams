package cc.thonly.reverie_dreams.compat.page;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.recipe.entry.DanmakuRecipe;
import eu.pb4.polydex.api.v1.recipe.*;
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
public class DanmakuPage implements PolydexPage { ;
    public static final Identifier id = Touhou.id("recipe/danmaku");
    public static final PolydexCategory CATEGORY = PolydexCategory.of(id);
    private static final Text TEXTURE = Text.empty();
    public static final ItemStack ICON = new GuiElementBuilder(Items.BARREL).setName(Text.translatable(id.toTranslationKey())).asStack();
    public final Identifier key;
    public final DanmakuRecipe value;
    private final List<PolydexIngredient<?>> ingredients;
    public DanmakuPage(Identifier key, DanmakuRecipe value) {
        this.key = key;
        this.value = value;
        List<PolydexIngredient<?>> list = new ArrayList<>();
        for (var x: List.of(value.getDye(), value.getCore(), value.getPower(), value.getPoint(), value.getMaterial())) {
            if(x.getItem() == Items.AIR) {
                list.add(PolydexIngredient.of(Ingredient.ofItem(Items.BARRIER), 1));
                continue;
            }
            list.add(PolydexIngredient.of(Ingredient.ofItem(x.getItem()), x.getCount()));
        }
        this.ingredients = list;
    }

    @Override
    public Identifier identifier() {
        return id;
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
