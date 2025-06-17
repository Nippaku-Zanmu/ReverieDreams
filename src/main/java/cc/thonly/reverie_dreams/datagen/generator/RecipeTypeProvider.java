package cc.thonly.reverie_dreams.datagen.generator;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.recipe.BaseRecipe;
import cc.thonly.reverie_dreams.recipe.BaseRecipeType;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Slf4j
public abstract class RecipeTypeProvider implements DataProvider {
    public final FabricDataOutput output;
    public final CompletableFuture<RegistryWrapper.WrapperLookup> future;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Map<Identifier, Factory<?>> identifierFactoryMap = new Object2ObjectOpenHashMap<>();

    public RecipeTypeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> future) {
        this.output = output;
        this.future = future;
        this.configured();
    }

    @SuppressWarnings("unchecked")
    public <R extends BaseRecipe> Factory<R> getOrCreateFactory(BaseRecipeType<?> recipeType, Class<R> rCastClass) {
        Identifier id = recipeType.getId();
        if (this.identifierFactoryMap.containsKey(id)) {
            return (Factory<R>) this.identifierFactoryMap.get(id);
        }
        Factory<?> factory = new Factory<>(recipeType);
        this.identifierFactoryMap.put(id, factory);
        return (Factory<R>) factory;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return CompletableFuture.runAsync(() -> this.export(writer));
    }

    public abstract void configured();

    @SuppressWarnings("unchecked")
    public void export(DataWriter writer) {
        try {
            Path path = Paths.get(DataGeneratorUtil.OUTPUT_DIR);
            for (Map.Entry<Identifier, Factory<?>> entry:identifierFactoryMap.entrySet()) {
                Factory<?> factory = entry.getValue();
                Codec codec = factory.getCodec();
                BaseRecipeType<?> recipeType = factory.getRecipeType();
                Map<Identifier, ?> registries = factory.getRegistries();
                Path generatePath = DataGeneratorUtil.getData(path, Touhou.MOD_ID, recipeType.getTypeId() + "_recipe", null);

                for (Map.Entry<Identifier, ?> registryEntry : registries.entrySet()) {
                    Identifier identifier = registryEntry.getKey();
                    Object value = registryEntry.getValue();
                    DataResult<JsonElement> result = codec.encodeStart(JsonOps.INSTANCE, value);
                    Optional<JsonElement> optional = result.result();

                    if (optional.isPresent()) {
                        JsonElement element = optional.get();
                        Path output = generatePath.resolve(identifier.getPath() + ".json");
                        String jsonString = this.gson.toJson(element);
                        byte[] bytes = jsonString.getBytes(StandardCharsets.UTF_8);
                        Files.createDirectories(output.getParent());

                        writer.write(output, bytes, Hashing.sha1().hashBytes(bytes));
                    }
                }
            }
        } catch (Exception err) {
            log.error("Error: ", err);
        }
    }

    @Getter
    public class Factory<R extends BaseRecipe> {
        protected final BaseRecipeType<R> recipeType;
        protected final Codec<R> codec;
        protected final Map<Identifier, R> registries = new Object2ObjectOpenHashMap<>();

        protected Factory(BaseRecipeType<R> recipeType) {
            this.recipeType = recipeType;
            this.codec = recipeType.getCodec();
        }

        public Factory register(Identifier id, R recipe) {
            Identifier identifier = Identifier.of(id.getNamespace(), id.getPath().replaceAll("/", "-"));
            this.registries.put(identifier, recipe);
            return this;
        }
    }

}
