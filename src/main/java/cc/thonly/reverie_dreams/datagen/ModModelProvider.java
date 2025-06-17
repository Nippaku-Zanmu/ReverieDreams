package cc.thonly.reverie_dreams.datagen;

import cc.thonly.mystias_izakaya.block.MIBlocks;
import cc.thonly.mystias_izakaya.item.MIItems;
import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.block.FumoBlocks;
import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.entity.ModEntityHolders;
import cc.thonly.reverie_dreams.item.ModGuiItems;
import cc.thonly.reverie_dreams.item.ModItems;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.*;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Item;

import java.util.Map;
import java.util.Optional;


public class ModModelProvider extends FabricModelProvider {
    private final Map<Block, TexturedModel> uniqueModels = ImmutableMap.<Block, TexturedModel>builder()
            .build();

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    private final BlockFamily SPIRITUAL_PLANKS = BlockFamilies.register(ModBlocks.SPIRITUAL_PLANKS)
            .slab(ModBlocks.SPIRITUAL_SLAB)
            .stairs(ModBlocks.SPIRITUAL_STAIR)
            .fence(ModBlocks.SPIRITUAL_FENCE)
            .fenceGate(ModBlocks.SPIRITUAL_FENCE_GATE)
            .button(ModBlocks.SPIRITUAL_BUTTON)
            .group("wooden").unlockCriterionName("has_planks")
            .build();

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerCubeWithCustomTextures(ModBlocks.DANMAKU_CRAFTING_TABLE, Blocks.OAK_PLANKS, TextureMap::frontSideWithCustomBottom);
        this.registerSmithingTable(blockStateModelGenerator, ModBlocks.STRENGTH_TABLE);
        blockStateModelGenerator.registerSimpleState(ModBlocks.GENSOKYO_ALTAR);

        blockStateModelGenerator.registerLog(ModBlocks.SPIRITUAL_LOG).log(ModBlocks.SPIRITUAL_LOG).wood(ModBlocks.SPIRITUAL_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_SPIRITUAL_LOG).log(ModBlocks.STRIPPED_SPIRITUAL_LOG).wood(ModBlocks.STRIPPED_SPIRITUAL_WOOD);
        this.registerFamily(blockStateModelGenerator, SPIRITUAL_PLANKS);

        blockStateModelGenerator.registerDoor(ModBlocks.SPIRITUAL_DOOR);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.SPIRITUAL_TRAPDOOR);

        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MAGIC_ICE_BLOCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.POINT_BLOCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.POWER_BLOCK);

        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.DREAM_BLUE_BLOCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.DREAM_RED_BLOCK);

        for (Block block : FumoBlocks.getRegisteredFumo()) {
            blockStateModelGenerator.registerSimpleState(block);
        }
        this.generateMIBlock(blockStateModelGenerator);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // 调试
        itemModelGenerator.register(ModItems.BATTLE_STICK, Models.HANDHELD);

        // 图标
        itemModelGenerator.register(ModItems.ICON, Models.GENERATED);
        itemModelGenerator.register(ModItems.FUMO_ICON, Models.GENERATED);
        itemModelGenerator.register(ModItems.ROLE_ICON, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPAWN_EGG, Models.GENERATED);

        // 材料
        itemModelGenerator.register(ModItems.POINT, Models.GENERATED);
        itemModelGenerator.register(ModItems.POWER, Models.GENERATED);
        itemModelGenerator.register(ModItems.UPGRADED_HEALTH_FRAGMENT, Models.GENERATED);
        itemModelGenerator.register(ModItems.BOMB_FRAGMENT, Models.GENERATED);
        itemModelGenerator.register(ModItems.SHIDE, Models.GENERATED);
        itemModelGenerator.register(ModItems.SHIMENAWA, Models.GENERATED);
        itemModelGenerator.register(ModItems.RED_ORB, Models.GENERATED);
        itemModelGenerator.register(ModItems.BLUE_ORB, Models.GENERATED);
        itemModelGenerator.register(ModItems.YELLOW_ORB, Models.GENERATED);
        itemModelGenerator.register(ModItems.GREEN_ORB, Models.GENERATED);
        itemModelGenerator.register(ModItems.PURPLE_ORB, Models.GENERATED);
        itemModelGenerator.register(ModItems.YIN_YANG_ORB, Models.GENERATED);

        // 道具
        itemModelGenerator.register(ModItems.TOUHOU_HELPER, Models.GENERATED);
        itemModelGenerator.register(ModItems.UPGRADED_HEALTH, Models.GENERATED);
        itemModelGenerator.register(ModItems.BOMB, Models.GENERATED);
        itemModelGenerator.register(ModItems.HORAI_DAMA_NO_EDA, Models.GENERATED);
        itemModelGenerator.register(ModItems.CROSSING_CHISEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.GAP_BALL, Models.GENERATED);
        itemModelGenerator.register(ModItems.BAGUA_FURNACE);
        itemModelGenerator.register(ModItems.TIME_STOP_CLOCK, Models.GENERATED);
        itemModelGenerator.register(ModItems.EARPHONE, Models.GENERATED);

        // 武器
        itemModelGenerator.register(ModItems.HAKUREI_CANE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.WIND_BLESSING_CANE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.MAGIC_BROOM);
        itemModelGenerator.register(ModItems.KNIFE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ROKANKEN, Models.HANDHELD);
        itemModelGenerator.register(ModItems.HAKUROKEN, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PAPILIO_PATTERN_FAN, Models.HANDHELD);
        itemModelGenerator.register(ModItems.GUNGNIR, Models.HANDHELD);
        itemModelGenerator.register(ModItems.LEVATIN, Models.HANDHELD);
        itemModelGenerator.register(ModItems.IBUKIHO, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SWORD_OF_HISOU, Models.HANDHELD);
        itemModelGenerator.register(ModItems.MAPLE_LEAF_FAN, Models.HANDHELD);
        itemModelGenerator.register(ModItems.MANPOZUCHI, Models.HANDHELD);
        itemModelGenerator.register(ModItems.NUE_TRIDENT);
        itemModelGenerator.register(ModItems.TRUMPET_GUN);
        itemModelGenerator.register(ModItems.TREASURE_HUNTING_ROD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.VIOLIN);
        itemModelGenerator.register(ModItems.KEYBOARD);
        itemModelGenerator.register(ModItems.TRUMPET);

        // 唱片
        itemModelGenerator.register(ModItems.HR01_01, Models.GENERATED);
        itemModelGenerator.register(ModItems.HR02_08, Models.GENERATED);
        itemModelGenerator.register(ModItems.HR03_01, Models.GENERATED);
        itemModelGenerator.register(ModItems.TH15_16, Models.GENERATED);
        itemModelGenerator.register(ModItems.TH15_17, Models.GENERATED);

//        itemModelGenerator.register(ModItems.EMPTY_SPELL_CARD, Models.GENERATED);

        // 调试
//        itemModelGenerator.register(ModItems.DEBUG_DANMAKU_ITEM, Models.GENERATED);
//        itemModelGenerator.register(ModItems.DEBUG_SPELL_CARD_ITEM, Models.GENERATED);
//        itemModelGenerator.register(ModItems.DEBUG_SPELL_CARD_ITEM2, Models.GENERATED);

        this.generateGuiItemModels(itemModelGenerator);
        this.generateBulletItemModels(itemModelGenerator);
        this.generateHolder(itemModelGenerator);
        this.generateMIItem(itemModelGenerator);
    }

    public void generateMIBlock(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleState(MIBlocks.COOKING_POT);
        blockStateModelGenerator.registerSimpleState(MIBlocks.CUTTING_BOARD);
        blockStateModelGenerator.registerSimpleState(MIBlocks.FRYING_PAN);
        blockStateModelGenerator.registerSimpleState(MIBlocks.GRILL);
        blockStateModelGenerator.registerSimpleState(MIBlocks.STEAMER);
    }

    public void generateMIItem(ItemModelGenerator itemModelGenerator) {
        for (Item item : MIItems.INGREDIENTS) {
            itemModelGenerator.register(item, Models.GENERATED);
        }
        for (Item item : MIItems.FOOD_ITEMS) {
            itemModelGenerator.register(item, Models.GENERATED);
        }
    }

    public void generateHolder(ItemModelGenerator itemModelGenerator) {
        ModEntityHolders.HOLDERS.forEach(itemModelGenerator::register);
    }

    public void generateGuiItemModels(ItemModelGenerator itemModelGenerator) {
        Model guiSlotModel = item("custom_slot", TextureKey.LAYER0);
        for (Item item : ModGuiItems.getRegisteredItems()) {
            itemModelGenerator.register(item, guiSlotModel);
        }
    }

    public void generateBulletItemModels(ItemModelGenerator itemModelGenerator) {
        for (Item item : ModItems.getRegisteredDanmakuItems()) {
            itemModelGenerator.register(item, Models.GENERATED);
        }
    }

    private void registerSmithingTable(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        TextureMap textureMap = new TextureMap()
                .put(TextureKey.PARTICLE, TextureMap.getSubId(block, "_front"))
                .put(TextureKey.DOWN, TextureMap.getSubId(block, "_bottom"))
                .put(TextureKey.UP, TextureMap.getSubId(block, "_top"))
                .put(TextureKey.NORTH, TextureMap.getSubId(block, "_front"))
                .put(TextureKey.SOUTH, TextureMap.getSubId(block, "_front"))
                .put(TextureKey.EAST, TextureMap.getSubId(block, "_side"))
                .put(TextureKey.WEST, TextureMap.getSubId(block, "_side"));
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, Models.CUBE.upload(block, textureMap, blockStateModelGenerator.modelCollector)));
    }

    private static Model item(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Touhou.id("item/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    private void registerFamily(BlockStateModelGenerator generator, BlockFamily family) {
        TexturedModel texturedModel = this.uniqueModels.getOrDefault(family.getBaseBlock(), TexturedModel.CUBE_ALL.get(family.getBaseBlock()));
        generator.new BlockTexturePool(texturedModel.getTextures()).base(family.getBaseBlock(), texturedModel.getModel()).family(family);
    }

    @Override
    public String getName() {
        return "Touhou Model Provider";
    }
}
