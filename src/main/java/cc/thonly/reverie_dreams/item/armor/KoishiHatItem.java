package cc.thonly.reverie_dreams.item.armor;

import cc.thonly.reverie_dreams.armor.KoishiHatArmorMaterial;
import cc.thonly.reverie_dreams.item.base.BasicPolymerArmorItem;
import cc.thonly.reverie_dreams.util.Vec3d2Player;
import net.minecraft.item.equipment.EquipmentType;

import java.util.ArrayList;
import java.util.List;

public class KoishiHatItem extends BasicPolymerArmorItem {
    public static final List<Vec3d2Player> VEC_3_DS = new ArrayList<>();

    public KoishiHatItem(String path, Settings settings) {
        super(path, KoishiHatArmorMaterial.INSTANCE, EquipmentType.HELMET, settings);
    }

}