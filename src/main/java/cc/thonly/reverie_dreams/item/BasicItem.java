package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.item.base.BasicPolymerItem;
import net.minecraft.item.Items;

public class BasicItem extends BasicPolymerItem {
    public BasicItem(String path, Settings settings) {
        super(path, settings, Items.TRIAL_KEY);
    }
}
