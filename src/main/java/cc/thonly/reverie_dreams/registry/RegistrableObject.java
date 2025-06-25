package cc.thonly.reverie_dreams.registry;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;

public interface RegistrableObject<T extends RegistrableObject<T>> {
    public void setId(Identifier id);

    public Identifier getId();

    public Codec<T> getCodec();

    public default Boolean canReloadable() {
        return false;
    }
}
