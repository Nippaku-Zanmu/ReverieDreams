package cc.thonly.reverie_dreams;

import cc.thonly.mystias_izakaya.datagen.MIIngredientProvider;
import cc.thonly.reverie_dreams.datagen.*;
import cc.thonly.reverie_dreams.lang.LanguageMapper;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;

public class TouhouDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        LanguageMapper.mapInit();
        pack.addProvider(ModSimpChineseLangProvider::new);
        pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModRecipeProvider::new);
        pack.addProvider(ModBlockLootTableProvider::new);
        pack.addProvider(ModEntityLootTableProvider::new);
        pack.addProvider(ModEquipmentAssetProvider::new);
        pack.addProvider(ModRegistryDataGenerator::new);
        pack.addProvider(ModJukeboxProvider::new);
        pack.addProvider(ModSoundProvider::new);

        pack.addProvider(MIIngredientProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);
    }

}
