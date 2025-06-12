package cc.thonly.reverie_dreams.item.weapon;

import cc.thonly.reverie_dreams.data.ModTags;
import cc.thonly.reverie_dreams.item.base.BasicPolymerMiningToolItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NueTrident extends BasicPolymerMiningToolItem {
    public static final ToolMaterial NUE_TRIDENT = new ToolMaterial(ModTags.Blocks.MIN_TOOL, 450, 4.5f, 5.5f, 1, ModTags.Items.EMPTY);

    public NueTrident(String path, float attackDamage, float attackSpeed, Settings settings) {
        super(path, NUE_TRIDENT, attackDamage + 3.5f, attackSpeed - 2.8f, settings);
    }


    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }
}
