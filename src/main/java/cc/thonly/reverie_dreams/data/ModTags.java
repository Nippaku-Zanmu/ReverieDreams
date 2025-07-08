package cc.thonly.reverie_dreams.data;

import cc.thonly.reverie_dreams.Touhou;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModTags {
    public static class BlockTypeTag {
        public static final TagKey<Block> MIN_TOOL = of("min_tool");
        public static final TagKey<Block> EMPTY = of("empty");
        public static final TagKey<Block> FUMO = of("fumo");
        public static final TagKey<Block> SILVER = of("silver");

        private static TagKey<Block> of(String id) {
            return TagKey.of(RegistryKeys.BLOCK, Touhou.id(id));
        }

        public static void register() {

        }
    }

    public static class ItemTypeTag {
        public static final TagKey<Item> EMPTY = of("empty");
        public static final TagKey<Item> FUMO = of("fumo");
        public static final TagKey<Item> ARMOR = of("armor");
        public static final TagKey<Item> SILVER_TOOL_MATERIALS = of("silver_tool_materials");
        public static final TagKey<Item> MAGIC_ICE_TOOL_MATERIALS = of("magic_ice_tool_materials");

        private static TagKey<Item> of(String id) {
            return TagKey.of(RegistryKeys.ITEM, Touhou.id(id));
        }

        public static void register() {

        }
    }
    public static class EntityTag {
        public static final TagKey<EntityType<?>> NPC_ROLE = of("role");

        private static TagKey<EntityType<?>> of(String id) {
            return TagKey.of(RegistryKeys.ENTITY_TYPE, Touhou.id(id));
        }

        public static void register() {

        }
    }

    public static void registerTags() {
        BlockTypeTag.register();
        ItemTypeTag.register();
    }

}
