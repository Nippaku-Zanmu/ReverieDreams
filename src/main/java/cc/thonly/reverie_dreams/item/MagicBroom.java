package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.data.ModTags;
import cc.thonly.reverie_dreams.entity.MagicBroomEntity;
import cc.thonly.reverie_dreams.entity.ModEntities;
import cc.thonly.reverie_dreams.item.base.BasicPolymerSwordItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagicBroom extends BasicPolymerSwordItem {
    public static final ToolMaterial ROKANKEN = new ToolMaterial(ModTags.Blocks.EMPTY, 1250, 7.5f, 5.5f, 10, ModTags.Items.EMPTY);

    public MagicBroom(String path, float attackDamage, float attackSpeed, Item.Settings settings) {
        super(path, ROKANKEN, attackDamage + 1f, attackSpeed - 2.4f, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        ItemStack itemStack = context.getStack();
        PlayerEntity player = context.getPlayer();
        BlockPos blockPos = context.getBlockPos();
        Hand hand = context.getHand();
        if (!world.isClient() && player != null) {
            MagicBroomEntity entity = new MagicBroomEntity(ModEntities.BROOM_ENTITY_TYPE, world, blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), itemStack.copy());
            world.spawnEntity(entity);
            itemStack.decrementUnlessCreative(1, player);
            player.swingHand(hand);
            return ActionResult.SUCCESS_SERVER;
        }
        return ActionResult.SUCCESS;
    }

}
