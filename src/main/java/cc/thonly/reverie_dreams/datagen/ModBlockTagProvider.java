package cc.thonly.reverie_dreams.datagen;

import cc.thonly.reverie_dreams.block.Fumo;
import cc.thonly.reverie_dreams.block.Fumos;
import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.data.ModTags;
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
        FabricTagProvider<Block>.FabricTagBuilder fumo = getOrCreateTagBuilder(ModTags.BlockTypeTag.FUMO).setReplace(false);
        FabricTagProvider<Block>.FabricTagBuilder empty = getOrCreateTagBuilder(ModTags.BlockTypeTag.EMPTY).setReplace(false);
        FabricTagProvider<Block>.FabricTagBuilder fences = getOrCreateTagBuilder(BlockTags.FENCES).setReplace(false);
        FabricTagProvider<Block>.FabricTagBuilder woodenFences = getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).setReplace(false);
        FabricTagProvider<Block>.FabricTagBuilder fenceGates = getOrCreateTagBuilder(BlockTags.FENCE_GATES).setReplace(false);
        FabricTagProvider<Block>.FabricTagBuilder stairs = getOrCreateTagBuilder(BlockTags.STAIRS).setReplace(false);
        FabricTagProvider<Block>.FabricTagBuilder slabs = getOrCreateTagBuilder(BlockTags.SLABS).setReplace(false);
        FabricTagProvider<Block>.FabricTagBuilder buttons = getOrCreateTagBuilder(BlockTags.BUTTONS).setReplace(false);
        FabricTagProvider<Block>.FabricTagBuilder pressurePlates = getOrCreateTagBuilder(BlockTags.PRESSURE_PLATES).setReplace(false);
        FabricTagProvider<Block>.FabricTagBuilder trapdoors = getOrCreateTagBuilder(BlockTags.TRAPDOORS).setReplace(false);
        FabricTagProvider<Block>.FabricTagBuilder doors = getOrCreateTagBuilder(BlockTags.DOORS).setReplace(false);
        FabricTagProvider<Block>.FabricTagBuilder sliver = getOrCreateTagBuilder(ModTags.BlockTypeTag.SILVER).setReplace(false);
        FabricTagProvider<Block>.FabricTagBuilder minTools = getOrCreateTagBuilder(ModTags.BlockTypeTag.MIN_TOOL).setReplace(false);
        FabricTagProvider<Block>.FabricTagBuilder axeMineable = getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).setReplace(false);
        FabricTagProvider<Block>.FabricTagBuilder hoeMineable = getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).setReplace(false);
        FabricTagProvider<Block>.FabricTagBuilder pickaxeMineable = getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).setReplace(false);
        FabricTagProvider<Block>.FabricTagBuilder shovelMineable = getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).setReplace(false);

        for (Fumo instance : Fumos.getView()) {
            fumo.add(instance.block());
        }

        FENCES.forEach(woodenFences::add);
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
