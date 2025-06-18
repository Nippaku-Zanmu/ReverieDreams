package cc.thonly.reverie_dreams.item.tool;

import cc.thonly.reverie_dreams.data.ModTags;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;

public interface SliverMaterial {
    ToolMaterial INSTANCE = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6.0f, 2.0f, 14, ModTags.Items.SLIVER_TOOL_MATERIALS);

}
