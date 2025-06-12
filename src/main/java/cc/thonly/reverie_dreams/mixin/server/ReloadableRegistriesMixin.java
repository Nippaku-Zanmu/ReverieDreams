package cc.thonly.reverie_dreams.mixin.server;

import net.minecraft.registry.CombinedDynamicRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.ReloadableRegistries;
import net.minecraft.registry.ServerDynamicRegistryType;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ReloadableRegistries.class)
public class ReloadableRegistriesMixin {
    @Inject(method = "reload", at = @At("HEAD"))
    private static void reload(CombinedDynamicRegistries<ServerDynamicRegistryType> dynamicRegistries, List<Registry.PendingTagLoad<?>> pendingTagLoads, ResourceManager resourceManager, Executor prepareExecutor, CallbackInfoReturnable<CompletableFuture<ReloadableRegistries.ReloadResult>> cir) {

    }
}
