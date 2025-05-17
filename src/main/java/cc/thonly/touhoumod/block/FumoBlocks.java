package cc.thonly.touhoumod.block;

import cc.thonly.touhoumod.item.BasicBlockItem;
import cc.thonly.touhoumod.sound.ModSoundEvents;
import cc.thonly.touhoumod.util.IdentifierGetter;
import cc.thonly.touhoumod.util.ObjectContainer;
import lombok.Getter;
import lombok.ToString;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FumoBlocks {
    protected static final List<Block> FUMO_LIST = new ArrayList<>();

    @Getter
    @ToString
    public enum FumoEnum {
        ALICE(new Vec3d(0, 0, 0), "alice"),
        AYA(new Vec3d(0, 0, 0), "aya"),
        BLUE_REIMU(new Vec3d(0, 0, 0), "blue_reimu"),
        CHEN(new Vec3d(0, 0, 0), "chen"),
        CHERRIES_SHION(new Vec3d(0, 0, 0), "cherries_shion"),
        CHIMATA(new Vec3d(0, 0, 0), "chimata"),
        CIRNO(new Vec3d(0, 0, 0), "cirno"),
        CLOWNPIECE(new Vec3d(0, 0, 0), "clownpiece"),
        EIKI(new Vec3d(0, -0.26, 0), "eiki"),
        EIRIN(new Vec3d(0, -0.26, 0), "eirin"),
        FLAN(new Vec3d(0, 0, 0), "flan"),
        FLANDRE(new Vec3d(0, -0.26, 0), "flandre"),
//        FLONNE(new Vec3d(0, 0, 0), "flonne"),
        //        FUMOITEM("fumoitem"),
        HATATE(new Vec3d(0, 0, 0), "hatate"),
        JOON(new Vec3d(0, -0.26, 0), "joon"),
        KAGUYA(new Vec3d(0, 0, 0), "kaguya"),
        KANMARISA(new Vec3d(0, 0, 0), "kanmarisa"),
        KASEN(new Vec3d(0, -0.26, 0), "kasen"),
        KOGASA(new Vec3d(0, -0.26, 0), "kogasa"),
        KOISHI(new Vec3d(0, 0, 0), "koishi"),
        KOKORO(new Vec3d(0, 0, 0), "kokoro"),
//        KORONE(new Vec3d(0, 0, 0), "korone"),
        KOSUZU(new Vec3d(0, -0.26, 0), "kosuzu"),
//        KYOU(new Vec3d(0, 0, 0), "kyou"),
        MARISA(new Vec3d(0, 0, 0), "marisa"),
        MARISA_HAT(new Vec3d(0, 0, 0), "marisa_hat"),
        MCKY(new Vec3d(0, 0, 0), "mcky"),
        MEILING(new Vec3d(0, -0.26, 0), "meiling"),
        MELON(new Vec3d(0, 0, 0), "melon"),
        MOKOU(new Vec3d(0, 0, 0), "mokou"),
        MOMIJI(new Vec3d(0, 0, 0), "momiji"),
        MYSTIA(new Vec3d(0, 0, 0), "mystia"),
        NAZRIN(new Vec3d(0, 0, 0), "nazrin"),
//        NEBULA(new Vec3d(0, 0, 0), "nebula"),
//        NEBUMO(new Vec3d(0, 0, 0), "nebumo"),
//        NEBUO(new Vec3d(0, 0, 0), "nebuo"),
        NEW_REIMU(new Vec3d(0, 0, 0), "new_reimu"),
        //        NITORI("nitori"),
        NUE(new Vec3d(0, 0, 0), "nue"),
        PARSEE(new Vec3d(0, 0, 0), "parsee"),
        PATCHOULI(new Vec3d(0, 0, 0), "patchouli"),
        PC98_MARISA(new Vec3d(0, 0, 0), "pc98_marisa"),
        RAN(new Vec3d(0, 0, 0), "ran"),
        REIMU(new Vec3d(0, 0, 0), "reimu"),
        REISEN(new Vec3d(0, 0, 0), "reisen"),
        REMILIA(new Vec3d(0, 0, 0), "remilia"),
        RENKO(new Vec3d(0, 0, 0), "renko"),
        RUMIA(new Vec3d(0, 0, 0), "rumia"),
        SAKUYA(new Vec3d(0, 0, 0), "sakuya"),
        SANAE(new Vec3d(0, 0, 0), "sanae"),
        SATORI(new Vec3d(0, 0, 0), "satori"),
        SEIJA(new Vec3d(0, 0, 0), "seija"),
        SHION(new Vec3d(0, 0, 0), "shion"),
//        SHIRO(new Vec3d(0, 0, 0), "shiro"),
        SHOU(new Vec3d(0, 0, 0), "shou"),
        SMART_CIRNO(new Vec3d(0, 0, 0), "smart_cirno"),
//        SNAILCHAN(new Vec3d(0, 0, 0), "snailchan"),
        SUIKA(new Vec3d(0, 0, 0), "suika"),
        //        SUNNY_MILK("sunny_milk"),
        SUWAKO(new Vec3d(0, 0, 0), "suwako"),
        TAN_CIRNO(new Vec3d(0, 0, 0), "tan_cirno"),
        TENSHI(new Vec3d(0, 0, 0), "tenshi"),
        TEWI(new Vec3d(0, 0, 0), "tewi"),
        UTSUHO(new Vec3d(0, 0, 0), "utsuho"),
        WAKASAGIHIME(new Vec3d(0, 0, 0), "wakasagihime"),
        YOUMU(new Vec3d(0, 0, 0), "youmu"),
//        YUE(new Vec3d(0, 0, 0), "yue"),
        YUKARI(new Vec3d(0, 0, 0), "yukari"),
        YUUKA(new Vec3d(0, 0, 0), "yuuka"),
        YUYUKO(new Vec3d(0, 0, 0), "yuyuko");

        final ObjectContainer<BasicFumoBlock> blockObjectContainer;
        final Vec3d offset;
        final String id;

        FumoEnum(Vec3d offset, String id) {
            this.offset = offset;
            this.blockObjectContainer = new ObjectContainer<>();
            this.id = id;
        }

        public static FumoEnum getBlock(String fumo_id) {
            return Arrays.stream(values())
                    .filter(fumo -> fumo.id.equals(fumo_id))
                    .findFirst()
                    .orElse(null);
        }
    }

    public static void registerFumoBlocks() {
        FumoEnum[] fumoEnums = FumoEnum.values();
        for (FumoEnum fumoEnum : fumoEnums) {
            String id = "fumo/" + fumoEnum.getId();
            Vec3d offset = fumoEnum.getOffset();
            Block block = registerBlock(new BasicFumoBlock(id, offset, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL)));
            fumoEnum.getBlockObjectContainer().setValue((BasicFumoBlock) block);
        }
    }

    public static Block registerBlock(IdentifierGetter block) {
        Registry.register(Registries.BLOCK, block.getIdentifier(), (Block) block);
        Registry.register(Registries.ITEM, block.getIdentifier(), new BasicBlockItem(block.getIdentifier(), (Block) block, Items.FIREWORK_ROCKET, new Item.Settings()) {
            @Override
            public ActionResult useOnBlock(ItemUsageContext context) {
                super.useOnBlock(context);
                if (!context.getWorld().isClient) {
                    return ActionResult.SUCCESS_SERVER;
                }
                return ActionResult.SUCCESS;
            }

            @Override
            public ActionResult use(World world, PlayerEntity user, Hand hand) {
                super.use(world, user, hand);
                if (!world.isClient) {
                    world.playSound(null, user.getBlockPos(), ModSoundEvents.randomFumo(), SoundCategory.BLOCKS, 1f, 1);
                    return ActionResult.SUCCESS_SERVER;
                }
                return ActionResult.SUCCESS;
            }
        });
        FUMO_LIST.add((Block) block);
        return (Block) block;
    }

    public static List<Block> getRegisteredFumo() {
        return List.copyOf(FUMO_LIST);
    }
    public static List<Item> getRegisteredFumoItems() {
        return List.copyOf(FUMO_LIST.stream().map(Block::asItem).toList());
    }
}
