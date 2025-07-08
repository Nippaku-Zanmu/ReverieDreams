package cc.thonly.reverie_dreams.entity.npc;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.entity.skin.RoleSkins;
import cc.thonly.reverie_dreams.registry.RegistryManager;
import cc.thonly.reverie_dreams.registry.StandaloneRegistry;
import net.minecraft.util.Identifier;

public class NPCRoles extends AbstractSkinBootstrap {
    private static final StandaloneRegistry<NPCRole> REGISTRY_KEY = RegistryManager.NPC_ROLE;

    // 主角组
    public static final NPCRole REIMU = registerRole(new NPCRole(Touhou.id("reimu"), RoleSkins.REIMU));
    public static final NPCRole CYAN_REIMU = registerRole(new NPCRole(Touhou.id("cyan_reimu"), RoleSkins.CYAN_REIMU));
    public static final NPCRole MARISA = registerRole(new NPCRole(Touhou.id("marisa"), RoleSkins.MARISA));

    // 红魔乡
    public static final NPCRole RUMIA = registerRole(new NPCRole(Touhou.id("rumia"), RoleSkins.RUMIA));
    public static final NPCRole CIRNO = registerRole(new NPCRole(Touhou.id("cirno"), RoleSkins.CIRNO));
    public static final NPCRole MEIRIN = registerRole(new NPCRole(Touhou.id("meirin"), RoleSkins.HOAN_MEIRIN));
    public static final NPCRole PATCHOULI = registerRole(new NPCRole(Touhou.id("patchouli"), RoleSkins.PATCHOULI));
    public static final NPCRole SAKUYA = registerRole(new NPCRole(Touhou.id("sakuya"), RoleSkins.SAKUYA));
    public static final NPCRole REMILIA = registerRole(new NPCRole(Touhou.id("remilia"), RoleSkins.REMILA));
    public static final NPCRole FLANDRE = registerRole(new NPCRole(Touhou.id("flandre"), RoleSkins.FLANDRE));

    // 妖妖梦
    public static final NPCRole LETTY_WHITEROCK = registerRole(new NPCRole(Touhou.id("letty_whiterock"), RoleSkins.LETTY_WHITEROCK));
    public static final NPCRole CHEN = registerRole(new NPCRole(Touhou.id("chen"), RoleSkins.CHEN));
    public static final NPCRole ALICE = registerRole(new NPCRole(Touhou.id("alice"), RoleSkins.ALICE));
    public static final NPCRole LILY_WHITE = registerRole(new NPCRole(Touhou.id("lily_white"), RoleSkins.LILY_WHITE));
    public static final NPCRole LUNASA_PRISMRIVER = registerRole(new NPCRole(Touhou.id("lunasa_prismriver"), RoleSkins.LUNASA_PRISMRIVER));
    public static final NPCRole MERLIN_PRISMRIVER = registerRole(new NPCRole(Touhou.id("merlin_prismriver"), RoleSkins.MERLIN_PRISMRIVER));
    public static final NPCRole LYRICA_PRISMRIVER = registerRole(new NPCRole(Touhou.id("lyrica_prismriver"), RoleSkins.LYRICA_PRISMRIVER));
    public static final NPCRole RAN = registerRole(new NPCRole(Touhou.id("ran"), RoleSkins.RAN));
    public static final NPCRole YOUMU = registerRole(new NPCRole(Touhou.id("youmu"), RoleSkins.YOUMU));
    public static final NPCRole YUYUKO = registerRole(new NPCRole(Touhou.id("yuyuko"), RoleSkins.YUYUKO));
    public static final NPCRole YUKARI = registerRole(new NPCRole(Touhou.id("yakumo_yukai"), RoleSkins.YAKUMO_YUKAI));

    // 永夜抄
    public static final NPCRole MYSTIA_LORELEI = registerRole(new NPCRole(Touhou.id("mystia_lorelei"), RoleSkins.MYSTIA_LORELEI));
    public static final NPCRole WRIGGLE_NIGHTBUG = registerRole(new NPCRole(Touhou.id("wriggle_nightbug"), RoleSkins.WRIGGLE_NIGHTBUG));
    public static final NPCRole KAMISHIRASAWA_KEINE = registerRole(new NPCRole(Touhou.id("kamishirasawa_keine"), RoleSkins.KAMISHIRASAWA_KEINE));
    public static final NPCRole REISEN = registerRole(new NPCRole(Touhou.id("reisen"), RoleSkins.REISEN));
    public static final NPCRole ERIN = registerRole(new NPCRole(Touhou.id("erin"), RoleSkins.ERIN));
    public static final NPCRole HOURAISAN_KAGUYA = registerRole(new NPCRole(Touhou.id("houraisan_kaguya"), RoleSkins.HOURAISAN_KAGUYA));
    public static final NPCRole HUZIWARA_NO_MOKOU = registerRole(new NPCRole(Touhou.id("huziwara_no_mokou"), RoleSkins.HUZIWARA_NO_MOKOU));

    // 花映塚
    public static final NPCRole SHIKIEIKI_YAMAXANADU = registerRole(new NPCRole(Touhou.id("shikieiki_yamaxanadu"), RoleSkins.SHIKIEIKI_YAMAXANADU));
    public static final NPCRole KAZAMI_YUKA = registerRole(new NPCRole(Touhou.id("kazami_yuka"), RoleSkins.KAZAMI_YUKA));

    // 风神录
    public static final NPCRole KAGIYAMA_HINA = registerRole(new NPCRole(Touhou.id("kagiyama_hina"), RoleSkins.KAGIYAMA_HINA));
    public static final NPCRole INUBASHIRI_MOMIZI = registerRole(new NPCRole(Touhou.id("inubashiri_momizi"), RoleSkins.INUBASHIRI_MOMIZI));
    public static final NPCRole KAWASIRO_NITORI = registerRole(new NPCRole(Touhou.id("kawasiro_nitori"), RoleSkins.KAWASIRO_NITORI));
    public static final NPCRole AYA = registerRole(new NPCRole(Touhou.id("aya"), RoleSkins.AYA));
    public static final NPCRole KOCHIYA_SANAE = registerRole(new NPCRole(Touhou.id("kochiuya_sanae"), RoleSkins.KOCHIYA_SANAE));
    public static final NPCRole YASAKA_KANAKO = registerRole(new NPCRole(Touhou.id("yasaka_kanako"), RoleSkins.YASAKA_KANAKO));
    public static final NPCRole MORIYA_SUWAKO = registerRole(new NPCRole(Touhou.id("moriya_suwako"), RoleSkins.MORIYA_SUWAKO));

    // 地灵殿
    public static final NPCRole KISUME = registerRole(new NPCRole(Touhou.id("kisume"), RoleSkins.KISUME));
    public static final NPCRole KURODANI_YAMAME = registerRole(new NPCRole(Touhou.id("kurodani_yamame"), RoleSkins.KURODANI_YAMAME));
    public static final NPCRole MIZUHASHI_PARSEE = registerRole(new NPCRole(Touhou.id("mizuhashi_parsee"), RoleSkins.MIZUHASHI_PARSEE));
    public static final NPCRole HOSHIGUMA_YUGI = registerRole(new NPCRole(Touhou.id("hoshiguma_yugi"), RoleSkins.HOSHIGUMA_YUGI));
    public static final NPCRole KAENBYOU_RIN = registerRole(new NPCRole(Touhou.id("kaenbyou_rin"), RoleSkins.KAENBYOU_RIN));
    public static final NPCRole KOMEIJI_SATORI = registerRole(new NPCRole(Touhou.id("komeiji_satori"), RoleSkins.KOMEIJI_SATORI));
    public static final NPCRole REIUJI_UTSUH = registerRole(new NPCRole(Touhou.id("reiuji_utsuh"), RoleSkins.REIUJI_UTSUH));
    public static final NPCRole KOMEIJI_KOISHI = registerRole(new NPCRole(Touhou.id("komeiji_koishi"), RoleSkins.KOMEIJI_KOISHI));

    // 星莲船
    public static final NPCRole NAZRIN = registerRole(new NPCRole(Touhou.id("nazrin"), RoleSkins.NAZRIN));
    public static final NPCRole TATARA_KOGASA = registerRole(new NPCRole(Touhou.id("tatara_kogasa"), RoleSkins.TATARA_KOGASA));
    public static final NPCRole NUE = registerRole(new NPCRole(Touhou.id("nue"), RoleSkins.NUE));

    // 神灵庙
    public static final NPCRole KASODANI_KYOUKO = registerRole(new NPCRole(Touhou.id("kasodani_kyouko"), RoleSkins.KASODANI_KYOUKO));
    public static final NPCRole MIYAKO_YOSHIKA = registerRole(new NPCRole(Touhou.id("miyako_yoshika"), RoleSkins.MIYAKO_YOSHIKA));
    public static final NPCRole KAKU_SEIGA = registerRole(new NPCRole(Touhou.id("kaku_seiga"), RoleSkins.KAKU_SEIGA));
    public static final NPCRole SOGA_NO_TOZIKO = registerRole(new NPCRole(Touhou.id("soga_no_toziko"), RoleSkins.SOGA_NO_TOZIKO));
    public static final NPCRole MONONOBE_NO_FUTO = registerRole(new NPCRole(Touhou.id("mononobe_no_futo"), RoleSkins.MONONOBE_NO_FUTO));
    public static final NPCRole TOYOSATOMIMI_NO_MIKO = registerRole(new NPCRole(Touhou.id("toyosatomimi_no_miko"), RoleSkins.TOYOSATOMIMI_NO_MIKO));
    public static final NPCRole HOUJUU_NUE = registerRole(new NPCRole(Touhou.id("houjuu_nue"), RoleSkins.HOUJUU_NUE));
    public static final NPCRole HUTATSUIWA_MAMIZOU = registerRole(new NPCRole(Touhou.id("hutatsuiwa_mamizou"), RoleSkins.HUTATSUIWA_MAMIZOU));

    // 三月精
    public static final NPCRole STAR = registerRole(new NPCRole(Touhou.id("star"), RoleSkins.STAR));
    public static final NPCRole LUNAR = registerRole(new NPCRole(Touhou.id("lunar"), RoleSkins.LUNAR));
    public static final NPCRole SUNNY = registerRole(new NPCRole(Touhou.id("sunny"), RoleSkins.SUNNY));

    // 秘封
    public static final NPCRole USAMI_RENKO = registerRole(new NPCRole(Touhou.id("usami_renko"), RoleSkins.USAMI_RENKO));
    public static final NPCRole MARIBEL_HEARN = registerRole(new NPCRole(Touhou.id("maribel_hearn"), RoleSkins.MARIBEL_HEARN));

    // 黄昏边境
    public static final NPCRole SUIKA = registerRole(new NPCRole(Touhou.id("suika"), RoleSkins.SUIKA));
    public static final NPCRole TENSHI = registerRole(new NPCRole(Touhou.id("tenshi"), RoleSkins.TENSHI));

    // ...

    public static void bootstrap(StandaloneRegistry<NPCRole> registry) {

    }

    public static NPCRole registerRole(NPCRole role) {
        return registerRole(role.getId(), role);
    }

    public static NPCRole registerRole(String name, NPCRole role) {
        return registerRole(Touhou.id(name), role);
    }

    public static NPCRole registerRole(Identifier id, NPCRole role) {
        NPCRole entry = RegistryManager.register(REGISTRY_KEY, id, role);
        return entry.build();
    }
}
