package cc.thonly.reverie_dreams.danmaku.trajectory;

import cc.thonly.reverie_dreams.danmaku.DanmakuTrajectory;
import cc.thonly.reverie_dreams.item.base.IDanmakuItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;

public class SingleTrajectory extends DanmakuTrajectory {
    @Override
    public void run(ServerWorld world, @Nullable LivingEntity livingEntity, ItemStack stack, Double x, Double y, Double z, float pitch, float yaw, float speed, float acceleration, float divergence, float offsetDist, IDanmakuItem pThis) {
        DanmakuTrajectory.spawnByItemStack(world, livingEntity, x, y, z, stack, pitch, yaw, 1.4f, acceleration, 5.0f, 0.25f);

    }
}
