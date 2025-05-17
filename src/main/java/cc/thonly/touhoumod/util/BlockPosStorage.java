package cc.thonly.touhoumod.util;

import com.google.gson.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.math.BlockPos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Slf4j
public class BlockPosStorage {
    private static final Map<Identifier, BlockPosStorage> INSTANCE = new Object2ObjectOpenHashMap<>();
    private final Identifier key;
    private final Set<Long> posSet = new HashSet<>();

    public BlockPosStorage(Identifier key) {
        this.key = key;
    }

    public static BlockPosStorage getOrCreate(Identifier key) {
        return INSTANCE.computeIfAbsent(key, BlockPosStorage::new);
    }

    public boolean add(BlockPos pos) {
        return posSet.add(pos.asLong());
    }

    public boolean remove(BlockPos pos) {
        return posSet.remove(pos.asLong());
    }

    public boolean contains(BlockPos pos) {
        return posSet.contains(pos.asLong());
    }

    public void clear() {
        posSet.clear();
    }

    public static Map<Identifier, BlockPosStorage> getInstance() {
        return INSTANCE;
    }

    public static void saveToFile(MinecraftServer server) {
        Path path = server.getSavePath(WorldSavePath.ROOT).resolve("data/block_pos_storages.json");

        try {
            Files.createDirectories(path.getParent());
            JsonObject json = BlockPosStorage.saveToJson();
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(json, writer);
            }
        } catch (IOException ioException) {
            log.error("{0}", ioException);
        }
    }

    public static void loadToFile(MinecraftServer server) {
        Path path = server.getSavePath(WorldSavePath.ROOT).resolve("data/block_pos_storages.json");
        if (Files.exists(path)) {
            try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(reader, JsonObject.class);
                BlockPosStorage.loadFromJson(json);
            } catch (IOException ioException) {
                log.error("{0}", ioException);
            }
        }
    }

    public static JsonObject saveToJson() {
        JsonObject root = new JsonObject();
        for (Map.Entry<Identifier, BlockPosStorage> entry : INSTANCE.entrySet()) {
            Identifier key = entry.getKey();
            BlockPosStorage storage = entry.getValue();

            JsonArray jsonElements = new JsonArray();
            for (long packed : storage.getPosSet()) {
                jsonElements.add(packed);
            }

            root.add(key.toString(), jsonElements);
        }
        return root;
    }

    public static void loadFromJson(JsonObject root) {
        INSTANCE.clear();
        for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
            String keyStr = entry.getKey();
            Identifier key = Identifier.of(keyStr);
            JsonArray array = entry.getValue().getAsJsonArray();

            BlockPosStorage storage = getOrCreate(key);
            for (JsonElement elem : array) {
                long packed = elem.getAsLong();
                storage.getPosSet().add(packed);
            }
        }
    }

}
