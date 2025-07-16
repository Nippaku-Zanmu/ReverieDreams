package cc.thonly.reverie_dreams.entity.skin;

import cc.thonly.reverie_dreams.registry.RegistrableObject;
import com.mojang.authlib.properties.Property;
import com.mojang.serialization.Codec;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.Identifier;


public class RoleSkin implements RegistrableObject<RoleSkin> {
    public static final Codec<RoleSkin> NULL_CODEC = Codec.unit(RoleSkin::new);
    public static final Codec<RoleSkin> CODEC = null;
    @Setter
    @Getter
    private Identifier id;
    private String value;
    private String signature;
    private Property instance;

    private RoleSkin() {
    }

    public RoleSkin(Identifier id, String value, String signature) {
        this.id = id;
        this.value = value;
        this.signature = signature;
        this.valid();
    }

    public Property get() {
        if (this.instance == null) {
            this.instance = texture(this.value, this.signature);
        }
        return this.instance;
    }

    private void valid() {
        Property property = this.get();

    }

    private static Property texture(String value, String signature) {
        return new Property("textures", value, signature);
    }

    @Override
    public Codec<RoleSkin> getCodec() {
        return NULL_CODEC;
    }
}
