package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.block.Fumo;
import cc.thonly.reverie_dreams.block.Fumos;
import cc.thonly.reverie_dreams.danmaku.DanmakuTypes;
import cc.thonly.reverie_dreams.danmaku.SpellCardTemplates;
import cc.thonly.reverie_dreams.entity.ModEntities;
import cc.thonly.reverie_dreams.entity.npc.NPCRole;
import cc.thonly.reverie_dreams.registry.RegistryManager;
import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> TOUHOU_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Touhou.id("item_group"));
    public static final RegistryKey<ItemGroup> TOUHOU_BULLET_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Touhou.id("item_group_bullet"));
    public static final RegistryKey<ItemGroup> TOUHOU_TEMPLATE_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Touhou.id("item_group_template"));
    public static final RegistryKey<ItemGroup> TOUHOU_FUMO_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Touhou.id("item_group_fumo"));
    public static final RegistryKey<ItemGroup> TOUHOU_ROLE_SPAWN_EGG_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Touhou.id("item_group_role_spawn_egg"));
    public static final RegistryKey<ItemGroup> TOUHOU_SPAWN_EGG_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Touhou.id("item_group_spawn_egg"));
    public static final ItemGroup TOUHOU_ITEM_GROUP = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(ModItems.ICON))
            .displayName(Text.translatable("item_group.touhou"))
            .build();
    public static final ItemGroup TOUHOU_ITEM_GROUP_BULLET = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(ModItems.DANMAKU))
            .displayName(Text.translatable("item_group.touhou.bullet"))
            .build();
    public static final ItemGroup TOUHOU_TEMPLATE_ITEM_GROUP_BULLET = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(ModItems.SPELL_CARD_TEMPLATE))
            .displayName(Text.translatable("item_group.touhou.template"))
            .build();
    public static final ItemGroup TOUHOU_ITEM_GROUP_FUMO = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(ModItems.FUMO_ICON))
            .displayName(Text.translatable("item_group.touhou.fumo"))
            .build();
    public static final ItemGroup TOUHOU_ITEM_GROUP_SPAWN_EGG = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(ModItems.SPAWN_EGG))
            .displayName(Text.translatable("item_group.touhou.spawn_egg"))
            .build();
    public static final ItemGroup TOUHOU_ITEM_GROUP_NPC_SPAWN_EGG = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(ModItems.ROLE_ICON))
            .displayName(Text.translatable("item_group.touhou.role.spawn_egg"))
            .build();

    public static void registerItemGroups() {
        PolymerItemGroupUtils.registerPolymerItemGroup(TOUHOU_ITEM_GROUP_KEY, TOUHOU_ITEM_GROUP);
        PolymerItemGroupUtils.registerPolymerItemGroup(TOUHOU_BULLET_ITEM_GROUP_KEY, TOUHOU_ITEM_GROUP_BULLET);
        PolymerItemGroupUtils.registerPolymerItemGroup(TOUHOU_TEMPLATE_ITEM_GROUP_KEY, TOUHOU_TEMPLATE_ITEM_GROUP_BULLET);
        PolymerItemGroupUtils.registerPolymerItemGroup(TOUHOU_FUMO_ITEM_GROUP_KEY, TOUHOU_ITEM_GROUP_FUMO);
        PolymerItemGroupUtils.registerPolymerItemGroup(TOUHOU_SPAWN_EGG_ITEM_GROUP_KEY, TOUHOU_ITEM_GROUP_SPAWN_EGG);
        PolymerItemGroupUtils.registerPolymerItemGroup(TOUHOU_ROLE_SPAWN_EGG_ITEM_GROUP_KEY, TOUHOU_ITEM_GROUP_NPC_SPAWN_EGG);
        ItemGroupEvents.modifyEntriesEvent(TOUHOU_ITEM_GROUP_KEY).register(itemGroup -> {
            for (Item item : ModItems.getItemView()) {
                itemGroup.add(item);
            }
        });
        ItemGroupEvents.modifyEntriesEvent(TOUHOU_BULLET_ITEM_GROUP_KEY).register(itemGroup -> {
            List<ItemStack> color = DanmakuTypes.allColor();
            color.forEach(itemGroup::add);
        });
        ItemGroupEvents.modifyEntriesEvent(TOUHOU_TEMPLATE_ITEM_GROUP_KEY).register(itemGroup -> {
            Map<Identifier, ItemStack> registryView = SpellCardTemplates.getRegistryItemStackView();
            Set<Map.Entry<Identifier, ItemStack>> views = registryView.entrySet();
            for (Map.Entry<Identifier, ItemStack> view : views) {
                ItemStack stack = view.getValue();
                itemGroup.add(stack.copy());
            }
        });
        ItemGroupEvents.modifyEntriesEvent(TOUHOU_FUMO_ITEM_GROUP_KEY).register(itemGroup -> {
            for (Fumo instance : Fumos.getView()) {
                itemGroup.add(instance.item());
            }
        });
        ItemGroupEvents.modifyEntriesEvent(TOUHOU_SPAWN_EGG_ITEM_GROUP_KEY).register(itemGroup -> {
            for (Item item : ModEntities.getSpawnEggItemView()) {
                itemGroup.add(item);
            }
        });
        ItemGroupEvents.modifyEntriesEvent(TOUHOU_ROLE_SPAWN_EGG_ITEM_GROUP_KEY).register(itemGroup -> {
            Collection<NPCRole> roles = RegistryManager.NPC_ROLE.values();
            for (NPCRole role : roles) {
                Item egg = role.getEgg();
                itemGroup.add(egg);
            }
        });
    }
}
