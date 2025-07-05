package cc.thonly.reverie_dreams.datagen;

import cc.thonly.mystias_izakaya.entity.MIEntities;
import cc.thonly.mystias_izakaya.item.MIItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootTableProvider;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModEntityLootTableProvider extends FabricEntityLootTableProvider {
    public ModEntityLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        this.register(MIEntities.WILD_PIG_ENTITY_TYPE,
                new LootTable.Builder()
                        .pool(new LootPool.Builder()
                                .rolls(UniformLootNumberProvider.create(1.0f, 3.0f))
                                .with(ItemEntry.builder(MIItems.WILD_BOAR_MEAT))
                        )
        );
    }
}
