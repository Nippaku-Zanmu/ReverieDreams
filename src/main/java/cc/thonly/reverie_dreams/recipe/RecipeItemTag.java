package cc.thonly.reverie_dreams.recipe;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Slf4j
public class RecipeItemTag {
    private static final Map<Identifier, RecipeItemTag> INSTANCES = new ConcurrentHashMap<>();

    @Getter
    private final Identifier recipeTagId;

    private final Set<Item> entries = new HashSet<>();
    private final Set<Identifier> preparingItemIdentifiers = new HashSet<>();

    private Set<Item> cachedResolvedEntries = null;

    private RecipeItemTag(Identifier recipeTagId) {
        this.recipeTagId = recipeTagId;
    }

    public synchronized RecipeItemTag addItem(Item... items) {
        Collections.addAll(entries, items);
        this.invalidateCache();
        return this;
    }

    public synchronized RecipeItemTag removeItem(Item... items) {
        for (Item item : items) {
            entries.remove(item);
        }
        this.invalidateCache();
        return this;
    }

    public synchronized RecipeItemTag addItemIdentifier(Item... items) {
        for (Item item : items) {
            this.preparingItemIdentifiers.add(Registries.ITEM.getId(item));
        }
        this.invalidateCache();
        return this;
    }

    public synchronized RecipeItemTag addItemIdentifier(Identifier... items) {
        Collections.addAll(preparingItemIdentifiers, items);
        this.invalidateCache();
        return this;
    }

    public synchronized RecipeItemTag addItemIdentifier(String... items) {
        for (String s : items) {
            this.preparingItemIdentifiers.add(Identifier.of(s));
        }
        this.invalidateCache();
        return this;
    }

    public synchronized RecipeItemTag removeItemIdentifier(Identifier... items) {
        for (Identifier id : items) {
            this.preparingItemIdentifiers.remove(id);
        }
        this.invalidateCache();
        return this;
    }

    public synchronized RecipeItemTag addFromTagKey(DynamicRegistryManager registryManager, TagKey<Item> itemTagKey) {
        Optional<Registry<Item>> optionalRegistry = registryManager.getOptional(RegistryKeys.ITEM);
        if (optionalRegistry.isPresent()) {
            Registry<Item> registry = optionalRegistry.get();
            for (RegistryEntry<Item> itemEntry : registry.iterateEntries(itemTagKey)) {
                this.addItemIdentifier(Registries.ITEM.getId(itemEntry.value()));
            }
            this.invalidateCache();
        } else {
            log.error("Can't read item tag id {} ", itemTagKey.id());
        }
        return this;
    }

    public void forEach(Consumer<? super Item> action) {
        this.getEntries().forEach(action);
    }

    public Stream<Item> stream() {
        return this.getEntries().stream();
    }

    public List<Item> asList() {
        return new ArrayList<>(this.getEntries());
    }

    public synchronized Set<Item> getEntries() {
        if (this.cachedResolvedEntries == null) {
            Set<Item> result = new HashSet<>(this.entries);
            for (Identifier id : this.preparingItemIdentifiers) {
                Item item = Registries.ITEM.get(id);
                if (item != Items.AIR) {
                    result.add(item);
                }
            }
            this.cachedResolvedEntries = result;
        }
        return this.cachedResolvedEntries;
    }

    private void invalidateCache() {
        this.cachedResolvedEntries = null;
    }

    public static RecipeItemTag of(Identifier recipeTagId) {
        return INSTANCES.computeIfAbsent(recipeTagId, RecipeItemTag::new);
    }

    public static RecipeItemTag of(RegistryKey<Item> registryKey) {
        return of(registryKey.getValue());
    }
}
