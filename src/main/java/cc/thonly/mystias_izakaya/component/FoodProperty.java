package cc.thonly.mystias_izakaya.component;

import cc.thonly.mystias_izakaya.api.FoodPropertyCallback;
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

import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
public class
FoodProperty implements RegistrableObject<FoodProperty> {
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

//    public static List<FoodProperty> getConflictingProperties(Item item) {
//        List<FoodProperty> properties = FoodProperty.getIngredientProperties(item);
//        List<FoodProperty> conflicts = new ArrayList<>();
//        for (int i = 0; i < properties.size(); i++) {
//            FoodProperty a = properties.get(i);
//            for (int j = i + 1; j < properties.size(); j++) {
//                FoodProperty b = properties.get(j);
//                if (a.isConflict(b)) {
//                    conflicts.add(a);
//                    conflicts.add(b);
//                }
//            }
//        }
//        return conflicts;
//    }


    public static String getDisplayPrefix(Item item, FoodProperty foodProperty) {
        List<FoodProperty> all = FoodProperty.getIngredientProperties(item);
        for (FoodProperty other : all) {
//            if (other != foodProperty && other.isConflict(foodProperty)) {
//                return "§c-";
//            }
        }
        return "§b+";
    }
    
    public Boolean is(FoodProperty property) {
        return this == property || this.getId().equals(property.getId()) || this.hashCode() == property.hashCode();
    }

    public Text getTooltip() {
        return Text.translatable(this.id.toTranslationKey("food_property"));
    }

    public String translateKey() {
        return this.id.toTranslationKey("food_property");
    }

    public static List<FoodProperty> getAllProperties(ItemStack itemStack) {
        Set<FoodProperty> set = new HashSet<>();
        set.addAll(getIngredientProperties(itemStack.getItem()));
        set.addAll(getFromItemStackComponent(itemStack));
        set.addAll(getFromItemStack(itemStack));
        return new ArrayList<>(set);
    }

    /**
     * 根据给定的 Item 查找所有包含该 Item 的 FoodProperty。
     * 遍历整个 FOOD_PROPERTY 注册表，检查每个 FoodProperty 是否包含该 Item。
     * @param item 目标物品
     * @return 包含该 Item 的所有 FoodProperty 列表
     */
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

    /**
     * 从 ItemStack 的自定义组件（MIDataComponentTypes.FOOD_PROPERTIES）中获取 FoodProperty 列表。
     * 该组件存储的是 FoodProperty 的 id 字符串列表，通过这些字符串再查询对应的 FoodProperty 对象。
     * @param itemStack 目标物品栈
     * @return 对应的 FoodProperty 列表
     */
    public static List<FoodProperty> getFromItemStackComponent(ItemStack itemStack) {
        List<String> ids = itemStack.getOrDefault(MIDataComponentTypes.FOOD_PROPERTIES, new ArrayList<>());
        return getFromStrings(ids);
    }

    /**
     * 直接从 ItemStack 的物品（Item）获取其所有对应的 FoodProperty。
     * 实质上是调用 getIngredientProperties 来获取所有包含该物品的 FoodProperty。
     * @param itemStack 目标物品栈
     * @return 包含该物品的所有 FoodProperty 列表
     */
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

    /**
     * 根据一组 FoodProperty 的 id 字符串列表，查询对应的 FoodProperty 对象列表。
     * @param ids FoodProperty 的 id 字符串列表
     * @return 对应的 FoodProperty 对象列表，若 id 无对应 FoodProperty 则忽略
     */
    public static List<FoodProperty> getFromStrings(List<String> ids) {
        List<FoodProperty> list = new ArrayList<>();
        for (String id : ids) {
            Identifier identifier = Identifier.of(id);
            FoodProperty foodProperty = MIRegistryManager.FOOD_PROPERTY.get(identifier);
            if (foodProperty != null) {
                list.add(foodProperty);
            }
        }
        return list;
    }

    @Override
    public Codec<FoodProperty> getCodec() {
        return CODEC;
    }

    @Override
    public Boolean isDirect() {
        return true;
    }
}
