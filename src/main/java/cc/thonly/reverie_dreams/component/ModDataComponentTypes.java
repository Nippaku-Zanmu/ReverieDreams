package cc.thonly.reverie_dreams.component;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.item.MusicalInstrumentItem;
import com.mojang.serialization.Codec;
import eu.pb4.polymer.core.api.other.PolymerComponent;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModDataComponentTypes {
    public static class Danmaku {
        public static final ComponentType<String> TEMPLATE = registerComponent("template",
                ComponentType.<String>builder().codec(Codec.STRING).build());
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
        public static final ComponentType<Float> ACCELERATION = registerComponent("acceleration",
                ComponentType.<Float>builder().codec(Codec.FLOAT).build());
        public static final ComponentType<Boolean> TILE = registerComponent("tile",
                ComponentType.<Boolean>builder().codec(Codec.BOOL).build());
        public static final ComponentType<Boolean> INFINITE = registerComponent("infinite",
                ComponentType.<Boolean>builder().codec(Codec.BOOL).build());

        public static void init() {

        }
    }
    public static final ComponentType<Identifier> ROLE_CARD_ID = registerComponent("role_card_id",
            ComponentType.<Identifier>builder()
                    .codec(Identifier.CODEC)
                    .build());
    public static final ComponentType<Integer> MAX_DISTANCE = registerComponent("max_disatance",
            ComponentType.<Integer>builder()
                    .codec(Codec.INT)
                    .build());
    public static final ComponentType<List<GapRecorder>> GAP_RECORDER = registerComponent("gap_recorder",
            ComponentType.<List<GapRecorder>>builder()
                    .codec(GapRecorder.LIST_CODEC)
                    .build());
    public static final ComponentType<BattleStickRecorder> BATTLE_STICK_RECORDER = registerComponent("battle_stick_recorder",
            ComponentType.<BattleStickRecorder>builder()
                    .codec(BattleStickRecorder.CODEC)
                    .build());
    public static final ComponentType<String> PLAYING_MUSIC = registerComponent("playing_music",
            ComponentType.<String>builder()
                    .codec(Codec.STRING)
                    .build());
    public static final ComponentType<NoteBlockInstrument> NOTE_TYPE = registerComponent("note_type",
            ComponentType.<NoteBlockInstrument>builder()
                    .codec(MusicalInstrumentItem.NOTE_BLOCK_INSTRUMENT_CODEC)
                    .build());
    public static final ComponentType<RoleFollowerArchive> ROLE_FOLLOWER_ARCHIVE = registerComponent("role_follower_archive",
            ComponentType.<RoleFollowerArchive>builder()
                    .codec(RoleFollowerArchive.CODEC)
                    .build()
            );
    public static final ComponentType<Boolean> ROLE_CAN_RESPAWN = registerComponent("role_can_respawn",
            ComponentType.<Boolean>builder()
                    .codec(Codec.BOOL)
                    .build()
            );

    static {
        Danmaku.init();
    }

    public static void init() {
    }

    public static <T> ComponentType<T> registerComponent(String path, ComponentType<T> componentType) {
        ComponentType<T> value = Registry.register(Registries.DATA_COMPONENT_TYPE, Touhou.id(path), componentType);
        PolymerComponent.registerDataComponent(value);
        return value;
    }
}
