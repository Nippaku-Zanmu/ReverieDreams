package cc.thonly.reverie_dreams.registry;

import com.sun.nio.sctp.IllegalUnbindException;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.Identifier;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class Key2ValueRegistry<K, V> implements Serializable {
    @Getter
    private final Identifier key;
    @Setter
    @Getter
    private ReloadableFactory reloadableFactory = null;
    @Getter
    private final Class<K> klass;
    @Getter
    private final Class<V> vlass;

    @Getter(AccessLevel.PROTECTED)
    private final Map<Integer, V> rawToEntry;
    @Getter(AccessLevel.PROTECTED)
    private final Map<K, V> keyToEntry;
    @Getter(AccessLevel.PROTECTED)
    private final Map<V, K> entryToKey;

    protected Key2ValueRegistry(Identifier key, Class<K> klass, Class<V> vlass) {
        if (key == null) {
            throw new IllegalArgumentException("Registry require a key, but it is null");
        }
        if (Key2ValueRegistryManager.REGISTRIES.containsKey(key)) {
            throw new IllegalUnbindException("Repeat the creation of the same named instance");
        }
        this.key = key;
        this.rawToEntry = new Object2ObjectLinkedOpenHashMap<>();
        this.keyToEntry = new Object2ObjectLinkedOpenHashMap<>();
        this.entryToKey = new Object2ObjectLinkedOpenHashMap<>();
        this.klass = klass;
        this.vlass = vlass;
        Key2ValueRegistryManager.REGISTRIES.put(key, this);
    }

    public static <K, V> Key2ValueRegistry<K, V> createRegistry(Identifier id, Class<K> klass, Class<V> vlass) {
        return new Key2ValueRegistry<>(id, klass, vlass);
    }

    public static <K, V> Key2ValueRegistry<K, V> of(Identifier id, Class<K> klass, Class<V> vlass) {
        return (Key2ValueRegistry<K, V>) Key2ValueRegistryManager.REGISTRIES.computeIfAbsent(id, x -> new Key2ValueRegistry<>(id, klass, vlass));
    }

    public static <K, V> Key2ValueRegistry<K, V> of(Class<K> klass, Class<V> vlass) {
        Set<Map.Entry<Identifier, Key2ValueRegistry<?, ?>>> registries = Key2ValueRegistryManager.REGISTRIES.entrySet();
        for (Map.Entry<Identifier, Key2ValueRegistry<?, ?>> entry : registries) {
            Key2ValueRegistry<?, ?> registry = entry.getValue();
            if (registry.klass == klass && registry.vlass == vlass) {
                return (Key2ValueRegistry<K, V>) registry;
            }
        }
        return new Key2ValueRegistry<>(Identifier.of(klass.getName().toLowerCase() + "_2_" + vlass.getName().toLowerCase()), klass, vlass);
    }

    public static <K, V> Key2ValueRegistry<K, V> ofNullable(Class<K> klass, Class<V> vlass) {
        Set<Map.Entry<Identifier, Key2ValueRegistry<?, ?>>> registries = Key2ValueRegistryManager.REGISTRIES.entrySet();
        for (Map.Entry<Identifier, Key2ValueRegistry<?, ?>> entry : registries) {
            Key2ValueRegistry<?, ?> registry = entry.getValue();
            if (registry.klass == klass && registry.vlass == vlass) {
                return (Key2ValueRegistry<K, V>) registry;
            }
        }
        return null;

    }

    public V add(K key, V value) {
        if (this.keyToEntry.containsKey(key)) {
            throw new IllegalStateException("Duplicate key: " + key);
        }
        if (this.keyToEntry.containsValue(value)) {
            throw new IllegalStateException("Duplicate value: " + value);
        }
        int raw = this.rawToEntry.size();
        this.rawToEntry.put(raw, value);
        this.keyToEntry.put(key, value);
        this.entryToKey.put(value, key);
        return value;
    }

    public V get(K key) {
        return keyToEntry.get(key);
    }

    public K getKey(V value) {
        return this.entryToKey.get(value);
    }

    public Optional<V> getOptional(K key) {
        return Optional.ofNullable(get(key));
    }

    public Optional<K> getKeyOptional(V value) {
        return Optional.ofNullable(this.getKey(value));
    }

    public Stream<K> keysStream() {
        return this.keyToEntry.keySet().stream();
    }

    public Stream<V> valuesStream() {
        return this.keyToEntry.values().stream();
    }

    public Set<K> keys() {
        return Collections.unmodifiableSet(this.keyToEntry.keySet());
    }

    public Set<V> values() {
        return Set.copyOf(this.keyToEntry.values());
    }

    public Key2ValueRegistry<K, V> reload(ReloadableFactory reloadableFactory) {
        this.reloadableFactory = reloadableFactory;
        return this;
    }

    public void reload() {
        if (this.reloadableFactory != null) {
            this.rawToEntry.clear();
            this.keyToEntry.clear();
            this.reloadableFactory.onReload();
        }
    }

    @FunctionalInterface
    public interface ReloadableFactory {
        void onReload();
    }
}
