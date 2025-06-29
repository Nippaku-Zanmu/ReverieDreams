package cc.thonly.reverie_dreams.entity.npc;

import cc.thonly.mystias_izakaya.component.FoodProperty;
import cc.thonly.mystias_izakaya.item.base.FoodItem;
import cc.thonly.reverie_dreams.entity.ai.goal.NpcBowAttackGoal;
import cc.thonly.reverie_dreams.entity.base.NPCEntity;
import cc.thonly.reverie_dreams.gui.NPCGui;
import cc.thonly.reverie_dreams.interfaces.ItemStackImpl;
import cc.thonly.reverie_dreams.inventory.NPCInventory;
import cc.thonly.reverie_dreams.item.ModItems;
import cc.thonly.reverie_dreams.sound.SoundEventInit;
import cc.thonly.reverie_dreams.util.ItemUtils;
import com.google.common.collect.ImmutableList;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.*;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potion;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

@Getter
@Setter
public abstract class NPCEntityImpl extends NPCEntity implements RangedAttackMob, NPCSettings {
    protected static final Item TAME_FOOD_ITEM = Items.CAKE;
    protected final Property skin;
    protected NPCState npcState = NPCState.NORMAL;
    protected boolean isSit = false;
    protected String npcOwner = "";
    protected String seatUUID = "";
    protected boolean isPause = false;
    protected NPCInventory inventory = new NPCInventory(NPCGui.size());
    protected ArmorStandEntity seat;
    protected int healthTick = 0;
    protected int maxHealthTick = 20 * 8;
    protected int updateAttackTick = 0;
    protected int maxUpdateAttackTick = 20 * 2 + 1;
    protected float nutrition = 20;
    protected float saturation = 20;
    protected int exhaustionLevel = 0;
    protected float hungerTick;
    protected boolean isSleepingCustom = false;
    protected BlockPos workingPos = new BlockPos(0, 0, 0);
    protected BlockPos sleepingPos;
    protected int workTick = 0;
    public static final HashSet<Item> ARROW_ITEMS = new HashSet<>();

    static {
        ARROW_ITEMS.add(Items.ARROW);
        ARROW_ITEMS.add(Items.TIPPED_ARROW);
        ARROW_ITEMS.add(Items.SPECTRAL_ARROW);
    }

    private final NpcBowAttackGoal<NPCEntityImpl> bowAttackGoal = new NpcBowAttackGoal<>(this, 1.0, 20, 15.0f);
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.5, false) {
        @Override
        public void stop() {
            super.stop();
            NPCEntityImpl.this.setAttacking(false);
        }

        @Override
        public void start() {
            super.start();
            NPCEntityImpl.this.setAttacking(true);
        }
    };
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.DOORS_TO_CLOSE, MemoryModuleType.NEAREST_BED);
    private static final ImmutableList<SensorType<? extends Sensor<? super NPCEntityImpl>>> SENSORS = ImmutableList.of();

    public NPCEntityImpl(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.skin = NPCEntitySkins.REIMU;
        this.init();
        this.updateAttackType();
    }

    public NPCEntityImpl(EntityType<? extends TameableEntity> entityType, World world, Property skin) {
        super(entityType, world);
        this.skin = skin;
        this.init();
        this.updateAttackType();
    }

    protected Brain.Profile<NPCEntityImpl> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
    }

    public void init() {
        AttributeContainer attributeContainer = this.getAttributes();
        if (attributeContainer != null) {
            EntityAttributeInstance scale = attributeContainer.getCustomInstance(EntityAttributes.SCALE);
            if (scale != null) {
                scale.setBaseValue(0.9);
            }
        }
        this.setNoGravity(false);
        this.setCanPickUpLoot(true);
        this.setTamed(false, true);
        ((MobNavigation) this.getNavigation()).setCanPathThroughDoors(true);
        this.setCanPickUpLoot(true);

        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 16.0f);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0f);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        DynamicRegistryManager registryManager = this.getRegistryManager();

        this.isSit = nbt.getBoolean("IsSit").orElse(false);


        int state = nbt.getInt("NPCState").orElse(0);
        this.npcState = NPCState.fromInt(state);
        this.npcOwner = nbt.getString("NpcOwner").orElse("");


        NbtCompound nbtInventory;
        nbtInventory = nbt.getCompound("Inventory").orElse(new NbtCompound());


        NbtCompound headInventory;
        headInventory = nbt.getCompound("HeadInventory").orElse(new NbtCompound());

        NbtCompound chestInventory;
        chestInventory = nbt.getCompound("ChestInventory").orElse(new NbtCompound());

        NbtCompound legsInventory;
        legsInventory = nbt.getCompound("LegsInventory").orElse(new NbtCompound());

        NbtCompound feetInventory;
        feetInventory = nbt.getCompound("FeetInventory").orElse(new NbtCompound());


        NPCInventory inventory = new NPCInventory(NPCGui.size());
        Inventories.readNbt(nbtInventory, inventory.heldStacks, registryManager);
        Inventories.readNbt(headInventory, inventory.getArmorInventory().getHead().heldStacks, registryManager);
        Inventories.readNbt(chestInventory, inventory.getArmorInventory().getChest().heldStacks, registryManager);
        Inventories.readNbt(legsInventory, inventory.getArmorInventory().getLegs().heldStacks, registryManager);
        Inventories.readNbt(feetInventory, inventory.getArmorInventory().getFeet().heldStacks, registryManager);
        this.inventory = inventory;

        if (nbt.contains("SeatUUID")) {
            String uuidStr = nbt.getString("SeatUUID").orElse("null");
            nbt.putString("SeatUUID", uuidStr);
        } else {
            nbt.putString("SeatUUID", "");
        }

        if (nbt.contains("FoodNutrition")) {
            this.nutrition = nbt.getFloat("FoodNutrition").orElse(20.0f);
        } else {
            nbt.putFloat("FoodNutrition", 20.0f);
        }

        if (nbt.contains("FoodSaturation")) {
            this.saturation = nbt.getInt("FoodSaturation").orElse(20);
        } else {
            nbt.putFloat("FoodSaturation", 0);
        }

        this.exhaustionLevel = nbt.getInt("FoodExhaustionLevel").orElse(0);

        this.workingPos = BlockPos.fromLong(nbt.getLong("WorkingPos").orElse(new BlockPos(0, 0, 0).asLong()));


        this.updateAttackType();
//        System.out.println(nbt.toString());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        DynamicRegistryManager registryManager = this.getRegistryManager();
        nbt.putBoolean("IsSit", this.isSit);
        nbt.putString("NpcOwner", this.npcOwner);
        nbt.putInt("NPCState", this.npcState.getId());
        nbt.putFloat("FoodNutrition", this.nutrition);
        nbt.putFloat("FoodSaturation", this.saturation);
        nbt.putFloat("FoodExhaustionLevel", this.exhaustionLevel);

        NbtCompound nbtInventory = new NbtCompound();
        NbtCompound headInventory = new NbtCompound();
        NbtCompound chestInventory = new NbtCompound();
        NbtCompound legsInventory = new NbtCompound();
        NbtCompound feetInventory = new NbtCompound();
        Inventories.writeNbt(nbtInventory, this.inventory.heldStacks, registryManager);
        Inventories.writeNbt(headInventory, this.inventory.getArmorInventory().getHead().heldStacks, registryManager);
        Inventories.writeNbt(chestInventory, this.inventory.getArmorInventory().getChest().heldStacks, registryManager);
        Inventories.writeNbt(legsInventory, this.inventory.getArmorInventory().getLegs().heldStacks, registryManager);
        Inventories.writeNbt(feetInventory, this.inventory.getArmorInventory().getFeet().heldStacks, registryManager);
        nbt.put("Inventory", nbtInventory);
        nbt.put("HeadInventory", headInventory);
        nbt.put("ChestInventory", chestInventory);
        nbt.put("LegsInventory", legsInventory);
        nbt.put("FeetInventory", feetInventory);

        nbt.putLong("WorkingPos", this.workingPos.asLong());

        if (!this.seatUUID.isEmpty()) {
            nbt.putString("SeatUUID", this.seatUUID);
        }
//        System.out.println(nbt.toString());
    }

    @Override
    protected void initGoals() {
        super.initGoals();
    }

    @Override
    public ItemStack getProjectileType(ItemStack stack) {
        if (stack.getItem() instanceof RangedWeaponItem) {
            Predicate<ItemStack> predicate = ((RangedWeaponItem) stack.getItem()).getHeldProjectiles();
            ItemStack itemStack = RangedWeaponItem.getHeldProjectile(this, predicate);
            return itemStack.isEmpty() ? new ItemStack(Items.ARROW) : itemStack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        ItemStack itemStack = this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW));
        ItemStack itemStack2 = this.getProjectileType(itemStack);
        PersistentProjectileEntity persistentProjectileEntity = this.createArrowProjectile(itemStack2, pullProgress, itemStack);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333) - persistentProjectileEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        World world = this.getWorld();
        if (world instanceof ServerWorld serverWorld) {
            ProjectileEntity.spawnWithVelocity(persistentProjectileEntity, serverWorld, itemStack2, d, e + g * (double) 0.2f, f, 1.6f, 14 - serverWorld.getDifficulty().getId() * 4);
        }
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
    }

    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier, @Nullable ItemStack shotFrom) {
        return ProjectileUtil.createArrowProjectile(this, arrow, damageModifier, shotFrom);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        World world = this.getEntityWorld();
        KeepInventoryTypes keepInventoryType = this.getKeepInventoryType();
        if (keepInventoryType == KeepInventoryTypes.DROP_ALL_ITEM) {
            for (int i = 0; i < this.inventory.size(); i++) {
                if (this.getDonDropSlotIndex().contains(i)) continue;
                ItemStack copiedStack = this.inventory.getStack(i).copy();
                ItemEntity itemEntity = new ItemEntity(world, this.getX(), this.getY(), this.getZ(), copiedStack);
                world.spawnEntity(itemEntity);
            }
            List<ItemStack> stacks = List.of(
                    this.inventory.getHead(),
                    this.inventory.getChest(),
                    this.inventory.getLegs(),
                    this.inventory.getFeet()
            );
            for (ItemStack stack : stacks) {
                ItemStack copiedStack = stack.copy();
                ItemEntity itemEntity = new ItemEntity(world, this.getX(), this.getY(), this.getZ(), copiedStack);
                world.spawnEntity(itemEntity);
            }
        } else if (keepInventoryType == KeepInventoryTypes.ONLY_HAND_AND_ARMOR) {
            List<ItemStack> stacks = List.of(
                    this.inventory.getMainHand(),
                    this.inventory.getOffHand(),
                    this.inventory.getHead(),
                    this.inventory.getChest(),
                    this.inventory.getLegs(),
                    this.inventory.getFeet()
            );
            for (ItemStack stack : stacks) {
                ItemStack copiedStack = stack.copy();
                ItemEntity itemEntity = new ItemEntity(world, this.getX(), this.getY(), this.getZ(), copiedStack);
                world.spawnEntity(itemEntity);
            }
        }
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public void sleep(BlockPos pos) {
        super.sleep(pos);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!getWorld().isClient() && this instanceof NPCEntityNeutralImpl impl) {
            if (stack.getItem() == ModItems.UPGRADED_HEALTH) {
                AttributeContainer attributes = this.getAttributes();
                EntityAttributeInstance max_health = attributes.getCustomInstance(EntityAttributes.MAX_HEALTH);
                float health = this.getMaxHealth() + 2;
                float maxHealth = this.getMaxHealth() + 2;
                if (max_health != null) {
                    max_health.setBaseValue(maxHealth);
                    setHealth(health + 2);
                }
                player.swingHand(hand);
                getEntityWorld().playSound(null, player.getX(), player.getEyeY(), player.getZ(), SoundEventInit.UP, player.getSoundCategory(), 1.0f, 1.0f);
                stack.decrementUnlessCreative(1, player);
                return ActionResult.SUCCESS_SERVER;
            }
            if (stack.getItem() == Items.POTION && this.canFeed()) {
                PotionContentsComponent potionContentsComponent = stack.get(DataComponentTypes.POTION_CONTENTS);
                UseRemainderComponent useRemainderComponent = stack.get(DataComponentTypes.USE_REMAINDER);
                boolean use = false;
                if (stack.getItem() instanceof FoodItem food) {
                    Set<FoodProperty> foodProperties = new HashSet<>(FoodProperty.getFromItemStack(stack));
                    Set<FoodProperty> foodPropertiesFromComponent = new HashSet<>(FoodProperty.getFromItemStackComponent(stack));

                    Set<FoodProperty> allProperties = new HashSet<>(foodProperties);
                    allProperties.addAll(foodPropertiesFromComponent);

                    for (FoodProperty foodProperty : allProperties) {
                        foodProperty.use((ServerWorld) this.getWorld(), this);
                    }

                    allProperties.forEach((property) -> this.nutrition++);

                    use = true;
                }
                if (potionContentsComponent != null) {
                    Optional<RegistryEntry<Potion>> potionOpt = potionContentsComponent.potion();
                    boolean present = potionOpt.isPresent();
                    if (present) {
                        Potion potion = potionOpt.get().value();
                        for (StatusEffectInstance instance : potion.getEffects()) {
                            StatusEffectInstance effectInstance = new StatusEffectInstance(instance);
                            this.addStatusEffect(effectInstance);
                        }
                        use = true;
                    }
                    List<StatusEffectInstance> statusEffectInstances = potionContentsComponent.customEffects();
                    for (var instance : statusEffectInstances) {
                        StatusEffectInstance effectInstance = new StatusEffectInstance(instance);
                        this.addStatusEffect(effectInstance);
                        use = true;
                    }
                }
                if (use) {
                    this.playSound(SoundEvents.ENTITY_GENERIC_DRINK.value(), 1.0f, 1.0f);
                    stack.decrementUnlessCreative(1, player);
                    if (useRemainderComponent != null && !player.isInCreativeMode()) {
                        ItemStack itemStack = useRemainderComponent.convert(stack, stack.getCount(), player.isInCreativeMode(), player::giveOrDropStack);
                        player.setStackInHand(hand, itemStack);
                    }
                }
                player.swingHand(hand);
                return ActionResult.SUCCESS_SERVER;
            }
            if ((((ItemStackImpl) (Object) stack).isFood() || stack.getItem() == TAME_FOOD_ITEM) && this.canFeed()) {
                ComponentMap components = stack.getComponents();
                UseRemainderComponent useRemainderComponent = stack.get(DataComponentTypes.USE_REMAINDER);
                FoodComponent foodComponent = components.get(DataComponentTypes.FOOD);
                ConsumableComponent consumableComponent = components.get(DataComponentTypes.CONSUMABLE);
                if (this.npcOwner.isEmpty() && stack.getItem() == TAME_FOOD_ITEM) {
                    Random random = new Random();
                    float chance = random.nextFloat();
                    World world = this.getEntityWorld();
                    if (chance <= 0.4) {
                        this.setOwner(player);
                        this.setTamed(true, true);
                        if (world instanceof ServerWorld) {
                            ((ServerWorld) world).spawnParticles(ParticleTypes.HEART, this.getX(), this.getY() + 1.0, this.getZ(), 5, 0.5, 0.5, 0.5, 0.1);
                        }
                    }
                    this.setHealth(this.getHealth() + 5);
                    stack.decrementUnlessCreative(1, player);
                }
                boolean use = false;
                if (foodComponent != null) {
                    int nutritionValue = foodComponent.nutrition();
                    float saturationValue = foodComponent.saturation();
                    if ((this.nutrition < 20 || this.saturation < 20) || foodComponent.canAlwaysEat()) {
                        this.nutrition += nutritionValue;
                        this.saturation += saturationValue;
                        use = true;
                    }
                }
                if (consumableComponent != null) {
                    for (ConsumeEffect onConsumeEffect : consumableComponent.onConsumeEffects()) {
                        if (onConsumeEffect instanceof ApplyEffectsConsumeEffect applyEffectsConsumeEffect) {
                            List<StatusEffectInstance> effects = applyEffectsConsumeEffect.effects();
                            for (StatusEffectInstance effect : effects) {
                                this.addStatusEffect(effect);
                            }
                        }
                    }
                }
                if (use) {
                    this.playSound(SoundEvents.ENTITY_GENERIC_EAT.value(), 1.0f, 1.0f);
                    stack.decrementUnlessCreative(1, player);
                    if (useRemainderComponent != null && !player.isInCreativeMode()) {
                        ItemStack itemStack = useRemainderComponent.convert(stack, stack.getCount(), player.isInCreativeMode(), player::giveOrDropStack);
                        player.setStackInHand(hand, itemStack);
                    }
                }
                player.swingHand(hand);
                return ActionResult.SUCCESS_SERVER;
            }
            if ((this.isOwner(player) || (player.isCreative() && this.isTamed())) && !this.getWorld().isClient()) {
                if (player instanceof ServerPlayerEntity serverPlayerEntity) {
                    NPCGui npcGui = new NPCGui(serverPlayerEntity, this);
                    npcGui.open();
                }
                return ActionResult.SUCCESS_SERVER;
            }
        }

        return super.interactMob(player, hand);
    }

//    @Override
    public void setOwnerUuid(@Nullable UUID uuid) {
        if (uuid != null) {
            this.npcOwner = uuid.toString();
            this.setTamed(true, true);
        }
    }

    @Override
    public void setOwner(LivingEntity player) {
        this.npcOwner = player.getUuid().toString();
        this.setTamed(true, true);
        if (player instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.TAME_ANIMAL.trigger(serverPlayerEntity, this);
        }
    }

    @Override
    public boolean isOwner(LivingEntity entity) {
        return entity.getUuid().toString().equalsIgnoreCase(this.npcOwner);
    }

    @Override
    public boolean canPickUpLoot() {
        return this.canPickItem();
    }

    @Override
    public void tickMovement() {
        World world = this.getWorld();
        if (world instanceof ServerWorld serverWorld) {
            if (this.isSleeping()) return;
            if (this.canPickUpLoot() && this.isAlive()) {
                Vec3i vec3i = this.getItemPickUpRangeExpander();
                List<ItemEntity> list = this.getWorld().getNonSpectatingEntities(ItemEntity.class, this.getBoundingBox().expand(vec3i.getX(), vec3i.getY(), vec3i.getZ()));
                for (ItemEntity itemEntity : list) {
                    if (itemEntity.isRemoved() || itemEntity.getStack().isEmpty() || itemEntity.cannotPickup() || !this.canGather(serverWorld, itemEntity.getStack()))
                        continue;
                    this.loot(serverWorld, itemEntity);
                }
            }
        }
        super.tickMovement();
    }

    @Override
    protected void loot(ServerWorld world, ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.getStack();
        if (this.inventory.canInsert(itemStack)) {
            if (ItemUtils.isArmorItem(itemStack)) {
                EquippableComponent equippableComponent = itemStack.get(DataComponentTypes.EQUIPPABLE);
                if (equippableComponent != null) {
                    boolean head = equippableComponent.slot() == EquipmentSlot.HEAD;
                    boolean chest = equippableComponent.slot() == EquipmentSlot.CHEST;
                    boolean legs = equippableComponent.slot() == EquipmentSlot.LEGS;
                    boolean feet = equippableComponent.slot() == EquipmentSlot.FEET;
                    if (head && this.inventory.getHead().isEmpty()) {
                        this.inventory.setHead(itemStack.copy());
                    } else if (chest && this.inventory.getChest().isEmpty()) {
                        this.inventory.setChest(itemStack.copy());
                    } else if (legs && this.inventory.getLegs().isEmpty()) {
                        this.inventory.setLegs(itemStack.copy());
                    } else if (feet && this.inventory.getFeet().isEmpty()) {
                        this.inventory.setFeet(itemStack.copy());
                    } else {
                        this.inventory.addStack(itemStack.copy());
                    }
                } else {
                    this.inventory.addStack(itemStack.copy());
                }
            } else if ((itemStack.getItem() instanceof ShieldItem) || (itemStack.getItem() == Items.TORCH)) {
                if (this.inventory.getOffHand().isEmpty()) {
                    this.inventory.setOffHand(itemStack.copy());
                } else {
                    this.inventory.addStack(itemStack.copy());
                }
            } else {
                if (this.inventory.getMainHand().isEmpty()) {
                    this.inventory.setMainHand(itemStack.copy());
                } else {
                    this.inventory.addStack(itemStack.copy());
                }
            }
            itemEntity.discard();
        }
    }

    protected void updateHealth() {
        if (this.getHealth() < this.getMaxHealth()) {
            this.healthTick++;
        } else {
            this.healthTick = 0;
        }
        if (this.getHealth() < this.getMaxHealth() && this.healthTick > this.maxHealthTick && this.saturation > 0f) {
            this.setHealth(getHealth() + 1);
            this.healthTick = 0;
            this.reduceHunger(1f);
        }
    }

    protected void updateAttackType() {
        if (this.getWorld() == null || this.getWorld().isClient) {
            return;
        }
        this.goalSelector.remove(this.meleeAttackGoal);
        this.goalSelector.remove(this.bowAttackGoal);
        ItemStack itemStack = this.getMainHandStack();
        if (this.inventory.containsAny(ARROW_ITEMS) && (itemStack.isOf(Items.BOW) || itemStack.getItem() instanceof BowItem)) {
            int i = this.getRegularAttackInterval();
            this.bowAttackGoal.setAttackInterval(i);
            this.goalSelector.add(4, this.bowAttackGoal);
        } else {
            this.goalSelector.add(4, this.meleeAttackGoal);
        }
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        EntityData initialize = super.initialize(world, difficulty, spawnReason, entityData);
        this.updateAttackType();
        return initialize;
    }

    protected int getHardAttackInterval() {
        return 20;
    }

    protected int getRegularAttackInterval() {
        return 40;
    }

    public NPCState getNextState() {
        if (this.isSleeping()) return this.npcState;
        NPCState next = NPCState.fromInt(this.npcState.getId() + 1);
        return next != null ? next : NPCState.NORMAL;
    }

    public void reduceHunger(float value) {
        float remaining = value;

        while (remaining > 0.0f) {
            if (this.saturation > 0.0f) {
                float delta = Math.min(0.5f, Math.min(this.saturation, remaining));
                this.saturation -= delta;
                remaining -= delta;
            } else if (this.nutrition > 0.0f) {
                float delta = Math.min(0.5f, Math.min(this.nutrition, remaining));
                this.nutrition -= delta;
                remaining -= delta;
            } else {
                break;
            }
        }
    }

    public void updateWorking() {
        if (this.npcState == NPCState.WORKING && this.workTick < 20) {
            this.workTick++;
        } else {
            this.workTick = 0;
        }
        if (this.npcState == NPCState.WORKING && this.workTick >= 20) {
            this.workTick = 0;
            BlockPos blockPos = this.getBlockPos();
            if (this.workingPos != null) {
                double distance = blockPos.getSquaredDistance(this.workingPos);

                if (distance > 8 * 8) {
                    boolean success = this.getNavigation().startMovingTo(
                            this.workingPos.getX() + 0.5,
                            this.workingPos.getY(),
                            this.workingPos.getZ() + 0.5,
                            1.0D
                    );
                }
            }
        }
    }

    public void updateName() {
        this.setCustomNameVisible(this.hasCustomName());
    }

    @Override
    public void tick() {
        World world = this.getEntityWorld();
        this.updateHealth();
        this.updateWorking();
        this.updateName();
        this.updateAttackTick++;
        if (this.updateAttackTick > this.maxUpdateAttackTick) {
            this.updateAttackType();
            this.updateAttackTick = 0;
        }

        this.exhaustionLevel = 0;
        if (this.consumeHunger()) {
            if (this.hasStatusEffect(StatusEffects.HUNGER)) {
                this.exhaustionLevel += 2;
            }
        }
        this.hungerTick += 0.1f * this.exhaustionLevel;
        if (this.hungerTick > 20) {
            this.hungerTick = 0;
            this.reduceHunger(0.5f);
        }

        if (this.nutrition > 20f) {
            this.nutrition = 20f;
        }
        if (this.saturation > 20f) {
            this.saturation = 20f;
        }

//        if (this.sleepingPos != null) {
//            if (!this.isSleeping()) {
//                this.sleep(this.sleepingPos);
//            }
//        } else {
//            if (this.isSleeping()) {
//                this.wakeUp();
//            }
//        }

        if (this.npcState == NPCState.SNAKING) {
            this.getNavigation().stop();
            if (this.getPose() != EntityPose.CROUCHING) {
                this.setPose(EntityPose.CROUCHING);
            }
            super.tick();
            return;
        }

        if (this.npcState == NPCState.SEATED) {
            if (this.seat == null) {
                List<ArmorStandEntity> list = world.getEntitiesByClass(
                                ArmorStandEntity.class,
                                new Box(this.getX() + 1, this.getY() + 1, this.getZ() + 1, this.getX() - 1, this.getY() - 1, this.getZ() - 1),
                                entity -> true)
                        .stream()
                        .filter(entity -> entity.getUuid().toString().equalsIgnoreCase(this.seatUUID))
                        .toList();
                if (!list.isEmpty()) {
                    this.seat = list.getFirst();
                } else {
                    spawnSeatAndSit();
                }
            }
            this.setSit(true);
            return;
        } else {
            if (this.seat != null) {
                this.seat.discard();
                this.seatUUID = "";
                this.seat = null;
            }
            this.setSit(false);
        }
        if (this.getPose() == EntityPose.CROUCHING) {
            this.setPose(EntityPose.STANDING);
        }
        super.tick();
    }

    private void spawnSeatAndSit() {
        ArmorStandEntity as = EntityType.ARMOR_STAND.create(this.getEntityWorld(), SpawnReason.TRIGGERED);
        if (as == null) return;

        as.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
        as.setInvisible(true);
        as.setNoGravity(true);
        as.setMarker(true);
        this.getEntityWorld().spawnEntity(as);

        this.seat = as;
        this.seatUUID = this.seat.getUuid().toString();
        this.startRiding(as, true);
    }


    @Override
    public float getMovementSpeed() {
        if (this.npcState == NPCState.NO_WALK || this.npcState == NPCState.SNAKING) return 0;
        if (this.isPause) return 0;
        return super.getMovementSpeed();
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        if (this.isDead()) {
            return false;
        }
        return super.damage(world, source, amount);
    }

    @Override
    public void damageArmor(DamageSource source, float amount) {
        if (this.canDamageEquipment()) {
            this.damageEquipment(source, amount, EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD);
        }
    }

    @Override
    public void damageHelmet(DamageSource source, float amount) {
        if (this.canDamageEquipment()) {
            this.damageEquipment(source, amount, EquipmentSlot.HEAD);
        }
    }

    @Override
    public boolean tryAttack(ServerWorld world, Entity target) {
        boolean result = super.tryAttack(world, target);
        if (result) {
            ItemStack mainHand = this.getMainHandStack();
            if (mainHand.isDamageable()) {
                mainHand.damage(1, this, null);
            }
        }
        return result;
    }

    //    @Override
    public Iterable<ItemStack> getArmorItems() {
        return List.of(
                this.getEquippedStack(EquipmentSlot.HEAD),
                this.getEquippedStack(EquipmentSlot.CHEST),
                this.getEquippedStack(EquipmentSlot.LEGS),
                this.getEquippedStack(EquipmentSlot.FEET)
        );
    }

    @Override
    public ItemStack getMainHandStack() {
        ItemStack stack = inventory.getStack(NPCInventory.MAIN_HAND);
        if (stack.isEmpty()) return super.getMainHandStack();
        return stack;
    }

    @Override
    public ItemStack getOffHandStack() {
        ItemStack stack = inventory.getStack(NPCInventory.OFF_HAND);
        if (stack.isEmpty()) return super.getOffHandStack();
        return stack;
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        if (slot == EquipmentSlot.HEAD) {
            return this.inventory.getHead();
        } else if (slot == EquipmentSlot.CHEST) {
            return this.inventory.getChest();
        } else if (slot == EquipmentSlot.LEGS) {
            return this.inventory.getLegs();
        } else if (slot == EquipmentSlot.FEET) {
            return this.inventory.getFeet();
        } else if (slot == EquipmentSlot.MAINHAND) {
            return this.inventory.getStack(NPCInventory.MAIN_HAND);
        } else if (slot == EquipmentSlot.OFFHAND) {
            return this.inventory.getStack(NPCInventory.OFF_HAND);
        }
        return super.getEquippedStack(slot);
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        super.equipStack(slot, stack);
        int idx = switch (slot) {
            case MAINHAND -> NPCInventory.MAIN_HAND;
            case OFFHAND -> NPCInventory.OFF_HAND;
            case HEAD -> -11;
            case CHEST -> -12;
            case LEGS -> -13;
            case FEET -> -14;
            default -> -1;
        };
        if (idx >= 0) this.inventory.setStack(idx, stack);
        if (-14 <= idx && idx <= -11) {
            if (idx == -11) {
                this.inventory.setHead(stack);
            }
            if (idx == -12) {
                this.inventory.setChest(stack);
            }
            if (idx == -13) {
                this.inventory.setLegs(stack);
            }
            if (idx == -14) {
                this.inventory.setFeet(stack);
            }
        }
        if (!this.getWorld().isClient) {
            this.updateAttackType();
        }
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    public static DefaultAttributeContainer createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.MAX_HEALTH, 20.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.ATTACK_DAMAGE, 1.0)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.1)
                .add(EntityAttributes.FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.TEMPT_RANGE, 10.0)
                .add(EntityAttributes.ENTITY_INTERACTION_RANGE, 3)
                .build();
    }

    @Override
    public @Nullable LivingEntity getOwner() {
        if (this.npcOwner.equalsIgnoreCase("")) return null;
        return getWorld().getPlayerByUuid(UUID.fromString(this.npcOwner));
    }

//    @Override
//    public @Nullable UUID getOwnerUuid() {
//        if (this.npcOwner.equalsIgnoreCase("")) return null;
//        return UUID.fromString(this.npcOwner);
//    }

    @Override
    public boolean isSitting() {
        return this.isSit();
    }

    @Override
    public Property getSkin() {
        return skin;
    }

}
