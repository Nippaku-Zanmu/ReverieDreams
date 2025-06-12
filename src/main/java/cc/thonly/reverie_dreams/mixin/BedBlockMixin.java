package cc.thonly.reverie_dreams.mixin;

import cc.thonly.reverie_dreams.entity.npc.NPCEntityImpl;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BedBlock.class)
public abstract class BedBlockMixin extends HorizontalFacingBlock implements BlockEntityProvider {
    protected BedBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "onUse", at = @At("HEAD"))
    public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (state.get(BedBlock.OCCUPIED).booleanValue()) {
            this.wakeNpc(world, pos);
        }
    }

    @Unique
    public boolean wakeNpc(World world, BlockPos pos) {
        List<NPCEntityImpl> list = world.getEntitiesByClass(NPCEntityImpl.class, new Box(pos), LivingEntity::isSleeping);
        if (list.isEmpty()) {
            return false;
        }
        list.getFirst().wakeUp();
        return true;
    }
}
