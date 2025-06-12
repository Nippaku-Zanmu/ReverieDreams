package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.item.base.BasicPolymerItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class BasicGuiItem extends BasicPolymerItem {
    public BasicGuiItem(String path, Item.Settings settings) {
        super(path, settings, Items.WHITE_STAINED_GLASS_PANE);
    }
}
