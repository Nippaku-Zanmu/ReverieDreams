package cc.thonly.reverie_dreams;

import lombok.extern.slf4j.Slf4j;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

@Slf4j
public class TouhouEarly implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        try {
            Class.forName("cc.thonly.reverie_dreams.util.FKMod");
        } catch (ClassNotFoundException e) {
            log.error("Can't preload", e);
        }
    }
}
