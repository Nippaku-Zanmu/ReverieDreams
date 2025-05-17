package cc.thonly.touhoumod.datagen;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.datagen.generator.JukeboxProvider;
import cc.thonly.touhoumod.sound.JukeBoxEntry;
import cc.thonly.touhoumod.sound.ModJukeboxSongs;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModJukeboxProvider extends JukeboxProvider {
    public ModJukeboxProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> future) {
        super(output, future);
    }

    @Override
    public void configured() {
        for (JukeBoxEntry entry : ModJukeboxSongs.ENTRIES) {
            this.add(Touhou.id(entry.getId()), entry.getRef());
        }
    }
}
