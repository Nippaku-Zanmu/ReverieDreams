package cc.thonly.reverie_dreams.registry;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.damage.DanmakuDamageType;
import cc.thonly.reverie_dreams.damage.DanmakuDamageTypes;
import cc.thonly.reverie_dreams.danmaku.DanmakuTrajectories;
import cc.thonly.reverie_dreams.danmaku.DanmakuTrajectory;
import cc.thonly.reverie_dreams.recipe.BaseRecipe;
import cc.thonly.reverie_dreams.recipe.BaseRecipeType;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.util.Identifier;

import java.util.Map;

@Slf4j
@SuppressWarnings("unchecked")
public class RegistrySchemas {
    public static final Map<Identifier, RegistrySchema<?>> REGISTRIES = new Object2ObjectLinkedOpenHashMap<>();
    public static final RegistrySchema<DanmakuTrajectory> DANMAKU_TRAJECTORY = ofEntry(DanmakuTrajectory.class, Touhou.id("danmaku_trajectory"))
            .codec(DanmakuTrajectory.CODEC)
            .build(DanmakuTrajectories::bootstrap);
    public static final RegistrySchema<DanmakuDamageType> DANMAKU_DAMAGE_TYPE = ofEntry(DanmakuDamageType.class, Touhou.id("danmaku_damage_type"))
            .codec(DanmakuDamageType.CODEC)
            .build(DanmakuDamageTypes::bootstrap);

    public static void bootstrap() {
        for (var entry : REGISTRIES.entrySet()) {
            RegistrySchema<?> registrySchema = entry.getValue();
            registrySchema.apply();
        }
    }

    public static <T extends SchemaObject<T>> T register(RegistrySchema<T> registryHashListEntry, Identifier key, T value) {
        return registryHashListEntry.add(key, value);
    }

    public static <T extends SchemaObject<T>> Map<Identifier, T> registers(RegistrySchema<T> registryHashListEntry, Map<Identifier, T> idToEntry) {
        return registryHashListEntry.add(idToEntry);
    }

    public static <T extends SchemaObject<T>> T set(RegistrySchema<T> registryHashListEntry, Identifier key, T value) {
        return registryHashListEntry.set(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T extends SchemaObject<T>> RegistrySchema<T> ofEntry(Class<T> type, Identifier key) {
        RegistrySchema<?> rawEntry = REGISTRIES.get(key);
        if (rawEntry != null) {
            return (RegistrySchema<T>) rawEntry;
        } else {
            RegistrySchema<T> newEntry = new RegistrySchema<>(key);
            REGISTRIES.put(key, newEntry);
            return newEntry;
        }
    }

}
