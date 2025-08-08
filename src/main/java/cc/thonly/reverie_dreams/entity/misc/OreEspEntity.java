package cc.thonly.reverie_dreams.entity.misc;

import eu.pb4.polymer.core.api.entity.PolymerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.world.World;
import xyz.nucleoid.packettweaker.PacketContext;

public class OreEspEntity extends DisplayEntity.BlockDisplayEntity implements PolymerEntity {
    public int lifetime = 100;

    public OreEspEntity(EntityType<?> entityType, World world ) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) return;
        this.lifetime--;
        if (lifetime <= 0||this.getWorld().getBlockState(this.getBlockPos()).getBlock()!=this.getBlockState().getBlock()) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public EntityType<?> getPolymerEntityType(PacketContext packetContext) {
        return EntityType.BLOCK_DISPLAY;
    }
}
