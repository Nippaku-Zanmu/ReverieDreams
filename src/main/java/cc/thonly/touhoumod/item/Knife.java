package cc.thonly.touhoumod.item;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.component.ModDataComponentTypes;
import cc.thonly.touhoumod.entity.DanmakuEntity;
import cc.thonly.touhoumod.entity.ModEntities;
import cc.thonly.touhoumod.entity.ModEntityHolders;
import cc.thonly.touhoumod.item.base.BasicPolymerDanmakuItem;
import cc.thonly.touhoumod.item.base.BasicPolymerSwordItem;
import cc.thonly.touhoumod.item.base.UsableDanmaku;
import cc.thonly.touhoumod.item.base.UsingDanmaku;
import cc.thonly.touhoumod.item.entry.DanmakuItemEntries;
import cc.thonly.touhoumod.registry.RegistryLists;
import cc.thonly.touhoumod.sound.ModSoundEvents;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.component.Component;
import net.minecraft.component.ComponentMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
@Setter
@Getter
public class Knife extends BasicPolymerSwordItem implements UsableDanmaku {

    public Knife(String path, float attackDamage, float attackSpeed, Settings settings) {
        super(
                path,
                ToolMaterial.IRON,
                attackDamage + 3.0f,
                attackSpeed - 2f,
                settings
                        .maxCount(1)
                        .useCooldown(1.5f)
                        .component(ModDataComponentTypes.Danmaku.TEMPLATE, Touhou.id("single").toString())
                        .component(ModDataComponentTypes.Danmaku.DAMAGE, 2.0f)
                        .component(ModDataComponentTypes.Danmaku.SPEED, 0.5f)
                        .component(ModDataComponentTypes.Danmaku.SCALE, 0.8f)
                        .component(ModDataComponentTypes.Danmaku.COUNT, 1)
                        .component(ModDataComponentTypes.Danmaku.TILE, false)
                        .component(ModDataComponentTypes.Danmaku.INFINITE, false)
        );
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack heldItemStack = user.getStackInHand(hand);
        ItemStack itemStack = new ItemStack(ModEntityHolders.KNIFE_DISPLAY);
        ComponentMap components = heldItemStack.getComponents();
        Iterator<Component<?>> iterator = components.stream().iterator();
        while (iterator.hasNext()) {
            Component<Object> next = (Component<Object>) iterator.next();
            itemStack.set(next.type(), next.value());
        }
        Boolean isInfinite = itemStack.getOrDefault(ModDataComponentTypes.Danmaku.INFINITE, false);
        if (!world.isClient && world instanceof ServerWorld serverWorld) {
            for (int i = 0; i < itemStack.getOrDefault(ModDataComponentTypes.Danmaku.COUNT, 1); i++) {
                this.shoot(serverWorld, user, hand);
            }
            if (!isInfinite) {
                itemStack.damage(1, user);
            }

            world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSoundEvents.FIRE, SoundCategory.NEUTRAL, 1f, 1.0f);
            return ActionResult.SUCCESS_SERVER;
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return ActionResult.SUCCESS;
    }

    public void shoot(ServerWorld serverWorld, PlayerEntity user, Hand hand) {
        this.spawn(serverWorld, user, hand);
    }

    public void spawn(ServerWorld serverWorld, PlayerEntity user, Hand hand) {
        ItemStack heldItemStack = user.getStackInHand(hand);
        ItemStack itemStack = new ItemStack(ModEntityHolders.KNIFE_DISPLAY);
        ComponentMap components = heldItemStack.getComponents();
        Iterator<Component<?>> iterator = components.stream().iterator();
        while (iterator.hasNext()) {
            Component<Object> next = (Component<Object>) iterator.next();
            itemStack.set(next.type(), next.value());
        }
        ItemStack stack = itemStack.copy();
        float pitch = user.getPitch();
        float yaw = user.getYaw();

        Item item = stack.getItem();

            DanmakuEntity danmakuEntity = new DanmakuEntity(
                    (LivingEntity) user,
                    stack.copy(),
                    hand,
                    item,
                    pitch,
                    yaw,
                    1.4f,
                    5.0f,
                    0.4f,
                    false
            );
            serverWorld.spawnEntity(danmakuEntity);

    }
}
