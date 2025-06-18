package cc.thonly.reverie_dreams.item.weapon;

import cc.thonly.reverie_dreams.data.ModTags;
import cc.thonly.reverie_dreams.item.base.BasicPolymerSwordItem;
import net.minecraft.item.ToolMaterial;

public class WindBlessingCane extends BasicPolymerSwordItem {
    public static final ToolMaterial WIND_BLESSING_CANE = new ToolMaterial(ModTags.BlockTypeTag.EMPTY, 250, 4.0f, 3.5f, 5, ModTags.ItemTypeTag.EMPTY);

    public WindBlessingCane(String path, float attackDamage, float attackSpeed, Settings settings) {
        super(path, WIND_BLESSING_CANE, attackDamage + 1f, attackSpeed - 2.4f, settings);
    }
}
