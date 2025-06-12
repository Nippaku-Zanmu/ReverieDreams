package cc.thonly.reverie_dreams.item.weapon;

import cc.thonly.reverie_dreams.data.ModTags;
import cc.thonly.reverie_dreams.item.base.BasicPolymerSwordItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class TreasureHuntingRod extends BasicPolymerSwordItem {
    public static final List<TagKey<Block>> ORE_BLOCK_TAGS = new ArrayList<>();
    public static final ToolMaterial MATERIAL = new ToolMaterial(ModTags.Blocks.EMPTY, 300, 4.0f, 4.5f, 5, ModTags.Items.EMPTY);

    static {
        ORE_BLOCK_TAGS.add(BlockTags.GOLD_ORES);
        ORE_BLOCK_TAGS.add(BlockTags.IRON_ORES);
        ORE_BLOCK_TAGS.add(BlockTags.DIAMOND_ORES);
        ORE_BLOCK_TAGS.add(BlockTags.REDSTONE_ORES);
        ORE_BLOCK_TAGS.add(BlockTags.LAPIS_ORES);
        ORE_BLOCK_TAGS.add(BlockTags.COAL_ORES);
        ORE_BLOCK_TAGS.add(BlockTags.EMERALD_ORES);
        ORE_BLOCK_TAGS.add(BlockTags.COPPER_ORES);
    }

    public TreasureHuntingRod(String path, float attackDamage, float attackSpeed, Settings settings) {
        super(path, MATERIAL, attackDamage + 2.0f, attackSpeed - 2.8f, settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            ServerPlayerEntity player = (ServerPlayerEntity) user;
            ItemStack stack = player.getStackInHand(hand);
            ItemCooldownManager cooldown = player.getItemCooldownManager();

            if (player.isSneaking()) {
                player.swingHand(hand);

                BlockPos origin = player.getBlockPos();
                int radius = 8;
                AtomicBoolean found = new AtomicBoolean(false);

                BlockPos.iterate(
                        origin.add(-radius, -radius, -radius),
                        origin.add(radius, radius, radius)
                ).forEach(pos -> {
                    if (world.isInBuildLimit(pos)) {
                        if (isOre(world.getBlockState(pos))) {
                            found.set(true);
                        }
                    }
                });

                if (found.get()) {
                    world.playSound(null, user.getX(), user.getEyeY(), user.getZ(), SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(), user.getSoundCategory(), 1.0f, 1.0f);
                }

                if (!player.isInCreativeMode()) {
                    stack.damage(1, player);
                }

                cooldown.set(stack, 35);
                return ActionResult.SUCCESS_SERVER;
            }
        }
        return super.use(world, user, hand);
    }

    public static boolean isOre(BlockState blockState) {
        for (TagKey<Block> tag : ORE_BLOCK_TAGS) {
            if (blockState.isIn(tag)) {
                return true;
            }
        }
        return false;
    }

}
