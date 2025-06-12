package cc.thonly.mystias_izakaya;

import cc.thonly.mystias_izakaya.block.MIBlocks;
import cc.thonly.mystias_izakaya.component.MIDataComponentTypes;
import cc.thonly.mystias_izakaya.item.MIItems;
import cc.thonly.mystias_izakaya.item.MiItemGroups;
import cc.thonly.mystias_izakaya.registry.MIRegistrySchemas;
import cc.thonly.reverie_dreams.Touhou;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.api.ModInitializer;
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
        MIItems.registerItems();
        MiItemGroups.registerItemGroups();
        MIRegistrySchemas.bootstrap();

    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
