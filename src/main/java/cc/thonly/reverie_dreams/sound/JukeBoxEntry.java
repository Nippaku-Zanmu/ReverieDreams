package cc.thonly.reverie_dreams.sound;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

@Getter
@Setter
public class JukeBoxEntry {
    private final String id;
    private final RegistryKey<JukeboxSong> jukeboxSongRegistryKey;
    private final RegistryEntry.Reference<SoundEvent> soundEventReference;
    private final int length;
    private final int output;
    private JukeboxSong ref;

    public JukeBoxEntry(String id, RegistryKey<JukeboxSong> jukeboxSongRegistryKey, RegistryEntry.Reference<SoundEvent> soundEventReference, int length, int output) {
        this.id = id;
        this.jukeboxSongRegistryKey = jukeboxSongRegistryKey;
        this.soundEventReference = soundEventReference;
        this.length = length;
        this.output = output;
        this.ref = createEntry(this.jukeboxSongRegistryKey, this.soundEventReference, length, output);
    }

    private static JukeboxSong createEntry(RegistryKey<JukeboxSong> key, RegistryEntry.Reference<SoundEvent> soundEvent, int lengthInSeconds, int comparatorOutput) {
        return new JukeboxSong(soundEvent, Text.translatable(Util.createTranslationKey("jukebox_song", key.getValue())), lengthInSeconds, comparatorOutput);
    }

}
