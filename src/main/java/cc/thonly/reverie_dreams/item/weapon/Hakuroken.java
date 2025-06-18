package cc.thonly.reverie_dreams.item.weapon;

import cc.thonly.reverie_dreams.data.ModTags;
import cc.thonly.reverie_dreams.item.base.BasicPolymerSwordItem;
import net.minecraft.item.ToolMaterial;

public class Hakuroken extends BasicPolymerSwordItem {
    public static final ToolMaterial HAKUROKEN = new ToolMaterial(ModTags.BlockTypeTag.EMPTY, 1250, 8.0f, 5.5f, 10, ModTags.ItemTypeTag.EMPTY);

    public Hakuroken(String path, float attackDamage, float attackSpeed, Settings settings) {
        super(path, HAKUROKEN, attackDamage + 1f, attackSpeed - 2.4f, settings);
    }
}
