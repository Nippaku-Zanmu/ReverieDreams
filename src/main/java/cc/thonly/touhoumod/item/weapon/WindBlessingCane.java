package cc.thonly.touhoumod.item.weapon;

import cc.thonly.touhoumod.data.ModTags;
import cc.thonly.touhoumod.item.base.BasicPolymerSwordItem;
import net.minecraft.item.ToolMaterial;

public class WindBlessingCane extends BasicPolymerSwordItem {
    public static final ToolMaterial WIND_BLESSING_CANE = new ToolMaterial(ModTags.Blocks.EMPTY, 250, 4.0f, 3.5f, 5, ModTags.Items.EMPTY);

    public WindBlessingCane(String path, float attackDamage, float attackSpeed, Settings settings) {
        super(path, WIND_BLESSING_CANE, attackDamage + 1f, attackSpeed - 2.4f, settings);
    }
}
