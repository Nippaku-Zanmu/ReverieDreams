package cc.thonly.reverie_dreams.recipe.entry;

import cc.thonly.reverie_dreams.recipe.BaseRecipe;
import cc.thonly.reverie_dreams.recipe.ItemStackRecipeWrapper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder(toBuilder = true)
public class StrengthTableRecipe extends BaseRecipe {
    public static final Codec<StrengthTableRecipe> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ItemStackRecipeWrapper.CODEC.fieldOf("main_item").forGetter(StrengthTableRecipe::getMainItem),
                    ItemStackRecipeWrapper.CODEC.fieldOf("off_item").forGetter(StrengthTableRecipe::getOffItem),
                    ItemStackRecipeWrapper.CODEC.fieldOf("output").forGetter(StrengthTableRecipe::getOutput)
            ).apply(instance, StrengthTableRecipe::new)
    );
    private final ItemStackRecipeWrapper mainItem;
    private final ItemStackRecipeWrapper offItem;
    private final ItemStackRecipeWrapper output;

    public ItemStackRecipeWrapper getOutput() {
        return new ItemStackRecipeWrapper(this.output.getItemStack().copy());
    }
}
