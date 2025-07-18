package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.entity.npc.NPCEntityImpl;
import cc.thonly.reverie_dreams.entity.npc.NPCRoleEntityImpl;
import cc.thonly.reverie_dreams.item.base.BasicPolymerItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class CursedDecoyDollItem extends BasicPolymerItem {
    public CursedDecoyDollItem(String path, Item.Settings settings) {
        super(path, settings, Items.ARMOR_STAND);
    }

    public CursedDecoyDollItem(Identifier identifier, Item.Settings settings) {
        super(identifier, settings, Items.ARMOR_STAND);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        if (!world.isClient && world instanceof ServerWorld serverWorld && player instanceof ServerPlayerEntity serverPlayer) {
            ItemStack stackInHand = serverPlayer.getStackInHand(hand);
            ArmorStandEntity armorStandEntity = new ArmorStandEntity(serverWorld, blockPos.getX(), blockPos.getY(), blockPos.getZ());
            serverWorld.spawnEntity(armorStandEntity);
            List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, new Box(blockPos).expand(24), livingEntity -> true);
            for (LivingEntity livingEntity : list) {
                if (livingEntity instanceof PlayerEntity) continue;
                if (livingEntity instanceof NPCRoleEntityImpl role) {
                    LivingEntity attacker = role.getAttacker();
                    LivingEntity target = role.getTarget();
                    if (attacker == player || target == player) {
                        continue;
                    }
                }
                if (livingEntity instanceof MobEntity mob) {
                    if (mob.getTarget() != null) {
                        mob.setTarget(armorStandEntity);
                    }
                }
                if (livingEntity.getAttacker() != null) {
                    livingEntity.setAttacker(armorStandEntity);
                }
            }
            stackInHand.decrementUnlessCreative(1, serverPlayer);
            return ActionResult.SUCCESS_SERVER;
        }
        return ActionResult.SUCCESS;
    }
}
