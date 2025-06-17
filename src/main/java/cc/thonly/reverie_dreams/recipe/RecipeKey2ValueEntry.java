package cc.thonly.reverie_dreams.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import net.minecraft.util.Identifier;

@AllArgsConstructor
@Getter
@ToString(callSuper = true)
public class RecipeKey2ValueEntry<T> {
    public final Identifier key;
    public final T value;

}
