package cc.thonly.mystias_izakaya.recipe.entry;

import cc.thonly.reverie_dreams.recipe.slot.CountRecipeSlot;
import cc.thonly.reverie_dreams.recipe.slot.RecipeSlot;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class KitchenRecipe {
    @Setter
    @Getter
    @ToString
    public static class Entry {
        protected final List<CountRecipeSlot> ingredientItems;
        protected final RecipeSlot output;
        private final Double costTime;

        public Entry(@NotNull Builder builder) {
            this.ingredientItems = builder.getIngredientItems();
            this.output = builder.getOutput();
            this.costTime = builder.getCostTime();
        }
    }

    @Accessors(chain = true)
    @Setter
    @Getter
    public static class Builder {
        public static final Codec<KitchenRecipe.Entry> ENTRY_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.list(CountRecipeSlot.CODEC).fieldOf("ingredients").forGetter(Entry::getIngredientItems),
                RecipeSlot.CODEC.fieldOf("output").forGetter(Entry::getOutput),
                Codec.DOUBLE.fieldOf("cost_time").forGetter(Entry::getCostTime)
        ).apply(instance, (ingredients, output, costTime) -> new Builder()
                .setIngredientItems(ingredients)
                .setOutput(output)
                .setCostTime(costTime)
                .build()));
        public static final Codec<Builder> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.list(CountRecipeSlot.CODEC).fieldOf("ingredients").forGetter(Builder::getIngredientItems),
                RecipeSlot.CODEC.fieldOf("output").forGetter(Builder::getOutput),
                Codec.DOUBLE.fieldOf("cost_time").forGetter(Builder::getCostTime)
        ).apply(instance, (ingredients, output, costTime) -> new Builder()
                .setIngredientItems(ingredients)
                .setCostTime(costTime)
                .setOutput(output)
        ));
        public static final MapCodec<Builder> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.list(CountRecipeSlot.CODEC).fieldOf("ingredients").forGetter(Builder::getIngredientItems),
                RecipeSlot.CODEC.fieldOf("output").forGetter(Builder::getOutput),
                Codec.DOUBLE.fieldOf("cost_time").forGetter(Builder::getCostTime)
        ).apply(instance, (ingredients, output, costTime) -> new Builder()
                .setIngredientItems(ingredients)
                .setOutput(output)
                .setCostTime(costTime)
        ));

        protected List<CountRecipeSlot> ingredientItems = new ArrayList<>();
        protected RecipeSlot output;
        protected Double costTime;

        public static Builder createBuilder() {
            return new Builder();
        }

        public KitchenRecipe.Builder addItem(Item item, Integer amount) {
            this.ingredientItems.add(new CountRecipeSlot(item, amount));
            return this;
        }

        public KitchenRecipe.Entry build() {
            return new Entry(this);
        }
    }
}
