package cc.thonly.reverie_dreams.recipe.entry;

import cc.thonly.reverie_dreams.recipe.BaseRecipe;
import cc.thonly.reverie_dreams.recipe.slot.ItemStackRecipeWrapper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class DanmakuRecipe extends BaseRecipe {
    public static final Codec<DanmakuRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStackRecipeWrapper.CODEC.fieldOf("dye").forGetter(DanmakuRecipe::getDye),
            ItemStackRecipeWrapper.CODEC.fieldOf("core").forGetter(DanmakuRecipe::getCore),
            ItemStackRecipeWrapper.CODEC.fieldOf("power").forGetter(DanmakuRecipe::getPower),
            ItemStackRecipeWrapper.CODEC.fieldOf("point").forGetter(DanmakuRecipe::getPoint),
            ItemStackRecipeWrapper.CODEC.fieldOf("material").forGetter(DanmakuRecipe::getMaterial),
            ItemStackRecipeWrapper.CODEC.fieldOf("output").forGetter(DanmakuRecipe::getOutput)
    ).apply(instance, DanmakuRecipe::new));

    private final ItemStackRecipeWrapper dye;
    private final ItemStackRecipeWrapper core;
    private final ItemStackRecipeWrapper power;
    private final ItemStackRecipeWrapper point;
    private final ItemStackRecipeWrapper material;
    private final ItemStackRecipeWrapper output;
}
