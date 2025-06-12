package cc.thonly.reverie_dreams.entity;

import cc.thonly.reverie_dreams.entity.npc.NPCEntityImpl;
import com.mojang.authlib.properties.Property;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;

public class YoukaiEntity extends NPCEntityImpl {
    public YoukaiEntity(EntityType<? extends TameableEntity> entityType, World world, Property skin) {
        super(entityType, world, skin);
    }
}
