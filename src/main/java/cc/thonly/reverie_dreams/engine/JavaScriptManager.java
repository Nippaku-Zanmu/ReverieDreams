package cc.thonly.reverie_dreams.engine;

import cc.thonly.mystias_izakaya.MystiasIzakaya;
import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.registry.RegistryManager;
import cc.thonly.reverie_dreams.registry.StandaloneRegistry;
import lombok.extern.slf4j.Slf4j;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class JavaScriptManager {
    public static final String DIRNAME = "javascript_src";
    public static final StandaloneRegistry<JavaScriptElement> REGISTRY = RegistryManager.JAVASCRIPT_ELEMENT;

    public static String getIntermediaryClass(String path) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            return path.replace('.', '/');
        } else {
            return Touhou.MAPPING_RESOLVER.mapClassName("intermediary", path.replace('.', '/'));
        }
    }

    public static Optional<JavaScriptElement> get(Identifier key) {
        return Optional.ofNullable(REGISTRY.get(key));
    }

    public static void reload(ResourceManager manager) {
        REGISTRY.reset();
        Map<Identifier, Resource> resources = manager.findResources(DIRNAME, id ->
                id.getNamespace().equals(Touhou.MOD_ID) && id.getPath().endsWith(".js")
        );
        for (Map.Entry<Identifier, Resource> entry : resources.entrySet()) {
            Identifier fileId = entry.getKey();
            Resource resource = entry.getValue();
            Identifier key = Identifier.of(fileId.getNamespace(), fileId.getPath().replace(DIRNAME+"/", "").replace(".json", ""));
            try (InputStream inputStream = resource.getInputStream()){
                String src = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                RegistryManager.register(REGISTRY, key, new JavaScriptElement(src));
            } catch (Exception e) {
                log.error("Can't load script {}", key, e);
            }
        }
    }

    public static void bootstrap(StandaloneRegistry<JavaScriptElement> registry) {

    }
}
