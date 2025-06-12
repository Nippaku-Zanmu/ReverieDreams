package cc.thonly.reverie_dreams.mixin;

import cc.thonly.reverie_dreams.interfaces.SimpleRegistrySetter;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(SimpleRegistry.class)
public class SimpleRegistryMixin<T> implements SimpleRegistrySetter {

    @Shadow
    private boolean frozen;

    @Shadow
    private @Nullable Map<T, RegistryEntry.Reference<T>> intrusiveValueToEntry;

    @Override
    public void setFrozen(boolean value) {
        this.frozen = value;
    }

//    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
//    public void add(RegistryKey<T> key, T value, RegistryEntryInfo info, CallbackInfoReturnable<RegistryEntry.Reference<T>> cir) {
//        if(intrusiveValueToEntry == null) {
//            this.intrusiveValueToEntry = new IdentityHashMap<>();
//        }
//    }
//
//    @Inject(method = "createEntry", at = @At("HEAD"), cancellable = true)
//    public void createEntry(T value, CallbackInfoReturnable<RegistryEntry.Reference<T>> cir) {
//        if(intrusiveValueToEntry == null) {
//            this.intrusiveValueToEntry = new IdentityHashMap<>();
//        }
//        cir.setReturnValue(this.intrusiveValueToEntry.computeIfAbsent(value, valuex -> RegistryEntry.Reference.intrusive((net.minecraft.registry.entry.RegistryEntryOwner<T>) this, valuex)));
//        cir.cancel();
//    }
}
