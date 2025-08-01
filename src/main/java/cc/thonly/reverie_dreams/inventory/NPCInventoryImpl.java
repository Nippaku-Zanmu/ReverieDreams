package cc.thonly.reverie_dreams.inventory;

import lombok.Getter;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

@Getter
public class NPCInventoryImpl extends SimpleInventory {
    public static final int MAX_SIZE = 24;
    public static final int HEAD = 18;
    public static final int CHEST = 19;
    public static final int LEGS = 20;
    public static final int FEET = 21;
    public static final int MAIN_HAND = 22;
    public static final int OFF_HAND = 23;

    public NPCInventoryImpl(int size) {
        super(size);
        init();
    }

    public NPCInventoryImpl(ItemStack... items) {
        super(items);
        init();
    }

    protected void init() {

    }

    public ItemStack getHand(Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            return getMainHand();
        } else if (hand == Hand.OFF_HAND) {
            return getOffHand();
        }
        return ItemStack.EMPTY;
    }

    public ItemStack getMainHand() {
        return this.getStack(MAIN_HAND);
    }

    public ItemStack getOffHand() {
        return this.getStack(OFF_HAND);
    }

    public ItemStack getHead() {
        return this.getStack(HEAD);
    }

    public ItemStack getChest() {
        return this.getStack(CHEST);
    }

    public ItemStack getLegs() {
        return this.getStack(LEGS);
    }

    public ItemStack getFeet() {
        return this.getStack(FEET);
    }

    public void setHand(Hand hand, ItemStack stack) {
        if (hand == Hand.MAIN_HAND) {
            setMainHand(stack);
        } else if (hand == Hand.OFF_HAND) {
            setOffHand(stack);
        }
    }

    public void setMainHand(ItemStack stack) {
        this.setStack(MAIN_HAND, stack.copy());
    }

    public void setOffHand(ItemStack stack) {
        this.setStack(OFF_HAND, stack.copy());
    }

    public void setHead(ItemStack stack) {
        this.setStack(HEAD, stack.copy());
    }

    public void setChest(ItemStack stack) {
        this.setStack(CHEST, stack.copy());
    }

    public void setLegs(ItemStack stack) {
        this.setStack(LEGS, stack.copy());
    }

    public void setFeet(ItemStack stack) {
        this.setStack(FEET, stack.copy());
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return super.canInsert(stack);
    }

    @Nonnull
    public List<Integer> findItemSlots(int maxLength, Predicate<ItemStack> isGood, Predicate<Integer> isExcludeIndex) {
        List<Integer> itemSlots = new LinkedList<>();
        for (int i = MAX_SIZE - 1; i >= 0; i--) {
            if (itemSlots.size() >= maxLength) break;
            ItemStack stack = this.getStack(i);
            if ((!isExcludeIndex.test(i)) && isGood.test(stack))
                itemSlots.add(i);
        }
        return itemSlots;
    }

    public Integer findSlot(Predicate<ItemStack> isGood) {
        List<Integer> slots = findItemSlots(1, isGood, (i) -> i >= 18 && i <= 21);
        return slots.isEmpty()?null:slots.getFirst();
    }
    public Integer findHand(Predicate<ItemStack> isGood) {
        return isGood.test(getStack(23))
                ? Integer.valueOf(23) :isGood.test(getStack(22))?22:null;
    }
}
