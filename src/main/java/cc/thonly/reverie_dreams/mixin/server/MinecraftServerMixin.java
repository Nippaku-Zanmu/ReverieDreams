package cc.thonly.reverie_dreams.mixin.server;

import cc.thonly.minecraft.impl.DynamicRegistryManagerCallback;
import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.item.armor.EarphoneItem;
import com.mojang.datafixers.DataFixer;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.SaveLoader;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.util.ApiServices;
import net.minecraft.world.level.storage.LevelStorage;
import nota.Nota;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.Proxy;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.BooleanSupplier;

@Slf4j
@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(Thread serverThread, LevelStorage.Session session, ResourcePackManager dataPackManager, SaveLoader saveLoader, Proxy proxy, DataFixer dataFixer, ApiServices apiServices, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory, CallbackInfo ci) {
        MinecraftServer minecraftServer = (MinecraftServer) (Object) this;
        DynamicRegistryManager.Immutable registryManager = minecraftServer.getRegistryManager();
        Touhou.setServer(minecraftServer);
        Touhou.setDynamicRegistryManager(registryManager);
        Nota.getAPI().server = minecraftServer;
    }

    @Inject(method = "reloadResources", at = @At("TAIL"))
    public void reload(Collection<String> dataPacks, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        MinecraftServer minecraftServer = (MinecraftServer) (Object) this;
        cir.getReturnValue().handle((value, throwable) -> {
            try {
                DynamicRegistryManagerCallback.start(minecraftServer);
            } catch (Exception err) {
                log.error("Can't modify dynamic registry manager");
            }
            return value;
        });
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void onTickEnd(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        EarphoneItem.VEC_3_DS.clear();
    }
}
