package cc.thonly.reverie_dreams.datagen.generator;

import com.google.common.hash.HashCode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import lombok.extern.slf4j.Slf4j;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Slf4j
@Environment(value = EnvType.CLIENT)
public abstract class EquipmentAssetProvider implements DataProvider {
    public final FabricDataOutput output;
    public final CompletableFuture<RegistryWrapper.WrapperLookup> future;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public EquipmentAssetProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> future) {
        this.output = output;
        this.future = future;
    }

    protected abstract void bootstrap(BiConsumer<RegistryKey<EquipmentAsset>, EquipmentModel> consumer);

    protected EquipmentModel createHumanoidOnlyModel(String id) {
        return EquipmentModel.builder().addHumanoidLayers(Identifier.ofVanilla(id)).build();
    }

    protected EquipmentModel createHumanoidAndHorseModel(String id) {
        return EquipmentModel.builder().addHumanoidLayers(Identifier.ofVanilla(id)).addLayers(EquipmentModel.LayerType.HORSE_BODY, EquipmentModel.Layer.createWithLeatherColor(Identifier.ofVanilla(id), false)).build();
    }

    protected EquipmentModel createHumanoidOnlyModel(Identifier id) {
        return EquipmentModel.builder().addHumanoidLayers(id).build();
    }

    protected EquipmentModel createHumanoidAndHorseModel(Identifier id) {
        return EquipmentModel.builder().addHumanoidLayers(id).addLayers(EquipmentModel.LayerType.HORSE_BODY, EquipmentModel.Layer.createWithLeatherColor(id, false)).build();
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return CompletableFuture.runAsync(() -> this.export(writer));
    }

    public void export(DataWriter writer) {
        try {
            Path path = Paths.get(DataGeneratorUtil.OUTPUT_DIR);
            HashMap<RegistryKey<EquipmentAsset>, EquipmentModel> map = new HashMap<>();
            this.bootstrap((key, model) -> {
                if (map.putIfAbsent(key, model) != null) {
                    throw new IllegalStateException("Tried to register equipment asset twice for id: " + String.valueOf(key));
                }
                Identifier identifier = key.getValue();
                String namespaceRef = identifier.getNamespace();
                String pathRef = identifier.getPath();
                Path generatePath = DataGeneratorUtil.getAssets(path, namespaceRef, "equipment", null);

                DataResult<JsonElement> result = EquipmentModel.CODEC.encodeStart(JsonOps.INSTANCE, model);
                Optional<JsonElement> optional = result.result();

                if (optional.isPresent()) {
                    JsonElement element = optional.get();
                    Path output = generatePath.resolve(pathRef + ".json");
                    String jsonString = this.gson.toJson(element);
                    byte[] bytes = jsonString.getBytes(StandardCharsets.UTF_8);
                    try {
                        Files.createDirectories(output.getParent());
                        writer.write(output, bytes, HashCode.fromBytes(bytes));
                    } catch (IOException e) {
                        log.error("Can't generate equipment asset {}", identifier.toString());
                    }
                }
            });
        } catch (Exception err) {
            log.error("Fail to generate equipment provider");
        }
    }

    @Override
    public String getName() {
        return "Equipment Asset Definitions";
    }
}
