package cc.thonly.reverie_dreams.world;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

public interface WorldGetter {
    public RegistryKey<World> getDreamWorld();
    public RegistryKey<World> getMoon();
}
