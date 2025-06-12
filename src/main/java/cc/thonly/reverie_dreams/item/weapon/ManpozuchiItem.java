package cc.thonly.reverie_dreams.item.weapon;

import cc.thonly.reverie_dreams.data.ModTags;
import cc.thonly.reverie_dreams.interfaces.LivingEntityImpl;
import cc.thonly.reverie_dreams.item.base.BasicPolymerPickaxeItem;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ManpozuchiItem extends BasicPolymerPickaxeItem {
    public static final ToolMaterial MATERIAL = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 59, 2.0F, 0.0F, 15, ModTags.Items.EMPTY);

    public ManpozuchiItem(String path, float attackDamage, float attackSpeed, Settings settings) {
        super(path, MATERIAL, attackDamage + 2.0f, attackSpeed - 2.8f, settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        boolean isSneaking = user.isSneaking();
        if (!world.isClient() && isSneaking && user instanceof ServerPlayerEntity player) {
            ItemStack stackInHand = user.getStackInHand(hand);
            AttributeContainer attributes = player.getAttributes();
            EntityAttributeInstance attributeInstance = attributes.getCustomInstance(EntityAttributes.SCALE);
            if (attributeInstance == null) {
                return ActionResult.PASS;
            }
            LivingEntityImpl lePlayerImpl = (LivingEntityImpl) player;
            double state = lePlayerImpl.getManpozuchiUsingState();
            if (state >= 0.2) {
                attributeInstance.setBaseValue(1.0 * state);
                lePlayerImpl.setManpozuchiUsingState(state - 0.1);
            } else {
                attributeInstance.setBaseValue(1.0);
                lePlayerImpl.setManpozuchiUsingState(1.0);
            }
            if (!user.isInCreativeMode()) {
                stackInHand.damage(1, user);
            }
            user.swingHand(hand);
            return ActionResult.SUCCESS_SERVER;
        }
        return super.use(world, user, hand);
    }
}
