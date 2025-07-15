package cc.thonly.minecraft.inventory;

import cc.thonly.reverie_dreams.recipe.ItemStackRecipeWrapper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;

import java.util.List;

public record Slot2ItemStack(int index, ItemStack itemStack) {
    public static final Codec<Slot2ItemStack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("slot").forGetter(Slot2ItemStack::index),
            ItemStackRecipeWrapper.FLEXIBLE_ITEMSTACK_CODEC.fieldOf("item").forGetter(Slot2ItemStack::itemStack)
    ).apply(instance, Slot2ItemStack::new));

    public static final Codec<List<Slot2ItemStack>> LIST_CODEC = CODEC.listOf();
}