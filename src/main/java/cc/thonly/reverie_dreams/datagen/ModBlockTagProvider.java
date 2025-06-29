package cc.thonly.reverie_dreams.datagen;

import cc.thonly.mystias_izakaya.block.MIBlocks;
import cc.thonly.reverie_dreams.block.FumoBlocks;
import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.data.ModTags;
import cc.thonly.reverie_dreams.item.base.BasicPolymerMiningToolItem;
import lombok.AccessLevel;
import lombok.Getter;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Getter(AccessLevel.PRIVATE)
public class ModBlockTagProvider extends FabricTagProvider<Block> {
    public static final Set<Block> FENCES = new HashSet<>();
    public static final Set<Block> FENCE_GATES = new HashSet<>();
    public static final Set<Block> STAIRS = new HashSet<>();
    public static final Set<Block> SLABS = new HashSet<>();
    public static final Set<Block> BUTTONS = new HashSet<>();
    public static final Set<Block> PRESSURE_PLATES = new HashSet<>();
    public static final Set<Block> TRAPDOORS = new HashSet<>();
    public static final Set<Block> DOORS = new HashSet<>();

    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BLOCK, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        FabricTagProvider<Block>.FabricTagBuilder fumo = getOrCreateTagBuilder(ModTags.BlockTypeTag.FUMO);
        FabricTagProvider<Block>.FabricTagBuilder empty = getOrCreateTagBuilder(ModTags.BlockTypeTag.EMPTY);
        FabricTagProvider<Block>.FabricTagBuilder fences = getOrCreateTagBuilder(BlockTags.FENCES);
        FabricTagProvider<Block>.FabricTagBuilder fenceGates = getOrCreateTagBuilder(BlockTags.FENCE_GATES);
        FabricTagProvider<Block>.FabricTagBuilder stairs = getOrCreateTagBuilder(BlockTags.STAIRS);
        FabricTagProvider<Block>.FabricTagBuilder slabs = getOrCreateTagBuilder(BlockTags.SLABS);
        FabricTagProvider<Block>.FabricTagBuilder buttons = getOrCreateTagBuilder(BlockTags.BUTTONS);
        FabricTagProvider<Block>.FabricTagBuilder pressurePlates = getOrCreateTagBuilder(BlockTags.PRESSURE_PLATES);
        FabricTagProvider<Block>.FabricTagBuilder trapdoors = getOrCreateTagBuilder(BlockTags.TRAPDOORS);
        FabricTagProvider<Block>.FabricTagBuilder doors = getOrCreateTagBuilder(BlockTags.DOORS);
        FabricTagProvider<Block>.FabricTagBuilder sliver = getOrCreateTagBuilder(ModTags.BlockTypeTag.SILVER);
        FabricTagProvider<Block>.FabricTagBuilder minTools = getOrCreateTagBuilder(ModTags.BlockTypeTag.MIN_TOOL);
        FabricTagProvider<Block>.FabricTagBuilder axeMineable = getOrCreateTagBuilder(BlockTags.AXE_MINEABLE);
        FabricTagProvider<Block>.FabricTagBuilder hoeMineable = getOrCreateTagBuilder(BlockTags.HOE_MINEABLE);
        FabricTagProvider<Block>.FabricTagBuilder pickaxeMineable = getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE);
        FabricTagProvider<Block>.FabricTagBuilder shovelMineable = getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE);

        for (Block block : FumoBlocks.getRegisteredFumo()) {
            fumo.add(block);
        }
        FENCES.forEach(fences::add);
        FENCE_GATES.forEach(fenceGates::add);
        STAIRS.forEach(stairs::add);
        SLABS.forEach(slabs::add);
        BUTTONS.forEach(buttons::add);
        PRESSURE_PLATES.forEach(pressurePlates::add);
        TRAPDOORS.forEach(trapdoors::add);
        DOORS.forEach(doors::add);

        pickaxeMineable.add(ModBlocks.SILVER_BLOCK, ModBlocks.SILVER_ORE, ModBlocks.DEEPSLATE_SILVER_ORE);
        pickaxeMineable.add(ModBlocks.ORB_ORE, ModBlocks.DEEPSLATE_ORB_ORE);
        sliver.add(ModBlocks.SILVER_BLOCK, ModBlocks.SILVER_ORE, ModBlocks.DEEPSLATE_SILVER_ORE);

        minTools.add(Blocks.BEDROCK);
        empty.add(Blocks.BEDROCK);
    }
}
