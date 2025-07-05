package cc.thonly.reverie_dreams.data;

import cc.thonly.mystias_izakaya.item.MIItems;
import cc.thonly.reverie_dreams.Touhou;
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
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.Optional;

public class ModLootModifies {
    public static final RegistryKey<LootTable> OPEN_MINESHAFT_CHEST = LootTables.ABANDONED_MINESHAFT_CHEST;

    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (source.isBuiltin() && key.equals(OPEN_MINESHAFT_CHEST)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.2f));
                for (var item : ModItems.getDiscItemView()) {
                    poolBuilder.with(ItemEntry.builder(item).weight(8));
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
