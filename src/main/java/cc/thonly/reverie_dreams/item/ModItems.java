package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.armor.EarphoneArmorMaterial;
import cc.thonly.reverie_dreams.armor.KoishiHatArmorMaterial;
import cc.thonly.reverie_dreams.armor.SilverArmorMaterial;
import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.component.ModDataComponentTypes;
import cc.thonly.reverie_dreams.item.armor.BasicArmorItem;
import cc.thonly.reverie_dreams.item.armor.EarphoneItem;
import cc.thonly.reverie_dreams.item.armor.KoishiHatItem;
import cc.thonly.reverie_dreams.item.base.BasicPolymerBlockItem;
import cc.thonly.reverie_dreams.item.base.BasicPolymerDiscItem;
import cc.thonly.reverie_dreams.item.debug.BattleStick;
import cc.thonly.reverie_dreams.item.entry.DanmakuColor;
import cc.thonly.reverie_dreams.item.entry.DanmakuItemType;
import cc.thonly.reverie_dreams.item.tool.*;
import cc.thonly.reverie_dreams.item.weapon.*;
import cc.thonly.reverie_dreams.sound.ModJukeboxSongs;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ModItems {
    private static final List<Item> ICON_LIST = new ArrayList<>();
    private static final List<Item> ITEM_LIST = new ArrayList<>();
    private static final List<Item> DISC_LIST = new ArrayList<>();
    private static final List<Item> DANMAKU_LIST = new ArrayList<>();

    // 调试
    public static final Item BATTLE_STICK = registerItemWithNotGroup(new BattleStick("battle_stick", new Item.Settings()));

    // 图标
    public static final Item ICON = registerIconItem(new BasicItem("icon", new Item.Settings()));
    public static final Item FUMO_ICON = registerIconItem(new BasicItem("fumo_icon", new Item.Settings()));
    public static final Item ROLE_ICON = registerIconItem(new BasicItem("role_icon", new Item.Settings()));
    public static final Item SPAWN_EGG = registerIconItem(new BasicItem("spawn_egg", new Item.Settings()));

    // 材料
    public static final Item POINT = registerItem(new BasicItem("point", new Item.Settings()));
    public static final Item POWER = registerItem(new BasicItem("power", new Item.Settings()));
    public static final Item UPGRADED_HEALTH_FRAGMENT = registerItem(new BasicItem("upgraded_health_fragment", new Item.Settings()));
    public static final Item BOMB_FRAGMENT = registerItem(new BasicItem("bomb_fragment", new Item.Settings()));
    public static final Item SHIDE = registerItem(new ShideItem("shide", new Item.Settings()));
    public static final Item SHIMENAWA = registerItem(new BasicItem("shimenawa", new Item.Settings()));
    public static final Item RED_ORB = registerItem(new BasicItem("red_orb", new Item.Settings()));
    public static final Item BLUE_ORB = registerItem(new BasicItem("blue_orb", new Item.Settings()));
    public static final Item YELLOW_ORB = registerItem(new BasicItem("yellow_orb", new Item.Settings()));
    public static final Item GREEN_ORB = registerItem(new BasicItem("green_orb", new Item.Settings()));
    public static final Item PURPLE_ORB = registerItem(new BasicItem("purple_orb", new Item.Settings()));
    public static final Item YIN_YANG_ORB = registerItem(new BasicItem("yin-yang_orb", new Item.Settings()));

    // 道具
    public static final Item TOUHOU_HELPER = registerItem(new TouhouHelperItem("touhou_helper", new Item.Settings()));
    public static final Item UPGRADED_HEALTH = registerItem(new UpgradedHealthItem("upgraded_health", new Item.Settings()));
    public static final Item BOMB = registerItem(new BombItem("bomb", new Item.Settings().useCooldown(2.0f)));
    public static final Item HORAI_DAMA_NO_EDA = registerItem(new BasicItem("horai-dama_no_eda", new Item.Settings()));
    public static final Item CROSSING_CHISEL = registerItem(new CrossingChisel("crossing_chisel", new Item.Settings()));
    public static final Item GAP_BALL = registerItem(new GapBall("gap_ball", new Item.Settings()));
    public static final Item BAGUA_FURNACE = registerItem(new BaguaFurnace("bagua_furnace", new Item.Settings()));
    public static final Item TIME_STOP_CLOCK = registerItem(new TimeStopClock("time_stop_clock", new Item.Settings()));
    public static final Item MAPLE_LEAF_FAN = registerItem(new MapleLeafFan("maple_leaf_fan", 0, 0, new Item.Settings()));
    public static final Item EARPHONE = registerItem(new EarphoneItem("earphone", new Item.Settings().maxCount(EquipmentType.HELMET.getMaxDamage(EarphoneArmorMaterial.BASE_DURABILITY))));
    public static final Item KOISHI_HAT = registerItem(new KoishiHatItem("koishi_hat", new Item.Settings().maxCount(EquipmentType.HELMET.getMaxDamage(KoishiHatArmorMaterial.BASE_DURABILITY))));

    // 武器
    public static final Item HAKUREI_CANE = registerItem(new HakureiCane("hakurei_cane", 0, 0, new Item.Settings()));
    public static final Item WIND_BLESSING_CANE = registerItem(new WindBlessingCane("wind_blessing_cane", 0, 0, new Item.Settings()));
    public static final Item MAGIC_BROOM = registerItem(new MagicBroom("magic_broom", 0, 0, new Item.Settings()));
    public static final Item KNIFE = registerItem(new Knife("knife", 0f, 0f, new Item.Settings()));
    public static final Item ROKANKEN = registerItem(new Rokanken("rokanken", 1f, 0.5f, new Item.Settings()));
    public static final Item HAKUROKEN = registerItem(new Hakuroken("hakuroken", 1f, 1f, new Item.Settings()));
    public static final Item PAPILIO_PATTERN_FAN = registerItem(new PapilioPatternFan("papilio_pattern_fan", 0f, 1f, new Item.Settings()));
    public static final Item GUNGNIR = registerItem(new Gungnir("gungnir", 0, 0, new Item.Settings()));
    public static final Item LEVATIN = registerItem(new Levatin("levatin", 0, 0, new Item.Settings()));
    public static final Item IBUKIHO = registerItem(new Ibukiho("ibukiho", 0, 0, new Item.Settings()));
    public static final Item SWORD_OF_HISOU = registerItem(new SwordOfHisou("sword_of_hisou", 0, 0, new Item.Settings()));
    public static final Item MANPOZUCHI = registerItem(new ManpozuchiItem("manpozuchi", 0, 0, new Item.Settings()));
    public static final Item NUE_TRIDENT = registerItem(new NueTrident("nue_trident", 0, 0, new Item.Settings()));
    public static final Item TRUMPET_GUN = registerItem(new TrumpetGun("trumpet_gun", new Item.Settings()));
    public static final Item TREASURE_HUNTING_ROD = registerItem(new TreasureHuntingRod("treasure_hunting_rod", 0, 0, new Item.Settings()));
    public static final Item VIOLIN = registerItem(new MusicalInstrumentItem("violin", new Item.Settings().component(ModDataComponentTypes.NOTE_TYPE, NoteBlockInstrument.FLUTE)));
    public static final Item KEYBOARD = registerItem(new MusicalInstrumentItem("keyboard", new Item.Settings().component(ModDataComponentTypes.NOTE_TYPE, NoteBlockInstrument.PLING)));
    public static final Item TRUMPET = registerItem(new MusicalInstrumentItem("trumpet", new Item.Settings().component(ModDataComponentTypes.NOTE_TYPE, NoteBlockInstrument.XYLOPHONE)));

    // 工具矿物类
    public static final Item RAW_SILVER = registerItem(new BasicItem("raw_silver", new Item.Settings()));
    public static final Item SILVER_INGOT = registerItem(new BasicItem("silver_ingot", new Item.Settings()));
    public static final Item SILVER_NUGGET = registerItem(new BasicItem("silver_nugget", new Item.Settings()));
    public static final Item SILVER_SWORD = registerItem(new BasicSwordItem("silver_sword", SilverMaterial.INSTANCE, 3.0f, -2.4f, new Item.Settings()));
    public static final Item SILVER_AXE = registerItem(new BasicAxeItem("silver_axe", SilverMaterial.INSTANCE, 6.0f, -2.8f, new Item.Settings()));
    public static final Item SILVER_PICKAXE = registerItem(new BasicPickaxeItem("silver_pickaxe", SilverMaterial.INSTANCE, 1.0f, -2.8f, new Item.Settings()));
    public static final Item SILVER_SHOVEL = registerItem(new BasicShovelItem("silver_shovel", SilverMaterial.INSTANCE, 1.5f, -3.0f, new Item.Settings()));
    public static final Item SILVER_HOE = registerItem(new BasicHoeItem("silver_hoe", SilverMaterial.INSTANCE, -2.0f, -1.0f, new Item.Settings()));
    public static final Item SILVER_HELMET = registerItem(new BasicArmorItem("silver_helmet", SilverArmorMaterial.INSTANCE, EquipmentType.HELMET, new Item.Settings().maxCount(EquipmentType.HELMET.getMaxDamage(SilverArmorMaterial.BASE_DURABILITY))));
    public static final Item SILVER_CHESTPLATE = registerItem(new BasicArmorItem("silver_chestplate", SilverArmorMaterial.INSTANCE, EquipmentType.CHESTPLATE, new Item.Settings().maxCount(EquipmentType.HELMET.getMaxDamage(SilverArmorMaterial.BASE_DURABILITY))));
    public static final Item SILVER_LEGGINGS = registerItem(new BasicArmorItem("silver_leggings", SilverArmorMaterial.INSTANCE, EquipmentType.LEGGINGS, new Item.Settings().maxCount(EquipmentType.HELMET.getMaxDamage(SilverArmorMaterial.BASE_DURABILITY))));
    public static final Item SILVER_BOOTS = registerItem(new BasicArmorItem("silver_boots", SilverArmorMaterial.INSTANCE, EquipmentType.BOOTS, new Item.Settings().maxCount(EquipmentType.HELMET.getMaxDamage(SilverArmorMaterial.BASE_DURABILITY))));

    // DISC
    public static final Item HR01_01 = registerDiscItem(new BasicPolymerDiscItem("hr01_01", new Item.Settings().jukeboxPlayable(ModJukeboxSongs.HR01_01.getJukeboxSongRegistryKey()), Items.MUSIC_DISC_5));
    public static final Item HR02_08 = registerDiscItem(new BasicPolymerDiscItem("hr02_08", new Item.Settings().jukeboxPlayable(ModJukeboxSongs.HR02_08.getJukeboxSongRegistryKey()), Items.MUSIC_DISC_5));
    public static final Item HR03_01 = registerDiscItem(new BasicPolymerDiscItem("hr03_01", new Item.Settings().jukeboxPlayable(ModJukeboxSongs.HR03_01.getJukeboxSongRegistryKey()), Items.MUSIC_DISC_5));
    public static final Item TH15_16 = registerDiscItem(new BasicPolymerDiscItem("th15_16", new Item.Settings().jukeboxPlayable(ModJukeboxSongs.TH15_16.getJukeboxSongRegistryKey()), Items.MUSIC_DISC_5));
    public static final Item TH15_17 = registerDiscItem(new BasicPolymerDiscItem("th15_17", new Item.Settings().jukeboxPlayable(ModJukeboxSongs.TH15_17.getJukeboxSongRegistryKey()), Items.MUSIC_DISC_5));

    static {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.OPERATOR).register(itemGroup -> {
            itemGroup.add(BATTLE_STICK);
        });
    }

    // 符卡模板
//    public static final Item EMPTY_SPELL_CARD = registerItem(new BasicPolymerSpellCardItem("empty_spell_card", new Item.Settings()) {
//        @Override
//        public void spellCard(ServerWorld world, PlayerEntity user, Hand hand, ItemStack offHandStack) {
//        }
//
//        @Override
//        public int getBulletConsumption() {
//            return 0;
//        }
//    });

//    public static final Item DEBUG_SPELL_CARD_ITEM = registerItem(new BasicPolymerSpellCardItem("test_spell_card", new Item.Settings()) {
//        @Override
//        public void spellCard(ServerWorld world, PlayerEntity user, Hand hand, ItemStack offHandStack) {
//            float pitch = user.getPitch();
//            float yaw = user.getYaw();
//
//            spawnDanmakuEntity(world, user, hand, offHandStack, pitch, yaw - 25, 1.4f, 5.0f);
//            spawnDanmakuEntity(world, user, hand, offHandStack, pitch, yaw, 1.4f, 5.0f);
//            spawnDanmakuEntity(world, user, hand, offHandStack, pitch, yaw + 25.0f, 1.4f, 5.0f);
//        }
//
//        @Override
//        public int getBulletConsumption() {
//            return 3;
//        }
//    });

//    public static final Item DEBUG_SPELL_CARD_ITEM2 = registerItem(new BasicPolymerSpellCardItem("test_spell_card2", new Item.Settings()) {
//        @Override
//        public void spellCard(ServerWorld world, PlayerEntity user, Hand hand, ItemStack offHandStack) {
//            ItemStack main = user.getMainHandStack().copy();
//            ItemStack copy = offHandStack.copy();
//
//            SpellCardEntity spellCardEntity = new SpellCardEntity(user, main, offHandStack, hand, 20 * 20) {
//                @Override
//                public void apply() {
//                    Integer tickCount = this.getTickCount();
//
//                    // 控制参数
//                    int bulletCount = 7;  // 弹幕数量
//                    float speed = 1.0f;   // 子弹的发射速度
//                    float divergence = 0.05f; // 子弹的发射偏差
//                    double radius = 2.0; // 发射轨迹的半径
//                    double angularSpeed = 0.1;  // 旋转速度
//
//                    // 旋转一圈后再回到原位
//                    double totalRotation = 2 * Math.PI;  // 一圈的旋转角度
//                    double angularStep = totalRotation / bulletCount;  // 每个子弹之间的角度间隔
//
//                    // 计算当前旋转方向
//                    int cycleTicks = 20 * 5; // 一个周期的 tick 数（20秒）
//                    int halfCycleTicks = cycleTicks / 2; // 半周期 tick 数（10秒）
//
//                    // 根据 tickCount 来调整旋转方向
//                    double currentRotation;
//                    if (tickCount % cycleTicks < halfCycleTicks) {
//                        // 顺时针旋转
//                        currentRotation = angularSpeed * (tickCount % cycleTicks);
//                    } else {
//                        // 逆时针旋转
//                        currentRotation = angularSpeed * (cycleTicks - (tickCount % cycleTicks));
//                    }
//
//                    // 子弹发射
//                    for (int i = 0; i < bulletCount; i++) {
//                        // 计算当前子弹的角度，确保它顺时针旋转一圈后转回来
//                        double angle = i * angularStep + currentRotation;
//
//                        // 计算子弹的偏移量
//                        double xOffset = radius * Math.cos(angle);
//                        double zOffset = radius * Math.sin(angle);
//
//                        // 创建子弹实体
//                        DanmakuEntity danmaku = new DanmakuEntity(
//                                (LivingEntity) this.getOwner(),
//                                copy.copy(),
//                                Hand.MAIN_HAND,
//                                (BasicPolymerDanmakuItemItem) copy.getItem(),
//                                this.getPitch(),  // 获取物品的俯仰角（pitch）
//                                this.getYaw(),    // 获取物品的偏航角（yaw）
//                                speed,            // 发射速度
//                                divergence        // 发射偏差
//                        );
//
//                        // 设置子弹的初始位置
//                        danmaku.setPos(this.getX() + xOffset, this.getY(), this.getZ() + zOffset);
//                        double motionX = speed * Math.cos(angle);
//                        double motionZ = speed * Math.sin(angle);
//                        danmaku.setVelocity(motionX, 0, motionZ);
//
//                        // 向世界中发射子弹
//                        if (!this.getWorld().isClient) {
//                            this.getWorld().spawnEntity(danmaku);
//                        }
//                    }
//                }
//            };
//
//            world.spawnEntity(spellCardEntity);
//            world.playSound(null, user.getX(), user.getEyeY(), user.getZ(), ModSoundEvents.SPELL_CARD, user.getSoundCategory(), 1.0f, 1.0f);
//        }
//
//        @Override
//        public int getBulletConsumption() {
//            return 0;
//        }
//    });

    // 弹幕
    public static final DanmakuItemType AMULET = DanmakuItemType.createBuilder("amulet", DanmakuColor.ALL_COLOR, 2f, 1f, 1.0f, false, false).build();
    public static final DanmakuItemType ARROWHEAD = DanmakuItemType.createBuilder("arrowhead", DanmakuColor.ALL_COLOR, 2f, 1f, 1.0f, false, false).build();
    public static final DanmakuItemType BALL = DanmakuItemType.createBuilder("ball", DanmakuColor.ALL_COLOR, 2f, 1f, 1.0f, true, false).build();
    public static final DanmakuItemType BUBBLE = DanmakuItemType.createBuilder("bubble", List.of(DanmakuColor.GREY, DanmakuColor.RED, DanmakuColor.PURPLE, DanmakuColor.DARK_BLUE, DanmakuColor.BLUE), 2.5f, 1f, 1.0f, true, false).build();
    public static final DanmakuItemType BULLET = DanmakuItemType.createBuilder("bullet", DanmakuColor.ALL_COLOR, 2f, 1f, 1.0f, false, false).build();
    public static final DanmakuItemType FIREBALL = DanmakuItemType.createBuilder("fireball", DanmakuColor.ALL_COLOR, 2f, 1f, 1.0f, true, false).build();
    public static final DanmakuItemType FIREBALL_GLOWY = DanmakuItemType.createBuilder("fireball_glowy", DanmakuColor.ALL_COLOR, 1f, 1f, 1.0f, true, false).build();
    public static final DanmakuItemType KUNAI = DanmakuItemType.createBuilder("kunai", DanmakuColor.ALL_COLOR, 2f, 1f, 1.0f, false, false).build();
    //public static final DanmakuItemType MENTOS = DanmakuItemType.createBuilder("mentos", ALL_COLOR, 2f, 1f, 1.0f, false).build(); // 暂占位
    public static final DanmakuItemType RICE = DanmakuItemType.createBuilder("rice", DanmakuColor.ALL_COLOR, 2f, 1f, 1.0f, false, false).build();
    public static final DanmakuItemType STAR = DanmakuItemType.createBuilder("star", DanmakuColor.ALL_COLOR, 2f, 1f, 1.0f, true, false).build();

    public static final List<DanmakuItemType> DANMAKU_ITEMS = List.of(
            AMULET,
            ARROWHEAD,
            BALL,
            BUBBLE,
            BULLET,
            FIREBALL,
            FIREBALL_GLOWY,
            KUNAI,
            RICE,
            STAR
    );

//    public static final Item DEBUG_DANMAKU_ITEM = registerDanmakuItemNoList(new BasicDanmakuItemTypeItem(
//            "debug_danmaku",
//            new Item.Settings()
//                    .component(ModDataComponentTypes.Danmaku.DAMAGE, 2.0f)
//                    .component(ModDataComponentTypes.Danmaku.SCALE, 1.0f)
//                    .component(ModDataComponentTypes.Danmaku.SPEED, 1.0f)
//                    .component(ModDataComponentTypes.Danmaku.INFINITE, true)
//    ));

    public static void registerItems() {
        ArrayList<ItemStack> discStack = new ArrayList<>();
        for (var disc : DISC_LIST) {
            discStack.add(disc.getDefaultStack());
        }
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(itemGroup -> {
            itemGroup.addAfter(Items.MUSIC_DISC_PIGSTEP, discStack);
        });
    }

    public static Item registerItem(IdentifierGetter item) {
        Registry.register(Registries.ITEM, item.getIdentifier(), (Item) item);
        ITEM_LIST.add((Item) item);
        return (Item) item;
    }

    public static Item registerItemWithNotGroup(IdentifierGetter item) {
        return Registry.register(Registries.ITEM, item.getIdentifier(), (Item) item);
    }

    public static Item registerIconItem(IdentifierGetter item) {
        Registry.register(Registries.ITEM, item.getIdentifier(), (Item) item);
        ICON_LIST.add((Item) item);
        return (Item) item;
    }

    public static Item registerDiscItem(IdentifierGetter item) {
        Registry.register(Registries.ITEM, item.getIdentifier(), (Item) item);
        DISC_LIST.add((Item) item);
        return (Item) item;
    }

    public static Item registerDanmakuItem(IdentifierGetter item) {
        Registry.register(Registries.ITEM, item.getIdentifier(), (Item) item);
        DANMAKU_LIST.add((Item) item);
        return (Item) item;
    }

    public static Item registerItem(String path, Function<Identifier, Item> itemFactory) {
        Identifier id = Touhou.id(path);
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, id);
        Item item = itemFactory.apply(id);
        Registry.register(Registries.ITEM, itemKey, item);
        ITEM_LIST.add(item);
        return item;
    }

    public static List<Item> getRegisteredItems() {
        return List.copyOf(ITEM_LIST);
    }

    public static List<Item> getRegisteredDiscItems() {
        return List.copyOf(DISC_LIST);
    }

    public static List<Item> getRegisteredDanmakuItems() {
        return List.copyOf(DANMAKU_LIST);
    }

    public static List<Item> getIconItems() {
        return List.copyOf(ICON_LIST);
    }
}
