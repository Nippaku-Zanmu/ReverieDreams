package cc.thonly.reverie_dreams.item.base;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.util.Rarity;
import xyz.nucleoid.packettweaker.PacketContext;

public class BasicPolymerDiscItem extends BasicPolymerItem {
    public BasicPolymerDiscItem(String path, Settings settings, Item item) {
        super(path, settings.maxCount(1).rarity(Rarity.UNCOMMON).translationKey(Items.MUSIC_DISC_5.getTranslationKey()), item);
    }

}
