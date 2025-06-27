package cc.thonly.reverie_dreams.item.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Deprecated
@Setter
@Getter
@ToString
public abstract class BasicPolymerSpellCardItem extends BasicPolymerItem {

    public BasicPolymerSpellCardItem(String path, Settings settings) {
        super(path, settings.maxCount(1), Items.MAP);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            ItemStack offHandStack = user.getStackInHand(Hand.OFF_HAND);
            if (offHandStack.getItem() instanceof BasicPolymerDanmakuItem item) {
                int amount = this.getBulletConsumption();
                if (!offHandStack.isEmpty() && (offHandStack.getCount() >= amount || user.isCreative())) {
                    spellCard((ServerWorld) world, user, hand, offHandStack);
                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                    user.incrementStat(Stats.USED.getOrCreateStat(offHandStack.getItem()));
                    offHandStack.decrementUnlessCreative(amount, user);
                    return ActionResult.SUCCESS_SERVER;
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    public void spawnDanmakuEntity(ServerWorld world, LivingEntity user, Hand hand, ItemStack offHandStack, float pitch, float yaw, float speed, float divergence) {
//        Item item = offHandStack.getItem();
//        if (item instanceof BasicPolymerDanmakuItem danmakuItem) {
//            DanmakuEntity danmakuEntity = new DanmakuEntity(
//                    (LivingEntity) user,
//                    offHandStack.copy(),
//                    hand,
//                    danmakuItem,
//                    pitch,
//                    yaw,
//                    speed,
//                    divergence,
//                    0.4f
//            );
//            world.spawnEntity(danmakuEntity);
//            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 0.1f, 1.2f);
//        }
    }


    public abstract void spellCard(ServerWorld world, PlayerEntity user, Hand hand, ItemStack offHandStack);

    public abstract int getBulletConsumption();

    public void defaultSpellCard(ServerWorld world, PlayerEntity user, Hand hand, ItemStack offHandStack) {
//        Item item = offHandStack.getItem();
//        if (item instanceof BasicPolymerDanmakuItem) {
//            DanmakuEntity danmakuEntity = new DanmakuEntity((LivingEntity) user, user.getStackInHand(hand).copy(), hand, offHandStack.getItem());
//            world.spawnEntity(danmakuEntity);
//            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 0.1f, 1.2f);
//        }
    }

}
