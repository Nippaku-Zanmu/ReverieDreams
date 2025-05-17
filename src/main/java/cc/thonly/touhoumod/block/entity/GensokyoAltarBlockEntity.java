package cc.thonly.touhoumod.block.entity;

import cc.thonly.touhoumod.block.GensokyoAltarBlock;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Getter
public class GensokyoAltarBlockEntity extends BlockEntity {
    private SimpleInventory inventory = new SimpleInventory(9);
    public int tick = 0;

    public GensokyoAltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GENSOKYO_ALTAR_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, GensokyoAltarBlockEntity blockEntity) {
        if (blockEntity.tick > 5) {
            GensokyoAltarBlock.AltarModel altarModel = GensokyoAltarBlock.STATE_TO_MODEL.get(state);
            if (altarModel != null) {
                altarModel.update();
            }
            blockEntity.tick = 0;
        }
        GensokyoAltarBlock.AltarModel altarModel = GensokyoAltarBlock.STATE_TO_MODEL.get(state);
        altarModel.angle += 2f;
        if(altarModel.angle>=360) {
            altarModel.angle = 0;
        }
        blockEntity.tick++;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, inventory.heldStacks, registries);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        SimpleInventory inventory = new SimpleInventory(9);
        Inventories.readNbt(nbt, inventory.heldStacks, registries);
        this.inventory = inventory;
    }

}
