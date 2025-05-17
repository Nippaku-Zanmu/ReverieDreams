package cc.thonly.touhoumod.entity;

import cc.thonly.touhoumod.entity.npc.NPCEntityImpl;
import com.mojang.authlib.properties.Property;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;

public class YoukaiEntity extends NPCEntityImpl {
    public YoukaiEntity(EntityType<? extends TameableEntity> entityType, World world, Property skin) {
        super(entityType, world, skin);
    }
}
