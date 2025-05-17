package cc.thonly.touhoumod.block.entity;

import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

@Getter
public class DanmakuCraftingTableBlockEntity extends BlockEntity {
    private SimpleInventory inventory = new SimpleInventory(5);

    public DanmakuCraftingTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DANMAKU_CRAFTING_TABLE_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, inventory.heldStacks, registries);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        SimpleInventory inventory = new SimpleInventory(6);
        Inventories.readNbt(nbt, inventory.heldStacks, registries);
        this.inventory = inventory;
    }
}