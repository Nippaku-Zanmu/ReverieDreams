package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.component.ModDataComponentTypes;
import cc.thonly.reverie_dreams.component.RoleFollowerArchive;
import cc.thonly.reverie_dreams.item.base.BasicPolymerItem;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class RoleFollowerArchiveItem extends BasicPolymerItem {

    public RoleFollowerArchiveItem(String path, Settings settings) {
        super(path, settings, Items.TRIAL_KEY);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World contextWorld = context.getWorld();
        PlayerEntity player = context.getPlayer();
        if (!contextWorld.isClient() && contextWorld instanceof ServerWorld world && player != null) {
            ItemStack stack = context.getStack();
            RoleFollowerArchive followerArchive = stack.get(ModDataComponentTypes.ROLE_FOLLOWER_ARCHIVE);
            if (followerArchive == null) {
                return ActionResult.PASS;
            }
            boolean canRespawn = stack.getOrDefault(ModDataComponentTypes.ROLE_CAN_RESPAWN, false);
            if (!canRespawn) {
                return ActionResult.PASS;
            }
            followerArchive.respawn(world, context.getBlockPos().up(), world.getRegistryManager());
            stack.decrementUnlessCreative(1, player);
            player.swingHand(context.getHand());
            return ActionResult.SUCCESS_SERVER;
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
        RoleFollowerArchive archive = stack.get(ModDataComponentTypes.ROLE_FOLLOWER_ARCHIVE);
        if (archive != null) {
            String name = archive.getName();
            MutableText main = Text.empty();
            MutableText mutableText = Text.Serialization.fromJson(name, Touhou.getDynamicRegistryManager());
            main.append("Name: ");
            main.append(mutableText);
            textConsumer.accept(main);
        }
    }
}
