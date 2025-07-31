package cc.thonly.reverie_dreams.entity.npc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum NPCWorkMode {
    COMBAT(0, Items.IRON_SWORD),
    FARM(1,Items.WHEAT_SEEDS),
    BREED(2,Items.WHEAT),
    SMELT(3,Items.FURNACE);
    
    private final Integer index;
    private final Item itemDisplay;
    public NPCWorkMode getNext(){
        NPCWorkMode npcWorkMode = fromInt(index + 1);
        return npcWorkMode ==null?fromInt(0):npcWorkMode;
    }
    public NPCWorkMode getPrevious(){
        NPCWorkMode npcWorkMode = fromInt(index - 1);
        return npcWorkMode ==null?fromInt(NPCWorkMode.values().length-1):npcWorkMode;
    }
    public static NPCWorkMode fromInt(Integer value) {
        return Arrays.stream(values())
                .filter(state -> state.index.equals(value))
                .findFirst()
                .orElse(null);
    }

}
