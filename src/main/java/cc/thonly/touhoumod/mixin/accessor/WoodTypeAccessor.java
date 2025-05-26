package cc.thonly.touhoumod.mixin.accessor;

import net.minecraft.block.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(WoodType.class)
public interface WoodTypeAccessor {
    @Accessor("VALUES")
    static Map<String, WoodType> getValues() {
        throw new UnsupportedOperationException();
    }
}
