package cc.thonly.reverie_dreams.data;

import cc.thonly.mystias_izakaya.item.MIItems;
import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.component.ModDataComponentTypes;
import cc.thonly.reverie_dreams.danmaku.SpellCardTemplates;
import cc.thonly.reverie_dreams.entity.HairballEntity;
import cc.thonly.reverie_dreams.entity.Yousei;
import cc.thonly.reverie_dreams.item.ModItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetComponentsLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class ModLootModifies {
    public static final RegistryKey<LootTable> VILLAGE_WEAPONSMITH_CHEST = LootTables.VILLAGE_WEAPONSMITH_CHEST;
    public static final RegistryKey<LootTable> END_CITY_TREASURE_CHEST = LootTables.END_CITY_TREASURE_CHEST;
    public static final RegistryKey<LootTable> OPEN_MINESHAFT_CHEST = LootTables.ABANDONED_MINESHAFT_CHEST;
    public static final RegistryKey<LootTable> NETHER_BRIDGE_CHEST = LootTables.NETHER_BRIDGE_CHEST;
    public static final RegistryKey<LootTable> VILLAGE_PLAINS_CHEST = LootTables.VILLAGE_PLAINS_CHEST;
    public static final RegistryKey<LootTable> VILLAGE_SAVANNA_HOUSE_CHEST = LootTables.VILLAGE_SAVANNA_HOUSE_CHEST;
    public static final RegistryKey<LootTable> PILLAGER_OUTPOST_CHEST = LootTables.PILLAGER_OUTPOST_CHEST;
    public static final RegistryKey<LootTable> SIMPLE_DUNGEON_CHEST = LootTables.PILLAGER_OUTPOST_CHEST;

    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (
                            key.equals(OPEN_MINESHAFT_CHEST)
                         || key.equals(NETHER_BRIDGE_CHEST)
                         || key.equals(VILLAGE_PLAINS_CHEST)
                         || key.equals(VILLAGE_SAVANNA_HOUSE_CHEST)
                         || key.equals(PILLAGER_OUTPOST_CHEST)
                         || key.equals(SIMPLE_DUNGEON_CHEST)
            ) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.2f));
                for (var item : ModItems.getDiscItemView()) {
                    poolBuilder.with(ItemEntry.builder(item).weight(8));
                }
                poolBuilder.with(ItemEntry.builder(ModItems.UPGRADED_HEALTH_FRAGMENT).weight(10));
                poolBuilder.with(ItemEntry.builder(ModItems.BOMB_FRAGMENT).weight(10));
                poolBuilder.with(ItemEntry.builder(ModItems.UPGRADED_HEALTH).weight(10));
                poolBuilder.with(ItemEntry.builder(ModItems.BOMB).weight(10));
                tableBuilder.pool(poolBuilder);
            }
        });
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (key.equals(OPEN_MINESHAFT_CHEST) || key.equals(VILLAGE_WEAPONSMITH_CHEST) || key.equals(END_CITY_TREASURE_CHEST)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.2f));
                for (var entry : SpellCardTemplates.getRegistryItemStackView().entrySet()) {
                    ItemStack itemStack = entry.getValue();
                    String templateId = itemStack.get(ModDataComponentTypes.Danmaku.TEMPLATE);
                    if (templateId == null) continue;

                    poolBuilder.with(ItemEntry.builder(itemStack.getItem())
                            .apply(SetComponentsLootFunction.builder(ModDataComponentTypes.Danmaku.TEMPLATE, templateId))
                            .weight(6));
                }
                tableBuilder.pool(poolBuilder);
            }
        });

        ServerLivingEntityEvents.AFTER_DEATH.register(ModLootModifies::modifyDrops);
    }

    private static void modifyDrops(LivingEntity entity, DamageSource damageSource) {
        dropPointPower(entity, damageSource);
    }

    private static void dropPointPower(LivingEntity entity, DamageSource damageSource) {
        Identifier entityId = Registries.ENTITY_TYPE.getId(entity.getType());
        World world = entity.getWorld();
        if ((entity instanceof HairballEntity || entity instanceof HostileEntity || entity instanceof Yousei) && world instanceof ServerWorld serverWorld) {
            Random random = Random.create();
            int dropChance = 55;
            int maxDropCount = 4;

            if (random.nextInt(100) < dropChance) {
                entity.dropStack(serverWorld, new ItemStack(ModItems.POWER, random.nextInt(maxDropCount + 1) + 1));
                entity.dropStack(serverWorld, new ItemStack(ModItems.POINT, random.nextInt(maxDropCount + 1) + 1));
            }
        }
    }

    private static RegistryKey<LootTable> vanillaKey(String path) {
        return RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla(path));
    }

    private static RegistryKey<LootTable> key(String path) {
        return RegistryKey.of(RegistryKeys.LOOT_TABLE, Touhou.id(path));
    }

}
