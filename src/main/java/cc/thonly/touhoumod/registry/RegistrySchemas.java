package cc.thonly.touhoumod.registry;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.damage.DanmakuDamageType;
import cc.thonly.touhoumod.item.base.UsingDanmaku;
import cc.thonly.touhoumod.script.ScriptEntry;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.util.Identifier;

import java.util.Map;

@Slf4j
public class RegistrySchemas {
    public static final Map<Identifier, RegistrySchema<?>> INSTANCE = new Object2ObjectLinkedOpenHashMap<>();
    public static final RegistrySchema<UsingDanmaku> USING_DANMAKU = ofEntry(UsingDanmaku.class, Touhou.id("using_danmaku")).build(UsingDanmakus::bootstrap);
    public static final RegistrySchema<DanmakuDamageType> DANMAKU_DAMAGE_TYPE = ofEntry(DanmakuDamageType.class, Touhou.id("danmaku_damage_type")).build(DanmakuDamageTypes::bootstrap);
    public static final RegistrySchema<ScriptEntry> SCRIPTS = ofEntry(ScriptEntry.class, Touhou.id("script")).reloadable().build(ScriptEntries::bootstrap);

    public static void bootstrap() {
        for (var entry : INSTANCE.entrySet()) {
            RegistrySchema<?> registrySchema = entry.getValue();
            registrySchema.apply();
        }
        ModRegistries.bootstrap();
    }

    public static <T> T register(RegistrySchema<T> registryHashListEntry, Identifier key, T value) {
        return registryHashListEntry.add(key, value);
    }

    public static <T> Map<Identifier, T> registers(RegistrySchema<T> registryHashListEntry, Map<Identifier, T> idToEntry) {
        return registryHashListEntry.add(idToEntry);
    }

    public static <T> T set(RegistrySchema<T> registryHashListEntry, Identifier key, T value) {
        return registryHashListEntry.set(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> RegistrySchema<T> ofEntry(Class<T> type, Identifier key) {
        RegistrySchema<?> rawEntry = INSTANCE.get(key);
        if (rawEntry != null) {
            return (RegistrySchema<T>) rawEntry;
        } else {
            RegistrySchema<T> newEntry = new RegistrySchema<>(key);
            INSTANCE.put(key, newEntry);
            return newEntry;
        }
    }

}
