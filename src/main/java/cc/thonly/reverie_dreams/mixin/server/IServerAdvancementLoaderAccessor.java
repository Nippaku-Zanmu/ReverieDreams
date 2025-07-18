package cc.thonly.reverie_dreams.mixin.server;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ServerAdvancementLoader.class)
public interface IServerAdvancementLoaderAccessor extends cc.thonly.reverie_dreams.interfaces.IServerAdvancementLoaderAccessor {
    @Accessor("advancements")
    public Map<Identifier, AdvancementEntry> getAdvancements();
}
