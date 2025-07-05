package cc.thonly.mystias_izakaya.entity;

import cc.thonly.mystias_izakaya.MystiasIzakaya;
import cc.thonly.reverie_dreams.entity.ModEntities;
import cc.thonly.reverie_dreams.item.base.BasicPolymerSpawnEggItem;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import eu.pb4.polymer.core.api.entity.PolymerEntityUtils;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BiomeTags;

@SuppressWarnings("unchecked")
public class MIEntities {
    public static final EntityType<WildPigEntity> WILD_PIG_ENTITY_TYPE =
            registerEntityNotSpawnEgg("wild_pig",
                    EntityType.Builder.<WildPigEntity>create(WildPigEntity::new, SpawnGroup.MONSTER)
                            .build(of("wild_pig")),
                    () -> AnimalEntity.createAnimalAttributes()
                            .add(EntityAttributes.MAX_HEALTH, 20.0)
                            .add(EntityAttributes.FLYING_SPEED, 0.6f)
                            .add(EntityAttributes.MOVEMENT_SPEED, 0.3f)
                            .add(EntityAttributes.ATTACK_DAMAGE, 2.0)
                            .add(EntityAttributes.SCALE, 1.5f)
                            .build());

    public static void init() {
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(ConventionalBiomeTags.IS_FOREST),
                SpawnGroup.MONSTER,
                MIEntities.WILD_PIG_ENTITY_TYPE,
                10,
                2,
                4
        );
    }

    private static RegistryKey<EntityType<?>> of(String name) {
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, MystiasIzakaya.id(name));
    }

    private static <T extends Entity> EntityType<T> registerEntity(String path, EntityType<T> entityType) {
        EntityType<T> entityTypeRef = Registry.register(Registries.ENTITY_TYPE, MystiasIzakaya.id(path), entityType);
        PolymerEntityUtils.registerType(entityTypeRef);
        return entityTypeRef;
    }

    private static <T extends Entity> EntityType<T> registerEntityNotSpawnEgg(String path, EntityType<T> entityType, ModEntities.CreateAttributesFunction createAttributesFunction) {
        EntityType<T> entityTypeRef = Registry.register(Registries.ENTITY_TYPE, MystiasIzakaya.id(path), entityType);
        FabricDefaultAttributeRegistry.register((EntityType<? extends MobEntity>) entityTypeRef, createAttributesFunction.apply());
        Item item = registerSpawnEggItem(new BasicPolymerSpawnEggItem(path + "_spawn_egg", (EntityType<? extends MobEntity>) entityTypeRef, new Item.Settings().modelId(MystiasIzakaya.id("spawn_egg"))));
        PolymerEntityUtils.registerType(entityTypeRef);
        ModEntities.SPAWN_EGG_ITEM_LIST.add(item);
        return entityTypeRef;
    }

    public static Item registerSpawnEggItem(IdentifierGetter item) {
        Registry.register(Registries.ITEM, item.getIdentifier(), (Item) item);
        ModEntities.SPAWN_EGG_ITEM_LIST.add((Item) item);
        return (Item) item;
    }

    @FunctionalInterface
    public interface CreateAttributesFunction {
        DefaultAttributeContainer apply();
    }
}
