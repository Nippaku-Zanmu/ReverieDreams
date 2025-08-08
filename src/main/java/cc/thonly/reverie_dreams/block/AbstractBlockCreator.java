package cc.thonly.reverie_dreams.block;

import cc.thonly.reverie_dreams.datagen.ModBlockTagProvider;
import cc.thonly.reverie_dreams.datagen.ModItemTagProvider;
import cc.thonly.reverie_dreams.item.BasicBlockItem;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

@Getter
public abstract class AbstractBlockCreator {
    public static final Map<Class<? extends Class<AbstractBlockCreator>>, List<AbstractBlockCreator>> INSTANCES = new Object2ObjectOpenHashMap<>();
    private final String name;
    private final Identifier id;

    @SuppressWarnings("unchecked")
    public AbstractBlockCreator(String name, Identifier id) {
        this.name = name;
        this.id = id;
        List<AbstractBlockCreator> list = INSTANCES.computeIfAbsent((Class<? extends Class<AbstractBlockCreator>>) (Object) this.getClass(), x -> new ObjectArrayList<>());
        list.add(this);
    }

    protected Block register(IdentifierGetter blockImpl) {
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
        if (block instanceof WallBlock) {
            ModBlockTagProvider.WALLS.add(block);
            ModItemTagProvider.WALLS.add(block.asItem());
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
        return block;
    }

    protected Identifier prefix(String name) {
        return Identifier.of(this.id.getNamespace(), name + "_" + this.id.getPath());
    }

    protected Identifier suffix(String name) {
        return Identifier.of(this.id.getNamespace(), this.id.getPath() + "_" + name);
    }

    protected Identifier prefix(Identifier id, String name) {
        return Identifier.of(id.getNamespace(), name + "_" + id.getPath());
    }

    protected Identifier suffix(Identifier id, String name) {
        return Identifier.of(id.getNamespace(), id.getPath() + "_" + name);
    }

    protected abstract AbstractBlockCreator build();
}
