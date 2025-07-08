package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.component.ModDataComponentTypes;
import cc.thonly.reverie_dreams.entity.base.NPCEntity;
import cc.thonly.reverie_dreams.entity.npc.NPCRole;
import cc.thonly.reverie_dreams.item.base.BasicPolymerItem;
import cc.thonly.reverie_dreams.registry.RegistryManager;
import lombok.Getter;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.function.Consumer;

@Getter
public class RoleCardItem extends BasicPolymerItem {

    public RoleCardItem(Identifier id, Settings settings) {
        super(id, settings, Items.TRIAL_KEY);
    }

    public RoleCardItem(String path, Settings settings) {
        super(path, settings, Items.TRIAL_KEY);
    }

    public Optional<RoleCard> getRoleCardComponent(ItemStack itemStack) {
        Identifier identifier = itemStack.get(ModDataComponentTypes.ROLE_CARD_ID);
        if (identifier == null) {
            return Optional.empty();
        }
        RoleCard roleCard = RegistryManager.ROLE_CARD.get(identifier);
        if (roleCard == null) {
            return Optional.empty();
        }
        return Optional.of(roleCard);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient() && context.getWorld() instanceof ServerWorld serverWorld && context.getPlayer() instanceof ServerPlayerEntity player) {
            Hand hand = context.getHand();
            ItemStack itemStack = context.getStack();
            Optional<RoleCard> roleCardWrapper = this.getRoleCardComponent(itemStack);
            if (roleCardWrapper.isPresent()) {
                RoleCard roleCard = roleCardWrapper.get();
                Optional<NPCRole> roleWrapper = roleCard.random();
                if (roleWrapper.isPresent()) {
                    NPCRole role = roleWrapper.get();
                    itemStack.decrementUnlessCreative(1, player);
                    EntityType<NPCEntity> entityType = role.get();
                    entityType.spawn(serverWorld, context.getBlockPos().up(), SpawnReason.SPAWN_ITEM_USE);
                    SoundEvent sound = SoundEvents.ITEM_BUCKET_FILL;
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), sound, player.getSoundCategory(), 2.0f, 1.0f);
                    player.swingHand(hand);
                    return ActionResult.SUCCESS_SERVER;
                }
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
        Optional<RoleCard> roleCardComponent = this.getRoleCardComponent(stack);
        if (roleCardComponent.isEmpty()) {
            textConsumer.accept(Text.translatable("item.disabled"));
            return;
        }
        if (roleCardComponent.get().isEmpty()) {
            textConsumer.accept(Text.translatable("item.disabled"));
            return;
        }
        textConsumer.accept(Text.translatable("item.tooltip.use"));
    }
}
