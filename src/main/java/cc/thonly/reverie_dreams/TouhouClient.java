package cc.thonly.reverie_dreams;

import cc.thonly.reverie_dreams.networking.CustomBytePayload;
import cc.thonly.reverie_dreams.networking.CustomBytePayloadClient;
import com.mojang.serialization.Codec;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TouhouClient implements ClientModInitializer {
    public static final Codec<TouhouClient> CODEC = Codec.unit(TouhouClient::new);
    public static final String MOD_ID = Touhou.MOD_ID;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final List<Block> SERVER_SIDE_BLOCKS = List.of(Blocks.NOTE_BLOCK, Blocks.TRIPWIRE);
    public static boolean isPlayed = false;

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(CustomBytePayload.PACKET_ID, CustomBytePayloadClient.Receiver::receiveClient);
    }

    public static Identifier id(String id) {
        return Identifier.of(MOD_ID, id);
    }

}