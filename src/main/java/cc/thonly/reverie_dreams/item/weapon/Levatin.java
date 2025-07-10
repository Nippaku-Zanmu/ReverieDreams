package cc.thonly.reverie_dreams.item.weapon;

import cc.thonly.reverie_dreams.data.ModTags;
import cc.thonly.reverie_dreams.item.base.BasicPolymerSwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.ItemTags;

public class Levatin extends BasicPolymerSwordItem {
    public static final ToolMaterial LEVATIN = new ToolMaterial(ModTags.BlockTypeTag.EMPTY, 1561, 8.0f, 5.5f, 10, ItemTags.NETHERITE_TOOL_MATERIALS);

    public Levatin(String path, float attackDamage, float attackSpeed, Settings settings) {
        super(path, LEVATIN, attackDamage + 1, attackSpeed - 2.4f, settings);
    }
}
