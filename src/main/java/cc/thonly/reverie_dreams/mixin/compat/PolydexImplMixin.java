package cc.thonly.reverie_dreams.mixin.compat;

import cc.thonly.reverie_dreams.compat.PolydexCompatImpl;
import eu.pb4.polydex.impl.PolydexImpl;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(PolydexImpl.class)
public class PolydexImplMixin {
    @Inject(method = "onReload", at = @At("TAIL"), cancellable = true)
    private static void onReload(ResourceManager manager, CallbackInfo ci) {
        PolydexCompatImpl.registerEntries();
    }
}
