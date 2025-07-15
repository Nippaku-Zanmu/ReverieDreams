package cc.thonly.reverie_dreams.entity.npc;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum NPCState {
    FOLLOW(0),
    NORMAL(1),
    NO_WALK(2),
    SNAKING(3),
    SEATED(4),
    WORKING(5),
    ;
    private final Integer id;

    public static NPCState fromInt(Integer value) {
        return Arrays.stream(values())
                .filter(state -> state.id.equals(value))
                .findFirst()
                .orElse(null);
    }
}