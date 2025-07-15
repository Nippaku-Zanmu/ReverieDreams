package cc.thonly.reverie_dreams.item.weapon;

import cc.thonly.reverie_dreams.data.ModTags;
import cc.thonly.reverie_dreams.item.base.BasicPolymerMiningToolItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NueTrident extends BasicPolymerMiningToolItem {
    public static final ToolMaterial NUE_TRIDENT = new ToolMaterial(ModTags.BlockTypeTag.MIN_TOOL, 450, 4.5f, 5.5f, 1, ItemTags.NETHERITE_TOOL_MATERIALS);

    public NueTrident(String path, float attackDamage, float attackSpeed, Settings settings) {
        super(path, NUE_TRIDENT, attackDamage + 3.5f, attackSpeed - 2.8f, settings);
    }

    @Override
    public boolean canMine(ItemStack stack, BlockState state, World world, BlockPos pos, LivingEntity user) {
        if (!world.isClient && user instanceof ServerPlayerEntity player) {
            boolean b = super.canMine(stack, state, world, pos, user);
            return b && !player.isInCreativeMode();
        }
        return super.canMine(stack, state, world, pos, user);
    }
}
