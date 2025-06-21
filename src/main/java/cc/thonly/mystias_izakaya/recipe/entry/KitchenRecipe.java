package cc.thonly.mystias_izakaya.recipe.entry;

import cc.thonly.mystias_izakaya.recipe.type.KitchenRecipeType;
import cc.thonly.reverie_dreams.recipe.BaseRecipe;
import cc.thonly.reverie_dreams.recipe.slot.ItemStackRecipeWrapper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@Setter
@Getter
@ToString
@Slf4j
public class KitchenRecipe extends BaseRecipe {
    public static final Codec<KitchenRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("recipe_type").forGetter(KitchenRecipe::getRecipeType),
            Codec.list(ItemStackRecipeWrapper.CODEC).fieldOf("ingredients").forGetter(KitchenRecipe::getIngredients),
            ItemStackRecipeWrapper.CODEC.fieldOf("output").forGetter(KitchenRecipe::getOutput),
            Codec.DOUBLE.optionalFieldOf("cost_time", 5.0).forGetter(KitchenRecipe::getCostTime)
    ).apply(instance, KitchenRecipe::new));
    protected final Identifier recipeType;
    protected final List<ItemStackRecipeWrapper> ingredients;
    protected final ItemStackRecipeWrapper output;
    private final Double costTime;

    public KitchenRecipe(KitchenRecipeType.KitchenType kitchenType, List<ItemStackRecipeWrapper> ingredients, ItemStackRecipeWrapper output, Number costTime) {
        this(kitchenType.toId(), ingredients,output, costTime);
    }

    public KitchenRecipe(Identifier recipeType, List<ItemStackRecipeWrapper> ingredients, ItemStackRecipeWrapper output, Number costTime) {
        this.recipeType = recipeType;
        this.ingredients = ingredients;
        this.output = output;
        this.costTime = costTime.doubleValue();
        if (this.ingredients.size() > 5) {
            log.error("Kitchen Recipe {} ingredients size > 5 in {}", recipeType, recipeType + ".json");
        }
    }

    public ItemStackRecipeWrapper getOutput() {
        return new ItemStackRecipeWrapper(this.output.getItemStack().copy());
    }

    public KitchenRecipeType.KitchenType getType() {
        return KitchenRecipeType.KitchenType.getFromId(this.recipeType);
    }
}
