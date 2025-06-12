package cc.thonly.reverie_dreams.datagen;

import cc.thonly.reverie_dreams.block.FumoBlocks;
import cc.thonly.reverie_dreams.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.DANMAKU_CRAFTING_TABLE);
        addDrop(ModBlocks.STRENGTH_TABLE);
        addDrop(ModBlocks.GENSOKYO_ALTAR);
        addDrop(ModBlocks.SPIRITUAL_LOG);
        addDrop(ModBlocks.SPIRITUAL_WOOD);
        addDrop(ModBlocks.STRIPPED_SPIRITUAL_LOG);
        addDrop(ModBlocks.STRIPPED_SPIRITUAL_WOOD);
        addDrop(ModBlocks.SPIRITUAL_PLANKS);
        addDrop(ModBlocks.MAGIC_ICE_BLOCK);

        addDrop(ModBlocks.POINT_BLOCK);
        addDrop(ModBlocks.POWER_BLOCK);

        for (Block fumo: FumoBlocks.getRegisteredFumo()) {
            addDrop(fumo);
        }
    }

    public void generateMI() {

    }
}
