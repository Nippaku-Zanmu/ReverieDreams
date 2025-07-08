package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.component.ModDataComponentTypes;
import cc.thonly.reverie_dreams.entity.npc.NPCRole;
import cc.thonly.reverie_dreams.registry.RegistrableObject;
import com.mojang.serialization.Codec;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

import java.util.*;

public class RoleCard implements RegistrableObject<RoleCard> {
    public static final Codec<RoleCard> CODEC = Codec.unit(RoleCard::new);
    public static final Long DEFAULT_COLOR = 16777215L;
    @Setter
    @Getter
    private Identifier id;
    private Identifier itemId;
    private Long color = DEFAULT_COLOR;
    private final List<NPCRole> entries = new LinkedList<>();

    private RoleCard() {
    }

    public RoleCard(Identifier id, Long color, List<NPCRole> roles) {
        this.id = id;
        this.color = color;
        this.of(roles);
    }

    public RoleCard(Identifier id, Long color, NPCRole... roles) {
        this.id = id;
        this.color = color;
        this.of(roles);
    }

    public RoleCard of(List<NPCRole> roles) {
        for (NPCRole role : roles) {
            if (!this.entries.contains(role)) {
                this.entries.add(role);
            }
        }
        return this;
    }

    public RoleCard of(NPCRole... roles) {
        return this.of(Arrays.asList(roles));
    }

    public ItemStack itemStack() {
        ItemStack itemStack = new ItemStack(ModItems.ROLE_CARD, 1);
        itemStack.set(DataComponentTypes.ITEM_NAME, Text.translatable(this.translationKey()));
        itemStack.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(this.color.intValue()));
        itemStack.set(ModDataComponentTypes.ROLE_CARD_ID, this.getId());
        return itemStack;
    }

    public Optional<NPCRole> random() {
        if (this.entries.isEmpty()) return Optional.empty();
        return Optional.ofNullable(this.entries.get(Random.create().nextInt(this.entries.size())));
    }

    public RoleCard build() {
        this.itemId = Identifier.of(this.id.getNamespace(), this.id.getPath() + "_role_card");
        return this;
    }

    public String translationKey() {
        return this.itemId.toTranslationKey();
    }

    public boolean isEmpty() {
        return this.entries.isEmpty();
    }

    @Override
    public Codec<RoleCard> getCodec() {
        return CODEC;
    }
}
