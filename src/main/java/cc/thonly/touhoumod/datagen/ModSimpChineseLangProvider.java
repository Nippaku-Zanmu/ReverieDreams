package cc.thonly.touhoumod.datagen;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.block.FumoBlocks;
import cc.thonly.touhoumod.block.ModBlocks;
import cc.thonly.touhoumod.effect.ModPotions;
import cc.thonly.touhoumod.effect.ModStatusEffects;
import cc.thonly.touhoumod.entity.ModEntities;
import cc.thonly.touhoumod.item.BasicDanmakuItem;
import cc.thonly.touhoumod.item.ModItems;
import cc.thonly.touhoumod.item.entry.DanmakuItemEntries;
import cc.thonly.touhoumod.lang.LanguageKeys;
import cc.thonly.touhoumod.sound.ModJukeboxSongs;
import cc.thonly.touhoumod.sound.ModSoundEvents;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModSimpChineseLangProvider extends FabricLanguageProvider implements LangToolImpl {
    private static final Map<String, String> COLOR_NAME_TRANSLATION = new HashMap<>();

    static {
        COLOR_NAME_TRANSLATION.put("black", "黑色");
        COLOR_NAME_TRANSLATION.put("dark_red", "暗红色");
        COLOR_NAME_TRANSLATION.put("red", "红色");
        COLOR_NAME_TRANSLATION.put("dark_purple", "暗紫色");
        COLOR_NAME_TRANSLATION.put("purple", "紫色");
        COLOR_NAME_TRANSLATION.put("dark_blue", "暗蓝色");
        COLOR_NAME_TRANSLATION.put("blue", "蓝色");
        COLOR_NAME_TRANSLATION.put("dark_cyan", "暗青色");
        COLOR_NAME_TRANSLATION.put("cyan", "青色");
        COLOR_NAME_TRANSLATION.put("dark_green", "暗绿色");
        COLOR_NAME_TRANSLATION.put("green", "绿色");
        COLOR_NAME_TRANSLATION.put("dark_yellow_green", "暗黄绿色");
        COLOR_NAME_TRANSLATION.put("yellow_green", "黄绿色");
        COLOR_NAME_TRANSLATION.put("yellow", "黄色");
        COLOR_NAME_TRANSLATION.put("orange", "橙色");
        COLOR_NAME_TRANSLATION.put("grey", "灰色");
    }

    public ModSimpChineseLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "zh_cn", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("itemGroup.touhou", "幻想乡追忆录 - 物品/方块");
        translationBuilder.add("itemGroup.touhou.bullet", "幻想乡追忆录 - 子弹");
        translationBuilder.add("itemGroup.touhou.fumo", "幻想乡追忆录 - Fumo");
        translationBuilder.add("itemGroup.touhou.spawn_egg", "幻想乡追忆录 - 刷怪蛋");
        translationBuilder.add("itemGroup.touhou.npc.spawn_egg", "幻想乡追忆录 - 东方角色刷怪蛋");
        translationBuilder.add(Touhou.id("recipe/danmaku_table").toTranslationKey(), "弹幕工作台");
        translationBuilder.add(Touhou.id("recipe/gensokyo_altar").toTranslationKey(), "幻想乡祭坛");
        translationBuilder.add(Touhou.id("recipe/strength_table").toTranslationKey(), "强化台");

        this.generateItemTranslations(wrapperLookup, translationBuilder);
        this.generateBlockTranslations(wrapperLookup, translationBuilder);
        this.generateFumoTranslations(wrapperLookup, translationBuilder);
        this.generateSoundTranslations(wrapperLookup, translationBuilder);
        this.generateEntityTranslations(wrapperLookup, translationBuilder);
        this.generateEffectTranslations(wrapperLookup, translationBuilder);

        translationBuilder.add("gui.npc.info", "§d");
        translationBuilder.add("gui.npc.info.name", "§d名字: %s");
        translationBuilder.add("gui.npc.info.food", "§d食物信息: ");
        translationBuilder.add("gui.npc.info.food.nutrition", "§6饥饿度: %s");
        translationBuilder.add("gui.npc.info.food.saturation", "§6饱食度: %s");
        translationBuilder.add("gui.npc.info.uuid", "§d通用唯一识别码: %s");
        translationBuilder.add("gui.npc.info.health", "§d生命值: %s/%s");
        translationBuilder.add("gui.npc.info.armor", "§d护甲值: %s");
        translationBuilder.add("gui.npc.mode.0", "§b当前模式为: §a正常");
        translationBuilder.add("gui.npc.mode.1", "§b当前模式为: §a禁止移动");
        translationBuilder.add("gui.npc.mode.2", "§b当前模式为: §a潜行");
        translationBuilder.add("gui.npc.mode.3", "§b当前模式为: §a坐下");

        LanguageKeys.SIMP_CHINESE.build(translationBuilder);
    }

    public void generateEntityTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModEntities.KILLER_BEE_ENTITY_TYPE.getTranslationKey(), "杀人蜂");
        translationBuilder.add("item." + EntityType.getId(ModEntities.KILLER_BEE_ENTITY_TYPE).toString().replaceAll(":", ".") + "_spawn_egg", "杀人蜂刷怪蛋");
        translationBuilder.add(ModEntities.GHOST_ENTITY_TYPE.getTranslationKey(), "幽灵");
        translationBuilder.add("item." + EntityType.getId(ModEntities.GHOST_ENTITY_TYPE).toString().replaceAll(":", ".") + "_spawn_egg", "幽灵刷怪蛋");
        translationBuilder.add(ModEntities.YouseiTypes.BLUE.getEntry().getTranslationKey(), "蓝色妖精");
        translationBuilder.add("item." + EntityType.getId(ModEntities.YouseiTypes.BLUE.getEntry()).toString().replaceAll(":", ".") + "_spawn_egg", "蓝色妖精刷怪蛋");
        translationBuilder.add(ModEntities.YouseiTypes.ORANGE.getEntry().getTranslationKey(), "橙色妖精");
        translationBuilder.add("item." + EntityType.getId(ModEntities.YouseiTypes.ORANGE.getEntry()).toString().replaceAll(":", ".") + "_spawn_egg", "橙色妖精刷怪蛋");
        translationBuilder.add(ModEntities.YouseiTypes.GREEN.getEntry().getTranslationKey(), "绿色妖精");
        translationBuilder.add("item." + EntityType.getId(ModEntities.YouseiTypes.GREEN.getEntry()).toString().replaceAll(":", ".") + "_spawn_egg", "绿色妖精刷怪蛋");
        translationBuilder.add(ModEntities.SUNFLOWER_YOUSEI_ENTITY_TYPE.getTranslationKey(), "向日葵妖精");
        translationBuilder.add("item." + EntityType.getId(ModEntities.SUNFLOWER_YOUSEI_ENTITY_TYPE).toString().replaceAll(":", ".") + "_spawn_egg", "向日葵妖精刷怪蛋");
        translationBuilder.add(ModEntities.GOBLIN_ENTITY_TYPE.getTranslationKey(), "哥布林");
        translationBuilder.add("item." + EntityType.getId(ModEntities.GOBLIN_ENTITY_TYPE).toString().replaceAll(":", ".") + "_spawn_egg", "哥布林刷怪蛋");

    }

    public void generateEffectTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        generateStatusEffect(translationBuilder, ModStatusEffects.ELIXIR_OF_LIFE, "不死");
        generateStatusEffect(translationBuilder, ModStatusEffects.MENTAL_DISORDER, "精神错乱");
        generateStatusEffect(translationBuilder, ModStatusEffects.BACK_OF_LIFE, "还生药");

        generatePotion(translationBuilder, ModPotions.ELIXIR_OF_LIFE_POTION, "蓬莱之药", "喷溅型蓬莱之药", "滞留型蓬莱之药");
        generatePotion(translationBuilder, ModPotions.MENTAL_DISORDER_POTION, "精神错乱药水", "喷溅型精神错乱药水", "滞留型精神错乱药水");
        generatePotion(translationBuilder, ModPotions.BACK_OF_LIFE_POTION, "还生药", "喷溅型还生药", "滞留型还生药");

    }

    public void generateItemTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        // 调试
        translationBuilder.add(ModItems.BATTLE_STICK, "战斗调试棒");

        // 图标
        translationBuilder.add(ModItems.ICON, "幻想乡追忆录　～ Reverie of Lost Dreams");
        translationBuilder.add(ModItems.FUMO_ICON, "毛绒玩偶图标");
        translationBuilder.add(ModItems.SPAWN_EGG, "刷怪蛋");

        // 材料
        translationBuilder.add(ModItems.POINT, "Point");
        translationBuilder.add(ModItems.POWER, "Power");
        translationBuilder.add(ModItems.UPGRADED_HEALTH_FRAGMENT, "Bomb碎片");
        translationBuilder.add(ModItems.BOMB_FRAGMENT, "Bomb碎片");
        translationBuilder.add(ModItems.RED_ORB, "红宝玉");
        translationBuilder.add(ModItems.BLUE_ORB, "蓝宝玉");
        translationBuilder.add(ModItems.YELLOW_ORB, "黄宝玉");
        translationBuilder.add(ModItems.GREEN_ORB, "绿宝玉");
        translationBuilder.add(ModItems.PURPLE_ORB, "紫宝玉");
        translationBuilder.add(ModItems.YIN_YANG_ORB, "阴阳玉");

        // 道具
        translationBuilder.add(ModItems.TOUHOU_HELPER, "东方模组入门");
        translationBuilder.add(ModItems.UPGRADED_HEALTH, "残机");
        translationBuilder.add(ModItems.BOMB, "Bomb");
        translationBuilder.add(ModItems.HORAI_DAMA_NO_EDA, "蓬莱玉枝");
        translationBuilder.add(ModItems.CROSSING_CHISEL, "穿墙之凿");
        translationBuilder.add(ModItems.GAP_BALL, "隙间之球");
        translationBuilder.add(ModItems.BAGUA_FURNACE, "八卦炉");
        translationBuilder.add(ModItems.TIME_STOP_CLOCK, "时停钟");

        // 武器
        translationBuilder.add(ModItems.HAKUREI_CANE, "博丽御币");
        translationBuilder.add(ModItems.WIND_BLESSING_CANE, "祝风御币");
        translationBuilder.add(ModItems.MAGIC_BROOM, "魔法扫帚");
        translationBuilder.add(ModItems.GUNGNIR, "神枪冈格尼尔");
        translationBuilder.add(ModItems.LEVATIN, "莱瓦汀");
        translationBuilder.add(ModItems.ROKANKEN, "楼观剑");
        translationBuilder.add(ModItems.HAKUROKEN, "白楼剑");
        translationBuilder.add(ModItems.PAPILIO_PATTERN_FAN, "凤蝶纹扇");
        translationBuilder.add(ModItems.IBUKIHO, "伊吹瓢");
        translationBuilder.add(ModItems.SWORD_OF_HISOU, "非想之剑");
        translationBuilder.add(ModItems.KNIFE, "飞刀");

        // 其他
        translationBuilder.add(ModItems.SHIDE, "纸垂");
        translationBuilder.add(ModItems.SHIMENAWA, "注连绳");

//        translationBuilder.add(ModItems.DEBUG_DANMAKU_ITEM, "调试弹幕");
//        translationBuilder.add(ModItems.DEBUG_SPELL_CARD_ITEM, "调试符卡");
//        translationBuilder.add(ModItems.DEBUG_SPELL_CARD_ITEM2, "调试符卡2");


        generateDanmakuItemTranslations(wrapperLookup, translationBuilder, true);
    }

    public void generateSoundTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        for (var sound : ModSoundEvents.FUMO_SOUNDS) {
            this.generateSoundEventSubtitle(translationBuilder, sound, "fumo");
        }
        this.generateSoundEventSubtitle(translationBuilder, ModSoundEvents.POINT, "收点");
        this.generateSoundEventSubtitle(translationBuilder, ModSoundEvents.SPELL_CARD, "符卡释放");
        this.generateSoundEventSubtitle(translationBuilder, ModSoundEvents.UP, "升级");
        this.generateSoundEventSubtitle(translationBuilder, ModSoundEvents.FIRE, "弹幕发射");

        this.generateDiscTranslations(wrapperLookup, translationBuilder);
    }

    public void generateDiscTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        generateJukeBox(translationBuilder, ModJukeboxSongs.HR01_01.getJukeboxSongRegistryKey(), "ZUN - 蓬莱人形　～ Dolls in Pseudo Paradise.");
        generateJukeBox(translationBuilder, ModJukeboxSongs.HR03_01.getJukeboxSongRegistryKey(), "ZUN - 童祭　～ Innocent Treasures");
        generateJukeBox(translationBuilder, ModJukeboxSongs.TH15_16.getJukeboxSongRegistryKey(), "东方绀珠传 - 前所未见的噩梦世界");
        generateJukeBox(translationBuilder, ModJukeboxSongs.TH15_17.getJukeboxSongRegistryKey(), "东方绀珠传 - Pandemonic Planet");
    }

    public void generateBlockTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModBlocks.DANMAKU_CRAFTING_TABLE, "弹幕工作台");
        translationBuilder.add(ModBlocks.GENSOKYO_ALTAR, "幻想乡祭坛");
        translationBuilder.add(ModBlocks.STRENGTH_TABLE, "强化台");
        translationBuilder.add(ModBlocks.SPIRITUAL_LOG, "绳文杉原木");
        translationBuilder.add(ModBlocks.SPIRITUAL_WOOD, "绳文杉树皮");
        translationBuilder.add(ModBlocks.STRIPPED_SPIRITUAL_LOG, "去皮绳文杉");
        translationBuilder.add(ModBlocks.STRIPPED_SPIRITUAL_WOOD, "去皮绳文杉树皮");
        translationBuilder.add(ModBlocks.SPIRITUAL_PLANKS, "绳文杉木板");
        translationBuilder.add(ModBlocks.MAGIC_ICE_BLOCK, "魔法冰");
        translationBuilder.add(ModBlocks.POINT_BLOCK, "Point方块");
        translationBuilder.add(ModBlocks.POWER_BLOCK, "Power方块");


        translationBuilder.add(ModBlocks.DREAM_RED_BLOCK, "网格方块");
        translationBuilder.add(ModBlocks.DREAM_BLUE_BLOCK, "网格方块");
    }

    public void generateFumoTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.AYA), "射命丸文Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.ALICE), "爱丽丝Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.CHEN), "八云橙Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.BLUE_REIMU), "蓝灵梦Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.CHERRIES_SHION), "依神紫苑Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.CHIMATA), "天弓千亦Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.CIRNO), "琪露诺Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.CLOWNPIECE), "克劳恩皮丝Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.EIKI), "四季映姫Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.EIRIN), "八意永琳Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.FLAN), "芙兰朵露·斯卡蕾特Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.FLANDRE), "芙兰朵露·斯卡蕾特Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.HATATE), "姬海棠果Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.JOON), "依神女苑Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.KAGUYA), "蓬莱山辉夜Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.KANMARISA), "雾雨魔理沙Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.KASEN), "茨木华扇Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.KOGASA), "多多良小伞Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.KOISHI), "古明地恋Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.KOKORO), "秦心Fumo");
//        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.KORONE), "戌神沁音Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.KOSUZU), "本居小铃Fumo");
//        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.KYOU), "Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.MARISA), "雾雨魔理沙Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.MARISA_HAT), "雾雨魔理沙Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.MCKY), "天弓千亦Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.MEILING), "红美铃Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.MELON), "伊吹萃香Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.MOKOU), "藤原妹红Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.MOMIJI), "犬走椛Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.MYSTIA), "米斯蒂娅·萝蕾拉Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.NAZRIN), "娜兹玲Fumo");
//        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.NEBULA), "Fumo");
//        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.NEBUMO), "Fumo");
//        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.NEBUO), "Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.NEW_REIMU), "博丽灵梦Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.NUE), "封兽鵺Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.PARSEE), "水桥帕露西Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.PATCHOULI), "帕秋莉·诺蕾姬Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.PC98_MARISA), "雾雨魔理沙Fumo（旧作）");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.RAN), "八云蓝Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.REIMU), "博丽灵梦Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.REISEN), "铃仙·优昙华院·因幡Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.REMILIA), "蕾米莉亚·斯卡蕾特Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.RENKO), "宇佐见莲子Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.RUMIA), "露米娅Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.SAKUYA), "十六夜咲夜Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.SANAE), "东风谷早苗Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.SATORI), "古明地觉Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.SEIJA), "鬼人正邪Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.SHION), "依神紫苑Fumo");
//        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.SHIRO), "Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.SHOU), "寅丸星Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.SMART_CIRNO), "小琪露诺Fumo");
//        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.SNAILCHAN), "Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.SUIKA), "伊吹萃香Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.SUWAKO), "洩矢诹访子Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.TAN_CIRNO), "大琪露诺Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.TENSHI), "比那名居天子Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.TEWI), "因幡帝Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.UTSUHO), "灵乌路空Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.WAKASAGIHIME), "若鹭姬Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.YOUMU), "魂魄妖梦Fumo");
//        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.YUE), "Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.YUKARI), "八云紫Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.YUUKA), "风见幽香Fumo");
        translationBuilder.add(unpackBlockObjectContainer(FumoBlocks.FumoEnum.YUYUKO), "西行寺幽幽子Fumo");
    }

    public Block unpackBlockObjectContainer(FumoBlocks.FumoEnum fumoEnum) {
        return fumoEnum.getBlockObjectContainer().getValue();
    }

    public void generateDanmakuItemTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder, boolean useChinese) {
        Map<String, String> bulletTranslations = new HashMap<>();
        bulletTranslations.put("amulet", "札弹");
        bulletTranslations.put("arrow", "箭弹");
        bulletTranslations.put("arrowhead", "鳞弹");
        bulletTranslations.put("bacteria", "杆菌弹");
        bulletTranslations.put("ball", "小玉");
        bulletTranslations.put("ball_outlined", "小玉（描边）");
        bulletTranslations.put("bubble", "大玉");
        bulletTranslations.put("bullet", "铳弹");
        bulletTranslations.put("butterfly", "蝶弹");
        bulletTranslations.put("coin", "钱币弹");
        bulletTranslations.put("fireball", "火光弹");
        bulletTranslations.put("fireball_glowy", "水光弹");
        bulletTranslations.put("heart", "心弹");
        bulletTranslations.put("jellybean", "椭弹");
        bulletTranslations.put("knife", "刀弹");
        bulletTranslations.put("kunai", "链弹");
        bulletTranslations.put("mentos", "中玉");
        bulletTranslations.put("note", "音符弹");
        bulletTranslations.put("orb", "光玉");
        bulletTranslations.put("pellet", "点弹");
        bulletTranslations.put("popcorn", "菌弹");
        bulletTranslations.put("raindrop", "滴弹");
        bulletTranslations.put("rest", "音符弹（休止符）");
        bulletTranslations.put("rice", "米弹");
        bulletTranslations.put("rose", "蔷薇弹");
        bulletTranslations.put("shard", "棱角米弹");
        bulletTranslations.put("star", "星弹");
        bulletTranslations.put("star_big", "大星弹");
        bulletTranslations.put("star_small", "小星弹");

        for (DanmakuItemEntries itemEntry : ModItems.DANMAKU_ITEMS) {
            List<BasicDanmakuItem> values = itemEntry.getValues();
            for (BasicDanmakuItem item : values) {
                Identifier identifier = Registries.ITEM.getId(item);

                String path = identifier.getPath();
                String[] parts = path.split("/");

                String key = parts[parts.length - 2];

                String translationString = bulletTranslations.getOrDefault(key, "null");
                translationBuilder.add(item, translationString);
            }
        }
    }
}
