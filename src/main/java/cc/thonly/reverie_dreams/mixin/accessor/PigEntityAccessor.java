package cc.thonly.reverie_dreams.mixin.accessor;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.PigVariant;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PigEntity.class)
public interface PigEntityAccessor {
    @Accessor("VARIANT")
    public static TrackedData<RegistryEntry<PigVariant>> VARIANT() {
        throw new UnsupportedOperationException();
    }
}
