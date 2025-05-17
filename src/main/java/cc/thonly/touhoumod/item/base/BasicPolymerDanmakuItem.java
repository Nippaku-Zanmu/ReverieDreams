package cc.thonly.touhoumod.item.base;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.component.ModDataComponentTypes;
import cc.thonly.touhoumod.item.entry.DanmakuItemEntries;
import cc.thonly.touhoumod.sound.ModSoundEvents;
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
public abstract class BasicPolymerDanmakuItem extends BasicPolymerItem implements UsableDanmaku {
    public static final Integer DEFAULT_COUNT = 3;
    public BasicPolymerDanmakuItem(String path, Settings settings, Item item) {
        super(path, settings.maxCount(1), item != null ? item : Items.SNOWBALL);
    }

    public BasicPolymerDanmakuItem(String path, Settings settings) {
        this(path, settings.maxCount(1), Items.SNOWBALL);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        Boolean isInfinite = itemStack.getOrDefault(ModDataComponentTypes.Danmaku.INFINITE, false);
        if (!world.isClient && world instanceof ServerWorld serverWorld) {
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
        Boolean infinite = stack.getOrDefault(ModDataComponentTypes.Danmaku.INFINITE, false);
        Integer count = stack.getOrDefault(ModDataComponentTypes.Danmaku.COUNT, DEFAULT_COUNT);
        String templateType = stack.getOrDefault(ModDataComponentTypes.Danmaku.TEMPLATE, Touhou.id("single").toString());
        DanmakuItemEntries.ColorEnum colorEnum = DanmakuItemEntries.ColorEnum.fromIndex(colorId);

        tooltip.add(Text.of("Color: " + colorEnum.getEnglishTranslation()));
        tooltip.add(Text.of("Damage: " + damage));
        tooltip.add(Text.of("Speed: " + speed));
        tooltip.add(Text.of("Infinite: " + infinite));
        tooltip.add(Text.of("Count: " + count));
        tooltip.add(Text.of("Type: " + templateType));

    }

    public abstract void shoot(ServerWorld serverWorld, PlayerEntity user, Hand hand);
}
