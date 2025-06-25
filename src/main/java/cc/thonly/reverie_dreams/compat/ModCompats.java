package cc.thonly.reverie_dreams.compat;

import cc.thonly.reverie_dreams.Touhou;
import lombok.extern.slf4j.Slf4j;
import net.fabricmc.loader.api.FabricLoader;

@Slf4j
public class ModCompats {
    public static void init() {
        load("polydex", PolydexCompatImpl::bootstrap);
        load("eiv", EIVCompatImpl::bootstrap);
        EIVCompatNetworkingImpl.bootstrap();
    }

    public static void load(String modId, CompatApplication application) {
        try {
            if (FabricLoader.getInstance().isModLoaded(modId)) {
                application.apply();
                Touhou.LOGGER.info("Loaded Compat for {}", modId);
            }
        } catch (Exception e) {
            log.warn("Can't load compat plugin", e);
        }
    }

    @FunctionalInterface
    public interface CompatApplication {
        void apply();
    }
}
