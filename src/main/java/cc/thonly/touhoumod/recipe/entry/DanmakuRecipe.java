package cc.thonly.touhoumod.recipe.entry;

import cc.thonly.touhoumod.recipe.slot.CountRecipeSlot;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.*;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class DanmakuRecipe {
    @Getter
    @ToString
    @AllArgsConstructor
    public static class Entry {
        private final CountRecipeSlot dye;
        private final CountRecipeSlot core;
        private final CountRecipeSlot power;
        private final CountRecipeSlot point;
        private final CountRecipeSlot material;
        private final CountRecipeSlot output;

        private Entry(@NotNull Builder builder) {
            this.dye = builder.dye;
            this.core = builder.core;
            this.power = builder.power;
            this.point = builder.point;
            this.material = builder.material;
            this.output = builder.output;
        }

        public Builder toBuilder() {
            return new Builder(this.dye, this.core, this.power, this.point, this.material, this.output);
        }

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Builder {
        public static final MapCodec<Builder> MAP_CODEC = RecordCodecBuilder.mapCodec(x -> x.group(
                CountRecipeSlot.CODEC.optionalFieldOf("dye", new CountRecipeSlot(Items.AIR, 0)).forGetter(Builder::getDye),
                CountRecipeSlot.CODEC.optionalFieldOf("core", new CountRecipeSlot(Items.AIR, 0)).forGetter(Builder::getCore),
                CountRecipeSlot.CODEC.optionalFieldOf("power", new CountRecipeSlot(Items.AIR, 0)).forGetter(Builder::getPower),
                CountRecipeSlot.CODEC.optionalFieldOf("point", new CountRecipeSlot(Items.AIR, 0)).forGetter(Builder::getPoint),
                CountRecipeSlot.CODEC.optionalFieldOf("material", new CountRecipeSlot(Items.AIR, 0)).forGetter(Builder::getMaterial),
                CountRecipeSlot.CODEC.optionalFieldOf("output", new CountRecipeSlot(Items.AIR, 0)).forGetter(Builder::getOutput)
        ).apply(x, Builder::create));
        public static final Codec<Builder> CODEC = MAP_CODEC.codec();
        public static final Codec<Entry> ENTRY_CODEC = CODEC
                .xmap(Builder::build, Entry::toBuilder);

        protected CountRecipeSlot dye = new CountRecipeSlot(Items.AIR, 0);
        protected CountRecipeSlot core = new CountRecipeSlot(Items.AIR, 0);
        protected CountRecipeSlot power = new CountRecipeSlot(Items.AIR, 0);
        protected CountRecipeSlot point = new CountRecipeSlot(Items.AIR, 0);
        protected CountRecipeSlot material = new CountRecipeSlot(Items.AIR, 0);
        protected CountRecipeSlot output = new CountRecipeSlot(Items.AIR, 0);

        public static Builder create() {
            return new Builder();
        }

        public static Builder create(CountRecipeSlot dye, CountRecipeSlot core, CountRecipeSlot power, CountRecipeSlot point, CountRecipeSlot material, CountRecipeSlot output) {
            Builder builder = new Builder();
            builder.dye(dye);
            builder.core(core);
            builder.power(power);
            builder.point(point);
            builder.material(material);
            builder.result(output);
            return builder;
        }

        public Builder dye(@Nullable CountRecipeSlot dye) {
            this.dye = dye != null ? dye : new CountRecipeSlot(Items.AIR, 0);
            return this;
        }

        public Builder core(@Nullable CountRecipeSlot core) {
            this.core = core != null ? core : new CountRecipeSlot(Items.AIR, 0);
            return this;
        }

        public Builder power(@Nullable CountRecipeSlot power) {
            this.power = power != null ? power : new CountRecipeSlot(Items.AIR, 0);
            return this;
        }

        public Builder point(@Nullable CountRecipeSlot point) {
            this.point = point != null ? point : new CountRecipeSlot(Items.AIR, 0);
            return this;
        }

        public Builder material(@Nullable CountRecipeSlot material) {
            this.material = material != null ? material : new CountRecipeSlot(Items.AIR, 0);
            return this;
        }

        public Builder result(@Nullable CountRecipeSlot result) {
            this.output = result != null ? result : new CountRecipeSlot(Items.AIR, 0);
            return this;
        }

        public Entry build() {
            return new Entry(this);
        }
    }

}
