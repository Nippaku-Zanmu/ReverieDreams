package cc.thonly.reverie_dreams.interfaces;

import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import net.minecraft.component.ComponentType;

public interface ComponentMapBuilderImpl {
    public Reference2ObjectMap<ComponentType<?>, Object> getComponents();
}
