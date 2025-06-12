package cc.thonly.reverie_dreams;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TouhouClient implements ClientModInitializer {
    public static final Codec<TouhouClient> CODEC = Codec.unit(TouhouClient::new);
    public static final String MOD_ID = "polymerized-touhou-mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final List<Block> SERVER_SIDE_BLOCKS = List.of(Blocks.NOTE_BLOCK,Blocks.TRIPWIRE);
    public static boolean isPlayed = false;

    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register((networkHandler, packetSender, client) -> {
            if (client.isIntegratedServerRunning()) {
                GameProfile localProfile = client.getGameProfile();
                GameProfile serverProfile = networkHandler.getProfile();

                if (localProfile.getId().equals(serverProfile.getId())) {
//                    PolymerResourcePackMod.generateAndCall(Touhou.getServer(), false, text -> {}, () -> {});
                    client.reloadResources().thenRun(() -> {});
                }
            }
        });
//        Identifier id = id("hatenaki_kaze_no_kisekisae");
//        SoundEvent sound = SoundEvent.of(id);
//        SoundInstance soundInstance = new PositionedSoundInstance(sound.id(), SoundCategory.RECORDS, 1.0f, 1.0f, SoundInstance.createRandom(), false, 0, SoundInstance.AttenuationType.NONE, 0.0, 0.0, 0.0, true);;
//        ((AbstractSoundInstanceAccessor) soundInstance).setRepeat(true);
//        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            if (client.currentScreen instanceof TitleScreen) {
//                if (!client.getSoundManager().isPlaying(soundInstance)) {
//                    isPlayed = true;
//                    MusicTracker musicTracker = client.getMusicTracker();
//                    musicTracker.stop();
//                    client.getSoundManager().stopAll();
//                    client.getSoundManager().play(soundInstance);
//                }
//            }
//        });
    }

    public static Identifier id(String id) {
        return Identifier.of(MOD_ID, id);
    }

}