package cc.thonly.reverie_dreams.data;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.recipe.RecipeManager;
import cc.thonly.reverie_dreams.registry.RegistrySchema;
import cc.thonly.reverie_dreams.registry.RegistrySchemas;
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
    public static void init() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new ModServerReloadListener());
    }

    public static class ModServerReloadListener implements SimpleSynchronousResourceReloadListener {
        @Override
        public Identifier getFabricId() {
            return Touhou.id("data");
        }

        @Override
        public void reload(ResourceManager manager) {
            RecipeManager.onReload(manager);
            for (var entry : RegistrySchemas.REGISTRIES.entrySet()) {
                RegistrySchema<?> registrySchema = entry.getValue();
                if (registrySchema.isReloadable() && registrySchema.getReloadableBootstrap() != null) {
                    registrySchema.reset();
                    registrySchema.getReloadableBootstrap().reload(manager);
                }
            }
            this.onLoad(manager);
        }

        public void onLoad(ResourceManager manager) {

        }
    }
}
