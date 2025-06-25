package cc.thonly.reverie_dreams.networking;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.registry.RegistryManager;
import cc.thonly.reverie_dreams.registry.StandaloneRegistry;
import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.*;

@Slf4j
public record RegistrySyncPayload(List<List<?>> object) implements CustomPayload {
    public static final Gson GSON = new Gson();
    public static final Identifier hello = Touhou.id("rd_sync");
    public static final CustomPayload.Id<RegistrySyncPayload> PACKET_ID = new CustomPayload.Id<>(hello);
    public static final PacketCodec<RegistryByteBuf, RegistrySyncPayload> codec = PacketCodec.of(RegistrySyncPayload::write, RegistrySyncPayload::read);

    public static RegistrySyncPayload read(RegistryByteBuf buf) {
        List<List<?>> entries = new ArrayList<>();
        String jsonString = buf.readString();
        JsonArray array = JsonParser.parseString(jsonString).getAsJsonArray();

        for (JsonElement element : array) {
            if (element instanceof JsonObject object) {
                try {
                    String keyStr = object.get("key").getAsString();
                    JsonElement registryElement = object.get("registry");

                    Identifier id = Identifier.of(keyStr);
                    StandaloneRegistry<?> schema = RegistryManager.REGISTRIES.get(id);

                    if (schema != null && registryElement != null) {
                        entries.add(schema.decode(registryElement));
                    }
                } catch (Exception e) {
                    log.error("Can't read buf", e);
                }
            }
        }

        return new RegistrySyncPayload(entries);
    }


    public void write(RegistryByteBuf buf) {
        JsonArray array = new JsonArray();
        Set<Map.Entry<Identifier, StandaloneRegistry<?>>> registries = RegistryManager.REGISTRIES.entrySet();
        for (Map.Entry<Identifier, StandaloneRegistry<?>> registryRef : registries) {
            try {
                StandaloneRegistry<?> registry = registryRef.getValue();
                boolean sync = registry.isSync();
                if (sync) {
                    JsonObject object = new JsonObject();
                    Identifier key = registryRef.getKey();
                    JsonElement element = registry.encode();
                    object.addProperty("key", key.toString());
                    object.add("registry", element);
                    array.add(object);
                }
            } catch (Exception e) {
                log.error("Can't write buf", e);
            }
        }
        buf.writeString(GSON.toJson(array));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
