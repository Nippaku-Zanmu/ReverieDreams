package cc.thonly.touhoumod.item.weapon;

import cc.thonly.touhoumod.data.ModTags;
import cc.thonly.touhoumod.item.base.BasicPolymerSwordItem;
import net.minecraft.item.ToolMaterial;

public class Gungnir extends BasicPolymerSwordItem {
    public static final ToolMaterial GUNGNIR = new ToolMaterial(ModTags.Blocks.EMPTY, 1561, 8.0f, 5.5f, 10, ModTags.Items.EMPTY);

    public Gungnir(String path, float attackDamage, float attackSpeed, Settings settings) {
        super(path, GUNGNIR, attackDamage + 1, attackSpeed - 2.4f, settings);
    }
}
