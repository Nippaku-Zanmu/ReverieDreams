package cc.thonly.touhoumod.data;

import cc.thonly.touhoumod.Touhou;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> EMPTY = of("empty");
        public static final TagKey<Block> FUMO = of("fumo");

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
