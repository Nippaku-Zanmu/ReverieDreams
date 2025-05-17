package cc.thonly.touhoumod.item;

import cc.thonly.touhoumod.entity.DanmakuEntity;
import cc.thonly.touhoumod.item.base.BasicPolymerItem;
import cc.thonly.touhoumod.sound.ModSoundEvents;
import cc.thonly.touhoumod.util.IdentifierGetter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;

public class BombItem extends BasicPolymerItem implements IdentifierGetter {
    public BombItem(String path, Settings settings) {
        super(path, settings, Items.PAPER);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && world instanceof ServerWorld serverWorld) {
            ItemStack itemStack = user.getStackInHand(hand);
            world.playSound(null, user.getX(), user.getEyeY(), user.getZ(), ModSoundEvents.SPELL_CARD, user.getSoundCategory(), 1.0f, 1.0f);
            List<Entity> nearbyEntities = serverWorld.getEntitiesByClass(Entity.class, user.getBoundingBox().expand(20), entity -> true);
            List<Entity> nearbyDanmaku = nearbyEntities.stream().filter((entity -> entity instanceof DanmakuEntity danmakuEntity && danmakuEntity.getOwner() != user)).toList();
            nearbyDanmaku.forEach((entity) -> {
                DanmakuEntity danmakuEntity = (DanmakuEntity) entity;
                Vec3d pos = danmakuEntity.getPos();
                int particleCount = (int) 8;
                double radius = danmakuEntity.getWidth() / 2 + 0.5;
                double heightOffset = danmakuEntity.getHeight();
                Random random = world.getRandom();
                int count = random.nextBetween(1, 4);
                for (int i = 0; i < count; i++) {
                    serverWorld.spawnEntity(
                            new ItemEntity(serverWorld,
                                    danmakuEntity.getX(),
                                    danmakuEntity.getY(),
                                    danmakuEntity.getZ(),
                                    new ItemStack(ModItems.POINT)
                            )
                    );
                }
                for (int i = 0; i < particleCount; i++) {
                    double angle = (2 * Math.PI / particleCount) * i;
                    double xOffset = radius * Math.cos(angle);
                    double zOffset = radius * Math.sin(angle);

                    ItemStackParticleEffect itemStackParticleEffect = new ItemStackParticleEffect(ParticleTypes.ITEM, ModItems.POINT.getDefaultStack());
                    serverWorld.spawnParticles(
                            itemStackParticleEffect,
                            pos.x,
                            pos.y,
                            pos.z,
                            1,
                            xOffset,
                            (heightOffset / particleCount) * i,
                            zOffset,
                            0.25
                    );
                }
                danmakuEntity.discard();
            });

            user.incrementStat(Stats.USED.getOrCreateStat(this));
            itemStack.decrementUnlessCreative(1, user);
            return ActionResult.SUCCESS_SERVER;
        }
        return ActionResult.SUCCESS;
    }
}
