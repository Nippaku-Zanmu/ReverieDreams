package cc.thonly.reverie_dreams.inventory;

import lombok.Getter;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;

import java.util.function.Function;

@Getter
public class PredicateInventory extends SimpleInventory {
    public static final Factory ARMOR_SLOT_FACTORY = (size, predicate) -> new PredicateInventory(1, predicate);
    private final Function<ItemStack, Boolean> predicate;

    public PredicateInventory(Function<ItemStack, Boolean> predicate) {
        super(1);
        this.predicate = predicate;
    }

    public PredicateInventory(int size, Function<ItemStack, Boolean> predicate) {
        super(size);
        this.predicate = predicate;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return super.canInsert(stack) && this.predicate.apply(stack);
    }

    public interface Factory {
        public PredicateInventory get(int size, Function<ItemStack, Boolean> predicate);
    }
}
