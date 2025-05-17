package cc.thonly.touhoumod.config;

import eu.midnightdust.lib.config.MidnightConfig;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TouhouConfiguration extends MidnightConfig {
    @Entry @Hidden public static int configVersion = 1;
}
