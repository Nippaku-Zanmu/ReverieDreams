package cc.thonly.reverie_dreams.item.armor;

import cc.thonly.reverie_dreams.armor.KoishiHatArmorMaterial;
import cc.thonly.reverie_dreams.item.base.BasicPolymerArmorItem;
import cc.thonly.reverie_dreams.util.Vec3d2Player;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class KoishiHatItem extends BasicPolymerArmorItem {

    public KoishiHatItem(String path, Settings settings) {
        super(path, KoishiHatArmorMaterial.INSTANCE, EquipmentType.HELMET, settings);
    }

    public static synchronized void onUseTick(World world, LivingEntity user, ItemStack stack) {
        if (user.getVelocity().lengthSquared() <= 1 && user.isSneaking()) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 10, 0, false, false, false));
        }
    }
}