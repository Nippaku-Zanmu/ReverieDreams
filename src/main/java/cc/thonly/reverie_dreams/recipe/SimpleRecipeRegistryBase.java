package cc.thonly.reverie_dreams.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class SimpleRecipeRegistryBase<T> {
    public abstract T register(Identifier key, T entry);
    public abstract T register(T entry);
    public abstract T get(Identifier key);
    public abstract List<Identifier> getKeys();
    public abstract Set<T> getEntries();
    public abstract Map<Identifier, T> getAll();
    public abstract int size();
    public abstract void clear();
    public abstract List<Key2ValueEntry> toList();

    @AllArgsConstructor
    @Getter
    @ToString(callSuper = true)
    public class Key2ValueEntry {
        public final Identifier key;
        public final T value;
    }
}
