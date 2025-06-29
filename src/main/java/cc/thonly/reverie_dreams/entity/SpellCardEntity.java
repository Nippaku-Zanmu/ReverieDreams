package cc.thonly.reverie_dreams.entity;

import cc.thonly.reverie_dreams.item.ModItems;
import cc.thonly.reverie_dreams.item.danmaku.DanmakuLauncher;
import eu.pb4.polymer.core.api.entity.PolymerEntity;
import eu.pb4.polymer.virtualentity.api.tracker.DisplayTrackedData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.joml.Vector3f;
import xyz.nucleoid.packettweaker.PacketContext;

import java.lang.reflect.Method;
import java.util.List;

import static net.minecraft.item.ItemDisplayContext.FIXED;

@Setter
@Getter
@ToString
@Deprecated
public class SpellCardEntity extends PersistentProjectileEntity implements PolymerEntity, DanmakuLauncher {
    protected Item item;
    protected Integer tickCount = 0;
    protected Integer maxTick = 20 * 30;
    protected Float scale = 1.0f;
    protected ItemStack itemStack = Items.ARROW.getDefaultStack();
    protected LivingEntity livingEntity;

    public SpellCardEntity(EntityType<SpellCardEntity> entityType, World world) {
        super(entityType, world);
        this.item = Items.PAPER;
        this.pickupType = PickupPermission.DISALLOWED;
        this.setCustomPierceLevel((byte) 1);
        this.setNoGravity(true);
    }

    public SpellCardEntity(LivingEntity livingEntity, ItemStack mainStack, ItemStack offStack, Hand hand, Integer maxTick) {
        super(ModEntities.SPELL_CARD_ENTITY_TYPE,
                livingEntity.getX(),
                livingEntity.getY() + livingEntity.getStandingEyeHeight() - 0.5,
                livingEntity.getZ(),
                livingEntity.getEntityWorld(),
                offStack.copy(),
                livingEntity.getStackInHand(hand));
        this.maxTick = maxTick;
        this.item = mainStack.getItem();
        this.itemStack = mainStack.copy();
        this.pickupType = PickupPermission.DISALLOWED;
        this.setCustomPierceLevel((byte) 1);
        this.setOwner(livingEntity);
        this.setNoGravity(true);
        this.livingEntity = livingEntity;
    }

    @Override
    public void tick() {
        super.tick();
        tickCount++;
        apply();
        if (tickCount > maxTick) {
            this.discard();
        }
    }

    @Override
    public void apply() {

    }

    public void spawnBullet(ServerWorld world, Entity spellCard, Hand hand, ItemStack offHandStack, float pitch, float yaw, float speed, float divergence, int bulletCount, double radius, double angularSpeed) {
//        Item item = offHandStack.getItem();
//        if (item instanceof BasicPolymerDanmakuItem danmakuItem) {
//            // 动态计算每个子弹的偏移量
//            for (int i = 0; i < bulletCount; i++) {
//                double angle = i * (2 * Math.PI / bulletCount) + (spellCard.age * angularSpeed);  // 动态旋转角度
//                double xOffset = radius * Math.cos(angle);  // 计算x轴偏移
//                double zOffset = radius * Math.sin(angle);  // 计算z轴偏移
//
//                // 根据计算的偏移量，创建子弹并设置位置
//                DanmakuEntity danmakuEntity = new DanmakuEntity(
//                        spellCard,
//                        offHandStack.copy(),
//                        hand,
//                        danmakuItem,
//                        pitch,
//                        yaw,
//                        speed,
//                        divergence,
//                        0.4f
//                );
//                danmakuEntity.setPos(spellCard.getX() + xOffset, spellCard.getY(), spellCard.getZ() + zOffset);
//                danmakuEntity.setOwner(spellCard);
//                // 计算子弹的速度
//                double motionX = speed * Math.cos(angle);
//                double motionZ = speed * Math.sin(angle);
//                danmakuEntity.setVelocity(motionX, 0, motionZ);
//                danmakuEntity.velocityModified = true;
//
//                // 在世界中生成子弹
//                if (!world.isClient) {
//                    world.spawnEntity(danmakuEntity);
//                }
//            }
//        }
    }

    @Override
    public void modifyRawTrackedData(List<DataTracker.SerializedEntry<?>> data, ServerPlayerEntity player, boolean initial) {
        PolymerEntity.super.modifyRawTrackedData(data, player, initial);
        setProjectileData(data, initial, scale, this.itemStack);
    }

    public static void setProjectileData(List<DataTracker.SerializedEntry<?>> data, boolean initial, float scale, ItemStack itemStack) {
        if (initial) {
            data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.TELEPORTATION_DURATION, 2));
            data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.SCALE, new Vector3f(scale)));
            data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.BILLBOARD, (byte) DisplayEntity.BillboardMode.CENTER.ordinal()));
            data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.Item.ITEM, itemStack));
            data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.Item.ITEM_DISPLAY, ItemDisplayContext.FIXED.getIndex()));
        }
    }

    public void setCustomPierceLevel(byte level) {
        if (!tryInvokeMethod(PersistentProjectileEntity.class, "setPierceLevel", byte.class, level)) {
            tryInvokeMethod(PersistentProjectileEntity.class, "method_7451", byte.class, level);
        }
    }

    private boolean tryInvokeMethod(Class<?> targetClass, String methodName, Class<?> paramType, Object paramValue) {
        try {
            Method method = targetClass.getDeclaredMethod(methodName, paramType);
            method.setAccessible(true);
            method.invoke(this, paramValue);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public EntityType<?> getPolymerEntityType(PacketContext context) {
        return EntityType.ITEM_DISPLAY;
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        if (this.item != null && !this.item.getDefaultStack().isEmpty()) {
            return this.item.getDefaultStack();
        } else {
            return new ItemStack(ModItems.ICON);
        }
    }
}
