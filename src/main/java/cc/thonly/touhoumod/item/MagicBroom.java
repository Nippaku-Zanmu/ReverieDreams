package cc.thonly.touhoumod.item;

import cc.thonly.touhoumod.data.ModTags;
import cc.thonly.touhoumod.item.base.BasicPolymerSwordItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

public class MagicBroom extends BasicPolymerSwordItem {
    public static final ToolMaterial ROKANKEN = new ToolMaterial(ModTags.Blocks.EMPTY, 1250, 7.5f, 5.5f, 10, ModTags.Items.EMPTY);

    public MagicBroom(String path, float attackDamage, float attackSpeed, Item.Settings settings) {
        super(path, ROKANKEN, attackDamage + 1f, attackSpeed - 2.4f, settings);
    }
}
