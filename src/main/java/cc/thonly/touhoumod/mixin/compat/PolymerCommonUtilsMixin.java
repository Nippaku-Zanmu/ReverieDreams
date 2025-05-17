package cc.thonly.touhoumod.mixin.compat;

import eu.pb4.polymer.common.api.PolymerCommonUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(remap = false, value = PolymerCommonUtils.class)
public class PolymerCommonUtilsMixin {
//    @Inject(method = "isServerNetworkingThread", at = @At("HEAD"), cancellable = true)
//    private static void isServerNetworkingThread(CallbackInfoReturnable<Boolean> cir) {
//        cir.setReturnValue(true);
//    }
}
