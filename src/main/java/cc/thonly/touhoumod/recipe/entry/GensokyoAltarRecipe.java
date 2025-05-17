package cc.thonly.touhoumod.recipe.entry;

import cc.thonly.touhoumod.recipe.slot.CountRecipeSlot;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@ToString
public class GensokyoAltarRecipe {
    @Getter
    @ToString
    public static class Entry {
        private final @Nullable CountRecipeSlot core;
        protected LinkedList<CountRecipeSlot> slots = new LinkedList<>();
        protected CountRecipeSlot output;

        private Entry(LinkedList<CountRecipeSlot> slots, @Nullable CountRecipeSlot core, @NotNull CountRecipeSlot output) {
            if (slots.size() > 8) {
                throw new IllegalStateException("Cannot add more than 8 slots to a recipe.");
            }
            this.slots = slots;
            this.core = core;
            this.output = output;
        }

        public List<CountRecipeSlot> getSlots() {
            return Collections.unmodifiableList(slots);
        }
    }

    public static class Builder {
        public static final MapCodec<Entry> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.list(CountRecipeSlot.CODEC).optionalFieldOf("slots", Collections.emptyList()).forGetter(Entry::getSlots),
                CountRecipeSlot.CODEC.optionalFieldOf("core", new CountRecipeSlot(Items.AIR, 1)).forGetter(entry -> entry.core),
                CountRecipeSlot.CODEC.fieldOf("output").forGetter(entry -> entry.output)
        ).apply(instance, (slots, core, result) -> new Entry(new LinkedList<>(slots), core, result)));
        public static final Codec<Entry> ENTRY_CODEC = MAP_CODEC.codec();

        private final LinkedList<CountRecipeSlot> slots = new LinkedList<>();
        private CountRecipeSlot result;
        private @Nullable CountRecipeSlot core;

        public static Builder create() {
            return new Builder();
        }

        public Builder addSlot(@Nullable CountRecipeSlot slot) {
            if (slot != null) {
                if (slots.size() >= 8) {
                    throw new IllegalStateException("Cannot add more than 8 slots to a recipe.");
                }
                slots.add(slot);
            } else {
                if (slots.size() >= 8) {
                    throw new IllegalStateException("Cannot add more than 8 slots to a recipe.");
                }
                slots.add(new CountRecipeSlot(Items.AIR, 1));
            }
            return this;
        }

        public Builder setCore(@Nullable CountRecipeSlot core) {
            this.core = core;
            return this;
        }

        public Builder setResult(@NotNull CountRecipeSlot result) {
            this.result = result;
            return this;
        }

        public Entry build() {
            if (this.result == null) {
                throw new IllegalStateException("Result must be set before building the recipe.");
            }

            while (this.slots.size() < 8) {
                this.slots.add(new CountRecipeSlot(Items.AIR, 1));
            }

            return new Entry(this.slots, this.core, this.result);
        }

        public @NotNull CountRecipeSlot getCore() {
            return this.core;
        }
    }
}