package cc.thonly.reverie_dreams.datagen;

import cc.thonly.reverie_dreams.datagen.generator.SoundProvider;
import cc.thonly.reverie_dreams.sound.ModJukeboxSongs;
import cc.thonly.reverie_dreams.sound.ModSoundEvents;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModSoundProvider extends SoundProvider {
    public ModSoundProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> future) {
        super(output, future);
    }

    @Override
    public void configured() {
        this.addWithRecords(ModJukeboxSongs.HR01_01, null);
        this.addWithRecords(ModJukeboxSongs.HR03_01, null);
        this.addWithRecords(ModJukeboxSongs.TH15_16, null);
        this.addWithRecords(ModJukeboxSongs.TH15_17, null);
        for (var soundEvent : ModSoundEvents.SOUND_EVENTS) {
            this.addWithSoundEvent(soundEvent, null);
        }
    }
}
