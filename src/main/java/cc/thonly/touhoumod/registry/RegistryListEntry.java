package cc.thonly.touhoumod.registry;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.minecraft.util.Identifier;

import java.util.*;

@Getter
@EqualsAndHashCode
@ToString
public final class RegistryListEntry<T> {
    private final Identifier key;
    private final Map<Integer, T> rawToEntry;
    private final Map<Identifier, T> idToEntry;
    private T defaultEntry;
    private BootstrapBuilder<T> builder;
    private boolean isFrozen = false;
    private boolean isFinished = false;

    public RegistryListEntry(Identifier key) {
        if (key == null) {
            throw new IllegalArgumentException("Registry require a key, but it is null");
        }
        this.key = key;
        this.rawToEntry = new HashMap<>();
        this.idToEntry = new HashMap<>();
    }

    public RegistryListEntry(Identifier key, Map<Identifier, T> idToEntry) {
        if (key == null) {
            throw new IllegalArgumentException("Registry require a key, but it is null");
        }
        if (idToEntry == null) {
            throw new IllegalArgumentException("Registry require an idToEntry, but it is null");
        }
        this.key = key;
        this.rawToEntry = new HashMap<>();
        this.idToEntry = idToEntry;
    }

    public T add(Identifier key, T value) {
        if (this.idToEntry.containsKey(key)) {
            return null;
        }

        this.rawToEntry.put(this.rawToEntry.size(), value);
        this.idToEntry.put(key, value);
        return value;
    }

    public Map<Identifier, T> add(Map<Identifier, T> idToEntry) {
        for (var entry : idToEntry.entrySet()) {
            this.add(entry.getKey(), entry.getValue());
        }
        return idToEntry;
    }


    public T add(Integer rawId, Identifier key, T value) {
        if (this.rawToEntry.containsKey(rawId) || this.idToEntry.containsKey(key)) {
            return null;
        }

        this.rawToEntry.put(rawId, value);
        this.idToEntry.put(key, value);
        return value;
    }

    public T set(Integer rawId, Identifier key, T value) {
        if (!this.rawToEntry.containsKey(rawId) || !this.idToEntry.containsKey(key)) {
            return null;
        }

        this.rawToEntry.put(rawId, value);
        this.idToEntry.put(key, value);
        return value;
    }

    public T set(Identifier key, T value) {
        if (!this.idToEntry.containsKey(key)) {
            return null;
        }

        this.idToEntry.put(key, value);

        Integer rawId = getRawIdByKey(key);
        if (rawId != null) {
            this.rawToEntry.put(rawId, value);
        }

        return value;
    }

    public T get(Integer rawId) {
        return this.rawToEntry.get(rawId);
    }

    public T get(Identifier key) {
        return this.idToEntry.get(key);
    }

    public Integer getRawId(T value) {
        for (Map.Entry<Integer, T> entry : this.rawToEntry.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Identifier getId(T value) {
        for (Map.Entry<Identifier, T> entry : this.idToEntry.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public T getOrDefault(Integer rawId) {
        return this.rawToEntry.getOrDefault(rawId, this.defaultEntry);
    }

    public T getOrDefault(Identifier key) {
        return this.idToEntry.getOrDefault(key, this.defaultEntry);
    }

    public Optional<T> getOptional(Integer rawId) {
        return Optional.ofNullable(this.get(rawId));
    }

    public Optional<T> getOptional(Identifier key) {
        return Optional.ofNullable(this.get(key));
    }

    public T getOrThrow(Integer rawId) {
        return Optional.ofNullable(this.get(rawId))
                .orElseThrow(() -> new NoSuchElementException("No entry found for raw ID: " + rawId));
    }

    public T getOrThrow(Identifier key) {
        return Optional.ofNullable(this.get(key))
                .orElseThrow(() -> new NoSuchElementException("No entry found for Identifier: " + key));
    }

    public Set<Map.Entry<Integer, T>> rawEntrySet() {
        return this.rawToEntry.entrySet();
    }

    public Set<Map.Entry<Identifier, T>> entrySet() {
        return this.idToEntry.entrySet();
    }

    public Set<Integer> rawIds() {
        return this.rawToEntry.keySet();
    }

    public Set<Identifier> keys() {
        return this.idToEntry.keySet();
    }

    public Collection<T> rawValues() {
        return this.rawToEntry.values();
    }

    public Collection<T> values() {
        return this.idToEntry.values();
    }

    public HashMap<Identifier, T> reset() {
        HashMap<Identifier, T> idToEntry = new HashMap<>(this.idToEntry);
        this.idToEntry.clear();
        this.rawToEntry.clear();
        return idToEntry;
    }

    public RegistryListEntry<T> defaultEntry(T entry) {
        this.defaultEntry = entry;
        return this;
    }

    public RegistryListEntry<T> freeze() {
        this.isFrozen = true;
        return this;
    }

    public RegistryListEntry<T> unfreeze() {
        this.isFrozen = false;
        return this;
    }

    public RegistryListEntry<T> build() {
        this.builder = registry -> {};
        this.isFinished = true;
        return this;
    }

    public RegistryListEntry<T> build(BootstrapBuilder<T> builder) {
        this.builder = builder;
        this.isFinished = true;
        return this;
    }

    public void apply() {
        builder.bootstrap(this);
    }

    private Integer getRawIdByKey(Identifier key) {
        for (Map.Entry<Integer, T> entry : this.rawToEntry.entrySet()) {
            if (this.idToEntry.get(key).equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    @FunctionalInterface
    public interface BootstrapBuilder<T> {
        void bootstrap(RegistryListEntry<T> registry);
    }
}