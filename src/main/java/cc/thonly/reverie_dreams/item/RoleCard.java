package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.component.ModDataComponentTypes;
import cc.thonly.reverie_dreams.datagen.generator.RecipeTypeProvider;
import cc.thonly.reverie_dreams.entity.npc.NPCRole;
import cc.thonly.reverie_dreams.item.base.BasicPolymerSpawnEggItem;
import cc.thonly.reverie_dreams.recipe.ItemStackRecipeWrapper;
import cc.thonly.reverie_dreams.recipe.entry.GensokyoAltarRecipe;
import cc.thonly.reverie_dreams.registry.RegistrableObject;
import com.mojang.serialization.Codec;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.Item;
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
        return itemStack.copy();
    }

    public Optional<NPCRole> random() {
        if (this.entries.isEmpty()) return Optional.empty();
        return Optional.ofNullable(this.entries.get(Random.create().nextInt(this.entries.size())));
    }

    public RoleCard build() {
        this.itemId = Identifier.of(this.id.getNamespace(), this.id.getPath() + "_role_card");
        for (NPCRole entry : this.entries) {
            Item egg = entry.getEgg();
            if (egg instanceof BasicPolymerSpawnEggItem eggItem) {
                eggItem.setColor(this.color);
            }
        }
        return this;
    }

    public String translationKey() {
        return this.itemId.toTranslationKey();
    }

    public boolean isEmpty() {
        return this.entries.isEmpty();
    }

    public RecipeBuilder createRecipeBuilder() {
        return new RecipeBuilder(this);
    }

    @Override
    public Codec<RoleCard> getCodec() {
        return CODEC;
    }

    @Getter
    public static class RecipeBuilder {
        private static final int[] INDEXES = new int[]{0, 1, 3, 4, 6, 7};
        private static final int[] POINT_INDEXES = new int[]{0, 1, 3};
        private static final int[] POWER_INDEXES = new int[]{4, 6, 7};
        private static final int[] EMPTY_INDEXES = new int[]{2, 5};
        private final RoleCard roleCard;
        private final List<ItemStackRecipeWrapper> itemStackRecipeWrappers;
        private GensokyoAltarRecipe result;
        private int plus = 0;

        public RecipeBuilder(RoleCard roleCard) {
            this.roleCard = roleCard;
            this.itemStackRecipeWrappers = new ArrayList<>();
        }

        public RecipeBuilder plus() {
            return this.plus(1);
        }

        public RecipeBuilder plus(int value) {
            this.plus += value;
            return this;
        }

        public RecipeBuilder itemStack(ItemStackRecipeWrapper... recipeWrappers) {
            Collections.addAll(this.itemStackRecipeWrappers, recipeWrappers);
            return this;
        }

        public RecipeBuilder itemStack(List<ItemStackRecipeWrapper> recipeWrappers) {
            this.itemStackRecipeWrappers.addAll(recipeWrappers);
            return this;
        }

        public RecipeBuilder build() {
            List<ItemStackRecipeWrapper> wrappers = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                wrappers.add(ItemStackRecipeWrapper.empty());
            }
            for (int i = 0; i < POINT_INDEXES.length; i++) {
                int pointIndex = POINT_INDEXES[i];
                wrappers.set(pointIndex, ItemStackRecipeWrapper.of(ModItems.POINT, 14 + i + this.plus));
            }
            for (int i = 0; i < POWER_INDEXES.length; i++) {
                int powerIndex = POWER_INDEXES[i];
                wrappers.set(powerIndex, ItemStackRecipeWrapper.of(ModItems.POWER, 18 + i + this.plus));
            }
            PrimitiveIterator.OfInt iterator = Arrays.stream(EMPTY_INDEXES).iterator();
            for (ItemStackRecipeWrapper itemStackRecipeWrapper : this.itemStackRecipeWrappers) {
                if (!iterator.hasNext()) continue;
                Integer next = iterator.next();
                wrappers.set(next, itemStackRecipeWrapper);
            }

            this.result = new GensokyoAltarRecipe(
                    ItemStackRecipeWrapper.of(ModItems.ROLE_CARD.getDefaultStack()),
                    wrappers,
                    ItemStackRecipeWrapper.of(this.roleCard.itemStack())
            );
            return this;
        }

        public void apply(RecipeTypeProvider.Factory<GensokyoAltarRecipe> factory) {
            factory.register(this.roleCard.getId(), this.result);
        }
    }
}
