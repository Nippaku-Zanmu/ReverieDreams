package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.component.ModDataComponentTypes;
import cc.thonly.reverie_dreams.item.base.BasicPolymerDanmakuItem;
import cc.thonly.reverie_dreams.item.base.BasicPolymerItem;
import cc.thonly.reverie_dreams.item.entry.DanmakuColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class SpellCardTemplateItem extends BasicPolymerItem {
    public SpellCardTemplateItem(String path, Settings settings) {
        super(path, settings, Items.PAPER);
    }

    public SpellCardTemplateItem(Identifier identifier, Settings settings) {
        super(identifier, settings, Items.PAPER);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        Integer colorId = stack.getOrDefault(ModDataComponentTypes.Danmaku.COLOR, -1);
        Float damage = stack.getOrDefault(ModDataComponentTypes.Danmaku.DAMAGE, null);
        Float scale = stack.getOrDefault(ModDataComponentTypes.Danmaku.SCALE, null);
        Float speed = stack.getOrDefault(ModDataComponentTypes.Danmaku.SPEED, null);
        Integer count = stack.getOrDefault(ModDataComponentTypes.Danmaku.COUNT, BasicPolymerDanmakuItem.DEFAULT_COUNT);
        String templateType = stack.getOrDefault(ModDataComponentTypes.Danmaku.TEMPLATE, Touhou.id("single").toString());
        DanmakuColor colorEnum = DanmakuColor.fromIndex(colorId);

        tooltip.add(Text.empty());
        tooltip.add(Text.empty().append(Text.translatable("item.tooltip.base_type")).append(Text.translatable(Identifier.of(templateType).toTranslationKey())));

    }
}
