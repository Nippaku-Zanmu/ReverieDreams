package cc.thonly.reverie_dreams.datagen;

import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.item.ModItems;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

public class ModRecipeGenerator extends RecipeGenerator {
    protected ModRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
        super(registries, exporter);
    }

    @Override
    public void generate() {
        // Point / 块
        offerIngotToBlockRecipe(exporter, ModItems.POINT, ModBlocks.POINT_BLOCK.asItem());
        offerBlockToIngotRecipe(exporter, ModBlocks.POINT_BLOCK.asItem(), ModItems.POINT);

        // Power / 块
        offerIngotToBlockRecipe(exporter, ModItems.POWER, ModBlocks.POWER_BLOCK.asItem());
        offerBlockToIngotRecipe(exporter, ModBlocks.POWER_BLOCK.asItem(), ModItems.POWER);

        // 银武器/工具
        offerSwordRecipe(exporter, ModItems.SILVER_SWORD, ModItems.SILVER_INGOT);
        offerPickaxeRecipe(exporter, ModItems.SILVER_PICKAXE, ModItems.SILVER_INGOT);
        offerAxeRecipe(exporter, ModItems.SILVER_AXE, ModItems.SILVER_INGOT);
        offerShovelRecipe(exporter, ModItems.SILVER_SHOVEL, ModItems.SILVER_INGOT);
        offerHoeRecipe(exporter, ModItems.SILVER_HOE, ModItems.SILVER_INGOT);
        // 银盔甲
        offerHelmetRecipe(exporter, ModItems.SILVER_HELMET, ModItems.SILVER_INGOT);
        offerChestplateRecipe(exporter, ModItems.SILVER_CHESTPLATE, ModItems.SILVER_INGOT);
        offerLeggingsRecipe(exporter, ModItems.SILVER_LEGGINGS, ModItems.SILVER_INGOT);
        offerBootsRecipe(exporter, ModItems.SILVER_BOOTS, ModItems.SILVER_INGOT);
        // 银粒 / 锭 / 块
        offerIngotToBlockRecipe(exporter, ModItems.SILVER_NUGGET, ModItems.SILVER_INGOT);
        offerBlockToIngotRecipe(exporter, ModItems.SILVER_INGOT, ModItems.SILVER_NUGGET);
        offerIngotToBlockRecipe(exporter, ModItems.SILVER_INGOT, ModBlocks.SILVER_BLOCK.asItem());
        offerBlockToIngotRecipe(exporter, ModBlocks.SILVER_BLOCK.asItem(), ModItems.SILVER_INGOT);

        // 注连绳
        createShapeless(RecipeCategory.MISC, ModItems.SHIMENAWA)
                .input(Items.LEATHER)
                .criterion("has_leather", conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, getRecipeName(ModItems.SHIMENAWA));

        // 纸垂
        createShaped(RecipeCategory.MISC, ModItems.SHIDE, 2)
                .pattern(" S ")
                .pattern("PPP")
                .input('S', ModItems.SHIMENAWA)
                .input('P', Items.PAPER)
                .criterion("has_leather", conditionsFromItem(ModItems.SHIMENAWA))
                .offerTo(exporter, getRecipeName(ModItems.SHIDE));

        // 弹幕工作台
        createShaped(RecipeCategory.DECORATIONS, ModBlocks.DANMAKU_CRAFTING_TABLE)
                .pattern("X")
                .pattern("#")
                .input('X', Items.FIREWORK_STAR)
                .input('#', Items.CRAFTING_TABLE)
                .criterion("always", conditionsFromItem(Items.AIR))
                .offerTo(exporter, getRecipeName(ModBlocks.DANMAKU_CRAFTING_TABLE));

        // 幻想乡祭坛
        createShaped(RecipeCategory.DECORATIONS, ModBlocks.GENSOKYO_ALTAR)
                .pattern("X")
                .pattern("#")
                .input('X', ModItems.SHIDE)
                .input('#', Items.ENCHANTING_TABLE)
                .criterion("always", conditionsFromItem(Items.AIR))
                .offerTo(exporter, getRecipeName(ModBlocks.GENSOKYO_ALTAR));

    }

    private void offerSwordRecipe(RecipeExporter exporter, Item result, Item ingot) {
        createShaped(RecipeCategory.COMBAT, result)
                .pattern("X")
                .pattern("X")
                .pattern("#")
                .input('X', ingot)
                .input('#', Items.STICK)
                .criterion("has_ingot", conditionsFromItem(ingot))
                .offerTo(exporter, getRecipeName(result));
    }

    private void offerPickaxeRecipe(RecipeExporter exporter, Item result, Item ingot) {
        createShaped(RecipeCategory.TOOLS, result)
                .pattern("XXX")
                .pattern(" # ")
                .pattern(" # ")
                .input('X', ingot)
                .input('#', Items.STICK)
                .criterion("has_ingot", conditionsFromItem(ingot))
                .offerTo(exporter, getRecipeName(result));
    }

    private void offerAxeRecipe(RecipeExporter exporter, Item result, Item ingot) {
        createShaped(RecipeCategory.TOOLS, result)
                .pattern("XX")
                .pattern("X#")
                .pattern(" #")
                .input('X', ingot)
                .input('#', Items.STICK)
                .criterion("has_ingot", conditionsFromItem(ingot))
                .offerTo(exporter, getRecipeName(result));
    }

    private void offerShovelRecipe(RecipeExporter exporter, Item result, Item ingot) {
        createShaped(RecipeCategory.TOOLS, result)
                .pattern("X")
                .pattern("#")
                .pattern("#")
                .input('X', ingot)
                .input('#', Items.STICK)
                .criterion("has_ingot", conditionsFromItem(ingot))
                .offerTo(exporter, getRecipeName(result));
    }

    private void offerHoeRecipe(RecipeExporter exporter, Item result, Item ingot) {
        createShaped(RecipeCategory.TOOLS, result)
                .pattern("XX")
                .pattern(" #")
                .pattern(" #")
                .input('X', ingot)
                .input('#', Items.STICK)
                .criterion("has_ingot", conditionsFromItem(ingot))
                .offerTo(exporter, getRecipeName(result));
    }

    private void offerHelmetRecipe(RecipeExporter exporter, Item result, Item ingot) {
        createShaped(RecipeCategory.COMBAT, result)
                .pattern("XXX")
                .pattern("X X")
                .input('X', ingot)
                .criterion("has_ingot", conditionsFromItem(ingot))
                .offerTo(exporter, getRecipeName(result));
    }

    private void offerChestplateRecipe(RecipeExporter exporter, Item result, Item ingot) {
        createShaped(RecipeCategory.COMBAT, result)
                .pattern("X X")
                .pattern("XXX")
                .pattern("XXX")
                .input('X', ingot)
                .criterion("has_ingot", conditionsFromItem(ingot))
                .offerTo(exporter, getRecipeName(result));
    }

    private void offerLeggingsRecipe(RecipeExporter exporter, Item result, Item ingot) {
        createShaped(RecipeCategory.COMBAT, result)
                .pattern("XXX")
                .pattern("X X")
                .pattern("X X")
                .input('X', ingot)
                .criterion("has_ingot", conditionsFromItem(ingot))
                .offerTo(exporter, getRecipeName(result));
    }

    private void offerBootsRecipe(RecipeExporter exporter, Item result, Item ingot) {
        createShaped(RecipeCategory.COMBAT, result)
                .pattern("X X")
                .pattern("X X")
                .input('X', ingot)
                .criterion("has_ingot", conditionsFromItem(ingot))
                .offerTo(exporter, getRecipeName(result));
    }

    private void offerIngotToBlockRecipe(RecipeExporter exporter, Item ingot, Item block) {
        createShaped(RecipeCategory.BUILDING_BLOCKS, block)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .input('X', ingot)
                .criterion("has_ingot", conditionsFromItem(ingot))
                .offerTo(exporter, getRecipeName(block));
    }

    private void offerBlockToIngotRecipe(RecipeExporter exporter, Item block, Item ingot) {
        createShapeless(RecipeCategory.MISC, ingot, 9)
                .input(block)
                .criterion("has_block", conditionsFromItem(block))
                .offerTo(exporter, getRecipeName(ingot) + "_from_block");
    }


}
