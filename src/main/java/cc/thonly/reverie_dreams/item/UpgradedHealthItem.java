package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.interfaces.LivingEntityImpl;
import cc.thonly.reverie_dreams.item.base.BasicPolymerItem;
import cc.thonly.reverie_dreams.sound.SoundEventInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class UpgradedHealthItem extends BasicPolymerItem {
    public UpgradedHealthItem(String path, Settings settings) {
        super(path, settings, Items.PAPER);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && world instanceof ServerWorld serverWorld) {
            ItemStack itemStack = user.getStackInHand(hand);
            world.playSound(null, user.getX(), user.getEyeY(), user.getZ(), SoundEventInit.UP, user.getSoundCategory(), 1.0f, 1.0f);
            LivingEntityImpl modifier = (LivingEntityImpl) user;
            float value = modifier.getMaxHealthModifier();
            modifier.setMaxHealthModifier(value + 2);
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            itemStack.decrementUnlessCreative(1, user);
            return ActionResult.SUCCESS_SERVER;
        }
        return ActionResult.SUCCESS;
    }
}
