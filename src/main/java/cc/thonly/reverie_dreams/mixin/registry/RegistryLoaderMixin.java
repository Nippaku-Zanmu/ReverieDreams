package cc.thonly.reverie_dreams.mixin.registry;

import cc.thonly.minecraft.impl.DynamicRegistryManagerCallback;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryLoader;
import net.minecraft.registry.RegistryWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Slf4j
@Mixin(RegistryLoader.class)
public class RegistryLoaderMixin {
    @Inject(method = "load", at = @At("RETURN"), cancellable = true)
    private static void onLoad(RegistryLoader.RegistryLoadable loadable, List<RegistryWrapper.Impl<?>> registries, List<RegistryLoader.Entry<?>> entries, CallbackInfoReturnable<DynamicRegistryManager.Immutable> cir) {

    }
}
