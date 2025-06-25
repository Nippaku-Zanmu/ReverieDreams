package cc.thonly.reverie_dreams.registry;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.damage.DanmakuDamageType;
import cc.thonly.reverie_dreams.damage.DanmakuDamageTypes;
import cc.thonly.reverie_dreams.danmaku.DanmakuTrajectories;
import cc.thonly.reverie_dreams.danmaku.DanmakuTrajectory;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.util.Identifier;

import java.util.Map;

@Slf4j
@SuppressWarnings("unchecked")
public class RegistryManager {
    public static final Map<Identifier, StandaloneRegistry<?>> REGISTRIES = new Object2ObjectLinkedOpenHashMap<>();
    public static final StandaloneRegistry<DanmakuTrajectory> DANMAKU_TRAJECTORY = ofEntry(DanmakuTrajectory.class, Touhou.id("danmaku_trajectory"))
            .codec(DanmakuTrajectory.CODEC)
            .build(DanmakuTrajectories::bootstrap);
    public static final StandaloneRegistry<DanmakuDamageType> DANMAKU_DAMAGE_TYPE = ofEntry(DanmakuDamageType.class, Touhou.id("danmaku_damage_type"))
            .codec(DanmakuDamageType.CODEC)
            .build(DanmakuDamageTypes::bootstrap);

    public static void bootstrap() {
        for (var entry : REGISTRIES.entrySet()) {
            StandaloneRegistry<?> standaloneRegistry = entry.getValue();
            standaloneRegistry.apply();
        }
    }

    public static <T extends RegistrableObject<T>> T register(StandaloneRegistry<T> registry, Identifier key, T value) {
        return registry.add(key, value);
    }

    public static <T extends RegistrableObject<T>> Map<Identifier, T> registerAll(StandaloneRegistry<T> registry, Map<Identifier, T> idToEntry) {
        return registry.add(idToEntry);
    }

    public static <T extends RegistrableObject<T>> T set(StandaloneRegistry<T> registry, Identifier key, T value) {
        return registry.set(key, value);
    }

    public static <T extends RegistrableObject<T>> StandaloneRegistry<T> ofEntry(Class<T> type, Identifier key) {
        StandaloneRegistry<?> rawEntry = REGISTRIES.get(key);
        if (rawEntry != null) {
            return (StandaloneRegistry<T>) rawEntry;
        } else {
            StandaloneRegistry<T> newEntry = new StandaloneRegistry<>(key);
            REGISTRIES.put(key, newEntry);
            return newEntry;
        }
    }

    public static <T extends RegistrableObject<T>> StandaloneRegistry<T> getRegistry(Class<T> type, Identifier key) {
        return (StandaloneRegistry<T>) REGISTRIES.get(key);
    }

}
