package cc.thonly.touhoumod.sound;

import cc.thonly.touhoumod.Touhou;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;

import java.util.LinkedList;
import java.util.List;

import static cc.thonly.touhoumod.sound.ModSoundEvents.registerReference;

public class ModJukeboxSongs {
    public static final List<JukeBoxEntry> ENTRIES = new LinkedList<>();
    public static final JukeBoxEntry HR01_01 = registerJukeBoxEntry("hr01_01", 233, 6);
    public static final JukeBoxEntry HR03_01 = registerJukeBoxEntry("hr03_01", 309, 6);
    public static final JukeBoxEntry TH15_16 = registerJukeBoxEntry("th15_16", 216, 6);
    public static final JukeBoxEntry TH15_17 = registerJukeBoxEntry("th15_17", 227, 6);

    private static JukeBoxEntry registerJukeBoxEntry(String id, int length, int output) {
        RegistryKey<JukeboxSong> jukeboxSongRegistryKey = registerJukeBoxSong(id);
        RegistryEntry.Reference<SoundEvent> soundEventReference = registerReference(id);
        JukeBoxEntry entry = new JukeBoxEntry(id, jukeboxSongRegistryKey, soundEventReference, length, output);
        ENTRIES.add(entry);
        return  entry;
    }

    private static RegistryKey<JukeboxSong> registerJukeBoxSong(String id) {
        return RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Touhou.id(id));
    }

    public static void init() {

    }

}
