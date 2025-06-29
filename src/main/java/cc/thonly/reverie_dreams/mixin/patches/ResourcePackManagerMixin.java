package cc.thonly.reverie_dreams.mixin.patches;

import lombok.extern.slf4j.Slf4j;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

@Mixin(ResourcePackManager.class)
@Pseudo
@Slf4j
public class ResourcePackManagerMixin {
    @Shadow @Final private Set<ResourcePackProvider> providers;

    @Redirect(
            method = "providePackProfiles",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/resource/ResourcePackProvider;register(Ljava/util/function/Consumer;)V"
            )
    )
    private void redirectRegister(ResourcePackProvider instance, Consumer<ResourcePackProfile> consumer) {
        try {
            instance.register(consumer);
        } catch (Exception e) {
            log.error("ResourcePackProvider register failed: ", e);
        }
    }
}
