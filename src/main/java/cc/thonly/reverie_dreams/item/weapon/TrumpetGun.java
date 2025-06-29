package cc.thonly.reverie_dreams.item.weapon;

import cc.thonly.reverie_dreams.danmaku.DanmakuTrajectory;
import cc.thonly.reverie_dreams.entity.DanmakuEntity;
import cc.thonly.reverie_dreams.item.ModItems;
import cc.thonly.reverie_dreams.item.base.BasicPolymerItem;
import cc.thonly.reverie_dreams.item.base.DanmakuItemType;
import cc.thonly.reverie_dreams.server.DelayedTask;
import cc.thonly.reverie_dreams.sound.SoundEventInit;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TrumpetGun extends BasicPolymerItem implements DanmakuItemType {

    public TrumpetGun(String path, Settings settings) {
        super(path, settings.maxCount(1).maxDamage(250), Items.TRIAL_KEY);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient() && user instanceof ServerPlayerEntity player) {
            ServerWorld serverWorld = (ServerWorld) world;
            float pitch = user.getPitch();
            float yaw = user.getYaw();
            DelayedTask.repeat(world.getServer(), 3, 2, () -> {
                DanmakuEntity entity = DanmakuTrajectory.spawnByItemStack(serverWorld, user, user.getX(), user.getY(), user.getZ(), new ItemStack(ModItems.BULLET.random()), pitch, yaw, 1.4f, 0f, 0.0f, 1.5f);
                entity.playSound(SoundEventInit.FIRE, 1.0f, 1.0f);
            });
            ItemCooldownManager itemCooldownManager = player.getItemCooldownManager();
            itemCooldownManager.set(stack, 35);
            if (!player.isInCreativeMode()) {
                stack.damage(1, player);
            }
            return ActionResult.SUCCESS_SERVER;
        }
        return super.use(world, user, hand);
    }

}
