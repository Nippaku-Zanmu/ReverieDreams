package cc.thonly.reverie_dreams.datagen.generator;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.extern.slf4j.Slf4j;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.jukebox.JukeboxSong;
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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
public abstract class JukeboxProvider implements DataProvider {
    public final FabricDataOutput output;
    public final CompletableFuture<RegistryWrapper.WrapperLookup> future;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Map<Identifier, JukeboxSong> identifierJukeboxSongMap = new Object2ObjectOpenHashMap<>();

    public JukeboxProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> future) {
        this.output = output;
        this.future = future;
        this.configured();
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return CompletableFuture.runAsync(() -> this.export(writer));
    }

    public JukeboxSong add(Identifier id, JukeboxSong song) {
        return this.identifierJukeboxSongMap.put(id, song);
    }

    public abstract void configured();

    @SuppressWarnings("deprecation")
    public void export(DataWriter writer) {
        try {
            Path path = Paths.get(DataGeneratorUtil.OUTPUT_DIR);
            for (var entry: this.identifierJukeboxSongMap.entrySet()) {
                String namespace = entry.getKey().getNamespace();
                String key = entry.getKey().getPath();
                JukeboxSong ref = entry.getValue();
                Path generatePath = DataGeneratorUtil.getData(path, namespace, RegistryKeys.JUKEBOX_SONG, null);

                DataResult<JsonElement> result = JukeboxSong.CODEC.encodeStart(JsonOps.INSTANCE, ref);
                Optional<JsonElement> optional = result.result();

                if (optional.isPresent()) {
                    JsonElement element = optional.get();
                    Path output = generatePath.resolve(key + ".json");
                    String jsonString = this.gson.toJson(element);
                    byte[] bytes = jsonString.getBytes(StandardCharsets.UTF_8);
                    Files.createDirectories(output.getParent());

                    writer.write(output, bytes, Hashing.sha1().hashBytes(bytes));
                }

            }
        } catch (Exception err) {
            log.error("Error: ",err);
        }
    }

    @Override
    public String getName() {
        return "Jukebox Song JSON Provider";
    }
}
