package cc.thonly.touhoumod.recipe.slot;

import cc.thonly.touhoumod.interfaces.ComponentMapBuilderImpl;
import cc.thonly.touhoumod.interfaces.ItemSettingsAccessorImpl;
import cc.thonly.touhoumod.interfaces.ItemStackImpl;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
public class CountRecipeSlot extends RecipeSlot {
    public static final Codec<CountRecipeSlot> CODEC = RecordCodecBuilder.create(x -> x.group(
            Registries.ITEM.getCodec().optionalFieldOf("item", Items.AIR).forGetter(CountRecipeSlot::getItem),
            Codec.INT.optionalFieldOf("count",1).forGetter(CountRecipeSlot::getCount),
            ComponentChanges.CODEC.optionalFieldOf("components", ComponentChanges.EMPTY).forGetter(CountRecipeSlot::getChanges)
    ).apply(x, (item,count,changes) -> {
        CountRecipeSlot recipeSlot = new CountRecipeSlot(item);
        recipeSlot.itemSettings = new Item.Settings();
        Set<Map.Entry<ComponentType<?>, Optional<?>>> entries = changes.entrySet();
        for (var componentType: entries) {
            ComponentType<Object> key = (ComponentType<Object>) componentType.getKey();
            Optional<Object> valueOpt = (Optional<Object>) componentType.getValue();
            valueOpt.ifPresent(value -> recipeSlot.itemSettings.component(key, value));
        }
        return recipeSlot;
    }));

    protected int count = 1;


    public CountRecipeSlot(Item item) {
        super(item != null ? item : Items.AIR, null);
    }

    public CountRecipeSlot(Item item, int count) {
        super(item != null ? item : Items.AIR, null);
        this.count = count;
    }

    public CountRecipeSlot(Item item, int count, Item.Settings itemSettings) {
        super(item != null ? item : Items.AIR, itemSettings);
        this.count = count;
    }

    public ItemStack getStack() {
        ItemStack itemStack = new ItemStack(this.item, this.count);
        ItemSettingsAccessorImpl itemSettings = this.getItemSettings();
        if (this.getItemSettings() != null) {
            ComponentMap.Builder components = itemSettings.getComponents();
            ComponentMapBuilderImpl componentsImpl = (ComponentMapBuilderImpl) (Object) components;
            for (var set : componentsImpl.getComponents().entrySet()) {
                ComponentType<?> key = set.getKey();
                Object value = set.getValue();
                ItemStackImpl.setComponentSafe(itemStack, key, value);
            }
        }
        return itemStack;
    }

    public int getCount() {
        return this.getItem().equals(Items.AIR) ? 0 : count;
    }
}
