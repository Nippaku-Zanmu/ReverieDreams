package cc.thonly.reverie_dreams.item;

import lombok.Getter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Getter
public class SpawnEggItemCreator {
    private final Long layer0;
    private final Long layer1;
    private Item item;

    protected SpawnEggItemCreator(Long layer0, Long layer1) {
        this.layer0 = layer0;
        this.layer1 = layer1;
    }

    public ItemStack color(ItemStack itemStack) {
        return itemStack;
    }
}
