package cc.thonly.reverie_dreams.mixin.accessor;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillagerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(VillagerEntity.class)
public interface VillagerEntityAccessor {
    @Accessor("VILLAGER_DATA")
    public static TrackedData<VillagerData> VILLAGER_DATA() {
        throw new UnsupportedOperationException();
    }

}
