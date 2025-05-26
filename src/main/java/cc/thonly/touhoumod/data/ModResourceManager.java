package cc.thonly.touhoumod.data;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.recipe.DanmakuRecipes;
import cc.thonly.touhoumod.recipe.GensokyoAltarRecipes;
import cc.thonly.touhoumod.recipe.SimpleRecipeRegistryBase;
import cc.thonly.touhoumod.recipe.StrengthTableRecipes;
import cc.thonly.touhoumod.recipe.entry.DanmakuRecipe;
import cc.thonly.touhoumod.recipe.entry.GensokyoAltarRecipe;
import cc.thonly.touhoumod.recipe.entry.StrengthTableRecipe;
import cc.thonly.touhoumod.registry.RegistrySchema;
import cc.thonly.touhoumod.registry.RegistrySchemas;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

public class ModResourceManager {
    public static void registerHooks() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return Touhou.id("data");
            }

            @Override
            public void reload(ResourceManager manager) {
                DanmakuRecipes.getRecipeRegistryRef().clear();
                DanmakuRecipes.registerRecipes();
                GensokyoAltarRecipes.getRecipeRegistryRef().clear();
                GensokyoAltarRecipes.registerRecipes();
                for (var entry : RegistrySchemas.INSTANCE.entrySet()) {
                    RegistrySchema<?> registrySchema = entry.getValue();
                    if (registrySchema.isReloadable() && registrySchema.getReloadableBootstrap() != null) {
                        registrySchema.reset();
                        registrySchema.getReloadableBootstrap().reload(manager);
                    }
                }
                this.load(manager);
            }

            public void load(ResourceManager manager) {
                parseRecipe(manager, "danmaku", DanmakuRecipes.getRecipeRegistryRef(), DanmakuRecipe.Builder.ENTRY_CODEC);
                parseRecipe(manager, "gensokyo_altar", GensokyoAltarRecipes.getRecipeRegistryRef(), GensokyoAltarRecipe.Builder.ENTRY_CODEC);
                parseRecipe(manager, "strength_table", StrengthTableRecipes.getRecipeRegistryRef(), StrengthTableRecipe.Builder.ENTRY_CODEC);
            }

            public <T> void parseRecipe(ResourceManager manager, String type, SimpleRecipeRegistryBase<T> registry, Codec<T> codec) {
                Map<Identifier, Resource> resources = manager.findResources(("recipes/" + type), id -> {
                    return id.getNamespace().equals(Touhou.MOD_ID) && id.getPath().endsWith(".json");
                });
                for (Map.Entry<Identifier, Resource> entry : resources.entrySet()) {
                    Identifier id = entry.getKey();
                    Resource resource = entry.getValue();
                    try (InputStream stream = resource.getInputStream()) {
                        JsonElement element = JsonParser.parseReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
                        Dynamic<JsonElement> input = new Dynamic<>(JsonOps.INSTANCE, element);
                        DataResult<T> parse = codec.parse(input);
                        Optional<T> result = parse.result();
                        result.ifPresent((value) -> {
                            registry.register(id, value);
                        });
                    } catch (IOException e) {
                        Touhou.LOGGER.error("[{} recipe] Failed to load recipe {}.", type, id, e);
                    }
                }
            }
        });
    }

    interface ParseFunction {
        void apply(Identifier id, JsonElement element);
    }
}
