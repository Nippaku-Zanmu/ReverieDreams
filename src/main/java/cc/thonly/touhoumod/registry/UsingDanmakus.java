package cc.thonly.touhoumod.registry;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.item.base.UsingDanmaku;
import net.minecraft.item.ItemStack;

public class UsingDanmakus {
    public static final UsingDanmaku SINGLE = RegistrySchemas.register(RegistrySchemas.USING_DANMAKU, Touhou.id("single"), (world, user, hand, pThis) -> {
        ItemStack itemStack = user.getStackInHand(hand).copy();
        float pitch = user.getPitch();
        float yaw = user.getYaw();
        UsingDanmaku.spawn(world, user, hand, itemStack, pitch, yaw, 1.4f, 5.0f);
    });
    public static final UsingDanmaku TRIPLE = RegistrySchemas.register(RegistrySchemas.USING_DANMAKU, Touhou.id("triple"), (world, user, hand, pThis) -> {
        ItemStack itemStack = user.getStackInHand(hand).copy();
        float pitch = user.getPitch();
        float yaw = user.getYaw();
        UsingDanmaku.spawn(world, user, hand, itemStack, pitch, yaw - 15.0f, 1.4f, 5.0f);
        UsingDanmaku.spawn(world, user, hand, itemStack, pitch, yaw, 1.4f, 5.0f);
        UsingDanmaku.spawn(world, user, hand, itemStack, pitch, yaw + 15.0f, 1.4f, 5.0f);

    });

    public static void bootstrap(RegistrySchema<UsingDanmaku> registry) {

    }
}
