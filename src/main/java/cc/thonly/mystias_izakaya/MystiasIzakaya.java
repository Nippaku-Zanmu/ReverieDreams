package cc.thonly.mystias_izakaya;

import cc.thonly.mystias_izakaya.block.MIBlocks;
import cc.thonly.mystias_izakaya.block.MiBlockEntities;
import cc.thonly.mystias_izakaya.component.MIDataComponentTypes;
import cc.thonly.mystias_izakaya.datafixer.MIDataFixer;
import cc.thonly.mystias_izakaya.impl.FoodPropertyCallback;
import cc.thonly.mystias_izakaya.item.MIItems;
import cc.thonly.mystias_izakaya.item.MiItemGroups;
import cc.thonly.mystias_izakaya.recipe.MiRecipeManager;
import cc.thonly.mystias_izakaya.registry.MIRegistrySchemas;
import cc.thonly.reverie_dreams.Touhou;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.api.ModInitializer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Setter
@Getter
public class MystiasIzakaya implements ModInitializer {
    public static final String MOD_NAME = "Mystias Izakaya";
    public static final String MOD_ID = Touhou.MOD_ID;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Loaded " + MOD_NAME);
        MIDataComponentTypes.init();
        MIBlocks.registerBlocks();
        MiBlockEntities.registerBlockEntities();
        MIItems.registerItems();
        MiItemGroups.registerItemGroups();
        MIRegistrySchemas.bootstrap();
        MiRecipeManager.bootstrap();
        MIDataFixer.bootstrap();
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
