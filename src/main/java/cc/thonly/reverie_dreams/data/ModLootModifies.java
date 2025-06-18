package cc.thonly.reverie_dreams.data;

import cc.thonly.mystias_izakaya.item.MIItems;
import cc.thonly.reverie_dreams.entity.HairballEntity;
import cc.thonly.reverie_dreams.entity.Yousei;
import cc.thonly.reverie_dreams.item.ModItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Optional;

public class ModLootModifies {
    public static final RegistryKey<LootTable> OPEN_MINESHAFT_CHEST = LootTables.ABANDONED_MINESHAFT_CHEST;
    public static final RegistryKey<LootTable> FISHING = LootTables.FISHING_FISH_GAMEPLAY;

    public static void register() {
        Optional<RegistryKey<LootTable>> bambooSaplingLootTableKeyOptional = Blocks.BAMBOO_SAPLING.getLootTableKey();

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (source.isBuiltin() && key.equals(OPEN_MINESHAFT_CHEST)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.2f));
                for (var item : ModItems.getRegisteredDiscItems()) {
                    poolBuilder.with(ItemEntry.builder(item).weight(8));
                }
                tableBuilder.pool(poolBuilder);
            }
        });
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (source.isBuiltin() && key.equals(FISHING)) {
                tableBuilder.modifyPools(tb -> {
                    tb.with(ItemEntry.builder(MIItems.SHRIMP).weight(10));
                    tb.with(ItemEntry.builder(MIItems.CRAB).weight(10));
                    tb.with(ItemEntry.builder(MIItems.SALMON).weight(10));
                    tb.with(ItemEntry.builder(MIItems.TROUT).weight(10));
                    tb.with(ItemEntry.builder(MIItems.TUNA).weight(10));
                    tb.with(ItemEntry.builder(MIItems.SUPREME_TUNA).weight(1));
                });
            }
        });
        LootTableEvents.REPLACE.register((key, table, source, registries) -> {
            if (source.isBuiltin() && bambooSaplingLootTableKeyOptional.isPresent() && key.equals(bambooSaplingLootTableKeyOptional.get())) {
                LootTable.Builder builder = new LootTable.Builder();
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f))
                        .with(ItemEntry.builder(MIItems.BAMBOO_SHOOTS).weight(10))
                        ;
                builder.pool(poolBuilder);
                return builder.build();
            }
            return table;
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

}
