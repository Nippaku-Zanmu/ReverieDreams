package cc.thonly.touhoumod.component;

import cc.thonly.touhoumod.Touhou;
import com.mojang.serialization.Codec;
import eu.pb4.polymer.core.api.other.PolymerComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.List;

public class ModDataComponentTypes {
    public static class Danmaku {
        public static final ComponentType<String> TEMPLATE = registerComponent("template",
                ComponentType.<String>builder().codec(Codec.STRING).build());
        public static final ComponentType<Integer> COLOR = registerComponent("color",
                ComponentType.<Integer>builder().codec(Codec.INT).build());
        public static final ComponentType<Integer> COUNT = registerComponent("count",
                ComponentType.<Integer>builder().codec(Codec.INT).build());
        public static final ComponentType<Float> DAMAGE = registerComponent("damage",
                ComponentType.<Float>builder().codec(Codec.FLOAT).build());
        public static final ComponentType<String> DAMAGE_TYPE = registerComponent("damage_type",
                ComponentType.<String>builder().codec(Codec.STRING).build());
        public static final ComponentType<Float> SCALE = registerComponent("scale",
                ComponentType.<Float>builder().codec(Codec.FLOAT).build());
        public static final ComponentType<Float> SPEED = registerComponent("speed",
                ComponentType.<Float>builder().codec(Codec.FLOAT).build());
        public static final ComponentType<Boolean> TILE = registerComponent("tile",
                ComponentType.<Boolean>builder().codec(Codec.BOOL).build());
        public static final ComponentType<Boolean> INFINITE = registerComponent("infinite",
                ComponentType.<Boolean>builder().codec(Codec.BOOL).build());

        public static void init() {

        }
    }

    public static final ComponentType<Integer> MAX_DISTANCE = registerComponent("max_disatance",
            ComponentType.<Integer>builder().codec(Codec.INT).build());
    public static final ComponentType<List<GapRecorder>> GAP_RECORDER = registerComponent("gap_recorder",
            ComponentType.<List<GapRecorder>>builder().codec(GapRecorder.LIST_CODEC).build());
    public static final ComponentType<BattleStickRecorder> BATTLE_STICK_RECORDER = registerComponent("battle_stick_recorder",
            ComponentType.<BattleStickRecorder>builder().codec(BattleStickRecorder.CODEC).build());

    public static void init() {
        Danmaku.init();
    }

    public static <T> ComponentType<T> registerComponent(String path, ComponentType<T> componentType) {
        ComponentType<T> value = Registry.register(Registries.DATA_COMPONENT_TYPE, Touhou.id(path), componentType);
        PolymerComponent.registerDataComponent(value);
        return value;
    }
}
