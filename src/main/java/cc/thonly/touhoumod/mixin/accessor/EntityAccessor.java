package cc.thonly.touhoumod.mixin.accessor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.data.TrackedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor
    static TrackedData<Boolean> getNAME_VISIBLE() {
        throw new UnsupportedOperationException();
    }
}