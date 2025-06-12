package cc.thonly.reverie_dreams.item.base;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import eu.pb4.polymer.core.api.item.PolymerSpawnEggItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

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

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        super.useOnBlock(context);
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        Hand hand = context.getHand();
        if (!world.isClient() && player instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.swingHand(hand);
            return ActionResult.SUCCESS_SERVER;
        }
        return ActionResult.SUCCESS;
    }
}
