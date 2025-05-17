package cc.thonly.touhoumod.datagen;

import cc.thonly.touhoumod.block.FumoBlocks;
import cc.thonly.touhoumod.data.ModTags;
import lombok.AccessLevel;
import lombok.Getter;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

@Getter(AccessLevel.PRIVATE)
public class ModBlockTagProvider extends FabricTagProvider<Block> {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BLOCK, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        FabricTagProvider<Block>.FabricTagBuilder fumo = getOrCreateTagBuilder(ModTags.Blocks.FUMO);
        for (Block block : FumoBlocks.getRegisteredFumo()) {
            fumo.add(block);
        }
        FabricTagProvider<Block>.FabricTagBuilder empty = getOrCreateTagBuilder(ModTags.Blocks.EMPTY);
        empty.add(Blocks.BEDROCK);
    }
}
