package cc.thonly.touhoumod.item.base;

import cc.thonly.touhoumod.component.ModDataComponentTypes;
import cc.thonly.touhoumod.entity.DanmakuEntity;
import eu.pb4.polymer.core.api.utils.PolymerObject;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;

@FunctionalInterface
public interface UsingDanmaku extends PolymerObject {
    void use(ServerWorld serverWorld, PlayerEntity user, Hand hand, UsableDanmaku pThis);
    static DanmakuEntity spawn(ServerWorld world, LivingEntity user, Hand hand, ItemStack stack, float pitch, float yaw, float speed, float divergence) {
        Item item = stack.getItem();
        Boolean tile = stack.getOrDefault(ModDataComponentTypes.Danmaku.TILE, false);
        if (item instanceof BasicPolymerDanmakuItem danmakuItem) {
            DanmakuEntity danmakuEntity = new DanmakuEntity(
                    (LivingEntity) user,
                    stack.copy(),
                    hand,
                    danmakuItem,
                    pitch,
                    yaw,
                    speed,
                    divergence,
                    0.4f,
                    tile
            );
            world.spawnEntity(danmakuEntity);
            return danmakuEntity;
        }
        return null;
    }
}
