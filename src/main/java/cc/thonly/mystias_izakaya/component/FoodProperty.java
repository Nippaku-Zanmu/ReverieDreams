package cc.thonly.mystias_izakaya.component;

import cc.thonly.mystias_izakaya.registry.MIRegistrySchemas;
import cc.thonly.reverie_dreams.effect.ModStatusEffects;
import cc.thonly.reverie_dreams.registry.SchemaObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
public class FoodProperty implements SchemaObject<FoodProperty> {
    public static final Codec<FoodProperty> CODEC = Codec.unit(new FoodProperty());
    public static final Codec<List<Item>> TAG_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.list(Identifier.CODEC)
                            .fieldOf("values")
                            .forGetter(items -> items.stream()
                                    .map(Registries.ITEM::getId)
                                    .collect(Collectors.toList()))
            ).apply(instance, identifiers ->
                    identifiers.stream()
                            .map(Registries.ITEM::get)
                            .collect(Collectors.toList())
            )
    );

    private Identifier id;
    private final StatusEffectInstance effectInstance;
    private List<Item> tags = new ArrayList<>();

    public FoodProperty() {
        this.effectInstance = new StatusEffectInstance(new StatusEffectInstance(ModStatusEffects.EMPTY, 1));
    }

    public FoodProperty(StatusEffectInstance effectInstance) {
        this.effectInstance = effectInstance;
    }

    public void use(ServerWorld world, LivingEntity user) {
        StatusEffectInstance effectInstance = new StatusEffectInstance(this.effectInstance);
        user.addStatusEffect(effectInstance);
    }

    public Text getTooltip() {
        return Text.translatable(this.id.toTranslationKey("food_property"));
    }

    public String getTranslateKey() {
        return this.id.toTranslationKey("food_property");
    }

    public static List<FoodProperty> getIngredientProperties(Item item) {
        List<FoodProperty> list = new ArrayList<>();
        Set<Map.Entry<Identifier, FoodProperty>> entries = MIRegistrySchemas.FOOD_PROPERTY.entrySet();
        for (Map.Entry<Identifier, FoodProperty> entry: entries) {
            FoodProperty foodProperty = entry.getValue();
            List<Item> tags = foodProperty.getTags();
            if (tags.contains(item)) {
                list.add(foodProperty);
            }
        }
        return list;
    }

    public static List<FoodProperty> getFromItemStack(ItemStack itemStack) {
        List<String> ids = itemStack.getOrDefault(MIDataComponentTypes.MI_FOOD_PROPERTIES, new ArrayList<>());
        return getFromStrings(ids);
    }

    public static List<FoodProperty> getFromStrings(List<String> ids) {
        List<FoodProperty> list = new ArrayList<>();
        for (String id : ids) {
            Identifier identifier = Identifier.of(id);
            FoodProperty foodProperty = MIRegistrySchemas.FOOD_PROPERTY.get(identifier);
            list.add(foodProperty);
        }
        return list;
    }

    @Override
    public Codec<FoodProperty> getCodec() {
        return CODEC;
    }
}
