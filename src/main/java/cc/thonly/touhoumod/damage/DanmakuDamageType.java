package cc.thonly.touhoumod.damage;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class DanmakuDamageType {
    private final RegistryKey<DamageType> registryKey;

    public DanmakuDamageType() {
        this.registryKey = null;
    }

    public DanmakuDamageType(RegistryKey<DamageType> registryKey) {
        this.registryKey = registryKey;
    }

    public DamageSource mapToSource(DamageSources sources) {
        if (this.registryKey == null) {
            return null;
        }
        if (this.registryKey == DamageTypes.GENERIC) {
            return sources.generic();
        }
        if (this.registryKey == DamageTypes.MAGIC) {
            return sources.magic();
        }
        return sources.generic();
    }

    public RegistryKey<DamageType> getType() {
        return this.registryKey;
    }

    public DamageType getValue(DynamicRegistryManager registryManager) {
        if (this.registryKey == null) return null;
        Registry<DamageType> registry = registryManager.getOrThrow(RegistryKeys.DAMAGE_TYPE);
        return registry.get(this.registryKey);
    }

    @FunctionalInterface
    public interface SourceGetter {
        DamageSource getSource();
    }
}
