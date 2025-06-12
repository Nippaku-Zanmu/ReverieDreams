package cc.thonly.reverie_dreams.entity;

import cc.thonly.reverie_dreams.effect.ModStatusEffects;
import cc.thonly.reverie_dreams.entity.ai.goal.NpcFollowOwnerGoal;
import cc.thonly.reverie_dreams.entity.ai.goal.StatusEffectTargetGoal;
import cc.thonly.reverie_dreams.entity.npc.NPCEntityImpl;
import cc.thonly.reverie_dreams.entity.npc.NPCEntitySkins;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class GhostEntity extends NPCEntityImpl {
    protected int particleTick = 0;
    protected int survivalTime = 0;

    public GhostEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world, NPCEntitySkins.Mob.GHOST);
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();
        this.particleTick++;
        if(!world.isClient()) {
            if (!this.hasCustomName()
                    && !this.hasVehicle()
                    && this.getPassengerList().isEmpty()
                    && this.survivalTime > 600 * 20
            ) {
                this.discard();
            }
            if(this.survivalTime <= 600 * 20) {
                this.survivalTime++;
            }

            if (this.particleTick > 3) {
                if (world instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(
                            ParticleTypes.WHITE_ASH,
                            this.getPos().x,
                            this.getPos().y,
                            this.getPos().z,
                            1,
                            0,
                            1,
                            0,
                            0.1
                    );
                }
                this.particleTick = 0;
            }
            if (world.isDay()) {
                StatusEffectInstance currentEffect = this.getStatusEffect(StatusEffects.INVISIBILITY);
                if (currentEffect == null || currentEffect.getDuration() <= 20) {
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 60, 0, false, false));
                }
            }
        }
    }

    @Override
    public boolean isPushable() {
        return !this.getWorld().isDay();
    }

    @Override
    public boolean isCollidable() {
        return !this.getWorld().isDay();
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));

        this.goalSelector.add(6, new NpcFollowOwnerGoal(this, 1.0, 2.0f, 10.0f));
        this.goalSelector.add(7, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0));

        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, NPCEntityImpl.class, 8.0f));
        this.goalSelector.add(10, new LookAroundGoal(this));

        this.targetSelector.add(2, new StatusEffectTargetGoal<>(this, PlayerEntity.class, true, ModStatusEffects.MENTAL_DISORDER));
        this.targetSelector.add(2, new StatusEffectTargetGoal<>(this, MobEntity.class, true, ModStatusEffects.MENTAL_DISORDER));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("SurvivalTime", this.survivalTime);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if(nbt.contains("SurvivalTime")) {
            this.survivalTime = nbt.getInt("SurvivalTime");
        } else {
            this.survivalTime = 0;
        }
    }

    @Override
    public KeepInventoryTypes getKeepInventoryType() {
        return KeepInventoryTypes.NOT_DROP_ANY;
    }

    @Override
    public Boolean canPickItem() {
        return false;
    }
}
