package cc.thonly.touhoumod.item;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.block.FumoBlocks;
import cc.thonly.touhoumod.entity.ModEntities;
import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> TOUHOU_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Touhou.id("item_group"));
    public static final RegistryKey<ItemGroup> TOUHOU_BULLET_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Touhou.id("item_group_bullet"));
    public static final RegistryKey<ItemGroup> TOUHOU_FUMO_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Touhou.id("item_group_fumo"));
    public static final RegistryKey<ItemGroup> TOUHOU_NPC_SPAWN_EGG_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Touhou.id("item_group_npc_spawn_egg"));
    public static final RegistryKey<ItemGroup> TOUHOU_SPAWN_EGG_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Touhou.id("item_group_spawn_egg"));
    public static final ItemGroup TOUHOU_ITEM_GROUP = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(ModItems.ICON))
            .displayName(Text.translatable("itemGroup.touhou"))
            .build();
    public static final ItemGroup TOUHOU_ITEM_GROUP_BULLET = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(Items.FIREWORK_STAR))
            .displayName(Text.translatable("itemGroup.touhou.bullet"))
            .build();
    public static final ItemGroup TOUHOU_ITEM_GROUP_FUMO = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(ModItems.FUMO_ICON))
            .displayName(Text.translatable("itemGroup.touhou.fumo"))
            .build();
    public static final ItemGroup TOUHOU_ITEM_GROUP_SPAWN_EGG = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(ModItems.SPAWN_EGG))
            .displayName(Text.translatable("itemGroup.touhou.spawn_egg"))
            .build();
    public static final ItemGroup TOUHOU_ITEM_GROUP_NPC_SPAWN_EGG = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(ModItems.SPAWN_EGG))
            .displayName(Text.translatable("itemGroup.touhou.npc.spawn_egg"))
            .build();

    public static void registerItemGroups() {
        PolymerItemGroupUtils.registerPolymerItemGroup(TOUHOU_ITEM_GROUP_KEY, TOUHOU_ITEM_GROUP);
        PolymerItemGroupUtils.registerPolymerItemGroup(TOUHOU_BULLET_ITEM_GROUP_KEY, TOUHOU_ITEM_GROUP_BULLET);
        PolymerItemGroupUtils.registerPolymerItemGroup(TOUHOU_FUMO_ITEM_GROUP_KEY, TOUHOU_ITEM_GROUP_FUMO);
        PolymerItemGroupUtils.registerPolymerItemGroup(TOUHOU_SPAWN_EGG_ITEM_GROUP_KEY, TOUHOU_ITEM_GROUP_SPAWN_EGG);
        PolymerItemGroupUtils.registerPolymerItemGroup(TOUHOU_NPC_SPAWN_EGG_ITEM_GROUP_KEY, TOUHOU_ITEM_GROUP_NPC_SPAWN_EGG);
        ItemGroupEvents.modifyEntriesEvent(TOUHOU_ITEM_GROUP_KEY).register(itemGroup -> {
            for (Item item : ModItems.getRegisteredItems()) {
                itemGroup.add(item);
            }
        });
        ItemGroupEvents.modifyEntriesEvent(TOUHOU_BULLET_ITEM_GROUP_KEY).register(itemGroup -> {
            for (Item item : ModItems.getRegisteredDanmakuItems()) {
                itemGroup.add(item);
            }
//            itemGroup.add(ModItems.DEBUG_DANMAKU_ITEM);
        });
        ItemGroupEvents.modifyEntriesEvent(TOUHOU_FUMO_ITEM_GROUP_KEY).register(itemGroup -> {
            for (Item item : FumoBlocks.getRegisteredFumoItems()) {
                itemGroup.add(item);
            }
        });
        ItemGroupEvents.modifyEntriesEvent(TOUHOU_SPAWN_EGG_ITEM_GROUP_KEY).register(itemGroup -> {
            for (Item item : ModEntities.getRegisteredSpawnEggItems()) {
                itemGroup.add(item);
            }
        });
        ItemGroupEvents.modifyEntriesEvent(TOUHOU_NPC_SPAWN_EGG_ITEM_GROUP_KEY).register(itemGroup -> {
            for (Item item : ModEntities.getRegisteredNpcSpawnEggItems()) {
                itemGroup.add(item);
            }
        });
    }
}
