package cc.thonly.touhoumod.registry;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.item.base.UsingDanmaku;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.util.Identifier;

import java.util.Map;

@Slf4j
public class RegistryLists {
    public static final Map<Identifier, RegistryListEntry<?>> INSTANCE = new Object2ObjectLinkedOpenHashMap<>();
    public static final RegistryListEntry<UsingDanmaku> USING_DANMAKU = ofEntry(UsingDanmaku.class, Touhou.id("using_danmaku")).build(UsingDanmakus::bootstrap);
    public static final RegistryListEntry<DanmakuDamageType> DANMAKU_DAMAGE_TYPE = ofEntry(DanmakuDamageType.class, Touhou.id("danmaku_damage_type")).build(DanmakuDamageTypes::bootstrap);

    public static void bootstrap() {
        for (var entry : INSTANCE.entrySet()) {
            RegistryListEntry<?> registryListEntry = entry.getValue();
            registryListEntry.apply();
        }
        ModRegistries.bootstrap();
    }

    public static <T> T register(RegistryListEntry<T> registryHashListEntry, Identifier key, T value) {
        return registryHashListEntry.add(key, value);
    }

    public static <T> Map<Identifier, T> registers(RegistryListEntry<T> registryHashListEntry, Map<Identifier, T> idToEntry) {
        return registryHashListEntry.add(idToEntry);
    }

    public static <T> T set(RegistryListEntry<T> registryHashListEntry, Identifier key, T value) {
        return registryHashListEntry.set(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> RegistryListEntry<T> ofEntry(Class<T> type, Identifier key) {
        RegistryListEntry<?> rawEntry = INSTANCE.get(key);
        if (rawEntry != null) {
            return (RegistryListEntry<T>) rawEntry;
        } else {
            RegistryListEntry<T> newEntry = new RegistryListEntry<>(key);
            INSTANCE.put(key, newEntry);
            return newEntry;
        }
    }

}
