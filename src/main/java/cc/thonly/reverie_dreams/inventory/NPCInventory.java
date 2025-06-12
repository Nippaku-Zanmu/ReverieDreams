package cc.thonly.reverie_dreams.inventory;

import cc.thonly.reverie_dreams.gui.NPCGui;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.HashMap;
import java.util.Map;

public class NPCInventory extends SimpleInventory {
    public static final SlotFilter HELMET_ONLY = stack -> {
        EquippableComponent equippable = stack.getComponents().get(DataComponentTypes.EQUIPPABLE);
        if (equippable == null) return true;
        EquipmentSlot slot = equippable.slot();
        if (slot == null) return true;
        return slot == EquipmentSlot.HEAD;
    };
    public static final SlotFilter CHESTPLATE_ONLY = stack -> {
        EquippableComponent equippable = stack.getComponents().get(DataComponentTypes.EQUIPPABLE);
        if (equippable == null) return true;
        EquipmentSlot slot = equippable.slot();
        if (slot == null) return true;
        return slot == EquipmentSlot.CHEST;
    };
    public static final SlotFilter LEGGINGS_ONLY = stack -> {
        EquippableComponent equippable = stack.getComponents().get(DataComponentTypes.EQUIPPABLE);
        if (equippable == null) return true;
        EquipmentSlot slot = equippable.slot();
        if (slot == null) return true;
        return slot == EquipmentSlot.LEGS;
    };
    public static final SlotFilter BOOTS_ONLY = stack -> {
        EquippableComponent equippable = stack.getComponents().get(DataComponentTypes.EQUIPPABLE);
        if (equippable == null) return true;
        EquipmentSlot slot = equippable.slot();
        if (slot == null) return true;
        return slot == EquipmentSlot.BODY;
    };
    public static final Map<Integer, SlotFilter> filters = new HashMap<>();
    public static final Integer HEAD_INDEX = NPCGui.size() - 6;
    public static final Integer CHEST_INDEX = cc.thonly.reverie_dreams.gui.NPCGui.size() - 5;
    public static final Integer LEGS_INDEX = cc.thonly.reverie_dreams.gui.NPCGui.size() - 4;
    public static final Integer FEET_INDEX = cc.thonly.reverie_dreams.gui.NPCGui.size() - 3;
    public static final int MAIN_HAND = cc.thonly.reverie_dreams.gui.NPCGui.size() - 1;
    public static final int OFF_HAND = cc.thonly.reverie_dreams.gui.NPCGui.size() - 2;
    public static final int HEAD = cc.thonly.reverie_dreams.gui.NPCGui.size() - 6;
    public static final int CHEST = cc.thonly.reverie_dreams.gui.NPCGui.size() - 5;
    public static final int LEGS = cc.thonly.reverie_dreams.gui.NPCGui.size() - 4;
    public static final int FEET = cc.thonly.reverie_dreams.gui.NPCGui.size() - 3;

    static {
        filters.put(HEAD_INDEX, HELMET_ONLY);
        filters.put(CHEST_INDEX, CHESTPLATE_ONLY);
        filters.put(LEGS_INDEX, LEGGINGS_ONLY);
        filters.put(FEET_INDEX, BOOTS_ONLY);
    }

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
        if(hand == Hand.MAIN_HAND) {
            return getMainHand();
        } else if(hand == Hand.OFF_HAND) {
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
        if(hand == Hand.MAIN_HAND) {
            setMainHand(stack);
        } else if(hand == Hand.OFF_HAND) {
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
}
