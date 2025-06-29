package cc.thonly.mystias_izakaya.item.base;

import cc.thonly.mystias_izakaya.component.FoodProperty;
import cc.thonly.reverie_dreams.item.base.BasicPolymerItem;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.*;
import java.util.function.Consumer;

public class FoodItem extends BasicPolymerItem {

    public FoodItem(String path, Settings settings) {
        super(path, settings, Items.APPLE);
    }

    public FoodItem(String path, List<FoodProperty> foodProperties, Settings settings) {
        super(path, settings, Items.APPLE);
    }

    public FoodItem(String path, List<FoodProperty> foodProperties, Integer nutrition, Float saturation, Settings settings) {
        this(path, settings.food(new FoodComponent.Builder().nutrition(nutrition + 2).saturationModifier(saturation + 2).build()));
    }

    public FoodItem(String path, Integer nutrition, Float saturation, Settings settings) {
        this(path, settings.food(new FoodComponent.Builder().nutrition(nutrition + 2).saturationModifier(saturation + 2).build()));
    }

    public static final Map<ItemStack, List<FoodProperty>> CACHED_ITEM2FOOD = new WeakHashMap<>();

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient() && world instanceof ServerWorld serverWorld) {
            Set<FoodProperty> foodProperties = new HashSet<>(FoodProperty.getFromItemStack(stack));
            Set<FoodProperty> foodPropertiesFromComponent = new HashSet<>(FoodProperty.getFromItemStackComponent(stack));

            Set<FoodProperty> allProperties = new HashSet<>(foodProperties);
            allProperties.addAll(foodPropertiesFromComponent);

            for (FoodProperty foodProperty : allProperties) {
                foodProperty.use(serverWorld, user);
            }
            if (user instanceof ServerPlayerEntity player) {
                HungerManager hungerManager = player.getHungerManager();
                allProperties.forEach((property) -> hungerManager.add(1, 0));
            }
        }
        return super.finishUsing(stack, world, user);
    }


    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
        List<FoodProperty> foodProperties = FoodProperty.getFromItemStackComponent(stack);
        List<FoodProperty> foodIngredientProperties = FoodProperty.getIngredientProperties(this);
        Set<FoodProperty> foodPropertyList = new HashSet<>();
        foodPropertyList.addAll(foodProperties);
        foodPropertyList.addAll(foodIngredientProperties);
        if (!foodPropertyList.isEmpty()) {
            textConsumer.accept(Text.empty().append(Text.translatable("item.tooltip.food_properties")));
        }
        for (FoodProperty foodProperty : foodPropertyList) {
            textConsumer.accept(Text.empty().append(FoodProperty.getDisplayPrefix(this, foodProperty)).append(foodProperty.getTooltip()));
        }

    }
}
