package cc.thonly.reverie_dreams.datagen;

import cc.thonly.reverie_dreams.data.ModTags;
import cc.thonly.reverie_dreams.registry.RegistryManager;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModEntityTagProvider extends FabricTagProvider<EntityType<?>> {

    public ModEntityTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ENTITY_TYPE, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        FabricTagProvider<EntityType<?>>.FabricTagBuilder roleBuilder = getOrCreateTagBuilder(ModTags.EntityTag.NPC_ROLE);

        RegistryManager.NPC_ROLE.values().forEach(role -> roleBuilder.add(role.getEntityType()));
    }
}
