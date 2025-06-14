package cc.thonly.mystias_izakaya.mixin;

import cc.thonly.mystias_izakaya.component.FoodProperty;
import cc.thonly.mystias_izakaya.item.base.IngredientItem;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.resource.featuretoggle.ToggleableFeature;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public abstract class ItemMixin implements ToggleableFeature, ItemConvertible, FabricItem {
    @Inject(method = "appendTooltip", at = @At("HEAD"))
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, CallbackInfo ci) {
        Item item = this.asItem();
        if (item instanceof IngredientItem) {
            return;
        }
        List<FoodProperty> foodProperties = FoodProperty.getIngredientProperties(item);
        if (!foodProperties.isEmpty()) {
            tooltip.add(Text.empty().append(Text.translatable("item.tooltip.food_properties")));
        }
        for (FoodProperty foodProperty : foodProperties) {
            tooltip.add(Text.empty().append(FoodProperty.getDisplayPrefix(item, foodProperty)).append(foodProperty.getTooltip()));
        }
    }
}
