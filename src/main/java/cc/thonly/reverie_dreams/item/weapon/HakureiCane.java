package cc.thonly.reverie_dreams.item.weapon;

import cc.thonly.reverie_dreams.data.ModTags;
import cc.thonly.reverie_dreams.item.base.BasicPolymerSwordItem;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.ItemTags;

public class HakureiCane extends BasicPolymerSwordItem {
    public static final ToolMaterial HAKUREI_CANE = new ToolMaterial(ModTags.BlockTypeTag.EMPTY, 250, 4.0f, 3.5f, 5, ItemTags.IRON_TOOL_MATERIALS);

    public HakureiCane(String path, float attackDamage, float attackSpeed, Settings settings) {
        super(path, HAKUREI_CANE, attackDamage + 1f, attackSpeed - 2.4f, settings);
    }
}
