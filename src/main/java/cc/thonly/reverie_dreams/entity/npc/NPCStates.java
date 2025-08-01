package cc.thonly.reverie_dreams.entity.npc;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.registry.RegistryManager;
import cc.thonly.reverie_dreams.registry.StandaloneRegistry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class NPCStates {
    public static final StandaloneRegistry<NPCState> REGISTRY = RegistryManager.NPC_STATE;
    public static final NPCState FOLLOW = register(Touhou.id("follow"), new NPCState("follow"));
    public static final NPCState NORMAL = register(Touhou.id("normal"), new NPCState("normal"));
    public static final NPCState NO_WALK = register(Touhou.id("no_walk"), new NPCState("no_walk"));
    public static final NPCState SNAKING = register(Touhou.id("sneaking"), new NPCState("sneaking"));
    public static final NPCState SEATED = register(Touhou.id("seated"), new NPCState("seated"));
    public static final NPCState WORKING = register(Touhou.id("working"), new NPCState("working"));
    public static final Map<Integer, NPCState> DEFAULT_RAW_ID2STATE = new HashMap<>(
            Map.of(
                    0, FOLLOW,
                    1, NORMAL,
                    2, NO_WALK,
                    3, SNAKING,
                    4, SEATED,
                    5, WORKING
            )
    );

    public static NPCState get(Identifier id) {
        return REGISTRY.getOrDefault(id, NORMAL);
    }

    public static NPCState fromInt(Integer rawId) {
        return DEFAULT_RAW_ID2STATE.getOrDefault(rawId, REGISTRY.get(rawId));
    }

    public static NPCState register(Identifier id, NPCState npcState) {
        return RegistryManager.register(REGISTRY, id, npcState);
    }

    public static void bootstrap(StandaloneRegistry<NPCState> registry) {

    }
}
