package cc.thonly.reverie_dreams.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.FeatureConfig;

public record DreamGridFeatureConfig(Identifier blockId) implements FeatureConfig {
    public static final Codec<DreamGridFeatureConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Identifier.CODEC.fieldOf("block").forGetter(config -> config.blockId)
            ).apply(instance, DreamGridFeatureConfig::new)
    );
}
