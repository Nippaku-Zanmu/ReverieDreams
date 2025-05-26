package cc.thonly.touhoumod.entity;


import cc.thonly.touhoumod.item.base.BasicPolymerDanmakuItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Setter
@Getter
@ToString
public class KnifeEntity extends DanmakuEntity {

    public KnifeEntity(EntityType<KnifeEntity> danmakuEntityEntityType, World world) {
        super((EntityType<DanmakuEntity>)(Object)danmakuEntityEntityType, world);
    }

    public KnifeEntity(LivingEntity livingEntity, ItemStack stack, Hand hand, BasicPolymerDanmakuItem item) {
        super(livingEntity, stack, hand, item);
    }

    public KnifeEntity(Entity livingEntity, ItemStack stack, Hand hand, BasicPolymerDanmakuItem item, float pitch, float yaw, float speed, float divergence, float offsetDist, boolean tile) {
        super(livingEntity, stack, hand, item, pitch, yaw, speed, divergence, offsetDist, true);
    }

    public KnifeEntity(Entity livingEntity, ItemStack stack, Hand hand, BasicPolymerDanmakuItem item, float pitch, float yaw, float speed, float divergence, float offsetDist) {
        super(livingEntity, stack, hand, item, pitch, yaw, speed, divergence, offsetDist, true);
    }


    @Override
    protected ItemStack getDefaultItemStack() {
        return ModEntityHolders.KNIFE_DISPLAY.getDefaultStack();
    }
}
