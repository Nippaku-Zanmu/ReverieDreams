package cc.thonly.reverie_dreams.recipe.slot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.ToString;
import net.minecraft.component.ComponentChanges;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Objects;

@Getter
@ToString
public class ItemStackRecipeWrapper {
    public static final Codec<Item> ITEM_CODEC_ALLOWING_AIR = Codec.STRING.xmap(
            id -> {
                Identifier identifier = Identifier.tryParse(id);
                Item item = Registries.ITEM.get(identifier);
                if (item == null) {
                    throw new IllegalArgumentException("Unknown item id: " + id);
                }
                return item;
            },
            item -> Registries.ITEM.getId(item).toString()
    );
    public static final Codec<ItemStack> FLEXIBLE_ITEMSTACK_CODEC = Codec.lazyInitialized(() ->
            RecordCodecBuilder.create(instance -> instance.group(
                    ITEM_CODEC_ALLOWING_AIR.fieldOf("id").forGetter(ItemStack::getItem),
                    Codec.INT.optionalFieldOf("count", 0).forGetter(ItemStack::getCount),
                    ComponentChanges.CODEC.optionalFieldOf("components", ComponentChanges.EMPTY)
                            .forGetter(stack -> stack.components.getChanges())
            ).apply(instance, (item, count, components) -> {
                ItemStack stack = new ItemStack(item, count);
                stack.components.setChanges(components);
                return stack;
            }))
    );

    public static final Codec<ItemStackRecipeWrapper> CODEC =
            FLEXIBLE_ITEMSTACK_CODEC.xmap(ItemStackRecipeWrapper::new, ItemStackRecipeWrapper::getItemStack);
    public static final ItemStackRecipeWrapper EMPTY = new ItemStackRecipeWrapper(ItemStack.EMPTY);

    private final ItemStack itemStack;

    public ItemStackRecipeWrapper(ItemStack itemStack) {
        if (itemStack == null) {
            itemStack = ItemStack.EMPTY;
        }
        this.itemStack = itemStack;
    }

    public boolean isEmpty() {
        return this.itemStack.isEmpty();
    }

    public static ItemStackRecipeWrapper of(ItemStack itemStack) {
        return new ItemStackRecipeWrapper(itemStack);
    }

    public Item getItem() {
        return this.itemStack.getItem();
    }

    public Integer getCount() {
        return this.itemStack.getCount();
    }

    public Boolean test(ItemStack other) {
        return ItemStack.areEqual(this.itemStack, other);
    }

    public Boolean greaterThan(ItemStack other) {
        if (this.itemStack == other) {
            return true;
        }
        if (this.itemStack.isEmpty()) {
            return true;
        }
        if (this.itemStack.getItem() != other.getItem()) {
            return false;
        }

        return ItemStack.areItemsAndComponentsEqual(this.itemStack, other) && (other.getCount() >= this.itemStack.getCount());
    }

    public boolean matchesAndSufficient(ItemStack other) {
        if (other == null) return false;
        if (!other.isOf(itemStack.getItem())) return false;
        if (!Objects.equals(other.components, itemStack.components)) return false;
        return other.getCount() >= itemStack.getCount();
    }

}
