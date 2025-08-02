package cc.thonly.reverie_dreams.engine;

import cc.thonly.reverie_dreams.registry.RegistrableObject;
import com.mojang.serialization.Codec;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.util.Identifier;

@Setter
@Getter
@Slf4j
public class JavaScriptElement implements RegistrableObject<JavaScriptElement> {
    public static final Codec<JavaScriptElement> CODEC = Codec.unit(JavaScriptElement::new);
    private Identifier id;
    private final String src;

    private JavaScriptElement() {
        this("");
    }

    public JavaScriptElement(String src) {
        this.src = src;
    }

    public boolean shell() {
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Codec<JavaScriptElement> getCodec() {
        return CODEC;
    }
}
