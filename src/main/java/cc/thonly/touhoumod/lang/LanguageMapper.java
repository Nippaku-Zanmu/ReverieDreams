package cc.thonly.touhoumod.lang;

import cc.thonly.touhoumod.entity.ModEntities.NPCEntityTypes;
import lombok.Getter;

@Getter
public class LanguageMapper {
    public static void mapInit() {
        // 简体中文
        LanguageKeys simpChinese = LanguageKeys.SIMP_CHINESE;

        // 主角组
        simpChinese.addNpcEntity(NPCEntityTypes.REIMU, "博丽灵梦", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.CYAN_REIMU, "青灵梦", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.MARISA, "雾雨魔理沙", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.RUMIA, "露米娅", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.CIRNO, "琪露诺", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.MEIRIN, "红美铃", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.PATCHOULI, "帕秋莉·诺蕾姬", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.SAKUYA, "十六夜咲夜", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.REMILIA, "蕾米莉亚·斯卡蕾特", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.FLANDRE, "芙兰朵露·斯卡蕾特", "刷怪蛋");

        // 妖妖梦
        simpChinese.addNpcEntity(NPCEntityTypes.LETTY_WHITEROCK, "蕾蒂·霍瓦特洛克", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.CHEN, "八云橙", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.ALICE, "爱丽丝·玛格特罗依德", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.LILY_WHITE, "莉莉白", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.LUNASA_PRISMRIVER, "露娜萨·普莉兹姆利巴", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.MERLIN_PRISMRIVER, "梅露兰·普莉兹姆利巴", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.LYRICA_PRISMRIVER, "莉莉卡·普莉兹姆利巴", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.RAN, "八云蓝", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.YOUMU, "魂魄妖梦", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.YUYUKO, "西行寺幽幽子", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.YUKARI, "八云紫", "刷怪蛋");

        // 永夜抄
        simpChinese.addNpcEntity(NPCEntityTypes.MYSTIA_LORELEI, "米斯蒂娅·萝蕾拉", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.WRIGGLE_NIGHTBUG, "莉格露·奈特巴格", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.KAMISHIRASAWA_KEINE, "上白泽慧音", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.REISEN, "铃仙·优昙华院·因幡", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.ERIN, "八意永琳", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.HOURAISAN_KAGUYA, "蓬莱山辉夜", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.HUZIWARA_NO_MOKOU, "藤原妹红", "刷怪蛋");

        // 花映塚
        simpChinese.addNpcEntity(NPCEntityTypes.SHIKIEIKI_YAMAXANADU, "四季映姬·夜摩仙那度", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.KAZAMI_YUKA, "风见幽香", "刷怪蛋");

        // 风神录
        simpChinese.addNpcEntity(NPCEntityTypes.KAGIYAMA_HINA, "键山雏", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.INUBASHIRI_MOMIZI, "犬走椛", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.KAWASIRO_NITORI, "河城荷取", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.AYA, "射命丸文", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.KOCHIYA_SANAE, "东风谷早苗", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.YASAKA_KANAKO, "八坂神奈子", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.MORIYA_SUWAKO, "洩矢诹访子", "刷怪蛋");

        // 地灵殿
        simpChinese.addNpcEntity(NPCEntityTypes.KISUME, "琪斯美", "刷怪蛋");
//        simpChinese.addNpcEntity(NPCEntityTypes.KURODANI_YAMAME, "黑山谷女", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.MIZUHASHI_PARSEE, "水桥帕露西", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.HOSHIGUMA_YUGI, "星熊勇仪", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.KAENBYOU_RIN, "火焰猫燐", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.KOMEIJI_SATORI, "古明地觉", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.REIUJI_UTSUH, "灵乌路空", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.KOMEIJI_KOISHI, "古明地恋", "刷怪蛋");

        // 神灵庙
        simpChinese.addNpcEntity(NPCEntityTypes.KASODANI_KYOUKO, "幽谷响子", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.TATARA_KOGASA, "多多良小伞", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.MIYAKO_YOSHIKA, "宫古芳香", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.KAKU_SEIGA, "霍青娥", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.SOGA_NO_TOZIKO, "苏我屠自古", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.MONONOBE_NO_FUTO, "物部布都", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.TOYOSATOMIMI_NO_MIKO, "丰聪耳神子", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.HOUJUU_NUE, "封兽鵺", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.HUTATSUIWA_MAMIZOU, "二岩猯藏", "刷怪蛋");

        // 星莲船

        // 三月精
        simpChinese.addNpcEntity(NPCEntityTypes.STAR, "斯塔·萨菲雅", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.LUNAR, "露娜·切露德", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.SUNNY, "桑尼·米尔克", "刷怪蛋");

        // 其他
        simpChinese.addNpcEntity(NPCEntityTypes.USAMI_RENKO, "宇佐见莲子", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.MARIBEL_HEARN, "玛艾露贝莉·赫恩", "刷怪蛋");

        // 黄昏
        simpChinese.addNpcEntity(NPCEntityTypes.SUIKA, "伊吹萃香", "刷怪蛋");
        simpChinese.addNpcEntity(NPCEntityTypes.TENSHI, "比那名居天子", "刷怪蛋");

        // 英文
        LanguageKeys english = LanguageKeys.ENGLISH;
        for (NPCEntityTypes entity : NPCEntityTypes.values()) {
            String id = entity.getId();
            String[] parts = id.split("_");
            StringBuilder nameBuilder = new StringBuilder();
            for (String part : parts) {
                if (!part.isEmpty()) {
                    nameBuilder.append(Character.toUpperCase(part.charAt(0)))
                            .append(part.substring(1).toLowerCase())
                            .append(" ");
                }
            }
            String displayName = nameBuilder.toString().trim();
            english.addNpcEntity(entity, displayName, " Spawn Egg");
        }
    }
}
