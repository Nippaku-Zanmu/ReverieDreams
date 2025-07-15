package cc.thonly.reverie_dreams.datagen;

import cc.thonly.mystias_izakaya.block.MIBlocks;
import cc.thonly.reverie_dreams.fumo.Fumo;
import cc.thonly.reverie_dreams.fumo.Fumos;
import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.data.ModTags;
import lombok.AccessLevel;
import lombok.Getter;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.tag.ProvidedTagBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Getter(AccessLevel.PRIVATE)
public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public static final Set<Block> FENCES = new HashSet<>();
    public static final Set<Block> FENCE_GATES = new HashSet<>();
    public static final Set<Block> LEAVES = new HashSet<>();
    public static final Set<Block> SAPLINGS = new HashSet<>();
    public static final Set<Block> STAIRS = new HashSet<>();
    public static final Set<Block> SLABS = new HashSet<>();
    public static final Set<Block> BUTTONS = new HashSet<>();
    public static final Set<Block> PRESSURE_PLATES = new HashSet<>();
    public static final Set<Block> TRAPDOORS = new HashSet<>();
    public static final Set<Block> DOORS = new HashSet<>();


    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        ProvidedTagBuilder<Block, Block> fumo = valueLookupBuilder(ModTags.BlockTypeTag.FUMO);
        ProvidedTagBuilder<Block, Block> empty = valueLookupBuilder(ModTags.BlockTypeTag.EMPTY);
        ProvidedTagBuilder<Block, Block> fences = valueLookupBuilder(BlockTags.FENCES);
        ProvidedTagBuilder<Block, Block> woodenFences = valueLookupBuilder(BlockTags.WOODEN_FENCES);
        ProvidedTagBuilder<Block, Block> fenceGates = valueLookupBuilder(BlockTags.FENCE_GATES);
        ProvidedTagBuilder<Block, Block> stairs = valueLookupBuilder(BlockTags.STAIRS);
        ProvidedTagBuilder<Block, Block> slabs = valueLookupBuilder(BlockTags.SLABS);
        ProvidedTagBuilder<Block, Block> saplings = valueLookupBuilder(BlockTags.SAPLINGS);
        ProvidedTagBuilder<Block, Block> leaves = valueLookupBuilder(BlockTags.LEAVES);
        ProvidedTagBuilder<Block, Block> buttons = valueLookupBuilder(BlockTags.BUTTONS);
        ProvidedTagBuilder<Block, Block> pressurePlates = valueLookupBuilder(BlockTags.PRESSURE_PLATES);
        ProvidedTagBuilder<Block, Block> trapdoors = valueLookupBuilder(BlockTags.TRAPDOORS);
        ProvidedTagBuilder<Block, Block> doors = valueLookupBuilder(BlockTags.DOORS);
        ProvidedTagBuilder<Block, Block> sliver = valueLookupBuilder(ModTags.BlockTypeTag.SILVER);
        ProvidedTagBuilder<Block, Block> minTools = valueLookupBuilder(ModTags.BlockTypeTag.MIN_TOOL);
        ProvidedTagBuilder<Block, Block> axeMineable = valueLookupBuilder(BlockTags.AXE_MINEABLE);
        ProvidedTagBuilder<Block, Block> hoeMineable = valueLookupBuilder(BlockTags.HOE_MINEABLE);
        ProvidedTagBuilder<Block, Block> pickaxeMineable = valueLookupBuilder(BlockTags.PICKAXE_MINEABLE);
        ProvidedTagBuilder<Block, Block> shovelMineable = valueLookupBuilder(BlockTags.SHOVEL_MINEABLE);
        
        for (Fumo instance : Fumos.getView()) {
            fumo.add(instance.block());
        }

        LEAVES.forEach(leaves::add);
        SAPLINGS.forEach(saplings::add);
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
        pickaxeMineable.add(ModBlocks.GENSOKYO_ALTAR);
        ModBlocks.SPIRITUAL.stream().forEach(axeMineable::add);
        MIBlocks.LEMON.stream().forEach(axeMineable::add);
        MIBlocks.GINKGO.stream().forEach(axeMineable::add);
        MIBlocks.PEACH.stream().forEach(axeMineable::add);
        ModBlocks.SPIRITUAL.stream().forEach(axeMineable::add);
        axeMineable.add(ModBlocks.DANMAKU_CRAFTING_TABLE);
        axeMineable.add(ModBlocks.MUSIC_BLOCK);
        sliver.add(ModBlocks.SILVER_BLOCK, ModBlocks.SILVER_ORE, ModBlocks.DEEPSLATE_SILVER_ORE);

        minTools.add(Blocks.BEDROCK);
        empty.add(Blocks.BEDROCK);
    }
}
