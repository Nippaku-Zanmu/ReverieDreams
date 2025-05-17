package cc.thonly.touhoumod.compat;

import net.fabricmc.loader.api.FabricLoader;

public class ModCompats {
    public static void init() {
        try {
            if (FabricLoader.getInstance().isModLoaded("polydex")) {
                PolydexCompatImpl.bootstrap();
//                ServerLifecycleEvents.SERVER_STARTED.register(PolydexCompatImpl::bootstrap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
