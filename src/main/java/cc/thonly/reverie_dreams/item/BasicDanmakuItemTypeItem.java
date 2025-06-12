package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.component.ModDataComponentTypes;
import cc.thonly.reverie_dreams.danmaku.DanmakuTrajectory;
import cc.thonly.reverie_dreams.item.base.BasicPolymerDanmakuItemItem;
import cc.thonly.reverie_dreams.registry.RegistrySchemas;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class BasicDanmakuItemTypeItem extends BasicPolymerDanmakuItemItem {
    public BasicDanmakuItemTypeItem(String path, Settings settings) {
        super(path, settings, Items.SNOWBALL);
    }

    @Override
    public void shoot(ServerWorld serverWorld, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand).copy();
        String templateType = stack.getOrDefault(ModDataComponentTypes.Danmaku.TEMPLATE, Touhou.id("single").toString());
        DanmakuTrajectory danmakuTrajectory = RegistrySchemas.DANMAKU_TRAJECTORY.get(Identifier.of(templateType));
        Float speed = stack.getOrDefault(ModDataComponentTypes.Danmaku.SPEED, 1.0f);
        Float acceleration = stack.getOrDefault(ModDataComponentTypes.Danmaku.ACCELERATION, 0.0f);
        danmakuTrajectory.run(serverWorld, user, stack, user.getX(), user.getY(), user.getZ(), user.getPitch(), user.getYaw(), speed, acceleration, 0f,1.5f, this);
     }
}
