package cc.thonly.touhoumod.mixin.server;

import cc.thonly.touhoumod.interfaces.ServerAdvancementLoaderAccessorImpl;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ServerAdvancementLoader.class)
public interface ServerAdvancementLoaderAccessor extends ServerAdvancementLoaderAccessorImpl {
    @Accessor("advancements")
    public Map<Identifier, AdvancementEntry> getAdvancements();
}
