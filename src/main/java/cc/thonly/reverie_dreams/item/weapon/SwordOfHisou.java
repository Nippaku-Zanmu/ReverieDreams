package cc.thonly.reverie_dreams.item.weapon;

import cc.thonly.reverie_dreams.data.ModTags;
import cc.thonly.reverie_dreams.item.base.BasicPolymerSwordItem;
import net.minecraft.item.ToolMaterial;

public class SwordOfHisou extends BasicPolymerSwordItem {
    public static final ToolMaterial HISOU = new ToolMaterial(ModTags.Blocks.EMPTY, 1561, 8.0f, 3f, 10, ModTags.Items.EMPTY);

    public SwordOfHisou(String path, float attackDamage, float attackSpeed, Settings settings) {
        super(path, HISOU, attackDamage + 1, attackSpeed - 2.4f, settings);
    }

}
