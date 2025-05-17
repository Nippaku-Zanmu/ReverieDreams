package cc.thonly.touhoumod.recipe;

import cc.thonly.touhoumod.component.ModDataComponentTypes;
import cc.thonly.touhoumod.item.BasicDanmakuItem;
import cc.thonly.touhoumod.item.ModItems;
import cc.thonly.touhoumod.item.entry.DanmakuItemEntries;
import cc.thonly.touhoumod.recipe.entry.DanmakuRecipe;
import cc.thonly.touhoumod.recipe.slot.CountRecipeSlot;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.*;

@Setter
@Getter
@ToString
public class DanmakuRecipes extends CustomRecipes {
    private static final RegistryInstance<DanmakuRecipe.Entry> INSTANCE = new RegistryInstance<>();

    public static void registerRecipes() {
        RegistryInstance<DanmakuRecipe.Entry> registry = getRecipeRegistryRef();

        for (DanmakuItemEntries entry : ModItems.DANMAKU_ITEMS) {
            List<BasicDanmakuItem> itemList = entry.getValues();
            for (BasicDanmakuItem itemEntry : itemList) {
                Integer color = itemEntry.getComponents().get(ModDataComponentTypes.Danmaku.COLOR);
                if (color != null) {
                    DanmakuItemEntries.ColorEnum colorEnum = DanmakuItemEntries.ColorEnum.fromIndex(color);
                    Item dye = colorEnum.getDye();
                    DanmakuRecipe.Entry buildedEntry = DanmakuRecipe.Builder.create(
                            new CountRecipeSlot(dye, 1),
                            new CountRecipeSlot(Items.FIREWORK_STAR, 1),
                            new CountRecipeSlot(ModItems.POINT, 35),
                            new CountRecipeSlot(ModItems.POWER, 35),
                            null,
                            new CountRecipeSlot(itemEntry, 1)
                    ).build();
                    registry.register(Registries.ITEM.getId(buildedEntry.getOutput().getItem()), buildedEntry);
                }
            }
        }
    }


    public static RegistryInstance<DanmakuRecipe.Entry> getRecipeRegistryRef() {
        return INSTANCE;
    }

    public static class RegistryInstance<T extends DanmakuRecipe.Entry> extends SimpleRegistryInstance<T> {
        List<Identifier> keys = new ArrayList<>();
        List<T> entries = new ArrayList<>();

        private RegistryInstance() {

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

        public List<DanmakuRecipe.Entry> tryGetRecipes(List<CountRecipeSlot> inputItems) {
            List<DanmakuRecipe.Entry> results = new LinkedList<>();
            CountRecipeSlot slot_dye = inputItems.get(0);
            CountRecipeSlot slot_core = inputItems.get(1);
            CountRecipeSlot slot_point = inputItems.get(2);
            CountRecipeSlot slot_power = inputItems.get(3);
            CountRecipeSlot slot_material = inputItems.get(4);

            for (DanmakuRecipe.Entry entry : this.entries) {
                boolean isMatch = true;
                CountRecipeSlot dye = entry.getDye();
                CountRecipeSlot core = entry.getCore();
                CountRecipeSlot power = entry.getPower();
                CountRecipeSlot point = entry.getPoint();
                CountRecipeSlot material = entry.getMaterial();

                isMatch = isMatch(isMatch, slot_dye, dye);
                isMatch = isMatch(isMatch, slot_core, core);
                isMatch = isMatch(isMatch, slot_point, point);
                isMatch = isMatch(isMatch, slot_power, power);
                isMatch = isMatch(isMatch, slot_material, material);

                if (isMatch) {
                    results.add(entry);
                }
            }
            return results;
        }

        private boolean isMatch(boolean prevMatch, CountRecipeSlot inputSlot, CountRecipeSlot recipeSlot) {
            if (!prevMatch) return false;
            if (recipeSlot.getItem() != Items.AIR) {
                if (inputSlot.getItem() != recipeSlot.getItem()) return false;
                if (inputSlot.getCount() < recipeSlot.getCount()) return false;
            } else {
                if (inputSlot.getItem() != Items.AIR) return false;
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
        public List<SimpleRegistryInstance<T>.Key2ValueEntry> toList() {
            List<SimpleRegistryInstance<T>.Key2ValueEntry> list = new LinkedList<>();
            int size = this.keys.size();
            for (int i = 0; i < this.entries.size(); i++) {
                Identifier key = Identifier.of("unknown_key");
                if (i + 1 < size) {
                    key = this.keys.get(i);
                }
                list.add(new Key2ValueEntry(key, this.entries.get(i)));
            }
            return list;
        }
    }
}
