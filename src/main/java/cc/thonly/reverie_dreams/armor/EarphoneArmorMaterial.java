package cc.thonly.reverie_dreams.armor;

import cc.thonly.reverie_dreams.Touhou;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;

import java.util.Map;

public interface EarphoneArmorMaterial {
    int BASE_DURABILITY = 150;
    RegistryKey<EquipmentAsset> REGISTRY_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Touhou.id("earphone"));

    ArmorMaterial INSTANCE = new ArmorMaterial(
            BASE_DURABILITY,
            Map.of(
                    EquipmentType.HELMET, 3,
                    EquipmentType.CHESTPLATE, 8,
                    EquipmentType.LEGGINGS, 6,
                    EquipmentType.BOOTS, 3
            ),
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            ItemTags.REPAIRS_GOLD_ARMOR,
            REGISTRY_KEY
    );
}
