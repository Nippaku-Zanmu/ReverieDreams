package cc.thonly.reverie_dreams.mixin;

import cc.thonly.reverie_dreams.effect.ModStatusEffects;
import cc.thonly.reverie_dreams.entity.DanmakuEntity;
import cc.thonly.reverie_dreams.interfaces.LivingEntityImpl;
import cc.thonly.reverie_dreams.sound.ModSoundEvents;
import cc.thonly.reverie_dreams.world.WorldGetter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.EnumSet;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityImpl {
    @Shadow
    public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

    @Shadow
    public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow
    public abstract void setHealth(float health);

    @Shadow
    public abstract float getMaxHealth();

    @Shadow
    public abstract float getHealth();

    @Shadow
    @Nullable
    protected abstract SoundEvent getDeathSound();

    @Shadow
    protected abstract @Nullable SoundEvent getHurtSound(DamageSource source);

    @Unique
    public double manpozuchiUsingState = 1;
    @Unique
    public float maxHealthModifier = 0f;
    @Unique
    public int deathCount = 0;
    @Unique
    private int deathCountResetTimer = 0;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "getMaxHealth", at = @At("RETURN"), cancellable = true)
    public void getMaxHealth(CallbackInfoReturnable<Float> ci) {
        float baseMaxHealth = ci.getReturnValue();
        float modifiedMaxHealth = baseMaxHealth + this.maxHealthModifier;

        if (modifiedMaxHealth < 1.0f) {
            modifiedMaxHealth = 1.0f;
        }

        ci.setReturnValue(modifiedMaxHealth);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        if (this.hasStatusEffect(ModStatusEffects.ELIXIR_OF_LIFE)) {
            if (this.deathCount == 1) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 0));
            }
            if (this.deathCount == 2) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 1));
            }
            if (this.deathCount == 3) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 2));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 20, 0));
            }
            if (this.deathCount == 3) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 3));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 20, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 20, 0));
            }
            if (this.deathCount == 3) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 3));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 20, 2));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 20, 1));
            }
            if (this.deathCount > 3) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 3));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 20, 2));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 20, 2));
            }
        } else {
            this.deathCount = 0;
        }

        if (!this.getWorld().isClient()) {
            MinecraftServer server = this.getWorld().getServer();
            World world = this.getWorld();
            double mobY = this.getY();
            RegistryKey<World> moonKey = ((WorldGetter) world).getMoon();
            RegistryKey<World> registryKey = world.getRegistryKey();
            if (server != null) {
                ServerWorld moonWorld = server.getWorld(moonKey);
                ServerWorld endWorld = server.getWorld(World.END);
                if (moonWorld != null && endWorld != null) {
                    if (registryKey.equals(World.END)) {
                        if (mobY >= endWorld.getHeight()) {
                            this.teleport(moonWorld, this.getX(), moonWorld.getHeight() - 1, this.getZ(), EnumSet.noneOf(PositionFlag.class), this.getYaw(), this.getPitch(), true);
                        }
                    } else if (registryKey.equals(moonKey)) {
                        this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 1, 0));
                        if (mobY >= moonWorld.getHeight()) {
                            this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 40 * 20, 0));
                            this.teleport(endWorld, this.getX(), endWorld.getHeight() - 1, this.getZ(), EnumSet.noneOf(PositionFlag.class), this.getYaw(), this.getPitch(), true);
                        }
                    }
                }
            }
        }

        if (!this.getWorld().isClient()) {
            this.deathCountResetTimer++;
            if (this.deathCountResetTimer >= 18000) {
                this.deathCount = Math.max(0, this.deathCount - 1);
                this.deathCountResetTimer = 0;
            }
        }
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void damage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        boolean bl1 = this.deathInElixir(world, source, amount, cir);
        if (!bl1) {
            this.deathByDanmaku(world, source, amount, cir);
        }
    }

    @Unique
    public boolean deathByDanmaku(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if ((this.getHealth() - amount <= 0f) && source.getSource() instanceof DanmakuEntity) {
            Entity self = (Entity) this;
            self.playSound(ModSoundEvents.BIU, 0.35F, 1.0F);
            return true;
        }
        return false;
    }

    @Unique
    public boolean deathInElixir(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.hasStatusEffect(ModStatusEffects.ELIXIR_OF_LIFE) && (this.getHealth() - amount <= 0f)) {
            this.deathCount++;
            this.setHealth(1f);
            this.setHealth(this.getMaxHealth());
            SoundEvent hurtSound = getHurtSound(source);
            SoundEvent deathSound = getDeathSound();
            getEntityWorld().playSound(null, this.getX(), this.getY(), this.getZ(), hurtSound, this.getSoundCategory(), 1.0f, 1.0f);
            getEntityWorld().playSound(null, this.getX(), this.getY(), this.getZ(), deathSound, this.getSoundCategory(), 1.0f, 1.0f);
            getEntityWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_TOTEM_USE, this.getSoundCategory(), 1.0f, 1.0f);
            for (var player : world.getPlayers()) {
                world.spawnParticles(player, ParticleTypes.TOTEM_OF_UNDYING, true, false, this.getX(), this.getY(), this.getZ(), 250, 1.5, 2, 1.5, 0.5);
            }
            cir.cancel();
            return true;
        }
        return false;
    }

    @Inject(method = "onDeath", at = @At("HEAD"), cancellable = true)
    public void onDeath(CallbackInfo ci) {
        if (this.maxHealthModifier >= 1) {
            this.maxHealthModifier--;
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putFloat("MaxHealthModifier", this.maxHealthModifier);
        nbt.putInt("DeathCount", this.deathCount);
        nbt.putInt("DeathCountResetTimer", this.deathCountResetTimer);
        nbt.putDouble("ManpozuchiUsingState", this.manpozuchiUsingState);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.maxHealthModifier = nbt.getFloat("MaxHealthModifier");
        this.deathCount = nbt.getInt("DeathCount");
        this.deathCountResetTimer = nbt.getInt("DeathCountResetTimer");
        this.manpozuchiUsingState = nbt.getDouble("ManpozuchiUsingState");
    }

    @Override
    public void setDeathCount(int deathCount) {
        this.deathCount = deathCount;
    }

    @Override
    public int getDeathCount() {
        return deathCount;
    }

    @Override
    public void setMaxHealthModifier(float value) {
        this.maxHealthModifier = value;
    }

    @Override
    public float getMaxHealthModifier() {
        return (float) this.maxHealthModifier;
    }

    @Override
    public void setManpozuchiUsingState(double value) {
        this.manpozuchiUsingState = value;
    }

    @Override
    public double getManpozuchiUsingState() {
        return this.manpozuchiUsingState;
    }
}

