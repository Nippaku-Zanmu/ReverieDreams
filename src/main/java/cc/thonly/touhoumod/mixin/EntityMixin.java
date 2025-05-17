package cc.thonly.touhoumod.mixin;

import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Leashable;
import net.minecraft.entity.data.DataTracked;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.scoreboard.ScoreHolder;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Nameable;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.ComponentAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin{
    @Shadow public abstract boolean isAlive();

    @Shadow public abstract World getWorld();

    @Shadow public abstract void emitGameEvent(RegistryEntry<GameEvent> event, @Nullable Entity entity);

//    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
//    public void interact(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
//        Entity entity;
//        if (this.isAlive() && (entity = (Entity) (Object)this) instanceof Leashable) {
//            Leashable leashable = (Leashable)((Object)entity);
//            System.out.println(0);
//            if (leashable.getLeashHolder() == player) {
//                System.out.println(1);
//                if (!this.getWorld().isClient()) {
//                    System.out.println(2);
//                    if (player.isInCreativeMode()) {
//                        System.out.println(3);
//                        leashable.detachLeashWithoutDrop();
//                    } else {
//                        System.out.println(4);
//                        leashable.detachLeash();
//                    }
//                    this.emitGameEvent(GameEvent.ENTITY_INTERACT, player);
//                }
//                cir.setReturnValue(ActionResult.SUCCESS.noIncrementStat());
//            }
//            ItemStack itemStack = player.getStackInHand(hand);
//            if (itemStack.isOf(Items.LEAD) && leashable.canLeashAttachTo()) {
//                System.out.println(5);
//                if (!this.getWorld().isClient()) {
//                    System.out.println(6);
//                    leashable.attachLeash(player, true);
//                }
//                itemStack.decrement(1);
//                cir.setReturnValue(ActionResult.SUCCESS);
//            }
//        }
//        cir.setReturnValue(ActionResult.PASS);
//    }
}
