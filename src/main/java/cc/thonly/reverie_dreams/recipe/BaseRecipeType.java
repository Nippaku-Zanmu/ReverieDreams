package cc.thonly.reverie_dreams.recipe;

import cc.thonly.reverie_dreams.recipe.slot.ItemStackRecipeWrapper;
import cc.thonly.reverie_dreams.recipe.type.DanmakuRecipeType;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import eu.pb4.polymer.core.api.utils.PolymerObject;
import eu.pb4.sgui.api.gui.SimpleGui;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.stream.Stream;

@Slf4j
@ToString
public abstract class BaseRecipeType<R extends BaseRecipe> implements PolymerObject {
    private final Map<Identifier, R> registries = new Object2ObjectOpenHashMap<>();
    private int nextRawId = 0;

    public abstract void reload(ResourceManager manager);

    public abstract void bootstrap();

    public abstract List<R> getMatches(List<ItemStackRecipeWrapper> wrappers);

    public abstract Boolean isMatch(ItemStackRecipeWrapper input, ItemStackRecipeWrapper recipe);

    public abstract Codec<R> getCodec();

    public abstract String getTypeId();

    public abstract Identifier getId();

    public BaseRecipeType<R> add(Identifier id, R recipe) {
        if (!this.registries.containsKey(id)) {
            recipe.setRawId(nextRawId++);
        } else {
            recipe.setRawId(this.registries.get(id).getRawId());
        }
        recipe.setId(id);
        this.registries.put(id, recipe);
        return this;
    }

    @SuppressWarnings("unchecked")
    public void add(Identifier key, Object value) {
        this.add(key, (R) value);
    }

    public R getRecipeById(Identifier id) {
        return this.registries.get(id);
    }

    public Map<Identifier, R> getRegistryView() {
        return Map.copyOf(this.registries);
    }

    public List<Identifier> keys() {
        return new ArrayList<>(this.registries.keySet());
    }

    public List<R> values() {
        return new ArrayList<>(this.registries.values());
    }

    public Integer size() {
        return this.registries.size();
    }

    public Stream<R> stream() {
        return this.registries.values().stream();
    }

    public BaseRecipeType<R> remove(Identifier id) {
        this.registries.remove(id);
        return this;
    }

    public BaseRecipeType<R> removeAll() {
        this.registries.clear();
        this.nextRawId = 0;
        return this;
    }

    public JsonElement encode() {
        JsonObject element = new JsonObject();
        Object2ObjectOpenHashMap<Identifier, R> registries = new Object2ObjectOpenHashMap<>(this.registries);
        Set<Map.Entry<Identifier, R>> entries = registries.entrySet();
        Codec<R> codec = this.getCodec();
        if (codec == null) {
            return element;
        }
        for (Map.Entry<Identifier, R> entry : entries) {
            R value = entry.getValue();
            DataResult<JsonElement> dataResult = codec.encodeStart(JsonOps.INSTANCE, value);
            Optional<JsonElement> result = dataResult.result();
            result.ifPresent((e) -> {
                element.add(value.getId().toString(), e);
            });
        }
        return element;
    }

    public List<BaseRecipe> decode(JsonElement element) {
        List<BaseRecipe> list = new LinkedList<>();
        Codec<R> codec = this.getCodec();

        if (codec == null) {
            return list;
        }

        if (!(element instanceof JsonObject jsonObject)) {
            return list;
        }

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            Identifier id;
            try {
                id = Identifier.of(key);
            } catch (Exception e) {
                log.error("Can't parse Identifier {}", key, e);
                continue;
            }

            Dynamic<JsonElement> dynamic = new Dynamic<>(JsonOps.INSTANCE, value);
            DataResult<R> parseResult = codec.parse(dynamic);

            parseResult.resultOrPartial(error -> {
                log.error("Can't parse {} -> {}", key ,error);
            }).ifPresent(r -> {
                r.setId(id);
                list.add(r);
            });
        }

        return list;
    }
}
