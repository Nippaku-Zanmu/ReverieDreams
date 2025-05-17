package cc.thonly.touhoumod.interfaces;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.util.Identifier;

import java.util.Map;

public interface ServerAdvancementLoaderAccessorImpl {
    Map<Identifier, AdvancementEntry> getAdvancements();
}
