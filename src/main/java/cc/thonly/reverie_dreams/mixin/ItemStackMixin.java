package cc.thonly.reverie_dreams.mixin;

import cc.thonly.reverie_dreams.interfaces.IItemStack;
import cc.thonly.reverie_dreams.server.ItemDescriptionManager;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.component.ComponentHolder;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin<T> implements IItemStack,
        ComponentHolder,
        FabricItemStack {
    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract boolean isEmpty();

    @Inject(method = "getTooltip", at = @At("RETURN"), cancellable = true)
    public void appendTooltip(Item.TooltipContext context, @Nullable PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir) {
        if (this.isEmpty()) {
            return;
        }
        Item item = this.getItem();
        List<MutableText> texts = ItemDescriptionManager.get(item);
        try {
            if (!texts.isEmpty()) {
                List<Text> returnValue = cir.getReturnValue();
                returnValue.addAll(texts);
            }
        } catch (Exception ignored) {
        }
    }

    @Unique
    @Override
    public boolean isFood() {
        ComponentMap components = this.getComponents();
        return components.get(DataComponentTypes.FOOD) != null;
    }
}
