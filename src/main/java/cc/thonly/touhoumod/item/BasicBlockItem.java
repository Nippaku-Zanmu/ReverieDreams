package cc.thonly.touhoumod.item;

import cc.thonly.touhoumod.item.base.BasicPolymerBlockItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class BasicBlockItem extends BasicPolymerBlockItem {
    public BasicBlockItem(Identifier identifier, Block block, Settings settings) {
        super(identifier, block, settings, Items.BARRIER);
    }

    public BasicBlockItem(Identifier identifier, Block block, Item item, Settings settings) {
        super(identifier, block, settings, item);
    }

    public BasicBlockItem(String path, Block block, Settings settings) {
        super(path, block, settings, Items.BARRIER);
    }

    public BasicBlockItem(String path, Block block, Item item, Settings settings) {
        super(path, block, settings, item);
    }
}
