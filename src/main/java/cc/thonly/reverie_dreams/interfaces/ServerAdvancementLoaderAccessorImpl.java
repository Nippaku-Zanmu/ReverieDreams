package cc.thonly.reverie_dreams.interfaces;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.util.Identifier;

import java.util.Map;

public interface ServerAdvancementLoaderAccessorImpl {
    Map<Identifier, AdvancementEntry> getAdvancements();
}
