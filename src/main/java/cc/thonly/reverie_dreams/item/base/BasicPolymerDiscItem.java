package cc.thonly.reverie_dreams.item.base;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Rarity;

public class BasicPolymerDiscItem extends BasicPolymerItem {
    public BasicPolymerDiscItem(String path, Settings settings, Item item) {
        super(path, settings.maxCount(1).rarity(Rarity.UNCOMMON).translationKey(Items.MUSIC_DISC_5.getTranslationKey()), item);
    }
}
