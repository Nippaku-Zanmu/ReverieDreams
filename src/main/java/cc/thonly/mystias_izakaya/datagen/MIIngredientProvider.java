package cc.thonly.mystias_izakaya.datagen;

import cc.thonly.mystias_izakaya.datagen.generator.IngredientProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class MIIngredientProvider extends IngredientProvider {
    public MIIngredientProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> future) {
        super(output, future);
    }

    @Override
    public void configured() {

    }

    @Override
    public String getName() {
        return "Mystia's Izakaya MIIngredient Generator";
    }
}
