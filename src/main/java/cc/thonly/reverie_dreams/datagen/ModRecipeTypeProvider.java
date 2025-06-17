package cc.thonly.reverie_dreams.datagen;

import cc.thonly.mystias_izakaya.MystiasIzakaya;
import cc.thonly.mystias_izakaya.item.MIItems;
import cc.thonly.mystias_izakaya.recipe.MiRecipeManager;
import cc.thonly.mystias_izakaya.recipe.entry.KitchenRecipe;
import cc.thonly.mystias_izakaya.recipe.type.KitchenRecipeType;
import cc.thonly.reverie_dreams.datagen.generator.RecipeTypeProvider;
import cc.thonly.reverie_dreams.recipe.RecipeManager;
import cc.thonly.reverie_dreams.recipe.entry.DanmakuRecipe;
import cc.thonly.reverie_dreams.recipe.slot.ItemStackRecipeWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryWrapper;
import cc.thonly.reverie_dreams.component.ModDataComponentTypes;
import cc.thonly.reverie_dreams.item.BasicDanmakuItemTypeItem;
import cc.thonly.reverie_dreams.item.ModItems;
import cc.thonly.reverie_dreams.item.entry.DanmakuColor;
import cc.thonly.reverie_dreams.item.entry.DanmakuItemType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeTypeProvider extends RecipeTypeProvider {
    public ModRecipeTypeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> future) {
        super(output, future);
    }

    @Override
    public void configured() {
        Factory<DanmakuRecipe> danmakuRegistry = this.getOrCreateFactory(RecipeManager.DANMAKU_TYPE, DanmakuRecipe.class);
        for (DanmakuItemType entry : ModItems.DANMAKU_ITEMS) {
            List<BasicDanmakuItemTypeItem> itemList = entry.getValues();
            for (BasicDanmakuItemTypeItem itemEntry : itemList) {
                Integer color = itemEntry.getComponents().get(ModDataComponentTypes.Danmaku.COLOR);
                if (color != null) {
                    DanmakuColor colorEnum = DanmakuColor.fromIndex(color);
                    assert colorEnum != null;
                    Item dye = colorEnum.getDye();

                    DanmakuRecipe recipe = new DanmakuRecipe(
                            new ItemStackRecipeWrapper(new ItemStack(dye, 1)),
                            new ItemStackRecipeWrapper(new ItemStack(Items.FIREWORK_STAR, 1)),
                            new ItemStackRecipeWrapper(new ItemStack(ModItems.POWER, 35)),
                            new ItemStackRecipeWrapper(new ItemStack(ModItems.POINT, 35)),
                            new ItemStackRecipeWrapper(ItemStack.EMPTY),
                            new ItemStackRecipeWrapper(new ItemStack(itemEntry, 1))
                    );
                    danmakuRegistry.register(Registries.ITEM.getId(recipe.getOutput().getItem()), recipe);
                }
            }
        }
        this.generateKitchenRecipe();
    }

    public void generateKitchenRecipe() {
        Factory<KitchenRecipe> kitchenRecipeFactory = this.getOrCreateFactory(MiRecipeManager.KITCHEN_RECIPE, KitchenRecipe.class);
        kitchenRecipeFactory.register(MystiasIzakaya.id("seafood_miso_soup"), new KitchenRecipe(
                KitchenRecipeType.KitchenType.COOKING_POT.getId(),
                List.of(
                        new ItemStackRecipeWrapper(Items.KELP.getDefaultStack())
                ),
                new ItemStackRecipeWrapper(MIItems.SEAFOOD_MISO_SOUP.getDefaultStack()),
                6.0
        ));
    }

    @Override
    public String getName() {
        return "Recipe Types";
    }
}
