package cc.thonly.touhoumod.interfaces;

import net.minecraft.component.ComponentMap;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeyedValue;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.Identifier;

public interface ItemSettingsAccessorImpl {
    public static RegistryKeyedValue<Item, String> BLOCK_PREFIXED_TRANSLATION_KEY() {
        throw new UnsupportedOperationException();
    }

    public static RegistryKeyedValue<Item, String> ITEM_PREFIXED_TRANSLATION_KEY() {
        throw new UnsupportedOperationException();
    }

    ComponentMap.Builder getComponents();

    Item getRecipeRemainder();

    FeatureSet getRequiredFeatures();

    RegistryKey<Item> getRegistryKey();

    RegistryKeyedValue<Item, String> getTranslationKey();

    RegistryKeyedValue<Item, Identifier> getModelId();
}
