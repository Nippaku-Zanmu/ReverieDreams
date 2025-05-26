package cc.thonly.touhoumod.entity;

import cc.thonly.touhoumod.entity.ai.goal.DanmakuGoal;
import cc.thonly.touhoumod.entity.holder.WingHolder;
import cc.thonly.touhoumod.entity.npc.NPCEntityImpl;
import cc.thonly.touhoumod.item.ModItems;
import cc.thonly.touhoumod.server.DelayedTask;
import com.mojang.authlib.properties.Property;
import eu.pb4.polymer.virtualentity.api.VirtualEntityUtils;
import eu.pb4.polymer.virtualentity.api.attachment.EntityAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Leashable;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class YouseiEntity extends NPCEntityImpl implements Leashable, FriendlyFaction, Yousei {
    public YouseiEntity(EntityType<? extends TameableEntity> entityType, World world, Property skin) {
        super(entityType, world, skin);
    }

    @Override
    public void onCreated(Entity entity) {
        super.onCreated(entity);
        var x = new ItemDisplayElement();
        var holder = new WingHolder(this);
        x.setItem(new ItemStack(ModEntityHolders.YOUSEI_WINGS));
        x.setInvisible(true);
        x.setTeleportDuration(3);
        x.setScale(new Vector3f(1.2f));
        holder.setElement(x);
        holder.addElement(x);
        EntityAttachment.ofTicking(holder, entity);
        VirtualEntityUtils.addVirtualPassenger(entity, x.getEntityId());
        ELEMENTS.put(entity, x);
    }

    @Override
    protected void initGoals() {
        super.initGoals();

        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new FlyGoal(this, 1.0));

//        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0));

        this.goalSelector.add(3, new DanmakuGoal(this, (self, target, world) -> {
            ItemStack stack = new ItemStack(ModItems.FIREBALL_GLOWY.random());
            final MinecraftServer server = world.getServer();
            final float[] pitchYaw = MobDanmakuFireLauncher.getPitchYaw(self, target);

            DelayedTask.repeat(server, 1, 0.3f, () -> {
                MobDanmakuFireLauncher.spawn(world, self, Hand.MAIN_HAND, stack, pitchYaw[0], pitchYaw[1] - 15.0f, 0.5f, 5.0f, 0.2f);
                MobDanmakuFireLauncher.spawn(world, self, Hand.MAIN_HAND, stack, pitchYaw[0], pitchYaw[1], 0.5f, 5.0f, 0.2f);
                MobDanmakuFireLauncher.spawn(world, self, Hand.MAIN_HAND, stack, pitchYaw[0], pitchYaw[1] + 15.0f, 0.5f, 5.0f, 0.2f);
            });
        }));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(4, new LookAtEntityGoal(this, MobEntity.class, 6.0f));
        this.goalSelector.add(5, new LookAroundGoal(this));

        this.targetSelector.add(3, new RevengeGoal(this).setGroupRevenge());
    }

    @Override
    public Boolean canPickItem() {
        return false;
    }

    @Override
    public String getFactionId() {
        return "mob";
    }
}
