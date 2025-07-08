package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.entity.villager.FumoSellerVillager;
import cc.thonly.reverie_dreams.item.base.BasicPolymerItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FumoLicenseItem extends BasicPolymerItem {
    public FumoLicenseItem(String path, Settings settings) {
        super(path, settings, Items.PAPER);
    }

    public FumoLicenseItem(Identifier identifier, Settings settings) {
        super(identifier, settings, Items.PAPER);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        World world = user.getWorld();
        if (world.isClient()) return ActionResult.SUCCESS;

        if (entity instanceof VillagerEntity villager) {
            BlockPos blockPos = entity.getBlockPos();
            Vec3d pos = villager.getPos();
            Text name = villager.getName();
            boolean hasCN = villager.hasCustomName();
            villager.discard();
            FumoSellerVillager sellerVillager = new FumoSellerVillager(villager.getVillagerData(), world);
            sellerVillager.setPos(pos.getX(), pos.getY(), pos.getZ());
            if (hasCN) {
                sellerVillager.setCustomName(name);
            }
            world.spawnEntity(sellerVillager);
            world.playSound(null, blockPos, SoundEvents.BLOCK_ANVIL_FALL, SoundCategory.PLAYERS);

            stack.decrementUnlessCreative(1, user);
            user.swingHand(hand);

            return ActionResult.SUCCESS_SERVER;
        }
        return ActionResult.FAIL;
    }
}
