package cc.thonly.reverie_dreams.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Getter
@EqualsAndHashCode
@ToString
public final class RegistrySchema<T extends SchemaObject<T>> implements Serializable {
    @Serial
    private static final long serialVersionUID = 199189765401L;
    private final Identifier key;
    private final Map<Integer, T> rawToEntry;
    private final Map<Identifier, T> idToEntry;
    private Map<Integer, T> baseRawToEntry = new Object2ObjectLinkedOpenHashMap<>();
    private Map<Identifier, T> baseIdToEntry = new Object2ObjectLinkedOpenHashMap<>();
    private DefaultValueGetter<T> defaultEntryGetter;
    private T defaultEntry;
    private BootstrapBuilder<T> builder = (server) -> {
    };
    private ReloadableBootstrap<T> reloadableBootstrap = (manager) -> {
    };
    private boolean isFrozen = false;
    private boolean isFinished = false;
    private boolean reloadable = false;
    private Codec<RegistrySchema<T>> registrySchemaCodec;
    private Codec<T> entryCodec;

    public RegistrySchema(Identifier key) {
        if (key == null) {
            throw new IllegalArgumentException("Registry require a key, but it is null");
        }
        this.key = key;
        this.rawToEntry = new Object2ObjectLinkedOpenHashMap<>();
        this.idToEntry = new Object2ObjectLinkedOpenHashMap<>();
    }

    public RegistrySchema(Identifier key, Map<Identifier, T> idToEntry) {
        if (key == null) {
            throw new IllegalArgumentException("Registry require a key, but it is null");
        }
        if (idToEntry == null) {
            throw new IllegalArgumentException("Registry require an idToEntry, but it is null");
        }
        this.key = key;
        this.rawToEntry = new Object2ObjectLinkedOpenHashMap<>();
        this.idToEntry = idToEntry;
    }

    public static <T extends SchemaObject<T>> Codec<RegistrySchema<T>> createCodec(
            Identifier key, Codec<T> tCodec
    ) {
        return RecordCodecBuilder.create(instance -> instance.group(
                Codec.unboundedMap(Codec.INT, tCodec).fieldOf("rawToEntry").forGetter(schema -> schema.rawToEntry),
                Codec.unboundedMap(Identifier.CODEC, tCodec).fieldOf("idToEntry").forGetter(schema -> schema.idToEntry)
        ).apply(instance, (rawToEntry, idToEntry) -> {
            RegistrySchema<T> schema = new RegistrySchema<>(key);
            schema.rawToEntry.putAll(rawToEntry);
            schema.idToEntry.putAll(idToEntry);
            for (var entry : idToEntry.entrySet()) {
                entry.getValue().setId(entry.getKey());
            }
            return schema;
        }));
    }
    public RegistrySchema<T> codec(Codec<T> tCodec) {
        this.entryCodec = tCodec;
        this.registrySchemaCodec = createCodec(this.key, tCodec);
        return this;
    }

    public T add(Identifier key, T value) {
        if (this.idToEntry.containsKey(key)) {
            return null;
        }

        this.rawToEntry.put(this.rawToEntry.size(), value);
        this.idToEntry.put(key, value);
        value.setId(key);
        return value;
    }

    public Map<Identifier, T> add(Map<Identifier, T> idToEntry) {
        for (var entry : idToEntry.entrySet()) {
            this.add(entry.getKey(), entry.getValue());
            entry.getValue().setId(entry.getKey());
        }
        return idToEntry;
    }


    public T add(Integer rawId, Identifier key, T value) {
        if (this.rawToEntry.containsKey(rawId) || this.idToEntry.containsKey(key)) {
            return null;
        }

        this.rawToEntry.put(rawId, value);
        this.idToEntry.put(key, value);
        value.setId(key);
        return value;
    }

    public T set(Integer rawId, Identifier key, T value) {
        if (!this.rawToEntry.containsKey(rawId) || !this.idToEntry.containsKey(key)) {
            return null;
        }

        this.rawToEntry.put(rawId, value);
        this.idToEntry.put(key, value);
        value.setId(key);
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
        value.setId(key);
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
                .orElseThrow(() -> new NoSuchElementException("No block found for raw ID: " + rawId));
    }

    public T getOrThrow(Identifier key) {
        return Optional.ofNullable(this.get(key))
                .orElseThrow(() -> new NoSuchElementException("No block found for Identifier: " + key));
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

    public boolean isInDataPack(Identifier key) {
        return this.idToEntry.containsKey(key);
    }

    public boolean isInDataPack(T value) {
        return this.idToEntry.containsValue(value);
    }

    public boolean isInDataPack(Integer rawId) {
        return this.rawToEntry.containsKey(rawId);
    }

    public void reset() {
        this.idToEntry.clear();
        this.rawToEntry.clear();
        if (this.isFinished) {
            this.idToEntry.putAll(this.baseIdToEntry);
            this.rawToEntry.putAll(this.baseRawToEntry);
        }
    }

    public RegistrySchema<T> defaultEntry(DefaultValueGetter<T> getter) {
        this.defaultEntryGetter = getter;
        return this;
    }

    public RegistrySchema<T> freeze() {
        this.isFrozen = true;
        return this;
    }

    public RegistrySchema<T> unfreeze() {
        this.isFrozen = false;
        return this;
    }

    public RegistrySchema<T> build() {
        this.builder = registry -> {
        };
        this.defaultEntry = this.defaultEntryGetter.get();
        return this;
    }

    public RegistrySchema<T> reloadable() {
        this.reloadable = true;
        this.reloadableBootstrap = (manager) -> {
        };
        return this;
    }

    public RegistrySchema<T> reloadable(ReloadableBootstrap<T> builder) {
        this.reloadable = true;
        this.reloadableBootstrap = builder;
        return this;
    }

    public RegistrySchema<T> build(BootstrapBuilder<T> builder) {
        this.builder = builder;
        return this;
    }

    public void apply() {
        if (!this.isFinished) {
            this.builder.bootstrap(this);
            this.baseRawToEntry = new Object2ObjectLinkedOpenHashMap<>(this.rawToEntry);
            this.baseIdToEntry = new Object2ObjectLinkedOpenHashMap<>(this.idToEntry);
            this.isFinished = true;
        }
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
    public interface BootstrapBuilder<T extends SchemaObject<T>> {
        void bootstrap(RegistrySchema<T> registry);
    }

    @FunctionalInterface
    public interface ReloadableBootstrap<T extends SchemaObject<T>> {
        void reload(ResourceManager manager);
    }

    @FunctionalInterface
    public interface DefaultValueGetter<T extends SchemaObject<T>> {
        @NotNull
        T get();
    }
}