package cc.thonly.reverie_dreams.item.armor;

import cc.thonly.reverie_dreams.armor.EarphoneArmorMaterial;
import cc.thonly.reverie_dreams.item.base.BasicPolymerArmorItem;
import cc.thonly.reverie_dreams.server.ParticleTickerManager;
import cc.thonly.reverie_dreams.util.Vec3d2Player;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class EarphoneItem extends BasicPolymerArmorItem {
    public static final List<Vec3d2Player> VEC_3_DS = new ArrayList<>();

    public EarphoneItem(String path, Item.Settings settings) {
        super(path, EarphoneArmorMaterial.INSTANCE, EquipmentType.HELMET, settings);
    }

    public static synchronized void onUseTick(World world, LivingEntity user, ItemStack stack) {
        if (!world.isClient() && world instanceof ServerWorld sWorld && user instanceof ServerPlayerEntity player) {
            if (!VEC_3_DS.isEmpty()) {
                for (Vec3d2Player vec3d2Player : VEC_3_DS) {
                    if (vec3d2Player.player() == user) {
                        continue;
                    }
                    double x1 = vec3d2Player.vec3d().x;
                    double y1 = vec3d2Player.vec3d().y;
                    double z1 = vec3d2Player.vec3d().z;
                    double x2 = user.getX();
                    double y2 = user.getY();
                    double z2 = user.getZ();
                    double distance = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2) + Math.pow((z2 - z1), 2));
                    if (distance < 32) {
                        ParticleTickerManager.joinQueue(sWorld, ParticleTypes.SONIC_BOOM, 10, vec3d2Player.vec3d(), user.getPos(), 1.0f);
                    }
                }
            }
        }
    }
}