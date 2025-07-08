package cc.thonly.reverie_dreams.impl;

import cc.thonly.reverie_dreams.recipe.BaseRecipe;
import cc.thonly.reverie_dreams.recipe.BaseRecipeType;
import cc.thonly.reverie_dreams.recipe.RecipeItemTag;
import cc.thonly.reverie_dreams.recipe.ItemStackRecipeWrapper;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@SuppressWarnings("unchecked")
public class RecipeCompatPatchesImpl {
    public static <R extends BaseRecipe> Builder<R> getOrCreateBuilder(BaseRecipeType<R> baseRecipeType) {
        return (Builder<R>) Builder.INSTANCE.computeIfAbsent(baseRecipeType, (x) -> new Builder<>(baseRecipeType));
    }

    public static void apply() {
        for (Map.Entry<BaseRecipeType<?>, Builder<?>> baseRecipeTypeBuilderEntry : Builder.INSTANCE.entrySet()) {
            BaseRecipeType<?> recipeType = baseRecipeTypeBuilderEntry.getKey();
            Builder<?> builder = baseRecipeTypeBuilderEntry.getValue();
            Map<Identifier, ?> registries = builder.getRegistries();
            for (Map.Entry<Identifier, ?> registry : registries.entrySet()) {
                log.info("Registered compatibility recipe {}", registry.getKey().toString());
                recipeType.add(registry.getKey(), registry.getValue());
            }
        }
    }

    @Accessors(chain = true)
    @Getter
    @Setter
    public static class Builder<R extends BaseRecipe> {
        public static final Map<BaseRecipeType<?>, Builder<?>> INSTANCE = new Object2ObjectOpenHashMap<>();
        public final BaseRecipeType<R> baseRecipeType;
        protected final Map<Identifier, BaseRecipe> registries = new Object2ObjectOpenHashMap<>();

        public Builder(BaseRecipeType<R> baseRecipeType) {
            this.baseRecipeType = baseRecipeType;
        }

        public Builder<R> add(Item targetItem, Item compatItem) {
            return this.add(new CompatEntry(targetItem, compatItem));
        }

        public Builder<R> add(Item targetItem, List<Item> compatItems) {
            compatItems.forEach(item -> this.add(new CompatEntry(targetItem, item)));
            return this;
        }

        public Builder<R> add(Item targetItem, Item... compatItems) {
            for (Item item : compatItems) {
                this.add(new CompatEntry(targetItem, item));
            }
            return this;
        }

        public Builder<R> add(Item targetItem, RecipeItemTag tagEntry) {
            tagEntry.forEach((item -> this.add(targetItem, item)));
            return this;
        }

        public Builder<R> add(CompatEntry compatEntry) {
            try {
                Map<Identifier, R> registryView = this.baseRecipeType.getRegistryView();
                for (Map.Entry<Identifier, R> view : registryView.entrySet()) {
                    R value = view.getValue();
//                    System.out.println(Arrays.stream(declaredFields).toList());

                    Object object = cloneWithLombokBuilder(value);
                    if (object instanceof BaseRecipe baseRecipe) {
                        Class<? extends BaseRecipe> brClass = baseRecipe.getClass();
                        Field[] declaredFields = brClass.getDeclaredFields();
                        boolean changed = false;
                        for (Field field : declaredFields) {
                            field.setAccessible(true);
                            Object fieldValue = field.get(value);

                            if (fieldValue instanceof ItemStackRecipeWrapper wrapper && wrapper.getItem().equals(compatEntry.targetItem)) {
                                if (wrapper.getItem().equals(compatEntry.compatItem)) {
                                    continue;
                                }
                                field.set(object, new ItemStackRecipeWrapper(new ItemStack(compatEntry.targetItem, wrapper.getCount())));
                                changed = true;
                            }
                            if (fieldValue instanceof List<?> list) {
                                if (list.isEmpty()) {
                                    continue;
                                }
                                Object first = list.getFirst();
                                if (!(first instanceof ItemStackRecipeWrapper)) {
                                    continue;
                                }

                                List<ItemStackRecipeWrapper> wrappers = new ArrayList<>();
                                boolean listChanged = false;

                                for (ItemStackRecipeWrapper wrapper : (List<ItemStackRecipeWrapper>) list) {
                                    if (compatEntry.targetItem.equals(compatEntry.compatItem)) {
                                        wrappers.add(wrapper);
                                        continue;
                                    }

                                    if (wrapper.getItem().equals(compatEntry.targetItem)) {
                                        wrappers.add(ItemStackRecipeWrapper.of(new ItemStack(compatEntry.compatItem, wrapper.getCount())));
                                        listChanged = true;
                                    } else {
                                        wrappers.add(wrapper);
                                    }
                                }

                                if (listChanged) {
                                    field.set(object, wrappers);
                                    changed = true;
                                }
                            }
                        }
                        if (changed) {
                            Identifier itemId = Registries.ITEM.getId(compatEntry.compatItem);
                            Identifier oldId = view.getKey();
                            Identifier newIdentifier = null;
                            if (oldId == null) {
                                oldId = Identifier.of("unknown_recipe_" + UUID.randomUUID());
                                newIdentifier = oldId;
                            } else {
                                newIdentifier = Identifier.of(oldId.getNamespace() + ":" + oldId.getPath() + "_" + itemId.toString().replaceAll(":", "_"));
                            }
                            registries.put(newIdentifier, baseRecipe);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Can't add recipe patches: ", e);
            }
            return this;
        }

        @SuppressWarnings("unchecked")
        private <T> T cloneWithLombokBuilder(T object) {
            try {
                Method toBuilder = object.getClass().getMethod("toBuilder");
                Object builder = toBuilder.invoke(object);
                Method build = builder.getClass().getMethod("build");
                return (T) build.invoke(builder);
            } catch (Exception e) {
                log.error("Can't clone with builder", e);
                return null;
            }
        }
    }

    public record CompatEntry(Item targetItem, Item compatItem) {
    }

}
