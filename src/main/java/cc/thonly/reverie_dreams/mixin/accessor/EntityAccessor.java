package cc.thonly.reverie_dreams.mixin.accessor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.data.TrackedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor("NAME_VISIBLE")
    static TrackedData<Boolean> getNameVisible() {
        throw new UnsupportedOperationException();
    }
}