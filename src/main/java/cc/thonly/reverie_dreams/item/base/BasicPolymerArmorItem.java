package cc.thonly.reverie_dreams.item.base;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.utils.PolymerClientDecoded;
import eu.pb4.polymer.core.api.utils.PolymerKeepModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public abstract class BasicPolymerArmorItem extends ArmorItem implements PolymerItem, PolymerClientDecoded, PolymerKeepModel, IdentifierGetter {
    final Identifier identifier;
    final Item vanillaItem;
    public static final List<ArmorItem> HEAD_ITEMS = new ArrayList<>();
    public static final List<ArmorItem> CHEST_ITEMS = new ArrayList<>();
    public static final List<ArmorItem> LEG_ITEMS = new ArrayList<>();
    public static final List<ArmorItem> FEET_ITEMS = new ArrayList<>();
    public static final List<ArmorItem> ITEMS = new ArrayList<>();

    public BasicPolymerArmorItem(String path, ArmorMaterial material, EquipmentType type, Settings settings) {
        this(Touhou.id(path), material, type, settings);
    }

    public BasicPolymerArmorItem(Identifier identifier, ArmorMaterial material, EquipmentType type, Settings settings) {
        super(material, type, settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, identifier)));
        this.identifier = identifier;
        if (type.equals(EquipmentType.HELMET)) {
            this.vanillaItem = Items.DIAMOND_HELMET;
            HEAD_ITEMS.add(this);
        } else if (type.equals(EquipmentType.CHESTPLATE)) {
            this.vanillaItem = Items.DIAMOND_CHESTPLATE;
            CHEST_ITEMS.add(this);
        } else if (type.equals(EquipmentType.LEGGINGS)) {
            this.vanillaItem = Items.DIAMOND_LEGGINGS;
            LEG_ITEMS.add(this);
        } else if (type.equals(EquipmentType.BOOTS)) {
            this.vanillaItem = Items.DIAMOND_BOOTS;
            FEET_ITEMS.add(this);
        } else {
            this.vanillaItem = Items.PAPER;
        }
        ITEMS.add(this);
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, PacketContext packetContext) {
        return this.vanillaItem;
    }

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, PacketContext context) {
        ItemStack stack = PolymerItem.super.getPolymerItemStack(itemStack, tooltipType, context);
        return stack;
    }

    @Override
    public @Nullable Identifier getPolymerItemModel(ItemStack stack, PacketContext context) {
        return this.identifier;
    }

}
