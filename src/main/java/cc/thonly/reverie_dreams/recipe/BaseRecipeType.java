package cc.thonly.reverie_dreams.recipe;

import cc.thonly.reverie_dreams.recipe.slot.ItemStackRecipeWrapper;
import com.mojang.serialization.Codec;
import eu.pb4.polymer.core.api.utils.PolymerObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class BaseRecipeType<R extends BaseRecipe> implements PolymerObject {
    private final Map<Identifier, R> registries = new Object2ObjectOpenHashMap<>();

    public abstract void reload(ResourceManager manager);

    public abstract void bootstrap();

    public abstract List<R> getMatches(List<ItemStackRecipeWrapper> wrappers);

    public abstract Boolean isMatch(ItemStackRecipeWrapper wrapper);

    public abstract Codec<R> getCodec();

    public abstract String getTypeId();

    public abstract Identifier getId();

    public BaseRecipeType<R> add(Identifier id, R recipe) {
        this.registries.put(id, recipe);
        recipe.setId(id);
        return this;
    }

    public BaseRecipeType<R> decodeAll(Map<Identifier, R> registries) {
        this.registries.clear();
        this.registries.putAll(registries);
        this.registries.forEach((key, recipe) -> recipe.setId(key));
        return this;
    }

    public R getRecipeById(Identifier id) {
        return this.registries.get(id);
    }

    public Map<Identifier, R> getRegistryView() {
        return Map.copyOf(this.registries);
    }

    public Stream<R> stream() {
        return registries.values().stream();
    }


    public BaseRecipeType<R> remove(Identifier id) {
        this.registries.remove(id);
        return this;
    }

    public BaseRecipeType<R> removeAll() {
        this.registries.clear();
        return this;
    }

}
