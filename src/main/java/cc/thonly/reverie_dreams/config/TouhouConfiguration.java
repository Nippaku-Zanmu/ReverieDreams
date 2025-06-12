package cc.thonly.reverie_dreams.config;

import eu.midnightdust.lib.config.MidnightConfig;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TouhouConfiguration extends MidnightConfig {
    @Comment(name = "Configuration Version")
    @Entry
    public static int CONFIG_VERSION = 1;

    @Comment(name = "Enable Debug Mode")
    @Entry
    public static boolean DEBUG_MODE = false;
}
