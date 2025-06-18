package cc.thonly.reverie_dreams.data;

import cc.thonly.reverie_dreams.Touhou;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> MIN_TOOL = of("min_tool");
        public static final TagKey<Block> EMPTY = of("empty");
        public static final TagKey<Block> FUMO = of("fumo");
        public static final TagKey<Block> SLIVER = of("sliver");

        private static TagKey<Block> of(String id) {
            return TagKey.of(RegistryKeys.BLOCK, Touhou.id(id));
        }

        public static void register() {

        }
    }

    public static class Items {
        public static final TagKey<Item> EMPTY = of("empty");
        public static final TagKey<Item> FUMO = of("fumo");
        public static final TagKey<Item> ARMOR = of("armor");
        public static final TagKey<Item> SLIVER_TOOL_MATERIALS = of("sliver_tool_materials");

        private static TagKey<Item> of(String id) {
            return TagKey.of(RegistryKeys.ITEM, Touhou.id(id));
        }

        public static void register() {

        }
    }

    public static void registerTags() {
        Blocks.register();
        Items.register();
    }

}
