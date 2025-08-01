package cc.thonly.reverie_dreams.block.base;

import cc.thonly.reverie_dreams.block.crop.TransparentPlant;
import cc.thonly.reverie_dreams.compat.BorukvaFoodCompatImpl;
import cc.thonly.reverie_dreams.interfaces.IMatureBlock;
import cc.thonly.reverie_dreams.util.CropAgeModelProvider;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import cc.thonly.reverie_dreams.util.PolymerCropCreator;
import com.mojang.serialization.MapCodec;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.Map;
import java.util.Optional;

@Setter
@Getter
@ToString
public abstract class BasicCropBlock extends PlantBlock implements Fertilizable, IMatureBlock, IdentifierGetter, PolymerBlock, PolymerTexturedBlock, FactoryBlock {
    protected final Identifier identifier;
    protected Item seed;
    protected BlockState polymerBlockState;
    protected CropAgeModelProvider modelProvider;

    protected BasicCropBlock(Settings settings) {
        super(settings);
        this.identifier = null;
    }

    public BasicCropBlock(Identifier identifier, Settings settings) {
        super(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)).nonOpaque().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));
        this.identifier = identifier;
        this.setDefaultState(this.stateManager.getDefaultState().with(this.getAgeProperty(), 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(this.getAgeProperty());
    }

    public abstract Integer getMaxAge();

    public abstract IntProperty getAgeProperty();

    @Override
    protected abstract MapCodec<? extends PlantBlock> getCodec();

    protected ItemConvertible getSeedsItem() {
        return this.seed;
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
        return Blocks.WHEAT.getDefaultState();
    }

    @Override
    protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
        return new ItemStack(this.getSeedsItem());
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return this.polymerBlockState;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        if (!BorukvaFoodCompatImpl.hasBorukvaFood()) {
            return floor.isOf(Blocks.FARMLAND);
        } else {
            return floor.isOf(Blocks.FARMLAND) || floor.isOf(BorukvaFoodCompatImpl.BETTER_FARMLAND);
        }
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return !this.isMature(state);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public final boolean isMature(BlockState state) {
        return this.getAge(state) >= this.getMaxAge();
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return !this.isMature(state);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int age = this.getAge(state);
        if (age >= this.getMaxAge()) return;

        if (world.getBaseLightLevel(pos, 0) >= 9) {
            float moisture = getAvailableMoisture(this, world, pos);

            int chance = (int)(10.0f / moisture) + 1;

            if (random.nextInt(chance) == 0) {
                world.setBlockState(pos, this.withAge(age + 1), Block.NOTIFY_LISTENERS);
            }
        }
    }


    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        this.applyGrowth(world, pos, state);
    }

    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        int i = Math.min(this.getMaxAge(), this.getAge(state) + this.getGrowthAmount(world));
        world.setBlockState(pos, this.withAge(i), Block.NOTIFY_LISTENERS);
    }

    protected int getGrowthAmount(World world) {
        return MathHelper.nextInt(world.random, 1, 3);
    }

    public BlockState withAge(int age) {
        return (BlockState) this.getDefaultState().with(this.getAgeProperty(), age);
    }

    public int getAge(BlockState state) {
        return state.get(this.getAgeProperty());
    }

    protected static float getAvailableMoisture(Block block, BlockView world, BlockPos pos) {
        boolean bl2;
        float f = 1.0f;
        BlockPos blockPos = pos.down();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float g = 0.0f;
                BlockState blockState = world.getBlockState(blockPos.add(i, 0, j));
                if (blockState.isOf(Blocks.FARMLAND)) {
                    g = 1.0f;
                    if (blockState.get(FarmlandBlock.MOISTURE) > 0) {
                        g = 3.0f;
                    }
                }
                if (i != 0 || j != 0) {
                    g /= 4.0f;
                }
                f += g;
            }
        }
        BlockPos blockPos2 = pos.north();
        BlockPos blockPos3 = pos.south();
        BlockPos blockPos4 = pos.west();
        BlockPos blockPos5 = pos.east();
        boolean bl = world.getBlockState(blockPos4).isOf(block) || world.getBlockState(blockPos5).isOf(block);
        boolean bl3 = bl2 = world.getBlockState(blockPos2).isOf(block) || world.getBlockState(blockPos3).isOf(block);
        if (bl && bl2) {
            f /= 2.0f;
        } else {
            boolean bl32;
            boolean bl4 = bl32 = world.getBlockState(blockPos4.north()).isOf(block) || world.getBlockState(blockPos5.north()).isOf(block) || world.getBlockState(blockPos5.south()).isOf(block) || world.getBlockState(blockPos4.south()).isOf(block);
            if (bl32) {
                f /= 2.0f;
            }
        }
        return f;
    }



    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new Model(world, pos, initialBlockState, this.getMaxAge());
    }

    @Getter
    public static class Model extends BlockModel {
        private static final Map<BasicCropBlock, ItemStack[]> MODELS = new Object2ObjectOpenHashMap<>();
        private final ServerWorld world;
        private final BlockPos blockPos;
        private final BlockState blockState;
        private final BasicCropBlock block;
        private final Integer maxAge;
        private final ItemStack[] models;
        private boolean isNormal = false;
        public ItemDisplayElement main;

        public Model(ServerWorld world, BlockPos blockPos, BlockState blockState, Integer maxAge) {
            this.world = world;
            this.blockPos = blockPos;
            this.blockState = blockState;
            this.maxAge = maxAge;
            Block block = blockState.getBlock();
            Optional<PolymerCropCreator.Instance> instanceOptional = PolymerCropCreator.getInstance(block);
            boolean isPresent = instanceOptional.isPresent();
            if (isPresent && block instanceof BasicCropBlock cropBlock) {
                PolymerCropCreator.Instance instance = instanceOptional.get();
                this.block = (BasicCropBlock) blockState.getBlock();
                this.models = MODELS.computeIfAbsent(cropBlock, (x) -> new ItemStack[this.maxAge]);
                Identifier identifier = instance.getIdentifier();
                String namespace = identifier.getNamespace();
                String path = identifier.getPath();
                for (int i = 0; i < this.maxAge; i++) {
                    Identifier modelId = Identifier.of(namespace, "block/" + path + "_stage" + i);
                    this.models[i] = ItemDisplayElementUtil.getModel(modelId);
                }
                this.isNormal = true;
                this.init(blockState);
            } else {
                this.block = null;
                this.models = MODELS.computeIfAbsent(null, (x) -> new ItemStack[1]);
            }
        }

        public void init(BlockState state) {
            this.main = ItemDisplayElementUtil.createSimple();
            updateItem(state);
            this.main.setScale(new Vector3f(1));
            this.addElement(main);
        }

        protected void updateItem(BlockState state) {
            int age = state.get(this.block.getAgeProperty());
            CropAgeModelProvider modelProvider = this.block.getModelProvider();
            this.main.setItem(modelProvider.get(this.models, age));
        }

        @Override
        public void notifyUpdate(HolderAttachment.UpdateType updateType) {
            if (this.isNormal && updateType == BlockBoundAttachment.BLOCK_STATE_UPDATE) {
                updateItem(this.blockState());
                this.tick();
            }
            super.notifyUpdate(updateType);
        }

        public ItemStack[] getModels() {
            return MODELS.get(this.block);
        }
    }
}
