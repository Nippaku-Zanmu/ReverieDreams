package cc.thonly.reverie_dreams.registry;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;

public interface SchemaObject<T extends SchemaObject<T>> {
    void setId(Identifier id);
    Identifier getId();
    Codec<T> getCodec();
}
