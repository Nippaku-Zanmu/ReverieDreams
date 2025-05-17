package cc.thonly.touhoumod.entity;

import cc.thonly.touhoumod.component.ModDataComponentTypes;
import cc.thonly.touhoumod.item.ModItems;
import cc.thonly.touhoumod.item.base.BasicPolymerDanmakuItem;
import cc.thonly.touhoumod.sound.ModSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;

@FunctionalInterface
public interface MobDanmakuFireLauncher {
    MobDanmakuFireLauncher DEFAULT = new MobDanmakuFireLauncher() {
        @Override
        public void fire(LivingEntity self, Entity target, ServerWorld world) {
            ItemStack stack = new ItemStack(ModItems.FIREBALL_GLOWY.random());
            float[] pitchYaw = getPitchYaw(self, target);
            spawn(world, self, Hand.MAIN_HAND, stack, pitchYaw[0], pitchYaw[1] - 15.0f, 1.0f, 5.0f, 0.35f);
            spawn(world, self, Hand.MAIN_HAND, stack, pitchYaw[0], pitchYaw[1], 1.05f, 5.0f, 0.35f);
            spawn(world, self, Hand.MAIN_HAND, stack, pitchYaw[0], pitchYaw[1] + 15.0f, 1.0f, 5.0f, 0.35f);
        }
    };

    void fire(LivingEntity self, Entity target, ServerWorld world);

    default void sound(LivingEntity self) {
        self.playSound(ModSoundEvents.FIRE);
    }

    static float[] getPitchYaw(Entity self, Entity target) {
        double dx = target.getX() - self.getX();
        double dy = target.getY() - self.getEyeY();
        double dz = target.getZ() - self.getZ();

        double horizontalDistance = Math.sqrt(dx * dx + dz * dz);


        float pitch = (float) (-Math.toDegrees(Math.atan2(dy, horizontalDistance)));
        float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx))) - 90.0f;
        return new float[]{pitch, yaw};
    }

    static DanmakuEntity spawn(ServerWorld world, LivingEntity entity, Hand hand, ItemStack stack, float pitch, float yaw, float speed, float divergence, float offsetDist) {
        Item item = stack.getItem();
        Boolean tile = stack.getOrDefault(ModDataComponentTypes.Danmaku.TILE, false);
        if (item instanceof BasicPolymerDanmakuItem danmakuItem) {
            DanmakuEntity danmakuEntity = new DanmakuEntity(
                    (LivingEntity) entity,
                    stack.copy(),
                    hand,
                    danmakuItem,
                    pitch,
                    yaw,
                    speed,
                    divergence,
                    offsetDist,
                    tile
            );
            world.spawnEntity(danmakuEntity);
            return danmakuEntity;
        }
        return null;
    }
}
