package cc.thonly.mystias_izakaya.block.entity;

import cc.thonly.mystias_izakaya.block.MIBlocks;
import cc.thonly.mystias_izakaya.block.MiBlockEntities;
import cc.thonly.mystias_izakaya.recipe.type.KitchenRecipeType;
import cc.thonly.reverie_dreams.recipe.slot.ItemStackRecipeWrapper;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Setter
@Getter
@ToString
public class KitchenwareBlockEntity extends BlockEntity {
    public static final BiMap<Block, KitchenRecipeType.KitchenType> BLOCK_2_KITCHEN_TYPE =
            HashBiMap.create(Map.of(
                    MIBlocks.COOKING_POT, KitchenRecipeType.KitchenType.COOKING_POT,
                    MIBlocks.CUTTING_BOARD, KitchenRecipeType.KitchenType.CUTTING_BOARD,
                    MIBlocks.FRYING_PAN, KitchenRecipeType.KitchenType.FRYING_PAN,
                    MIBlocks.GRILL, KitchenRecipeType.KitchenType.GRILL,
                    MIBlocks.STEAMER, KitchenRecipeType.KitchenType.STREAMER
            ));
    public static final Supplier<ItemStackRecipeWrapper> DEFAULT_WRAPPER_FACTORY = () -> new ItemStackRecipeWrapper(ItemStack.EMPTY);
    public static final Gson GSON = new Gson();
    private SimpleInventory inventory = new SimpleInventory(6);
    private KitchenRecipeType.KitchenType recipeType;
    private Identifier recipeId;
    private ItemStackRecipeWrapper preOutput = DEFAULT_WRAPPER_FACTORY.get();
    private Double tickLeft = 0.0;

    public KitchenwareBlockEntity(BlockPos pos, BlockState state) {
        super(MiBlockEntities.KITCHENWARE_BLOCK_ENTITY, pos, state);
        Block block = state.getBlock();
        this.recipeType = BLOCK_2_KITCHEN_TYPE.get(block);
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, KitchenwareBlockEntity kitchenwareBlockEntity) {
        KitchenwareBlockEntity blockEntity = kitchenwareBlockEntity.get();
        if (blockEntity.recipeType == null) {
            return;
        }
        if (!world.isClient() && world instanceof ServerWorld serverWorld) {
            if (blockEntity.isWorking()) {
                blockEntity.tickLeft--;
                serverWorld.spawnParticles(
                        ParticleTypes.SNOWFLAKE,
                        blockPos.getX(),
                        blockPos.getY(),
                        blockPos.getZ(),
                        1,
                        0,
                        0.5,
                        0,
                        0.1
                );
                blockEntity.markDirty();
            } else if (!blockEntity.isWorking() && !blockEntity.preOutput.getItemStack().isEmpty()) {
                blockEntity.inventory.setStack(5, blockEntity.preOutput.getItemStack().copy());
                blockEntity.preOutput = DEFAULT_WRAPPER_FACTORY.get();
                List<ServerPlayerEntity> players = serverWorld.getPlayers();

                for (ServerPlayerEntity player : players) {
                    serverWorld.playSound(player, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BLOCK_NOTE_BLOCK_PLING, SoundCategory.PLAYERS, 1.0f, 1.0f);
                }
                blockEntity.markDirty();
            }
        }
    }

    public boolean isWorking() {
        return !(this.tickLeft <= 0);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, inventory.heldStacks, registries);
        nbt.putDouble("TickLeft", this.tickLeft);
        DataResult<JsonElement> dataResult = ItemStackRecipeWrapper.CODEC.encodeStart(JsonOps.INSTANCE, this.preOutput);
        Optional<JsonElement> result = dataResult.result();
        if (result.isPresent()) {
            JsonElement element = result.get();
            nbt.putString("PreOutput", GSON.toJson(element));
        }
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        SimpleInventory inventory = new SimpleInventory(6);
        Inventories.readNbt(nbt, inventory.heldStacks, registries);
        this.inventory = inventory;
        this.tickLeft = nbt.getDouble("TickLeft");
        if (nbt.contains("PreOutput")) {
            String preOutputJson = nbt.getString("PreOutput");
            JsonElement json = JsonParser.parseString(preOutputJson);
            Dynamic<JsonElement> input = new Dynamic<>(JsonOps.INSTANCE, json);
            DataResult<ItemStackRecipeWrapper> parse = ItemStackRecipeWrapper.CODEC.parse(input);
            Optional<ItemStackRecipeWrapper> result = parse.result();
            result.ifPresent(wrapper -> this.preOutput = wrapper);
        }
    }

    public KitchenwareBlockEntity get() {
        return this;
    }
}
