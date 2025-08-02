package cc.thonly.reverie_dreams.item.debug;

import cc.thonly.reverie_dreams.item.base.BasicPolymerItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class OwnerStickItem extends BasicPolymerItem {
    public OwnerStickItem(String path, Settings settings) {
        super(path, settings.maxCount(1), Items.STICK);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        World world = user.getWorld();
        if (world.isClient()) return ActionResult.SUCCESS;
        if (entity instanceof TameableEntity target) {
            target.setOwner(user);
            ((ServerWorld) world).spawnParticles(ParticleTypes.HEART, target.getX(), target.getY() + 1.0, target.getZ(), 5, 0.5, 0.5, 0.5, 0.1);
        }
        return ActionResult.SUCCESS_SERVER;
    }
}
