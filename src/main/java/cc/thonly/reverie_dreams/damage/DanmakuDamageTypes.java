package cc.thonly.reverie_dreams.damage;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.registry.RegistrySchema;
import cc.thonly.reverie_dreams.registry.RegistrySchemas;
import net.minecraft.entity.damage.DamageTypes;

public class DanmakuDamageTypes {
    public static final DanmakuDamageType GENERIC_TYPE = RegistrySchemas.register(RegistrySchemas.DANMAKU_DAMAGE_TYPE, Touhou.id("generic"), new DanmakuDamageType(DamageTypes.GENERIC));

    public static void bootstrap(RegistrySchema<DanmakuDamageType> registry) {
        registry.defaultEntry(() -> DanmakuDamageTypes.GENERIC_TYPE);
    }
}
