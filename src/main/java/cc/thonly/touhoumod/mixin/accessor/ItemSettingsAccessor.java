package cc.thonly.touhoumod.mixin.accessor;

import cc.thonly.touhoumod.interfaces.ItemSettingsAccessorImpl;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.component.ComponentMap;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeyedValue;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Item.Settings.class)
public interface ItemSettingsAccessor extends FabricItem.Settings, ItemSettingsAccessorImpl {
    @Accessor("BLOCK_PREFIXED_TRANSLATION_KEY")
    public static RegistryKeyedValue<Item, String> BLOCK_PREFIXED_TRANSLATION_KEY() {
        throw new UnsupportedOperationException();
    }

    @Accessor("ITEM_PREFIXED_TRANSLATION_KEY")
    public static RegistryKeyedValue<Item, String> ITEM_PREFIXED_TRANSLATION_KEY() {
        throw new UnsupportedOperationException();
    }

    @Accessor("components")
    public ComponentMap.Builder getComponents();

    @Accessor("recipeRemainder")
    @Nullable
    public Item getRecipeRemainder();

    @Accessor("requiredFeatures")
    public FeatureSet getRequiredFeatures();

    @Accessor("registryKey")
    @Nullable
    public RegistryKey<Item> getRegistryKey();

    @Accessor("translationKey")
    public RegistryKeyedValue<Item, String> getTranslationKey();

    @Accessor("modelId")
    public RegistryKeyedValue<Item, Identifier> getModelId();
}
