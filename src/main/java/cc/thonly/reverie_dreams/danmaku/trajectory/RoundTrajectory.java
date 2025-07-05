package cc.thonly.reverie_dreams.danmaku.trajectory;

import cc.thonly.reverie_dreams.danmaku.DanmakuTrajectory;
import cc.thonly.reverie_dreams.item.base.DanmakuItemType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class RoundTrajectory extends DanmakuTrajectory {
    private static final int BULLET_COUNT = 12;     // 子弹数量
    private static final float RADIUS = 1f;         // 环形半径
    private static final float ANGLE_OFFSET = 0f;   // 起始角度偏移

    @Override
    public void run(ServerWorld world, @Nullable LivingEntity livingEntity, ItemStack stack,
                    Double x, Double y, Double z, float pitch, float yaw,
                    float speed, float acceleration, float divergence, float offsetDist,
                    DanmakuItemType pThis) {
        double centerX = x;
        double centerY = y;
        double centerZ = z;

        for (int i = 0; i < BULLET_COUNT; i++) {
            float angleDeg = ((360f / BULLET_COUNT) * i) + ANGLE_OFFSET;
            float angleRad = (float) Math.toRadians(angleDeg);

            // 基础圆上单位向量（绕Y轴）
            Vector3f offset = new Vector3f((float) Math.cos(angleRad), 0, (float) Math.sin(angleRad));

            // 构造摄像机方向的旋转四元数（注意Minecraft是pitch绕X，yaw绕Y，顺序是Yaw * Pitch）
            Quaternionf rotation = new Quaternionf()
                    .rotateY((float) Math.toRadians(-yaw))
                    .rotateX((float) Math.toRadians(-pitch));
            offset.rotate(rotation);

            // 计算弹幕位置
            Vec3d pos = new Vec3d(centerX + offset.x * RADIUS, centerY + offset.y * RADIUS, centerZ + offset.z * RADIUS);

            // 将方向转换为pitch/yaw
            Vec3d dir = new Vec3d(offset.x, offset.y, offset.z).normalize();
            float bulletYaw = (float) Math.toDegrees(Math.atan2(-dir.x, -dir.z));
            float bulletPitch = (float) Math.toDegrees(-Math.asin(dir.y));

            spawnByItemStack(
                    world,
                    livingEntity,
                    pos.x,
                    pos.y,
                    pos.z,
                    stack,
                    bulletPitch,
                    bulletYaw,
                    speed,
                    acceleration,
                    divergence,
                    offsetDist
            );
        }
    }

}
