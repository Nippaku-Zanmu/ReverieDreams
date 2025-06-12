package cc.thonly.reverie_dreams.item.weapon;

import cc.thonly.reverie_dreams.data.ModTags;
import cc.thonly.reverie_dreams.item.base.BasicPolymerSwordItem;
import net.minecraft.item.ToolMaterial;

public class PapilioPatternFan extends BasicPolymerSwordItem {
    public static final ToolMaterial PAPILIO_PATTERN_FAN = new ToolMaterial(ModTags.Blocks.EMPTY, 1561, 8.0f, 5f, 10, ModTags.Items.EMPTY);

    public PapilioPatternFan(String path, float attackDamage, float attackSpeed, Settings settings) {
        super(path, PAPILIO_PATTERN_FAN, attackDamage + 1f, attackSpeed - 2.4f, settings);
    }
}
