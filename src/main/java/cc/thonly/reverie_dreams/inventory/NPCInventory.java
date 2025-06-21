package cc.thonly.reverie_dreams.inventory;

import cc.thonly.reverie_dreams.gui.NPCGui;
import lombok.Getter;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

@Getter
public class NPCInventory extends SimpleInventory {
    public static final int MAIN_HAND = NPCGui.size() - 1;
    public static final int OFF_HAND = NPCGui.size() - 2;
//    public static final int HEAD = NPCGui.size() - 6;
//    public static final int CHEST = NPCGui.size() - 5;
//    public static final int LEGS = NPCGui.size() - 4;
//    public static final int FEET = NPCGui.size() - 3;

    private final ArmorInventoryWrapper armorInventory = new ArmorInventoryWrapper();

    public NPCInventory(int size) {
        super(size);
        init();
    }

    public NPCInventory(ItemStack... items) {
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
        return this.armorInventory.getHead().getStack(0);
    }

    public ItemStack getChest() {
        return this.armorInventory.getChest().getStack(0);
    }

    public ItemStack getLegs() {
        return this.armorInventory.getLegs().getStack(0);
    }

    public ItemStack getFeet() {
        return this.armorInventory.getFeet().getStack(0);
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
        this.armorInventory.getHead().setStack(0,stack.copy());
    }

    public void setChest(ItemStack stack) {
        this.armorInventory.getChest().setStack(0,stack.copy());
    }

    public void setLegs(ItemStack stack) {
        this.armorInventory.getLegs().setStack(0,stack.copy());
    }

    public void setFeet(ItemStack stack) {
        this.armorInventory.getFeet().setStack(0,stack.copy());
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return super.canInsert(stack);
    }
}
