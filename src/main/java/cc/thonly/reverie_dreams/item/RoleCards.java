package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.entity.npc.NPCRoles;
import cc.thonly.reverie_dreams.registry.RegistryManager;
import cc.thonly.reverie_dreams.registry.StandaloneRegistry;
import net.minecraft.util.Identifier;

import java.util.List;

public class RoleCards {
    private static final StandaloneRegistry<RoleCard> REGISTRY_KEY = RegistryManager.ROLE_CARD;
    public static final RoleCard PROTAGONIST_GROUP = register(new RoleCard(Touhou.id("protagonist_group"),
            16727357L,
            List.of(NPCRoles.REIMU,
                    NPCRoles.CYAN_REIMU,
                    NPCRoles.MARISA)
    ).build());
    public static final RoleCard KOUMAKYOU = register(new RoleCard(Touhou.id("koumakyou"),
            16716820L,
            List.of(NPCRoles.RUMIA,
                    NPCRoles.CIRNO,
                    NPCRoles.MEIRIN,
                    NPCRoles.PATCHOULI,
                    NPCRoles.SAKUYA,
                    NPCRoles.REMILIA,
                    NPCRoles.FLANDRE)
    ).build());
    public static final RoleCard YOUYOUMU = register(new RoleCard(Touhou.id("youyoumu"),
            16717035L,
            List.of(NPCRoles.LETTY_WHITEROCK,
                    NPCRoles.CHEN,
                    NPCRoles.ALICE,
                    NPCRoles.LILY_WHITE,
                    NPCRoles.LUNASA_PRISMRIVER,
                    NPCRoles.MERLIN_PRISMRIVER,
                    NPCRoles.LYRICA_PRISMRIVER,
                    NPCRoles.RAN,
                    NPCRoles.YOUMU,
                    NPCRoles.YUYUKO,
                    NPCRoles.YUKARI)
    ).build());
    public static final RoleCard EIYASHOU = register(new RoleCard(Touhou.id("eiyashou"),
            6160593L,
            List.of(NPCRoles.MYSTIA_LORELEI,
                    NPCRoles.WRIGGLE_NIGHTBUG,
                    NPCRoles.KAMISHIRASAWA_KEINE,
                    NPCRoles.REISEN,
                    NPCRoles.ERIN,
                    NPCRoles.HOURAISAN_KAGUYA,
                    NPCRoles.HUZIWARA_NO_MOKOU)
    ).build());
    public static final RoleCard KAEIZUKA = register(new RoleCard(Touhou.id("kaeizuka"),
            53595L,
            List.of(NPCRoles.SHIKIEIKI_YAMAXANADU,
                    NPCRoles.KAZAMI_YUKA)
    ).build());
    public static final RoleCard FUUJINROKU = register(new RoleCard(Touhou.id("fuujinroku"),
            6934784L,
            List.of(NPCRoles.KAGIYAMA_HINA,
                    NPCRoles.INUBASHIRI_MOMIZI,
                    NPCRoles.KAWASIRO_NITORI,
                    NPCRoles.AYA,
                    NPCRoles.KOCHIYA_SANAE,
                    NPCRoles.YASAKA_KANAKO,
                    NPCRoles.MORIYA_SUWAKO)
    ).build());
    public static final RoleCard CHIREIDEN = register(new RoleCard(Touhou.id("chireiden"),
            41777L,
            List.of(NPCRoles.KISUME,
                    NPCRoles.KURODANI_YAMAME,
                    NPCRoles.MIZUHASHI_PARSEE,
                    NPCRoles.HOSHIGUMA_YUGI,
                    NPCRoles.KAENBYOU_RIN,
                    NPCRoles.KOMEIJI_SATORI,
                    NPCRoles.REIUJI_UTSUH,
                    NPCRoles.KOMEIJI_KOISHI)
    ).build());
    public static final RoleCard SEIRENSEN = register(new RoleCard(Touhou.id("seirensen"),
            1506915L,
            List.of(NPCRoles.NAZRIN,
                    NPCRoles.TATARA_KOGASA,
                    NPCRoles.NUE)
    ).build());
    public static final RoleCard SHINREIBYOU = register(new RoleCard(Touhou.id("shinreibyou"),
            16775603L,
            List.of(NPCRoles.KASODANI_KYOUKO,
                    NPCRoles.MIYAKO_YOSHIKA,
                    NPCRoles.KAKU_SEIGA,
                    NPCRoles.SOGA_NO_TOZIKO,
                    NPCRoles.MONONOBE_NO_FUTO,
                    NPCRoles.TOYOSATOMIMI_NO_MIKO,
                    NPCRoles.HOUJUU_NUE,
                    NPCRoles.HUTATSUIWA_MAMIZOU)
    ).build());
    public static final RoleCard KISHINJOU = register(new RoleCard(Touhou.id("kishinjou"),
            16761692L,
            List.of()
    ).build());
    public static final RoleCard KANJUDEN = register(new RoleCard(Touhou.id("kanjuden"),
            14024704L,
            List.of()
    ).build());
    public static final RoleCard TENKUUSHOU = register(new RoleCard(Touhou.id("tenkuushou"),
            6750023L,
            List.of()
    ).build());
    public static final RoleCard KIKEIJUU = register(new RoleCard(Touhou.id("kikeijuu"),
            12386304L,
            List.of()
    ).build());
    public static final RoleCard KOURYUUDOU = register(new RoleCard(Touhou.id("kouryuudou"),
            15853109L,
            List.of()
    ).build());
    public static final RoleCard JUUOUEN = register(new RoleCard(Touhou.id("juuouen"),
            7252587L,
            List.of()
    ).build());
    public static final RoleCard KINJOUKYOU = register(new RoleCard(Touhou.id("kinjoukyou"),
            12969971L,
            List.of()
    ).build());

    // 其他作品
    public static final RoleCard SANGETSUSEI = register(new RoleCard(Touhou.id("sangetsusei"),
            16770140L,
            List.of(
                    NPCRoles.STAR,
                    NPCRoles.LUNAR,
                    NPCRoles.SUNNY
            )
    ).build());
    public static final RoleCard HIFUU = register(new RoleCard(Touhou.id("hifuu"),
            8931582L,
            List.of(
                    NPCRoles.USAMI_RENKO,
                    NPCRoles.MARIBEL_HEARN
            )
    ).build());
    public static final RoleCard TASOGARE_FURONTIA = register(new RoleCard(Touhou.id("tasogare_furontia"),
            16683336L,
            List.of(
                    NPCRoles.SUIKA,
                    NPCRoles.TENSHI
            )
    ).build());

    public static RoleCard register(RoleCard roleCard) {
        return register(roleCard.getId(), roleCard);
    }

    public static RoleCard register(String name, RoleCard roleCard) {
        return register(Touhou.id(name), roleCard);
    }

    public static RoleCard register(Identifier key, RoleCard roleCard) {
        return RegistryManager.register(REGISTRY_KEY, key, roleCard);
    }

    public static void bootstrap(StandaloneRegistry<RoleCard> registry) {

    }
}
