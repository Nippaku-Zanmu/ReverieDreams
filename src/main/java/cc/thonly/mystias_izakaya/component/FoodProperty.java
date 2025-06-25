package cc.thonly.mystias_izakaya.component;

import cc.thonly.mystias_izakaya.impl.FoodPropertyCallback;
import cc.thonly.mystias_izakaya.registry.MIRegistryManager;
import cc.thonly.reverie_dreams.effect.ModStatusEffects;
import cc.thonly.reverie_dreams.registry.RegistrableObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
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
@ToString
public class FoodProperty implements RegistrableObject<FoodProperty> {
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
    private Set<Item> tags = new ObjectOpenHashSet<>();
    private Set<FoodProperty> conflicts = new ObjectOpenHashSet<>();

    public FoodProperty() {
        this.effectInstance = new StatusEffectInstance(new StatusEffectInstance(ModStatusEffects.EMPTY, 1));
    }

    public FoodProperty(StatusEffectInstance effectInstance) {
        this.effectInstance = effectInstance;
    }

    public final void use(ServerWorld world, LivingEntity user) {
        StatusEffectInstance effectInstance = new StatusEffectInstance(this.effectInstance);
        user.addStatusEffect(effectInstance);
        FoodPropertyCallback.EVENT.invoker().onUse(world, user, this);
        this.onUse(world, user);
    }

    public void onUse(ServerWorld world, LivingEntity user) {

    }

    @SuppressWarnings("JavaExistingMethodCanBeUsed")
    public static List<FoodProperty> getConflictingProperties(Item item) {
        List<FoodProperty> properties = FoodProperty.getIngredientProperties(item);
        List<FoodProperty> conflicts = new ArrayList<>();
        for (int i = 0; i < properties.size(); i++) {
            FoodProperty a = properties.get(i);
            for (int j = i + 1; j < properties.size(); j++) {
                FoodProperty b = properties.get(j);
                if (a.isConflict(b)) {
                    conflicts.add(a);
                    conflicts.add(b);
                }
            }
        }
        return conflicts;
    }


    public static String getDisplayPrefix(Item item, FoodProperty foodProperty) {
        List<FoodProperty> all = FoodProperty.getIngredientProperties(item);
        for (FoodProperty other : all) {
            if (other != foodProperty && other.isConflict(foodProperty)) {
                return "§c-";
            }
        }
        return "§b+";
    }

    public static List<FoodProperty> getConflictingProperties(List<FoodProperty> properties) {
        List<FoodProperty> conflicts = new ArrayList<>();
        for (int i = 0; i < properties.size(); i++) {
            FoodProperty a = properties.get(i);
            for (int j = i + 1; j < properties.size(); j++) {
                FoodProperty b = properties.get(j);
                if (a.isConflict(b)) {
                    conflicts.add(a);
                    conflicts.add(b);
                }
            }
        }
        return conflicts;
    }

    public static Boolean isConflict(FoodProperty a, FoodProperty b) {
        Set<FoodProperty> conflicts1 = a.getConflicts();
        Set<FoodProperty> conflicts2 = b.getConflicts();
        return conflicts1.contains(b) || conflicts2.contains(a);
    }
    
    public Boolean is(FoodProperty property) {
        return this == property || this.getId().equals(property.getId()) || this.hashCode() == property.hashCode();
    }

    public Boolean isConflict(FoodProperty property) {
        return this.conflicts.contains(property);
    }

    public FoodProperty setConflict(FoodProperty property) {
        this.conflicts.add(property);
        property.getConflicts().add(this);
        return this;
    }

    public Text getTooltip() {
        return Text.translatable(this.id.toTranslationKey("food_property"));
    }

    public String getTranslateKey() {
        return this.id.toTranslationKey("food_property");
    }

    public static List<FoodProperty> getIngredientProperties(Item item) {
        List<FoodProperty> list = new ArrayList<>();
        Set<Map.Entry<Identifier, FoodProperty>> entries = MIRegistryManager.FOOD_PROPERTY.entrySet();
        for (Map.Entry<Identifier, FoodProperty> entry : entries) {
            FoodProperty foodProperty = entry.getValue();
            Set<Item> tags = foodProperty.getTags();
            if (tags.contains(item)) {
                list.add(foodProperty);
            }
        }
        return list;
    }

    public static List<FoodProperty> getFromItemStackComponent(ItemStack itemStack) {
        List<String> ids = itemStack.getOrDefault(MIDataComponentTypes.MI_FOOD_PROPERTIES, new ArrayList<>());
        return getFromStrings(ids);
    }

    public static List<FoodProperty> getFromItemStack(ItemStack itemStack) {
        List<FoodProperty> list = new ArrayList<>();
        Item item = itemStack.getItem();
        Set<Map.Entry<Identifier, FoodProperty>> entries = MIRegistryManager.FOOD_PROPERTY.entrySet();
        for (Map.Entry<Identifier, FoodProperty> entry : entries) {
            FoodProperty foodProperty = entry.getValue();
            Set<Item> tags = foodProperty.getTags();
            if (tags.contains(item)) {
                list.add(foodProperty);
            }
        }
        return list;
    }

    public static List<FoodProperty> getFromStrings(List<String> ids) {
        List<FoodProperty> list = new ArrayList<>();
        for (String id : ids) {
            Identifier identifier = Identifier.of(id);
            FoodProperty foodProperty = MIRegistryManager.FOOD_PROPERTY.get(identifier);
            list.add(foodProperty);
        }
        return list;
    }

    @Override
    public Codec<FoodProperty> getCodec() {
        return CODEC;
    }

    @Override
    public Boolean canReloadable() {
        return true;
    }
}
