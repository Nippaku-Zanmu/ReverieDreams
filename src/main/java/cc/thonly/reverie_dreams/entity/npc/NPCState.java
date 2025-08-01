package cc.thonly.reverie_dreams.entity.npc;

import cc.thonly.reverie_dreams.registry.RegistrableObject;
import com.mojang.serialization.Codec;
import lombok.Getter;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Getter
public class NPCState implements RegistrableObject<NPCState> {
    public static final Codec<NPCState> CODEC = Codec.unit(NPCState::new);

    private Identifier id;
    private final String type;

    private NPCState() {
        this.type = null;
    }

    public NPCState(String type) {
        this.type = type;
    }

    public String translationId() {
        return "gui.npc.mode." + this.type;
    }

    public MutableText translationKey() {
        return Text.translatable(translationId());
    }

    @Override
    public void setId(Identifier id) {
        this.id = id;
    }

    @Override
    public Codec<NPCState> getCodec() {
        return CODEC;
    }
}