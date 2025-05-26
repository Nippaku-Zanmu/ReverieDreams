package cc.thonly.touhoumod.recipe;

import cc.thonly.touhoumod.recipe.entry.StrengthTableRecipe;
import cc.thonly.touhoumod.recipe.slot.CountRecipeSlot;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.*;

@Setter
@Getter
@ToString
public class StrengthTableRecipes extends CustomRecipes {
    private static final RecipeRegistryBase<StrengthTableRecipe.Entry> INSTANCE = new RecipeRegistryBase<>();

    public static void registerRecipes() {
        RecipeRegistryBase<StrengthTableRecipe.Entry> registry = getRecipeRegistryRef();

    }

    public static RecipeRegistryBase<StrengthTableRecipe.Entry> getRecipeRegistryRef() {
        return INSTANCE;
    }

    public static class RecipeRegistryBase<T extends StrengthTableRecipe.Entry> extends SimpleRecipeRegistryBase<T> {
        List<Identifier> keys = new ArrayList<>();
        List<T> entries = new ArrayList<>();

        private RecipeRegistryBase() {

        }

        @Override
        public T register(T entry) {
            if (this.entries.contains(entry)) {
                return null;
            }
            this.entries.add(entry);
            while (this.keys.size() < this.entries.size()) {
                this.keys.add(Identifier.of("null"));
            }
            return entry;
        }

        @Override
        public T register(Identifier id, T entry) {
            if (this.keys.contains(id)) {
                return null;
            }
            this.keys.add(id);
            return this.register(entry);
        }

        public T tryGetRecipes(CountRecipeSlot mainItem, CountRecipeSlot offItem) {
            for (T entry: this.entries) {
                boolean isMatch = true;
                isMatch = isMatch(isMatch, mainItem, entry.getMainItem());
                isMatch = isMatch(isMatch, offItem, entry.getOffItem());
                if(isMatch) {
                    return entry;
                }
            }
            return null;
        }

        private boolean isMatch(boolean prevMatch, CountRecipeSlot inputSlot, CountRecipeSlot recipeSlot) {
            if (!prevMatch) return false;
            if (inputSlot.getItem() != Items.AIR && recipeSlot.getItem() != Items.AIR) {
                if (inputSlot.getItem() != recipeSlot.getItem()) return false;
                if (inputSlot.getCount() < recipeSlot.getCount()) return false;
            } else {
                return false;
            }
            return true;
        }

        @Override
        public T get(Identifier key) {
            if (this.keys.size() != this.entries.size()) {
                return null;
            }
            int i = this.keys.indexOf(key);
            if (i == -1) {
                return null;
            }
            return this.entries.get(i);
        }

        @Override
        public Map<Identifier, T> getAll() {
            Map<Identifier, T> all = new HashMap<>();
            for (Identifier key: keys) {
                T value = this.get(key);
                all.put(key, value);
            }
            return all;
        }

        @Override
        public int size() {
            return Math.max(this.keys.size(), this.entries.size());
        }

        @Override
        public List<Identifier> getKeys() {
            return new ArrayList<>(this.keys);
        }

        @Override
        public Set<T> getEntries() {
            return new HashSet<>(this.entries);
        }

        @Override
        public void clear() {
            this.keys.clear();
            this.entries.clear();
        }

        @Override
        public List<SimpleRecipeRegistryBase<T>.Key2ValueEntry> toList() {
            List<SimpleRecipeRegistryBase<T>.Key2ValueEntry> list = new LinkedList<>();
            for (var key: this.keys) {
                list.add(new Key2ValueEntry(key, this.get(key)));
            }
            return list;
        }
    }
}
