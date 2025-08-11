package cc.thonly.reverie_dreams.interfaces;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

public interface IWorld {
    public RegistryKey<World> getDreamWorld();
    public RegistryKey<World> getMoon();
}
