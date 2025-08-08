package cc.thonly.reverie_dreams.block;

import cc.thonly.reverie_dreams.Touhou;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class DecorativeBlockCreator extends AbstractBlockCreator{
    public static final List<DecorativeBlockCreator> INSTANCES = new ArrayList<>();
    public static final List<Item> BLOCK_ITEMS = new ArrayList<>();
    private BlockFamily blockFamily;
    private Block block;
    private Block stair;
    private Block slab;
    private Block wall;

    private DecorativeBlockCreator(Identifier id) {
        super(id.getPath(), id);
        INSTANCES.add(this);
    }

    private DecorativeBlockCreator(String name) {
        this(Touhou.id(name));
    }

    @Override
    protected DecorativeBlockCreator build() {
        return this;
    }
}
