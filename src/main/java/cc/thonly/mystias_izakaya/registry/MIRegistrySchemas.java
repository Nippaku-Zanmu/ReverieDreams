package cc.thonly.mystias_izakaya.registry;

import cc.thonly.mystias_izakaya.MystiasIzakaya;
import cc.thonly.mystias_izakaya.component.FoodProperty;
import cc.thonly.reverie_dreams.registry.RegistrySchema;
import cc.thonly.reverie_dreams.registry.RegistrySchemas;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MIRegistrySchemas extends RegistrySchemas {
    public static final RegistrySchema<FoodProperty> FOOD_PROPERTY = ofEntry(FoodProperty.class, MystiasIzakaya.id("food_property"))
            .codec(FoodProperty.CODEC)
            .reloadable(FoodProperties::reload)
            .build(FoodProperties::bootstrap);

    public static void bootstrap() {
        FOOD_PROPERTY.apply();
    }
}
