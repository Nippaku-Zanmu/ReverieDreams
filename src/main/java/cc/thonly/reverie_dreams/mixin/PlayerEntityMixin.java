package cc.thonly.reverie_dreams.mixin;

import cc.thonly.reverie_dreams.item.armor.EarphoneItem;
import cc.thonly.reverie_dreams.item.armor.KoishiHatItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void onEarphoneTick(CallbackInfo ci) {
        ItemStack stack = this.getEquippedStack(EquipmentSlot.HEAD);
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof EarphoneItem) {
                EarphoneItem.onUseTick(this.getWorld(), this, stack);
            }
            if (stack.getItem() instanceof KoishiHatItem) {
                KoishiHatItem.onUseTick(this.getWorld(), this, stack);
            }
        }
    }

//    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
//    public void damage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
//        Entity attacker = source.getAttacker();
//        if(attacker == null) return;
//        if (!attacker.isAttackable()) {
//            return;
//        }
//        if(!world.isClient()) {
//            List<NPCEntityImpl> npcList = world.getEntitiesByClass(
//                            NPCEntityImpl.class,
//                            new Box(this.getX() + 8, this.getY() + 8, this.getZ() + 8,
//                                    this.getX() - 8, this.getY() - 8, this.getZ() - 8
//                            ),
//                            entity -> true)
//                    .stream()
//                    .filter(entity -> entity.getUuidAsString().equalsIgnoreCase(this.getUuidAsString()))
//                    .toList();
//            if(!npcList.isEmpty()) {
//                for (var npc: npcList) {
//                    if(npc.isSit()) continue;
//                    npc.setTarget((LivingEntity) attacker);
//                }
//            }
//        }
//    }
//
//    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
//    public void attack(Entity target, CallbackInfo ci) {
//        World world = this.getEntityWorld();
//        if (!target.isAttackable()) {
//            return;
//        }
//        if(!world.isClient()) {
//            List<NPCEntityImpl> npcList = world.getEntitiesByClass(
//                            NPCEntityImpl.class,
//                            new Box(this.getX() + 8, this.getY() + 8, this.getZ() + 8,
//                                    this.getX() - 8, this.getY() - 8, this.getZ() - 8
//                            ),
//                            entity -> true)
//                    .stream()
//                    .filter(entity -> entity.getUuidAsString().equalsIgnoreCase(this.getUuidAsString()))
//                    .toList();
//            if(!npcList.isEmpty()) {
//                for (var npc: npcList) {
//                    if(npc.isSit()) continue;
//                    npc.setTarget((LivingEntity) target);
//                }
//            }
//        }
//    }
}
