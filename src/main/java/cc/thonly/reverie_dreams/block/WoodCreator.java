package cc.thonly.reverie_dreams.block;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.block.base.*;
import cc.thonly.reverie_dreams.datagen.ModBlockTagProvider;
import cc.thonly.reverie_dreams.datagen.ModItemTagProvider;
import cc.thonly.reverie_dreams.item.BasicBlockItem;
import cc.thonly.reverie_dreams.item.ModItems;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import eu.pb4.polymer.blocks.api.BlockModelType;
import lombok.Getter;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class WoodCreator {
    public static final List<WoodCreator> INSTANCES = new ArrayList<>();
    public static final List<Item> BLOCK_ITEMS = new ArrayList<>();
    @Getter
    private final String name;
    @Getter
    private final Identifier id;
    @Getter
    private final SaplingGenerator saplingGenerator;
    @Getter
    private BlockFamily blockFamily;
    private Block log;
    private Block wood;
    private Block strippedLog;
    private Block strippedWood;
    private Block leaves;
    private Block sapling;
    private Block planks;
    private Block stair;
    private Block slab;
    private Block door;
    private Block trapdoor;
    private Block fence;
    private Block fenceGate;
    private Block button;

    private WoodCreator(Identifier id, SaplingGenerator saplingGenerator) {
        this.id = id;
        this.name = id.getPath();
        this.saplingGenerator = saplingGenerator;
        INSTANCES.add(this);
    }

    private WoodCreator(String name, SaplingGenerator saplingGenerator) {
        this(Touhou.id(name), saplingGenerator);
    }

    private Identifier prefix(String name) {
        return Identifier.of(this.id.getNamespace(), name + "_" + this.id.getPath());
    }

    private Identifier suffix(String name) {
        return Identifier.of(this.id.getNamespace(), this.id.getPath() + "_" + name);
    }

    private Identifier prefix(Identifier id, String name) {
        return Identifier.of(id.getNamespace(), name + "_" + id.getPath());
    }

    private Identifier suffix(Identifier id, String name) {
        return Identifier.of(id.getNamespace(), id.getPath() + "_" + name);
    }

    public WoodCreator build() {
        this.log = new BasicPillarBlock(suffix("log"), AbstractBlock.Settings.copy(Blocks.OAK_LOG).nonOpaque());
        this.wood = new BasicPillarBlock(suffix("wood"), AbstractBlock.Settings.copy(Blocks.OAK_LOG).nonOpaque());
        this.strippedLog = new BasicPillarBlock(prefix(suffix("log"), "stripped"), AbstractBlock.Settings.copy(Blocks.OAK_LOG).nonOpaque());
        this.strippedWood = new BasicPillarBlock(prefix(suffix("wood"), "stripped"), AbstractBlock.Settings.copy(Blocks.OAK_WOOD).nonOpaque());
        this.leaves = new BasicLeavesBlock(suffix("leaves"), AbstractBlock.Settings.copy(Blocks.OAK_LEAVES));
        this.sapling = new BasicPolymerSaplingBlock(suffix("sapling"), this.saplingGenerator, AbstractBlock.Settings.copy(Blocks.OAK_SAPLING));
        this.planks = new BasicPolymerBlock(suffix("planks"), BlockModelType.FULL_BLOCK, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));
        this.stair = new BasicPolymerStairsBlock(suffix("stair"), this.planks.getDefaultState(), AbstractBlock.Settings.copy(Blocks.OAK_STAIRS));
        this.slab = new BasicPolymerSlabBlock(suffix("slab"), this.planks.getDefaultState(), AbstractBlock.Settings.copy(Blocks.OAK_SLAB));
        this.door = new BasicPolymerDoorBlock(suffix("door"), AbstractBlock.Settings.copy(Blocks.OAK_DOOR));
        this.trapdoor = new BasicPolymerTrapdoorBlock(suffix("trapdoor"), AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR));
        this.fence = new BasicPolymerFenceBlock(suffix("fence"), AbstractBlock.Settings.copy(Blocks.OAK_FENCE));
        this.fenceGate = new BasicPolymerFenceGateBlock(suffix("fence_gate"), WoodType.OAK, AbstractBlock.Settings.copy(Blocks.OAK_FENCE_GATE));
        this.button = new BasicPolymerButtonBlock(suffix("button"), BlockSetType.OAK, 30, AbstractBlock.Settings.copy(Blocks.OAK_BUTTON));
        this.stream().forEach((block) -> {
            IdentifierGetter blockImpl = (IdentifierGetter) block;
            this.register(blockImpl);
        });
        StrippableBlockRegistry.register(this.log, this.strippedLog);
        this.blockFamily = BlockFamilies.register(this.planks())
                .slab(this.slab())
                .stairs(this.stair())
                .fence(this.fence())
                .fenceGate(this.fenceGate())
                .button(this.button())
                .group("wooden").unlockCriterionName("has_planks")
                .build();
        return this;
    }

    public Block register(IdentifierGetter blockImpl) {
        Registry.register(Registries.BLOCK, blockImpl.getIdentifier(), (Block) blockImpl);
        Item blockItem = null;
        boolean isIgnored = blockImpl instanceof SignBlock || blockImpl instanceof WallSignBlock || blockImpl instanceof HangingSignBlock || blockImpl instanceof WallHangingSignBlock;
        if (!isIgnored) {
            blockItem = Registry.register(Registries.ITEM, blockImpl.getIdentifier(), (Item) new BasicBlockItem(blockImpl.getIdentifier(), (Block) blockImpl, new Item.Settings()));
        }
        Block block = (Block) blockImpl;
        if (block instanceof FenceBlock) {
            ModBlockTagProvider.FENCES.add(block);
            ModItemTagProvider.FENCES.add(block.asItem());
        }
        if (block instanceof FenceGateBlock) {
            ModBlockTagProvider.FENCE_GATES.add(block);
            ModItemTagProvider.FENCE_GATES.add(block.asItem());
        }
        if (block instanceof StairsBlock) {
            ModBlockTagProvider.STAIRS.add(block);
            ModItemTagProvider.STAIRS.add(block.asItem());
        }
        if (block instanceof SlabBlock) {
            ModBlockTagProvider.SLABS.add(block);
            ModItemTagProvider.SLABS.add(block.asItem());
        }
        if (block instanceof ButtonBlock) {
            ModBlockTagProvider.BUTTONS.add(block);
            ModItemTagProvider.BUTTONS.add(block.asItem());
        }
        if (block instanceof PressurePlateBlock) {
            ModBlockTagProvider.PRESSURE_PLATES.add(block);
            ModItemTagProvider.PRESSURE_PLATES.add(block.asItem());
        }
        if (block instanceof TrapdoorBlock) {
            ModBlockTagProvider.TRAPDOORS.add(block);
            ModItemTagProvider.TRAPDOORS.add(block.asItem());
        }
        if (block instanceof DoorBlock) {
            ModBlockTagProvider.DOORS.add(block);
            ModItemTagProvider.DOORS.add(block.asItem());
        }
        BLOCK_ITEMS.add(block.asItem());
        return block;
    }

    public Block log() {
        return this.log;
    }

    public Block wood() {
        return this.wood;
    }

    public Block strippedLog() {
        return this.strippedLog;
    }

    public Block strippedWood() {
        return this.strippedWood;
    }

    public Block leaves() {
        return this.leaves;
    }

    public Block sapling() {
        return this.sapling;
    }

    public Block planks() {
        return this.planks;
    }

    public Block stair() {
        return this.stair;
    }

    public Block slab() {
        return this.slab;
    }

    public Block door() {
        return this.door;
    }

    public Block trapdoor() {
        return this.trapdoor;
    }

    public Block fence() {
        return this.fence;
    }

    public Block fenceGate() {
        return this.fenceGate;
    }

    public Block button() {
        return this.button;
    }

    public Stream<Block> stream() {
        return Stream.of(
                log,
                wood,
                strippedLog,
                strippedWood,
                leaves,
                sapling,
                planks,
                stair,
                slab,
                door,
                trapdoor,
                fence,
                fenceGate,
                button
        ).filter(Objects::nonNull);
    }


    public static WoodCreator create(String name, SaplingGenerator saplingGenerator) {
        return new WoodCreator(name, saplingGenerator);
    }

    public static WoodCreator create(Identifier id, SaplingGenerator saplingGenerator) {
        return new WoodCreator(id, saplingGenerator);
    }
}
