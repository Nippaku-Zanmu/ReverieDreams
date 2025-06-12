package cc.thonly.reverie_dreams.item.base;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.component.ModDataComponentTypes;
import cc.thonly.reverie_dreams.item.entry.DanmakuColor;
import cc.thonly.reverie_dreams.sound.ModSoundEvents;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;

@Setter
@Getter
@ToString
public abstract class BasicPolymerDanmakuItemItem extends BasicPolymerItem implements DanmakuItemType {
    public static final Integer DEFAULT_COUNT = 3;
    public BasicPolymerDanmakuItemItem(String path, Settings settings, Item item) {
        super(path, settings.maxCount(1), item != null ? item : Items.SNOWBALL);
    }

    public BasicPolymerDanmakuItemItem(String path, Settings settings) {
        this(path, settings.maxCount(1), Items.SNOWBALL);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        Boolean isInfinite = itemStack.getOrDefault(ModDataComponentTypes.Danmaku.INFINITE, false);
        if (!world.isClient && world instanceof ServerWorld serverWorld) {
            var cooldownManager = user.getItemCooldownManager();
            cooldownManager.set(itemStack, 10);
            for (int i = 0; i < itemStack.getOrDefault(ModDataComponentTypes.Danmaku.COUNT, DEFAULT_COUNT); i++) {
                this.shoot(serverWorld, user, hand);
            }
            if (!isInfinite) {
                itemStack.damage(1, user);
            }

            world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSoundEvents.FIRE, SoundCategory.NEUTRAL, 1f, 1.0f);
            return ActionResult.SUCCESS_SERVER;
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        Integer colorId = stack.getOrDefault(ModDataComponentTypes.Danmaku.COLOR, -1);
        Float damage = stack.getOrDefault(ModDataComponentTypes.Danmaku.DAMAGE, null);
        Float scale = stack.getOrDefault(ModDataComponentTypes.Danmaku.SCALE, null);
        Float speed = stack.getOrDefault(ModDataComponentTypes.Danmaku.SPEED, null);
        Integer count = stack.getOrDefault(ModDataComponentTypes.Danmaku.COUNT, DEFAULT_COUNT);
        String templateType = stack.getOrDefault(ModDataComponentTypes.Danmaku.TEMPLATE, Touhou.id("single").toString());
        DanmakuColor colorEnum = DanmakuColor.fromIndex(colorId);

        tooltip.add(Text.empty());
        tooltip.add(Text.empty().append(Text.translatable("item.tooltip.color")).append(colorEnum.getEnglishTranslation()));
        tooltip.add(Text.empty().append(Text.translatable("item.tooltip.damage")).append(String.valueOf(damage)));
        tooltip.add(Text.empty().append(Text.translatable("item.tooltip.speed")).append(String.valueOf(speed)));
        tooltip.add(Text.empty().append(Text.translatable("item.tooltip.count")).append(String.valueOf(count)));
        tooltip.add(Text.empty().append(Text.translatable("item.tooltip.base_type")).append(templateType));

    }

    public abstract void shoot(ServerWorld serverWorld, PlayerEntity user, Hand hand);
}
