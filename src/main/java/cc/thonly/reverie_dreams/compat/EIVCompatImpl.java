package cc.thonly.reverie_dreams.compat;

import cc.thonly.reverie_dreams.Touhou;
import de.crafty.eiv.common.CommonEIVClient;
import de.crafty.eiv.common.recipe.*;
import eu.pb4.polymer.core.api.other.PolymerScreenHandlerUtils;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.rsm.api.RegistrySyncUtils;
import lombok.extern.slf4j.Slf4j;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SuppressWarnings("unchecked")
// Until EIV Fixed Registry Sync
public class EIVCompatImpl {
    public static void bootstrap() {
        ItemViewRecipes instance = ItemViewRecipes.INSTANCE;
        try {
            Class<ItemViewRecipes> clazz = ItemViewRecipes.class;
            Field field = clazz.getDeclaredField("fluidItemMap");
            field.setAccessible(true);
            Object object = field.get(instance);
            HashMap<Fluid, Item> fluidItemMap = (HashMap<Fluid, Item>) object;
            for (Map.Entry<Fluid, Item> entry : fluidItemMap.entrySet()) {
                Item item = entry.getValue();
                RegistrySyncUtils.setServerEntry(Registries.ITEM, item);
            }
            ScreenHandlerType<?> screenHandlerType = Registries.SCREEN_HANDLER.get(Identifier.of("eiv:recipe_view"));
            if (screenHandlerType != null) {
                RegistrySyncUtils.setServerEntry(Registries.SCREEN_HANDLER, screenHandlerType);
            }
            if (Touhou.ENV_TYPE == EnvType.CLIENT) {
                PolymerScreenHandlerUtils.registerType(CommonEIVClient.RECIPE_VIEW_MENU);
            }
            PolymerResourcePackUtils.addModAssets("eiv");
        } catch (Exception e) {
            log.error("Can't make reflection for EIV");
        }
    }
}
