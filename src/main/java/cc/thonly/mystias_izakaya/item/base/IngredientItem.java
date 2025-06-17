package cc.thonly.mystias_izakaya.item.base;

import cc.thonly.mystias_izakaya.component.FoodProperty;
import cc.thonly.reverie_dreams.item.base.BasicPolymerItem;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IngredientItem extends BasicPolymerItem {
    public static final Map<Item, Set<FoodProperty>> ITEM_INGREDIENT_CACHED = new HashMap<>();

    public IngredientItem(String path, Settings settings) {
        super(path, settings, Items.APPLE);
    }

    public IngredientItem(String path, Integer nutrition, Float saturation, Settings settings) {
        this(path, settings.food(new FoodComponent.Builder().nutrition(nutrition + 2).saturationModifier(saturation + 1).build()));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        List<FoodProperty> foodProperties = FoodProperty.getIngredientProperties(this);
        if (!foodProperties.isEmpty()) {
            tooltip.add(Text.empty().append(Text.translatable("item.tooltip.food_properties")));
        }
        for (FoodProperty foodProperty : foodProperties) {
            tooltip.add(Text.empty().append(FoodProperty.getDisplayPrefix(this, foodProperty)).append(foodProperty.getTooltip()));
        }
    }

    public static boolean isIngredient(Item item) {
        return item instanceof IngredientItem || (!(item instanceof FoodItem) && !FoodProperty.getIngredientProperties(item).isEmpty());
    }

    public static boolean isIngredient(ItemStack item) {
        return item.getItem() instanceof IngredientItem || (!(item.getItem() instanceof FoodItem) && !FoodProperty.getIngredientProperties(item.getItem()).isEmpty());
    }
}
