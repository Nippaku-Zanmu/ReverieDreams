package cc.thonly.touhoumod.block;

import lombok.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SignBlockGroup {
    public Block standingBlock;
    public Block wallBlock;
    public Item sign;
}
