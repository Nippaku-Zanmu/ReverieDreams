package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.item.base.BasicPolymerItem;
import cc.thonly.reverie_dreams.server.DelayedTask;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTickManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TimeStopClock extends BasicPolymerItem {
    public TimeStopClock(String path, Settings settings) {
        super(path, settings.maxCount(1).maxDamage(200).repairable(ItemTags.GOLD_TOOL_MATERIALS), Items.CLOCK);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        MinecraftServer server = world.getServer();
        if (!world.isClient() && server != null) {
            ItemStack itemStack = user.getStackInHand(hand);
            ServerTickManager tickManager = server.getTickManager();
            tickManager.setFrozen(!tickManager.isFrozen());
            if (!user.isInCreativeMode()) {
                itemStack.damage(1, user);
                if (itemStack.isDamageable() && itemStack.getDamage() >= itemStack.getMaxDamage()) {
                    itemStack.decrement(1);
                }
            }
            DelayedTask.create(server, 20 * 20, () -> tickManager.setFrozen(false));
            return ActionResult.SUCCESS_SERVER;
        }
        return ActionResult.SUCCESS;
    }
}
