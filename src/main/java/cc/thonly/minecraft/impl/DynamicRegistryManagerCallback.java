package cc.thonly.minecraft.impl;

import cc.thonly.minecraft.mixin.NamedAccessor;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryInfo;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.stream.Stream;

@SuppressWarnings({"unchecked", "deprecation"})
public interface DynamicRegistryManagerCallback {
    List<DynamicRegistryFactory<?>> CALLBACKS = new ArrayList<>();

    static <T> void add(DynamicRegistryFactory<T> factory) {
        CALLBACKS.add(factory);
    }

    static void start(MinecraftServer server) {
        DynamicRegistryManager.Immutable registryManager = server.getRegistryManager();
        start(registryManager);
    }

    static void start(DynamicRegistryManager.Immutable registryManager) {
        Stream<RegistryKey<? extends Registry<?>>> stream = registryManager.streamAllRegistryKeys();
        stream.forEach((registryKey -> {
            for (DynamicRegistryFactory<?> factory : CALLBACKS) {
                if (factory.registryKey == registryKey) {
                    Optional<? extends Registry<?>> optional = registryManager.getOptional(factory.registryKey);
                    optional.ifPresent(registry -> {
                        if (registry instanceof SimpleRegistry<?> simpleRegistry) {
                            factory.start(simpleRegistry);
                        }
                    });
                }
            }
        }));
    }

    static void start(SimpleRegistry<?> registry) {
        for (DynamicRegistryFactory<?> factory : CALLBACKS) {
            if (factory.registryKey.equals(registry.getKey())) {
                factory.start(registry);
            }
        }

    }

    static <T> DynamicRegistryFactory<T> createFactory(RegistryKey<? extends Registry<T>> registryKey) {
        return new DynamicRegistryFactory<>(registryKey);
    }

    @Getter
    class DynamicRegistryFactory<T> {
        private final RegistryKey<? extends Registry<T>> registryKey;
        private final Map<Identifier, T> registries = new Object2ObjectLinkedOpenHashMap<>();
        private final Map<Identifier, RegistryEntry<T>> entries = new Object2ObjectLinkedOpenHashMap<>();
        private final Map<Identifier, RegistryEntryInfo> infos = new Object2ObjectLinkedOpenHashMap<>();
        private final List<Info<T>> registryInfos = new ObjectArrayList<>();
        private final List<TagKeyBuilder<T>> tagKeyBuilders = new ObjectArrayList<>();

        protected DynamicRegistryFactory(RegistryKey<? extends Registry<T>> registryKey) {
            assert registryKey != null;
            this.registryKey = registryKey;
        }

        public T register(Identifier key, T value) {
            return register(key, value, RegistryEntryInfo.DEFAULT);
        }

        public T register(Identifier key, T value, RegistryEntryInfo info) {
            return register(key, RegistryEntry.of(value), info);
        }

        public T register(Identifier key, RegistryEntry<T> value) {
            return register(key, value, RegistryEntryInfo.DEFAULT);
        }

        public T register(Identifier key, RegistryEntry<T> registryEntry, RegistryEntryInfo info) {
            RegistryKey<T> registryKey = RegistryKey.of(this.registryKey, key);
            T value = registryEntry.value();
            this.registries.put(key, value);
            this.entries.put(key, registryEntry);
            this.infos.put(key, info);
            if (registryEntry instanceof RegistryEntry.Reference<T> reference) {
                reference.setRegistryKey(registryKey);
            }
            this.registryInfos.add(new Info<>(registryKey, value, registryEntry, info));
            return value;
        }

        public void addTagBuilder(TagKeyBuilder<T>... tagKeyBuilder) {
            this.tagKeyBuilders.addAll(Arrays.asList(tagKeyBuilder));
        }

        public void start(SimpleRegistry<?> sr) {
            SimpleRegistry<T> registry = (SimpleRegistry<T>) sr;
            this.build(registry);
            this.buildTags(registry);
        }

        protected void build(SimpleRegistry<T> registry) {
            for (Info<T> registryInfo : this.registryInfos) {
                if (!(registryInfo.registryEntry instanceof RegistryEntry.Reference<?>)) {
                    var reference = RegistryEntry.Reference.intrusive(registry, registryInfo.value);
                    ;
                    registryInfo.registryEntry = reference;
                    reference.setRegistryKey(registryInfo.key);
                }
                registry.keyToEntry.remove(registryInfo.key);
                registry.idToEntry.remove(registryInfo.key.getValue());
                registry.valueToEntry.remove(registryInfo.value);
                registry.entryToRawId.removeInt(registryInfo.value);
                registry.keyToEntryInfo.remove(registryInfo.key);
            }
            for (Info<T> registryInfo : this.registryInfos) {
                if (!(registryInfo.value instanceof RegistryEntry.Reference<?>)) continue;
                var reference = (RegistryEntry.Reference<T>) registryInfo.value;
                registry.keyToEntry.put(registryInfo.key, reference);
                registry.idToEntry.put(registryInfo.key.getValue(), reference);
                registry.valueToEntry.put(registryInfo.value, reference);
                int next = registry.rawIdToEntry.size();
                registry.entryToRawId.put(registryInfo.value, next);
                registry.keyToEntryInfo.put(registryInfo.key, registryInfo.info);
                registry.getLifecycle().add(registryInfo.info.lifecycle());
            }
        }

        protected void buildTags(SimpleRegistry<T> registry) {
            for (TagKeyBuilder<T> tagKeyBuilder : this.tagKeyBuilders) {
                RegistryEntryList.Named<T> named = registry.tags.computeIfAbsent(tagKeyBuilder.tagKey, x -> NamedAccessor.callNew(registry, tagKeyBuilder.tagKey));

                for (T value : tagKeyBuilder.values) {
                    RegistryEntry<T> entry = registry.getEntry(value);
                    if (named.entries != null) {
                        named.entries.add(entry);
                    } else {
                        named.entries = new ArrayList<>();
                    }
                }
            }
        }

        public static <T> DynamicRegistryFactory<T> create(RegistryKey<? extends Registry<T>> registryKey) {
            return new DynamicRegistryFactory<>(registryKey);
        }

        @Getter
        @AllArgsConstructor
        public static class Info<T> {
            RegistryKey<T> key;
            T value;
            RegistryEntry<T> registryEntry;
            RegistryEntryInfo info;
        }

        @Getter
        public static class TagKeyBuilder<T> {
            private final TagKey<T> tagKey;
            private final List<T> values = new ObjectArrayList<>();

            public TagKeyBuilder(TagKey<T> tagKey) {
                this.tagKey = tagKey;
            }

            public TagKeyBuilder<T> add(T... values) {
                this.values.addAll(Arrays.asList(values));
                return this;
            }
        }

    }
}
