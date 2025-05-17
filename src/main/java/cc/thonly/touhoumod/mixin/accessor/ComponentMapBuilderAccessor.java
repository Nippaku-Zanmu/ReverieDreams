package cc.thonly.touhoumod.mixin.accessor;

import cc.thonly.touhoumod.interfaces.ComponentMapBuilderImpl;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ComponentMap.Builder.class)
public interface ComponentMapBuilderAccessor extends ComponentMapBuilderImpl {
    @Accessor("components")
    Reference2ObjectMap<ComponentType<?>, Object> getComponents();
}
