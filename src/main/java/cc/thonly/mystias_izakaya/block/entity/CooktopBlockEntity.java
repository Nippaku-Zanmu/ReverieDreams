package cc.thonly.mystias_izakaya.block.entity;

import cc.thonly.mystias_izakaya.block.CooktopBlock;
import cc.thonly.mystias_izakaya.block.MiBlockEntities;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.FuelRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Setter
@Getter
@ToString
public class CooktopBlockEntity extends BlockEntity {
    private SimpleInventory inventory = new SimpleInventory(1);
    public int ticks = 0;

    public CooktopBlockEntity(BlockPos pos, BlockState state) {
        super(MiBlockEntities.COOKTOP_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, CooktopBlockEntity blockEntity) {
        if (blockEntity == null || world.isClient()) {
            return;
        }

        world.setBlockState(blockPos, state.with(CooktopBlock.LIT, blockEntity.isWorking()), Block.NOTIFY_ALL_AND_REDRAW);

        blockEntity.ticks--;
        if (blockEntity.ticks < 0) {
            blockEntity.ticks = 0;
        }
    }

    public boolean isWorking() {
        return this.ticks > 0;
    }

    public boolean use() {
        if (this.world == null) {
            return false;
        }
        MinecraftServer server = this.world.getServer();
        if (server == null) {
            return false;
        }
        ItemStack itemStack = this.inventory.getStack(0);
        FuelRegistry fuelRegistry = server.getFuelRegistry();
        if (fuelRegistry.isFuel(itemStack)) {
            int fuelTicks = fuelRegistry.getFuelTicks(itemStack);
            System.out.println(fuelTicks);
            this.ticks += fuelTicks;
            itemStack.decrement(1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        nbt.putInt("Energy", this.ticks);
        Inventories.writeNbt(nbt, this.inventory.heldStacks, registries);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        this.ticks = nbt.getInt("Energy").orElse(0);
        Inventories.readNbt(nbt, this.inventory.heldStacks, registries);
    }

}
