package cc.thonly.mystias_izakaya.recipe.type;

import cc.thonly.mystias_izakaya.MystiasIzakaya;
import cc.thonly.mystias_izakaya.recipe.entry.KitchenRecipe;
import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.recipe.BaseRecipeType;
import cc.thonly.reverie_dreams.recipe.ItemStackRecipeWrapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class KitchenRecipeType extends BaseRecipeType<KitchenRecipe> {
    private static KitchenRecipeType INSTANCE;
    public final Map<KitchenType, Map<Identifier, KitchenRecipe>> kitchenRegistries = new Object2ObjectOpenHashMap<>();

    public KitchenRecipeType() {
        INSTANCE = this;
    }

    public static synchronized KitchenRecipeType getInstance() {
        return INSTANCE;
    }

    @Override
    public void reload(ResourceManager manager) {
        this.kitchenRegistries.clear();
        Map<Identifier, Resource> resources = manager.findResources((this.getTypeId() + "_recipe"), id -> {
            return id.getNamespace().equals(Touhou.MOD_ID) && id.getPath().endsWith(".json");
        });
        for (Map.Entry<Identifier, Resource> entry : resources.entrySet()) {
            Identifier id = entry.getKey();
            Identifier registryKey = Identifier.of(id.getNamespace(), id.getPath().replaceFirst("^kitchen_recipe/", "").replaceAll("\\.json$", ""));
            Resource resource = entry.getValue();
            try (InputStream stream = resource.getInputStream()) {
                JsonElement json = JsonParser.parseReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
                Dynamic<JsonElement> input = new Dynamic<>(JsonOps.INSTANCE, json);

                DataResult<KitchenRecipe> result = this.getCodec().parse(input);

                result.resultOrPartial(error -> log.error("Failed to load kitchen recipe {}, {}", id, error))
                        .ifPresent(recipe -> {
                            this.add(registryKey, recipe);
                        });
            } catch (IOException e) {
                log.error("Failed to load kitchen recipe {}, {}, {}", id, e.getMessage(), e);
            }
        }
    }

    @Override
    public BaseRecipeType<KitchenRecipe> add(Identifier id, KitchenRecipe recipe) {
        super.add(id, recipe);
        this.register(recipe.getType(), id, recipe);;
        return this;
    }

    public void register(KitchenType type, Identifier key, KitchenRecipe recipe) {
        Map<Identifier, KitchenRecipe> registry = this.kitchenRegistries.computeIfAbsent(type, R -> new Object2ObjectOpenHashMap<>());
        recipe.setId(key);
        registry.put(key, recipe);
    }

    public Map<Identifier, KitchenRecipe> getRecipeView(KitchenType type) {
        return Map.copyOf(this.kitchenRegistries.getOrDefault(type, new Object2ObjectOpenHashMap<>()));
    }

    @Override
    public void bootstrap() {

    }

    public List<KitchenRecipe> getMatches(KitchenType type, List<ItemStackRecipeWrapper> inputs) {
        List<KitchenRecipe> matches = new ArrayList<>();
        Map<Identifier, KitchenRecipe> registryView = this.getRecipeView(type);

        for (KitchenRecipe recipe : registryView.values()) {
            List<ItemStackRecipeWrapper> ingredients = recipe.getIngredients();

            boolean allMatched = ingredients.stream().allMatch(ingredient ->
                    inputs.stream().anyMatch(input ->
                            ingredient.greaterThan(input.getItemStack())
                    )
            );

            if (allMatched) {
                matches.add(recipe);
            }
        }

        return matches;
    }

    @Override
    public List<KitchenRecipe> getMatches(List<ItemStackRecipeWrapper> list) {
        return List.of();
    }

    @Override
    public Boolean isMatch(ItemStackRecipeWrapper input, ItemStackRecipeWrapper recipe) {
        return false;
    }

    @Override
    public Codec<KitchenRecipe> getCodec() {
        return KitchenRecipe.CODEC;
    }

    @Override
    public String getTypeId() {
        return "kitchen";
    }

    @Override
    public Identifier getId() {
        return MystiasIzakaya.id(this.getTypeId());
    }

    @Getter
    public enum KitchenType {
        COOKING_POT(MystiasIzakaya.id("cooking_pot")),
        CUTTING_BOARD(MystiasIzakaya.id("cutting_board")),
        FRYING_PAN(MystiasIzakaya.id("frying_pan")),
        GRILL(MystiasIzakaya.id("grill")),
        STREAMER(Identifier.of("streamer")),
        ;
        private static final Map<Identifier, KitchenType> SEARCH_CACHED = new Object2ObjectOpenHashMap<>();
        private final Identifier id;

        KitchenType(Identifier id) {
            this.id = id;
        }

        public Identifier toId() {
            return this.id;
        }

        public static KitchenType getFromId(Identifier recipeId) {
            if (SEARCH_CACHED.containsKey(recipeId)) {
                SEARCH_CACHED.get(recipeId);
            }
            List<KitchenType> result = Arrays.stream(values()).toList().stream().filter(recipeType -> recipeId.equals(recipeType.id)).toList();
            if (result.isEmpty()) {
                return null;
            } else {
                KitchenType first = result.getFirst();
                SEARCH_CACHED.put(recipeId, first);
                return first;
            }
        }
    }
}
