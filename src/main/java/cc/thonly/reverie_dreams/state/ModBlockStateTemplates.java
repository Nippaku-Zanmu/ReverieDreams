package cc.thonly.reverie_dreams.state;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.state.property.EnumProperty;

import java.util.Map;

public interface ModBlockStateTemplates {
    Map<String, EnumProperty<?>> STATES = new Object2ObjectOpenHashMap<>();
    EnumProperty<EightDirection> FACING_8 = EnumProperty.of("facing_8", EightDirection.class);
    EnumProperty<SixteenDirection> FACING_16 = EnumProperty.of("facing_16", SixteenDirection.class);

    public static void bootstrap() {
        register("facing_8", FACING_8);
        register("facing_16", FACING_16);
    }

    public static EnumProperty<?> register(String name, EnumProperty<?> property) {
        return STATES.putIfAbsent(name, property);
    }
}
