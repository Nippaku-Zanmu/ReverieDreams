package cc.thonly.reverie_dreams.recipe;

import cc.thonly.reverie_dreams.recipe.entry.GensokyoAltarRecipe;
import cc.thonly.reverie_dreams.recipe.slot.CountRecipeSlot;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.*;

@Setter
@Getter
@ToString
public class GensokyoAltarRecipes extends CustomRecipes {
    private static final RecipeRegistryBase<GensokyoAltarRecipe.Entry> INSTANCE = new RecipeRegistryBase<>();

    public static void registerRecipes() {
        RecipeRegistryBase<GensokyoAltarRecipe.Entry> registry = getRecipeRegistryRef();


    }

    public static RecipeRegistryBase<GensokyoAltarRecipe.Entry> getRecipeRegistryRef() {
        return INSTANCE;
    }

    public static class RecipeRegistryBase<T extends GensokyoAltarRecipe.Entry> extends SimpleRecipeRegistryBase<T> {
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

        public GensokyoAltarRecipe.Entry tryGetRecipes(SimpleInventory inventory, BlockPos pos) {
            GensokyoAltarRecipe.Entry entry = null;
            List<CountRecipeSlot> inputs = new LinkedList<>();
            for (int i = 0; i < inventory.size(); i++) {
                ItemStack stack = inventory.getStack(i);
                inputs.add(new CountRecipeSlot(stack.getItem(), stack.getCount()));
            }

            for (var recipe : entries) {
                List<CountRecipeSlot> slots = recipe.getSlots();
                boolean isMatch = true;
                CountRecipeSlot slot0 = slots.get(0);
                CountRecipeSlot slot1 = slots.get(1);
                CountRecipeSlot slot2 = slots.get(2);
                CountRecipeSlot slot3 = slots.get(3);
                CountRecipeSlot slot4 = slots.get(4);
                CountRecipeSlot slot5 = slots.get(5);
                CountRecipeSlot slot6 = slots.get(6);
                CountRecipeSlot slot7 = slots.get(7);
                CountRecipeSlot slot8 = recipe.getCore();

                isMatch = isMatch(isMatch, inputs.get(0), slot0);
                isMatch = isMatch(isMatch, inputs.get(1), slot1);
                isMatch = isMatch(isMatch, inputs.get(2), slot2);
                isMatch = isMatch(isMatch, inputs.get(3), slot3);
                isMatch = isMatch(isMatch, inputs.get(4), slot4);
                isMatch = isMatch(isMatch, inputs.get(5), slot5);
                isMatch = isMatch(isMatch, inputs.get(6), slot6);
                isMatch = isMatch(isMatch, inputs.get(7), slot7);
                isMatch = isMatch(isMatch, inputs.get(8), slot8);

                if (isMatch) {
                    entry = recipe;
                    break;
                }

            }
            return entry;
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
            return new HashSet<>(entries);
        }

        private boolean isMatch(boolean prevMatch, CountRecipeSlot inputSlot, CountRecipeSlot recipeSlot) {
            if (!prevMatch) return false;
            if (recipeSlot.getItem() != Items.AIR) {
                if (inputSlot.getItem() != recipeSlot.getItem()) return false;
                if (inputSlot.getCount() != recipeSlot.getCount()) return false;
            } else {
                if (inputSlot.getItem() != Items.AIR) return false;
            }
            return true;
        }

        @Override
        public void clear() {
            this.keys.clear();
            entries.clear();
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
