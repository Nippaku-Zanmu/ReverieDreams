package cc.thonly.reverie_dreams.recipe.slot;

import cc.thonly.reverie_dreams.interfaces.ComponentMapBuilderImpl;
import cc.thonly.reverie_dreams.interfaces.ItemSettingsAccessorImpl;
import cc.thonly.reverie_dreams.interfaces.ItemStackImpl;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@Getter
@Setter
@ToString
public class RecipeSlot {
    @NotNull
    protected Item item = Items.AIR;
    @Nullable
    protected Item.Settings itemSettings;
    @Nullable
    protected ComponentChanges changes;

    public RecipeSlot(Item item, @Nullable Item.Settings itemSettings) {
        this.item = item != null ? item : Items.AIR;
        this.itemSettings = itemSettings;
    }

    public @NotNull Item getItem() {
        return this.item;
    }

    public ItemSettingsAccessorImpl getItemSettings() {
        return (this.itemSettings != null) ? ((ItemSettingsAccessorImpl) (Object) this.itemSettings) : ((ItemSettingsAccessorImpl) (Object) new Item.Settings());
    }

    public ItemStack getStack() {
        ItemStack itemStack = new ItemStack(this.item);
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

    public RecipeSlot setItemSettings(Item.Settings itemSettings) {
        this.itemSettings = itemSettings;
        return this;
    }

}
