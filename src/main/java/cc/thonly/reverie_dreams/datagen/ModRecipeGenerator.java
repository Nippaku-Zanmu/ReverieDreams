package cc.thonly.reverie_dreams.datagen;

import cc.thonly.mystias_izakaya.block.MIBlocks;
import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.item.ModItems;
import com.google.common.collect.ImmutableList;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

public class ModRecipeGenerator extends RecipeGenerator {
    public static ImmutableList<ItemConvertible> SILVER = ImmutableList.of(ModBlocks.SILVER_ORE.asItem(), ModItems.RAW_SILVER);

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

        // 魔法冰
        createShaped(RecipeCategory.DECORATIONS, ModBlocks.MAGIC_ICE_BLOCK)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .input('X', Items.ICE)
                .criterion("has_ice", conditionsFromItem(Items.ICE))
                .offerTo(exporter, getRecipeName(ModBlocks.MAGIC_ICE_BLOCK));

        // 速度羽毛
        createShaped(RecipeCategory.DECORATIONS, ModItems.SPEED_FEATHER)
                .pattern("XXX")
                .pattern("X#X")
                .pattern("XXX")
                .input('X', Items.ICE)
                .input('#', Items.DIAMOND)
                .criterion("has_diamond", conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter, getRecipeName(ModItems.SPEED_FEATHER));

        // 冰武器/工具
        offerSwordRecipe(exporter, ModItems.MAGIC_ICE_SWORD, ModBlocks.MAGIC_ICE_BLOCK.asItem());
        offerPickaxeRecipe(exporter, ModItems.MAGIC_ICE_PICKAXE, ModBlocks.MAGIC_ICE_BLOCK.asItem());
        offerAxeRecipe(exporter, ModItems.MAGIC_ICE_AXE, ModBlocks.MAGIC_ICE_BLOCK.asItem());
        offerShovelRecipe(exporter, ModItems.MAGIC_ICE_SHOVEL, ModBlocks.MAGIC_ICE_BLOCK.asItem());
        offerHoeRecipe(exporter, ModItems.MAGIC_ICE_HOE, ModBlocks.MAGIC_ICE_BLOCK.asItem());

        // 冰盔甲
        offerHelmetRecipe(exporter, ModItems.MAGIC_ICE_HELMET, ModBlocks.MAGIC_ICE_BLOCK.asItem());
        offerChestplateRecipe(exporter, ModItems.MAGIC_ICE_CHESTPLATE, ModBlocks.MAGIC_ICE_BLOCK.asItem());
        offerLeggingsRecipe(exporter, ModItems.MAGIC_ICE_LEGGINGS, ModBlocks.MAGIC_ICE_BLOCK.asItem());
        offerBootsRecipe(exporter, ModItems.MAGIC_ICE_BOOTS, ModBlocks.MAGIC_ICE_BLOCK.asItem());

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

        // 宝玉 / 宝玉块
        offerIngotToBlockRecipe(exporter, ModItems.RED_ORB, ModBlocks.RED_ORB_BLOCK.asItem());
        offerBlockToIngotRecipe(exporter, ModBlocks.RED_ORB_BLOCK.asItem(), ModItems.RED_ORB);

        offerIngotToBlockRecipe(exporter, ModItems.YELLOW_ORB, ModBlocks.YELLOW_ORB_BLOCK.asItem());
        offerBlockToIngotRecipe(exporter, ModBlocks.YELLOW_ORB_BLOCK.asItem(), ModItems.YELLOW_ORB);

        offerIngotToBlockRecipe(exporter, ModItems.BLUE_ORB, ModBlocks.BLUE_ORB_BLOCK.asItem());
        offerBlockToIngotRecipe(exporter, ModBlocks.BLUE_ORB_BLOCK.asItem(), ModItems.BLUE_ORB);

        offerIngotToBlockRecipe(exporter, ModItems.GREEN_ORB, ModBlocks.GREEN_ORB_BLOCK.asItem());
        offerBlockToIngotRecipe(exporter, ModBlocks.GREEN_ORB_BLOCK.asItem(), ModItems.GREEN_ORB);

        offerIngotToBlockRecipe(exporter, ModItems.PURPLE_ORB, ModBlocks.PURPLE_ORB_BLOCK.asItem());
        offerBlockToIngotRecipe(exporter, ModBlocks.PURPLE_ORB_BLOCK.asItem(), ModItems.PURPLE_ORB);

        // Bomb
        offerIngotToBlockRecipe(exporter, ModItems.UPGRADED_HEALTH_FRAGMENT, ModItems.UPGRADED_HEALTH);

        // 残机
        offerIngotToBlockRecipe(exporter, ModItems.BOMB_FRAGMENT, ModItems.BOMB);

        // 烧银矿
        offerSmelting(SILVER, RecipeCategory.MISC, ModItems.SILVER_INGOT, 0.7F, 250, "silver_ingot");

        // 音乐盒
        createShaped(RecipeCategory.REDSTONE, ModBlocks.MUSIC_BLOCK)
                .pattern("XXX")
                .pattern("X#X")
                .pattern("XXX")
                .input('X', Items.EMERALD)
                .input('#', Items.NOTE_BLOCK)
                .criterion("has_emerald", conditionsFromItem(Items.EMERALD))
                .offerTo(exporter, getRecipeName(ModBlocks.MUSIC_BLOCK));

        // 弹幕工作台
        createShaped(RecipeCategory.DECORATIONS, ModBlocks.DANMAKU_CRAFTING_TABLE)
                .pattern("XXX")
                .pattern("X#X")
                .pattern("XXX")
                .input('X', Items.REDSTONE)
                .input('#', Items.CRAFTING_TABLE)
                .criterion("has_redstone", conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, getRecipeName(ModBlocks.DANMAKU_CRAFTING_TABLE));

        // 幻想乡祭坛
        createShaped(RecipeCategory.DECORATIONS, ModBlocks.GENSOKYO_ALTAR)
                .pattern("X")
                .pattern("#")
                .input('X', Items.EMERALD_BLOCK)
                .input('#', Items.ENCHANTING_TABLE)
                .criterion("has_emerald", conditionsFromItem(Items.EMERALD_BLOCK))
                .offerTo(exporter, getRecipeName(ModBlocks.GENSOKYO_ALTAR));

        // 强化台
        createShaped(RecipeCategory.DECORATIONS, ModBlocks.STRENGTH_TABLE)
                .pattern("XXX")
                .pattern("X#X")
                .pattern("XXX")
                .input('X', ModItems.SILVER_INGOT)
                .input('#', Items.ENCHANTING_TABLE)
                .criterion("has_silver", conditionsFromItem(ModItems.SILVER_INGOT))
                .offerTo(exporter, getRecipeName(ModBlocks.STRENGTH_TABLE));

        // 厨具
        createShaped(RecipeCategory.DECORATIONS, MIBlocks.COOKING_POT)
                .pattern(" Y ")
                .pattern("X X")
                .pattern("XXX")
                .input('X', Items.IRON_INGOT)
                .input('Y', Items.IRON_NUGGET)
                .criterion("always", conditionsFromItem(Items.AIR))
                .offerTo(exporter, getRecipeName(MIBlocks.COOKING_POT));
        ;
        createShaped(RecipeCategory.DECORATIONS, MIBlocks.CUTTING_BOARD)
                .pattern(" Y ")
                .pattern("XXX")
                .input('X', Items.OAK_SLAB)
                .input('Y', Items.IRON_SWORD)
                .criterion("always", conditionsFromItem(Items.AIR))
                .offerTo(exporter, getRecipeName(MIBlocks.CUTTING_BOARD));
        ;
        createShaped(RecipeCategory.DECORATIONS, MIBlocks.FRYING_PAN)
                .pattern(" XX")
                .pattern(" XX")
                .pattern("Y  ")
                .input('X', Items.IRON_INGOT)
                .input('Y', Items.IRON_NUGGET)
                .criterion("always", conditionsFromItem(Items.AIR))
                .offerTo(exporter, getRecipeName(MIBlocks.FRYING_PAN));
        ;
        createShaped(RecipeCategory.DECORATIONS, MIBlocks.GRILL)
                .pattern("YYY")
                .pattern("X X")
                .pattern("XXX")
                .input('X', Items.IRON_INGOT)
                .input('Y', Items.IRON_NUGGET)
                .criterion("always", conditionsFromItem(Items.AIR))
                .offerTo(exporter, getRecipeName(MIBlocks.GRILL));
        ;
        createShaped(RecipeCategory.DECORATIONS, MIBlocks.STEAMER)
                .pattern("YYY")
                .pattern("X X")
                .pattern("XXX")
                .input('X', Items.IRON_INGOT)
                .input('Y', Items.OAK_SLAB)
                .criterion("always", conditionsFromItem(Items.AIR))
                .offerTo(exporter, getRecipeName(MIBlocks.STEAMER));
        ;
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
