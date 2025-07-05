package cc.thonly.reverie_dreams.registry;

import cc.thonly.reverie_dreams.util.ItemColor;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.Map;

public class Key2ValueRegistryManager {
    public static final Map<Identifier, Key2ValueRegistry<?, ?>> REGISTRIES = new Object2ObjectLinkedOpenHashMap<>();
    public static final Key2ValueRegistry<Item, ItemColor> ITEM_COLOR = Key2ValueRegistry.createRegistry(Identifier.of("item_color"), Item.class, ItemColor.class);

    public static void bootstrap() {

    }

    public static <K, V> V register(Key2ValueRegistry<K, V> registry, K key, V value) {
        registry.add(key, value);
        return value;
    }

}
