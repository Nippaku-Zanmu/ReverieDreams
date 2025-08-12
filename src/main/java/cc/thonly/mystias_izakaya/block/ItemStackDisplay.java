package cc.thonly.mystias_izakaya.block;

import cc.thonly.mystias_izakaya.MystiasIzakaya;
import cc.thonly.mystias_izakaya.block.entity.ItemStackDisplayBlockEntity;
import cc.thonly.reverie_dreams.block.GensokyoAltarBlock;
import cc.thonly.reverie_dreams.block.entity.GensokyoAltarBlockEntity;
import cc.thonly.reverie_dreams.block.entity.ModBlockEntities;
import cc.thonly.reverie_dreams.interfaces.IItemStack;
import cc.thonly.reverie_dreams.recipe.ItemStackRecipeWrapper;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.UseRemainderComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class ItemStackDisplay extends BlockWithEntity implements FactoryBlock, IdentifierGetter {
    public static final Map<BlockState, Model> STATE_TO_MODEL = new HashMap<>();
    public static final MapCodec<ItemStackDisplay> CODEC = ItemStackDisplay.createCodec(ItemStackDisplay::new);
    private Identifier identifier;

    private ItemStackDisplay(Settings settings) {
        super(settings);
    }

    public ItemStackDisplay(String name, Settings settings) {
        this(MystiasIzakaya.id(name), settings);
    }

    public ItemStackDisplay(Identifier identifier, Settings settings) {
        super(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;
    }


    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack stack = player.getMainHandStack();
        if (stack == null) {
            stack = player.getOffHandStack();
        }
        if (!world.isClient && world instanceof ServerWorld serverWorld) {
            if (player.isSneaking()) {
                boolean isFood = ((IItemStack) (Object) stack).isFood();
                if (isFood) {
                    ComponentMap components = stack.getComponents();
                    UseRemainderComponent useRemainderComponent = stack.get(DataComponentTypes.USE_REMAINDER);
                    FoodComponent foodComponent = components.get(DataComponentTypes.FOOD);
                    ConsumableComponent consumableComponent = components.get(DataComponentTypes.CONSUMABLE);
                    boolean isEmitedEat = false;
                    if (foodComponent != null) {
                        int nutritionValue = foodComponent.nutrition();
                        float saturationValue = foodComponent.saturation();
                        HungerManager hungerManager = player.getHungerManager();
                        if ((hungerManager.getFoodLevel() < 20 || hungerManager.getSaturationLevel() < 20) || foodComponent.canAlwaysEat()) {
                            hungerManager.add(nutritionValue, saturationValue);
                            isEmitedEat = true;
                        }
                    }
                    if (consumableComponent != null) {
                        for (ConsumeEffect onConsumeEffect : consumableComponent.onConsumeEffects()) {
                            if (onConsumeEffect instanceof ApplyEffectsConsumeEffect applyEffectsConsumeEffect) {
                                List<StatusEffectInstance> effects = applyEffectsConsumeEffect.effects();
                                for (StatusEffectInstance effect : effects) {
                                    player.addStatusEffect(effect);
                                }
                            }
                        }
                    }
                    if (isEmitedEat) {
                        player.playSound(SoundEvents.ENTITY_GENERIC_EAT.value(), 1.0f, 1.0f);
                        stack.decrementUnlessCreative(1, player);
                        if (useRemainderComponent != null && !player.isInCreativeMode()) {
                            ItemStack itemStack = useRemainderComponent.convert(stack, stack.getCount(), player.isInCreativeMode(), player::giveOrDropStack);
                            if (serverWorld.getBlockEntity(pos) instanceof ItemStackDisplayBlockEntity isdBlockEntity) {
                                isdBlockEntity.setItem(ItemStackRecipeWrapper.of(itemStack));
                                serverWorld.updateListeners(pos, state, state, Block.NOTIFY_ALL);
                                isdBlockEntity.markDirty();
                            }
                        }
                        return ActionResult.SUCCESS_SERVER;
                    }
                }
            }
            if (serverWorld.getBlockEntity(pos) instanceof ItemStackDisplayBlockEntity isdBlockEntity) {
                ItemStackRecipeWrapper item = isdBlockEntity.getItem();
                if (!stack.isEmpty() && item.isEmpty()) {
                    ItemStackRecipeWrapper itemStackRecipeWrapper = ItemStackRecipeWrapper.of(stack.copy());
                    itemStackRecipeWrapper.getItemStack().setCount(1);
                    stack.decrementUnlessCreative(1, player);
                    isdBlockEntity.setItem(itemStackRecipeWrapper);
                    isdBlockEntity.setYaw(player.getYaw());
                } else {
                    ItemEntity itemEntity = new ItemEntity(serverWorld, pos.getX(), pos.getY(), pos.getZ(), item.getItemStack(), 0, 0.2, 0);
                    isdBlockEntity.setItem(ItemStackRecipeWrapper.empty());
                    serverWorld.spawnEntity(itemEntity);
                }
                serverWorld.updateListeners(pos, state, state, Block.NOTIFY_ALL);
                isdBlockEntity.markDirty();
            }
            return ActionResult.SUCCESS_SERVER;
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient() && world instanceof ServerWorld serverWorld && world.getBlockEntity(pos) instanceof ItemStackDisplayBlockEntity isdBlockEntity) {
            ItemStackRecipeWrapper item = isdBlockEntity.getItem();
            if (!item.isEmpty()) {
                ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), item.getItemStack(), 0, 0.2, 0);
                isdBlockEntity.setItem(ItemStackRecipeWrapper.empty());
                serverWorld.spawnEntity(itemEntity);
            }
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState blockState, PacketContext packetContext) {
        return Blocks.BARRIER.getDefaultState();
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ItemStackDisplayBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, MIBlockEntities.ITEM_DISPLAY_BLOCK_ENTITY, ItemStackDisplayBlockEntity::tick);
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        var model = new Model(world, this, initialBlockState, pos);
        STATE_TO_MODEL.put(initialBlockState, model);
        return model;
    }

    @Getter
    public static class Model extends BlockModel {
        private final ServerWorld serverWorld;
        private final Block block;
        private final BlockPos blockPos;
        private final ItemDisplayElement main;
        private ItemStackDisplayBlockEntity blockEntity;
        private final ItemDisplayElement item;

        public Model(ServerWorld serverWorld, Block block, BlockState initialBlockState, BlockPos blockPos) {
            this.serverWorld = serverWorld;
            this.block = block;
            this.blockPos = blockPos;

            this.main = ItemDisplayElementUtil.createSimple(initialBlockState.getBlock().asItem());
            this.main.setScale(new Vector3f(1.8f));
            this.main.setOffset(new Vec3d(0, -0.05, 0));
            this.item = ItemDisplayElementUtil.createSimple(Items.AIR);
            this.item.setScale(new Vector3f(0.5f));
            this.item.setOffset(new Vec3d(0, -0.22, 0));

            addElement(this.main);
            addElement(this.item);
        }

        public void updateItem(BlockState blockState) {
            BlockEntity blockEntity = this.serverWorld.getBlockEntity(this.blockPos);
            if (this.blockEntity == null && blockEntity instanceof ItemStackDisplayBlockEntity itemStackDisplayBlockEntity) {
                this.blockEntity = itemStackDisplayBlockEntity;
            }

            ItemStackRecipeWrapper item;
            if (this.blockEntity != null && !ItemStack.areEqual(this.blockEntity.getItem().getItemStack(), this.item.getItem())) {
                removeElement(this.item);
                item = this.blockEntity.getItem();
                this.item.setItem(item.getItemStack().copy());
                this.item.setOffset(new Vec3d(0, -0.22, 0));
                this.item.setRotation((float) 0, (float) this.blockEntity.getYaw() + 180);
                addElement(this.item);
            }
        }

        @Override
        public void notifyUpdate(HolderAttachment.UpdateType updateType) {
            if (updateType == BlockBoundAttachment.BLOCK_STATE_UPDATE) {
                updateItem(this.blockState());
            }
            this.tick();
            super.notifyUpdate(updateType);
        }
    }
}
