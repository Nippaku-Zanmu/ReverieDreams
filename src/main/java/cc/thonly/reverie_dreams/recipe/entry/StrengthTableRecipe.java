package cc.thonly.reverie_dreams.recipe.entry;

import cc.thonly.reverie_dreams.recipe.slot.CountRecipeSlot;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StrengthTableRecipe {

    @Getter
    @ToString
    public static class Entry {
        private final CountRecipeSlot mainItem;
        private final CountRecipeSlot offItem;
        private final CountRecipeSlot output;

        private Entry(@NotNull Builder builder) {
            this.mainItem = builder.mainItem;
            this.offItem = builder.offItem;
            this.output = builder.output;
        }
    }

    public static class Builder {
        public static final Codec<Entry> ENTRY_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                CountRecipeSlot.CODEC.fieldOf("mainItem").forGetter(Entry::getMainItem),
                CountRecipeSlot.CODEC.fieldOf("offItem").forGetter(Entry::getOffItem),
                CountRecipeSlot.CODEC.fieldOf("resultItem").forGetter(Entry::getOutput)
        ).apply(instance, (mainItem, offItem, resultItem) -> new Builder()
                .mainItem(mainItem)
                .offItem(offItem)
                .output(resultItem)
                .build()));
        public static final Codec<Builder> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                CountRecipeSlot.CODEC.optionalFieldOf("mainItem", null).forGetter(builder -> builder.mainItem),
                CountRecipeSlot.CODEC.optionalFieldOf("offItem", null).forGetter(builder -> builder.offItem),
                CountRecipeSlot.CODEC.optionalFieldOf("resultItem", null).forGetter(builder -> builder.output)
        ).apply(instance, Builder::create));
        public static final MapCodec<Builder> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                CountRecipeSlot.CODEC.optionalFieldOf("mainItem", null).forGetter(builder -> builder.mainItem),
                CountRecipeSlot.CODEC.optionalFieldOf("offItem", null).forGetter(builder -> builder.offItem),
                CountRecipeSlot.CODEC.optionalFieldOf("resultItem", null).forGetter(builder -> builder.output)
        ).apply(instance, Builder::create));

        @Nullable
        protected CountRecipeSlot mainItem;
        @Nullable
        protected CountRecipeSlot offItem;
        @Nullable
        protected CountRecipeSlot output;

        public static Builder create() {
            return new Builder();
        }

        public static Builder create(@Nullable CountRecipeSlot mainItem, @Nullable CountRecipeSlot offItem, @Nullable CountRecipeSlot output) {
            return new Builder();
        }

        public Builder mainItem(@Nullable CountRecipeSlot mainItem) {
            this.mainItem = mainItem;
            return this;
        }

        public Builder offItem(@Nullable CountRecipeSlot offItem) {
            this.offItem = offItem;
            return this;
        }

        public Builder output(@Nullable CountRecipeSlot output) {
            this.output = output;
            return this;
        }

        public Entry build() {
            if (this.mainItem == null || this.offItem == null || this.output == null) {
                throw new IllegalStateException("Main item, off item, and result item must be set.");
            }
            return new Entry(this);
        }
    }
}
