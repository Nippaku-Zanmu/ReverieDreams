package cc.thonly.touhoumod.mixin;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.world.WorldGetter;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(World.class)
public abstract class WorldMixin implements WorldAccess,
        AutoCloseable, WorldGetter {
    @Unique
    private static final RegistryKey<World> DREAM_WORLD = RegistryKey.of(RegistryKeys.WORLD, Touhou.id("dream_world"));
    @Unique
    private static final RegistryKey<World> THE_MOON = RegistryKey.of(RegistryKeys.WORLD, Touhou.id("the_moon"));


    @Override
    public RegistryKey<World> getDreamWorld() {
        return DREAM_WORLD;
    }

    @Override
    public RegistryKey<World> getMoon() {
        return THE_MOON;
    }
}
