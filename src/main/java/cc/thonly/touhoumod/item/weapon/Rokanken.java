package cc.thonly.touhoumod.item.weapon;

import cc.thonly.touhoumod.data.ModTags;
import cc.thonly.touhoumod.item.base.BasicPolymerSwordItem;
import net.minecraft.item.ToolMaterial;

public class Rokanken extends BasicPolymerSwordItem {
    public static final ToolMaterial ROKANKEN = new ToolMaterial(ModTags.Blocks.EMPTY, 1250, 8.0f, 5.5f, 10, ModTags.Items.EMPTY);

    public Rokanken(String path, float attackDamage, float attackSpeed, Settings settings) {
        super(path, ROKANKEN, attackDamage + 1f, attackSpeed - 2.4f, settings);
    }
}
