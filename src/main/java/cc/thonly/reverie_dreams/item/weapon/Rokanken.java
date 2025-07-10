package cc.thonly.reverie_dreams.item.weapon;

import cc.thonly.reverie_dreams.data.ModTags;
import cc.thonly.reverie_dreams.item.base.BasicPolymerSwordItem;
import net.minecraft.item.ToolMaterial;

public class Rokanken extends BasicPolymerSwordItem {
    public static final ToolMaterial ROKANKEN = new ToolMaterial(ModTags.BlockTypeTag.EMPTY, 1250, 8.0f, 5.5f, 10, ModTags.ItemTypeTag.SILVER_BLOCK);

    public Rokanken(String path, float attackDamage, float attackSpeed, Settings settings) {
        super(path, ROKANKEN, attackDamage + 1f, attackSpeed - 2.4f, settings);
    }
}
