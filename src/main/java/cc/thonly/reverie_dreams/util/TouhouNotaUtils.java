package cc.thonly.reverie_dreams.util;

import lombok.extern.slf4j.Slf4j;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import nota.model.RepeatMode;
import nota.model.Song;
import nota.player.EntitySongPlayer;
import nota.player.SongPlayer;
import nota.utils.NBSDecoderPlus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
public final class TouhouNotaUtils {
    public static final String STR_PATH = "config/reverie_dreams";
    public static final Path PATH = Paths.get(STR_PATH);
    public static final Map<Identifier, SongPlayer> id2Song = new HashMap<>();

    static {
        try {
            if (!Files.exists(PATH)) {
                Files.createDirectories(PATH);
            }
        } catch (IOException e) {
            log.error("Failed to create directory: " + STR_PATH, e);
        }
    }

    public static void play(ServerPlayerEntity player, String playingMusic, NoteBlockInstrument noteBlockInstrument) {
        String filename = playingMusic;
        playingMusic = playingMusic.replaceAll(" ", "_");
        playingMusic = playingMusic.toLowerCase();
        MinecraftServer server = player.getServer();
        assert server != null;
        PlayerManager playerManager = server.getPlayerManager();
        Song song;
        try {
            song = NBSDecoderPlus.parse(getFilePath(filename).toFile(), noteBlockInstrument);
        } catch (Exception e) {
            log.error("读取音乐失败: " + playingMusic, e);
            player.sendMessage(Text.literal("§c无法读取音乐：" + playingMusic), false);
            return;
        }

        Identifier id = null;
        try {
            id = Identifier.ofVanilla(player.getGameProfile().getName().toLowerCase() + "_" + playingMusic);
        } catch (Exception e) {
            log.error("Can't set music id minecraft:" + playingMusic, e);
            id = Identifier.of(UUID.randomUUID().toString().replaceAll("-", ""));
        }
        SongPlayer prev = id2Song.get(id);
        if (prev != null) {
            prev.setPlaying(false);
        }

        EntitySongPlayer esp = new EntitySongPlayer(song);
        esp.setId(id);
        esp.setEntity(player);
        esp.setDistance(32);
        esp.setRepeatMode(RepeatMode.NONE);
        for (var sPlayer : playerManager.getPlayerList()) {
            esp.addPlayer(sPlayer);
        }
        esp.setPlaying(true);
        id2Song.put(id, esp);
    }

    public static List<String> getFileNames() {
        try {
            return Files.list(PATH)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().toLowerCase().endsWith(".nbs"))
                    .map(path -> path.getFileName().toString())
                    .toList();
        } catch (IOException e) {
            log.warn("扫描音乐目录失败", e);
            return new ArrayList<>();
        }
    }

    public static Path getFilePath(String filename) {
        return PATH.resolve(filename);
    }
}
