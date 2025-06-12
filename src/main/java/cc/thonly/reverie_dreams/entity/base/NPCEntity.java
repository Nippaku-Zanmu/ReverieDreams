package cc.thonly.reverie_dreams.entity.base;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class NPCEntity extends TameableEntity implements PlayerPolymerEntity {

    protected NPCEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.onCreated(this);
    }

    @Override
    public abstract @Nullable LivingEntity getOwner();

    @Override
    public abstract @Nullable UUID getOwnerUuid();

}
