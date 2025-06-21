package cc.thonly.reverie_dreams.block;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Getter
@Setter
@ToString
public class MarisaHatBlock extends BasicFumoBlock{
    public MarisaHatBlock(Identifier identifier, Vec3d offsets, Settings settings) {
        super(identifier, offsets, settings);
    }

    public MarisaHatBlock(String path, Vec3d offsets, Settings settings) {
        super(path, offsets, settings);
    }

    public MarisaHatBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            return ActionResult.SUCCESS_SERVER;
        }
        return ActionResult.SUCCESS;
    }
}
