package cc.thonly.touhoumod.item.base;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.util.IdentifierGetter;
import eu.pb4.polymer.core.api.item.PolymerSpawnEggItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

@Setter
@Getter
@ToString
public class BasicPolymerSpawnEggItem extends PolymerSpawnEggItem implements IdentifierGetter {

    private final Identifier identifier;

    public BasicPolymerSpawnEggItem(String identifier, EntityType<? extends MobEntity> type, Settings settings) {
        super(type, settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, Touhou.id(identifier))));
        this.identifier = Touhou.id(identifier);
    }

    public BasicPolymerSpawnEggItem(String identifier, EntityType<? extends MobEntity> type, Item polymerItem, Settings settings) {
        super(type, polymerItem, settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, Touhou.id(identifier))));
        this.identifier = Touhou.id(identifier);
    }

    public BasicPolymerSpawnEggItem(String identifier, EntityType<? extends MobEntity> type, Item polymerItem, boolean useModel, Settings settings) {
        super(type, polymerItem, useModel, settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, Touhou.id(identifier))));
        this.identifier = Touhou.id(identifier);
    }

    public BasicPolymerSpawnEggItem(Identifier identifier, EntityType<? extends MobEntity> type, Settings settings) {
        super(type, settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, identifier)));
        this.identifier = identifier;
    }

    public BasicPolymerSpawnEggItem(Identifier identifier, EntityType<? extends MobEntity> type, Item polymerItem, Settings settings) {
        super(type, polymerItem, settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, identifier)));
        this.identifier = identifier;
    }

    public BasicPolymerSpawnEggItem(Identifier identifier, EntityType<? extends MobEntity> type, Item polymerItem, boolean useModel, Settings settings) {
        super(type, polymerItem, useModel, settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, identifier)));
        this.identifier = identifier;
    }
}
