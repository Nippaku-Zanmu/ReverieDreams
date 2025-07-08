package cc.thonly.reverie_dreams.item.base;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Rarity;

public class BasicPolymerDiscItem extends BasicPolymerItem {
    public static final Item VANILLA_ITEM = Items.MUSIC_DISC_5;
    public BasicPolymerDiscItem(String path, Settings settings) {
        super(path, settings.maxCount(1).rarity(Rarity.UNCOMMON).translationKey(Items.MUSIC_DISC_5.getTranslationKey()), VANILLA_ITEM);
    }

}
