package cc.thonly.mystias_izakaya.item;

import cc.thonly.reverie_dreams.util.IdentifierGetter;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MIItems {

    public static void registerItems() {

    }

    public static Item registerItem(IdentifierGetter item) {
        Registry.register(Registries.ITEM, item.getIdentifier(), (Item) item);
        return (Item) item;
    }
}
