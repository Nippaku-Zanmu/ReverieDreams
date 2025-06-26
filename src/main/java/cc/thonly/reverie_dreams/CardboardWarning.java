package cc.thonly.reverie_dreams;

import com.mojang.logging.LogUtils;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;

import java.util.List;

public class CardboardWarning {
    public static final String MOD_NAME = "Reverie Dreams";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final List<String> BROKEN_BUKKIT_IMPL = List.of("cardboard", "banner", "arclight");

    public static final String BUKKIT_NAME;
    public static final boolean LOADED;

    static {
        var name = "";
        var loaded = false;
        for (var x : BROKEN_BUKKIT_IMPL) {
            var m = FabricLoader.getInstance().getModContainer(x);
            if (m.isPresent()) {
                name = m.get().getMetadata().getName() + " (" + x + ")";
                loaded = true;
                break;
            }
        }

        BUKKIT_NAME = name;
        LOADED = loaded;
    }

    public static void checkAndAnnounce() {
        if (LOADED) {
            LOGGER.error("==============================================");
            LOGGER.error("");
            LOGGER.error(BUKKIT_NAME + " detected! This mod is known to cause issues!");
            LOGGER.error(MOD_NAME + " might not work correctly because of it.");
            LOGGER.error("You won't get any support as long as it's present!");
            LOGGER.error("");
            LOGGER.error("Read more at: https://gist.github.com/Patbox/e44844294c358b614d347d369b0fc3bf");
            LOGGER.error("");
            LOGGER.error("==============================================");
        }
    }
}
