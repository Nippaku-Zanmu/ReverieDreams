package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.item.base.BasicPolymerItem;
import net.minecraft.item.Items;

public class BaguaFurnace extends BasicPolymerItem {
    public BaguaFurnace(String path, Settings settings) {
        super(path, settings.maxCount(1), Items.TRIAL_KEY);
    }
}
