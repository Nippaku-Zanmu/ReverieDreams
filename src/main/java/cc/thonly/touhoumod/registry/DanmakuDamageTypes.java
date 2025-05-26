package cc.thonly.touhoumod.registry;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.damage.DanmakuDamageType;
import net.minecraft.entity.damage.DamageTypes;

public class DanmakuDamageTypes {
    public static final DanmakuDamageType GENERIC_TYPE = RegistrySchemas.register(RegistrySchemas.DANMAKU_DAMAGE_TYPE, Touhou.id("generic"), new DanmakuDamageType(DamageTypes.GENERIC));
    public static void bootstrap(RegistrySchema<DanmakuDamageType> registry) {
        registry.defaultEntry(DanmakuDamageTypes.GENERIC_TYPE);
    }
}
