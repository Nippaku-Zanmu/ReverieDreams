package cc.thonly.reverie_dreams.recipe.entry;

import cc.thonly.reverie_dreams.recipe.BaseRecipe;
import cc.thonly.reverie_dreams.recipe.slot.ItemStackRecipeWrapper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class GensokyoAltarRecipe extends BaseRecipe {
    public static final Codec<GensokyoAltarRecipe> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ItemStackRecipeWrapper.CODEC.fieldOf("core").forGetter(GensokyoAltarRecipe::getCore),
                    ItemStackRecipeWrapper.CODEC.listOf().fieldOf("slots").forGetter(GensokyoAltarRecipe::getSlots),
                    ItemStackRecipeWrapper.CODEC.fieldOf("output").forGetter(GensokyoAltarRecipe::getOutput)
            ).apply(instance, GensokyoAltarRecipe::new)
    );
    private final ItemStackRecipeWrapper core;
    private final List<ItemStackRecipeWrapper> slots;
    private final ItemStackRecipeWrapper output;

    public List<ItemStackRecipeWrapper> getSlots() {
        return Collections.unmodifiableList(this.slots);
    }

    public ItemStackRecipeWrapper getOutput() {
        return new ItemStackRecipeWrapper(this.output.getItemStack().copy());
    }

}
