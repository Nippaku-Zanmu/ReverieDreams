package cc.thonly.mystias_izakaya.item.base;

import cc.thonly.mystias_izakaya.component.FoodProperty;
import cc.thonly.reverie_dreams.item.base.BasicPolymerItem;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;

public class IngredientItem extends BasicPolymerItem {
    public IngredientItem(String path, Settings settings) {
        super(path, settings, Items.APPLE);
    }

    public IngredientItem(String path, Integer nutrition, Float saturation, Settings settings) {
        this(path, settings.food(new FoodComponent.Builder().nutrition(nutrition).saturationModifier(saturation).build()));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        List<FoodProperty> foodProperties = FoodProperty.getFromItemStack(stack);
        tooltip.add(Text.empty());
        for (FoodProperty foodProperty : foodProperties) {
            tooltip.add(Text.empty().append(foodProperty.getTooltip()));
        }

    }
}
