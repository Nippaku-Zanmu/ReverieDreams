package cc.thonly.reverie_dreams.inventory;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;


@Setter
@Getter
@Deprecated
public class ArmorInventoryWrapper {
    public static final SlotFilter HELMET_FILTER = stack -> {
        EquippableComponent equippable = stack.getComponents().get(DataComponentTypes.EQUIPPABLE);
        if (equippable == null) return true;
        EquipmentSlot slot = equippable.slot();
        if (slot == null) return true;
        return slot == EquipmentSlot.HEAD;
    };
    public static final SlotFilter CHESTPLATE_FILTER = stack -> {
        EquippableComponent equippable = stack.getComponents().get(DataComponentTypes.EQUIPPABLE);
        if (equippable == null) return true;
        EquipmentSlot slot = equippable.slot();
        if (slot == null) return true;
        return slot == EquipmentSlot.CHEST;
    };
    public static final SlotFilter LEGGINGS_FILTER = stack -> {
        EquippableComponent equippable = stack.getComponents().get(DataComponentTypes.EQUIPPABLE);
        if (equippable == null) return true;
        EquipmentSlot slot = equippable.slot();
        if (slot == null) return true;
        return slot == EquipmentSlot.LEGS;
    };
    public static final SlotFilter BOOTS_FILTER = stack -> {
        EquippableComponent equippable = stack.getComponents().get(DataComponentTypes.EQUIPPABLE);
        if (equippable == null) return true;
        EquipmentSlot slot = equippable.slot();
        if (slot == null) return true;
        return slot == EquipmentSlot.FEET;
    };
    private PredicateInventory head = new PredicateInventory(1, HELMET_FILTER::test);
    private PredicateInventory chest = new PredicateInventory(1, CHESTPLATE_FILTER::test);
    private PredicateInventory legs = new PredicateInventory(1, LEGGINGS_FILTER::test);
    private PredicateInventory feet = new PredicateInventory(1, BOOTS_FILTER::test);
}
