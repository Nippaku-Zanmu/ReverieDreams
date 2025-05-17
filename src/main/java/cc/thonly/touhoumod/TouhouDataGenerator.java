package cc.thonly.touhoumod;

import cc.thonly.touhoumod.datagen.*;
import cc.thonly.touhoumod.lang.LanguageMapper;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.List;

public class TouhouDataGenerator implements DataGeneratorEntrypoint {
    public static final String ASSETS = "assets";
    public static final String DATA = "data";
    public static String OUTPUT_DIR;

    static {
        try {
            Class<?> clazz = Class.forName("net.fabricmc.fabric.impl.datagen.FabricDataGenHelper");
            Field field = clazz.getDeclaredField("OUTPUT_DIR");
            field.setAccessible(true);
            OUTPUT_DIR = (String) field.get(null);
        } catch (Exception err) {
            OUTPUT_DIR = null;
            err.printStackTrace();
        }
    }

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
        pack.addProvider(ModRegistryDataGenerator::new);
        pack.addProvider(ModJukeboxProvider::new);
        pack.addProvider(ModSoundProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);
    }

    public static Path getAssets(Path output, String namespace, RegistryKey<?> key, @Nullable List<String> other) {
        return getOutput(output, namespace, key, other, ASSETS);
    }

    public static Path getData(Path output, String namespace, RegistryKey<?> key, @Nullable List<String> other) {
        return getOutput(output, namespace, key, other, DATA);
    }

    public static Path getOutput(Path output, String namespace, RegistryKey<?> key, @Nullable List<String> other, String type) {
        Path base = output.resolve(type).resolve(namespace);
        if (key != null) {
            base = base.resolve(key.getValue().getPath());
        }
        if (other != null) {
            for (String string : other) {
                base = base.resolve(string);
            }
        }
        return base;
    }
}
