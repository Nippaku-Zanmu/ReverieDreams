package cc.thonly.reverie_dreams.entity;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.entity.base.NPCEntity;
import cc.thonly.reverie_dreams.entity.npc.NPCEntityImpl;
import cc.thonly.reverie_dreams.entity.npc.NPCEntityNeutralImpl;
import cc.thonly.reverie_dreams.entity.npc.NPCEntitySkins;
import cc.thonly.reverie_dreams.item.base.BasicPolymerSpawnEggItem;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import com.mojang.authlib.properties.Property;
import eu.pb4.polymer.core.api.entity.PolymerEntityUtils;
import lombok.Getter;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unchecked")
public class ModEntities {
    @Getter
    public enum NPCEntityTypes {
        // 主角组
        REIMU("reimu", NPCEntitySkins.REIMU),
        CYAN_REIMU("cyan_reimu", NPCEntitySkins.CYAN_REIMU),
        MARISA("marisa", NPCEntitySkins.MARISA),
        RUMIA("rumia", NPCEntitySkins.RUMIA),
        CIRNO("cirno", NPCEntitySkins.CIRNO),
        MEIRIN("meirin", NPCEntitySkins.HOAN_MEIRIN),
        PATCHOULI("patchouli", NPCEntitySkins.PATCHOULI),
        SAKUYA("sakuya", NPCEntitySkins.SAKUYA),
        REMILIA("remilia", NPCEntitySkins.REMILA),
        FLANDRE("flandre", NPCEntitySkins.FLANDRE),

        // 妖妖梦
        LETTY_WHITEROCK("letty_whiterock", NPCEntitySkins.LETTY_WHITEROCK),
        CHEN("chen", NPCEntitySkins.CHEN),
        ALICE("alice", NPCEntitySkins.ALICE),
        LILY_WHITE("lily_white", NPCEntitySkins.LILY_WHITE),
        LUNASA_PRISMRIVER("lunasa_prismriver", NPCEntitySkins.LUNASA_PRISMRIVER),
        MERLIN_PRISMRIVER("merlin_prismriver", NPCEntitySkins.MERLIN_PRISMRIVER),
        LYRICA_PRISMRIVER("lyrica_prismriver", NPCEntitySkins.LYRICA_PRISMRIVER),
        RAN("ran", NPCEntitySkins.RAN),
        YOUMU("youmu", NPCEntitySkins.YOUMU),
        YUYUKO("yuyuko", NPCEntitySkins.YUYUKO),
        YUKARI("yakumo_yukai", NPCEntitySkins.YAKUMO_YUKAI),

        // 永夜抄
        MYSTIA_LORELEI("mystia_lorelei", NPCEntitySkins.MYSTIA_LORELEI),
        WRIGGLE_NIGHTBUG("wriggle_nightbug", NPCEntitySkins.WRIGGLE_NIGHTBUG),
        KAMISHIRASAWA_KEINE("kamishirasawa_keine", NPCEntitySkins.KAMISHIRASAWA_KEINE),
        REISEN("reisen", NPCEntitySkins.REISEN),
        ERIN("erin", NPCEntitySkins.ERIN),
        HOURAISAN_KAGUYA("houraisan_kaguya", NPCEntitySkins.HOURAISAN_KAGUYA),
        HUZIWARA_NO_MOKOU("huziwara_no_mokou", NPCEntitySkins.HUZIWARA_NO_MOKOU),

        // 花映塚
        SHIKIEIKI_YAMAXANADU("shikieiki_yamaxanadu", NPCEntitySkins.SHIKIEIKI_YAMAXANADU),
        KAZAMI_YUKA("kazami_yuka", NPCEntitySkins.KAZAMI_YUKA),

        // 风神录
        KAGIYAMA_HINA("kagiyama_hina", NPCEntitySkins.KAGIYAMA_HINA),
        INUBASHIRI_MOMIZI("inubashiri_momizi", NPCEntitySkins.INUBASHIRI_MOMIZI),
        KAWASIRO_NITORI("kawasiro_nitori", NPCEntitySkins.KAWASIRO_NITORI),
        AYA("aya", NPCEntitySkins.AYA),
        KOCHIYA_SANAE("kochiuya_sanae", NPCEntitySkins.KOCHIYA_SANAE),
        YASAKA_KANAKO("yasaka_kanako", NPCEntitySkins.YASAKA_KANAKO),
        MORIYA_SUWAKO("moriya_suwako", NPCEntitySkins.MORIYA_SUWAKO),

        // 地灵殿
        KISUME("kisume", NPCEntitySkins.KISUME),
        KURODANI_YAMAME("kurodani_yamame", NPCEntitySkins.KURODANI_YAMAME),
        MIZUHASHI_PARSEE("mizuhashi_parsee", NPCEntitySkins.MIZUHASHI_PARSEE),
        HOSHIGUMA_YUGI("hoshiguma_yugi", NPCEntitySkins.HOSHIGUMA_YUGI),
        KAENBYOU_RIN("kaenbyou_rin", NPCEntitySkins.KAENBYOU_RIN),
        KOMEIJI_SATORI("komeiji_satori", NPCEntitySkins.KOMEIJI_SATORI),
        REIUJI_UTSUH("reiuji_utsuh", NPCEntitySkins.REIUJI_UTSUH),
        KOMEIJI_KOISHI("komeiji_koishi", NPCEntitySkins.KOMEIJI_KOISHI),

        // 星莲船
        NAZRIN("nazrin", NPCEntitySkins.NAZRIN),
        TATARA_KOGASA("tatara_kogasa", NPCEntitySkins.TATARA_KOGASA),
        NUE("nue", NPCEntitySkins.NUE),

        // 神灵庙
        KASODANI_KYOUKO("kasodani_kyouko", NPCEntitySkins.KASODANI_KYOUKO),
        MIYAKO_YOSHIKA("miyako_yoshika", NPCEntitySkins.MIYAKO_YOSHIKA),
        KAKU_SEIGA("kaku_seiga", NPCEntitySkins.KAKU_SEIGA),
        SOGA_NO_TOZIKO("soga_no_toziko", NPCEntitySkins.SOGA_NO_TOZIKO),
        MONONOBE_NO_FUTO("mononobe_no_futo", NPCEntitySkins.MONONOBE_NO_FUTO),
        TOYOSATOMIMI_NO_MIKO("toyosatomimi_no_miko", NPCEntitySkins.TOYOSATOMIMI_NO_MIKO),
        HOUJUU_NUE("houjuu_nue", NPCEntitySkins.HOUJUU_NUE),
        HUTATSUIWA_MAMIZOU("hutatsuiwa_mamizou", NPCEntitySkins.HUTATSUIWA_MAMIZOU),

        // 三月精
        STAR("star", NPCEntitySkins.STAR),
        LUNAR("lunar", NPCEntitySkins.LUNAR),
        SUNNY("sunny", NPCEntitySkins.SUNNY),

        // 其他
        USAMI_RENKO("usami_renko", NPCEntitySkins.USAMI_RENKO),
        MARIBEL_HEARN("maribel_hearn", NPCEntitySkins.MARIBEL_HEARN),

        // 黄昏
        SUIKA("suika", NPCEntitySkins.SUIKA),
        TENSHI("tenshi", NPCEntitySkins.TENSHI),
        ;

        public final String id;
        public final Property property;
        public final Class<? extends NPCEntityImpl> clazz;
        public final EntityType<NPCEntity> value;

        NPCEntityTypes(String id, Property property) {
            this(id, property, NPCEntityNeutralImpl.class);
        }

        NPCEntityTypes(String id, Property property, Class<? extends NPCEntityImpl> clazz) {
            EntityType<NPCEntity> value = registerEntity(id,
                    EntityType.Builder.<NPCEntity>create(
                                    (type, world) -> {
                                        try {
                                            return clazz.getConstructor(EntityType.class, World.class, Property.class)
                                                    .newInstance(type, world, property);
                                        } catch (Exception e) {
                                            throw new RuntimeException("Failed to instantiate NPCEntityImpl for type " + id, e);
                                        }
                                    },
                                    SpawnGroup.MISC)
                            .build(of(id)));
            FabricDefaultAttributeRegistry.register(value, NPCEntityImpl.createAttributes());
            registerNpcSpawnEggItem(new BasicPolymerSpawnEggItem(id + "_spawn_egg", value, new Item.Settings().modelId(Touhou.id("spawn_egg"))));
            this.id = id;
            this.property = property;
            this.value = value;
            this.clazz = clazz;
        }

        public static void init() {
        }

        @FunctionalInterface
        public interface NpcEntityFactory<T extends NPCEntity> {
            T create(EntityType<T> type, World world, Property skin);
        }

    }

    @Getter
    public enum YouseiTypes {
        BLUE("01", (type, world) -> new YouseiEntity(type, world, NPCEntitySkins.YOUSEI01)),
        ORANGE("02", (type, world) -> new YouseiEntity(type, world, NPCEntitySkins.YOUSEI02)),
        GREEN("03", (type, world) -> new YouseiEntity(type, world, NPCEntitySkins.YOUSEI03)),
        ;
        private final String endId;
        private final EntityType<YouseiEntity> entry;

        YouseiTypes(String endId, EntityType.EntityFactory<YouseiEntity> factory) {
            this.endId = endId;
            this.entry = registerEntityWithSpawnEgg("yousei_" + endId,
                    EntityType.Builder.<YouseiEntity>create(factory, SpawnGroup.MONSTER)
                            .build(of("yousei_" + endId)),
                    () -> LivingEntity.createLivingAttributes()
                            .add(EntityAttributes.MAX_HEALTH, 25.0)
                            .add(EntityAttributes.FLYING_SPEED, 0.8f)
                            .add(EntityAttributes.MOVEMENT_SPEED, 0.15f)
                            .add(EntityAttributes.ATTACK_DAMAGE, 0.5)
                            .add(EntityAttributes.SCALE, 0.6f)
                            .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.1)
                            .add(EntityAttributes.FOLLOW_RANGE, 16.0)
                            .add(EntityAttributes.TEMPT_RANGE, 10.0)
                            .add(EntityAttributes.ENTITY_INTERACTION_RANGE, 3)
                            .build());
        }

        public static YouseiTypes getById(String id) {
            return Arrays.stream(YouseiTypes.values()).filter(e -> e.endId.equals(id)).limit(1).findAny().orElse(null);
        }

        public static EntityType<YouseiEntity> getEntryById(String id) {
            YouseiTypes youseiTypes = getById(id);
            if (youseiTypes != null) {
                return youseiTypes.getEntry();
            } else {
                return null;
            }
        }

        public static void init() {
        }
    }

    private static final List<Item> NPC_SPAWN_EGG_ITEM_LIST = new LinkedList<>();
    private static final List<Item> SPAWN_EGG_ITEM_LIST = new LinkedList<>();

    public static Item registerNpcSpawnEggItem(IdentifierGetter item) {
        Registry.register(Registries.ITEM, item.getIdentifier(), (Item) item);
        NPC_SPAWN_EGG_ITEM_LIST.add((Item) item);
        return (Item) item;
    }

    public static Item registerSpawnEggItem(IdentifierGetter item) {
        Registry.register(Registries.ITEM, item.getIdentifier(), (Item) item);
        SPAWN_EGG_ITEM_LIST.add((Item) item);
        return (Item) item;
    }

    public static List<Item> getRegisteredSpawnEggItems() {
        return List.copyOf(SPAWN_EGG_ITEM_LIST);
    }

    public static List<Item> getRegisteredNpcSpawnEggItems() {
        return List.copyOf(NPC_SPAWN_EGG_ITEM_LIST);
    }

    public static final EntityType<DanmakuEntity> DANMAKU_ENTITY_TYPE =
            registerEntity("danmaku_bullet",
                    EntityType.Builder.<DanmakuEntity>create(DanmakuEntity::new, SpawnGroup.MISC)
                            .build(of("danmaku_bullet")));
    public static final EntityType<KnifeEntity> KNIFE_ENTITY_TYPE =
            registerEntity("knife",
                    EntityType.Builder.<KnifeEntity>create(KnifeEntity::new, SpawnGroup.MISC)
                            .build(of("knife")));
    public static final EntityType<SpellCardEntity> SPELL_CARD_ENTITY_TYPE =
            registerEntity("spell_card",
                    EntityType.Builder.<SpellCardEntity>create(SpellCardEntity::new, SpawnGroup.MISC)
                            .build(of("danmaku_bullet")));
    public static final EntityType<KillerBeeEntity> KILLER_BEE_ENTITY_TYPE =
            registerEntityWithSpawnEgg("killer_bee",
                    EntityType.Builder.<KillerBeeEntity>create(KillerBeeEntity::new, SpawnGroup.MONSTER)
                            .build(of("killer_bee")),
                    () -> AnimalEntity.createAnimalAttributes()
                            .add(EntityAttributes.MAX_HEALTH, 10.0)
                            .add(EntityAttributes.FLYING_SPEED, 0.6f)
                            .add(EntityAttributes.MOVEMENT_SPEED, 0.3f)
                            .add(EntityAttributes.ATTACK_DAMAGE, 2.0)
                            .add(EntityAttributes.SCALE, 1.5f)
                            .build());
    public static final EntityType<GhostEntity> GHOST_ENTITY_TYPE =
            registerEntityWithSpawnEgg("ghost",
                    EntityType.Builder.<GhostEntity>create(GhostEntity::new, SpawnGroup.MONSTER)
                            .build(of("ghost")),
                    () -> LivingEntity.createLivingAttributes()
                            .add(EntityAttributes.MAX_HEALTH, 20.0)
                            .add(EntityAttributes.MOVEMENT_SPEED, 0.1)
                            .add(EntityAttributes.ATTACK_DAMAGE, 0.5)
                            .add(EntityAttributes.SCALE, 0.8f)
                            .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.1)
                            .add(EntityAttributes.FOLLOW_RANGE, 8.0)
                            .add(EntityAttributes.TEMPT_RANGE, 10.0)
                            .add(EntityAttributes.ENTITY_INTERACTION_RANGE, 3)
                            .build());
    public static final EntityType<SunflowerYouseiEntity> SUNFLOWER_YOUSEI_ENTITY_TYPE = registerEntityWithSpawnEgg("sunflower_yousei",
            EntityType.Builder.<SunflowerYouseiEntity>create((type, world) -> new SunflowerYouseiEntity(type, world, NPCEntitySkins.SUNFLOWER_YOUSEI), SpawnGroup.MONSTER)
                    .build(of("sunflower_yousei")),
            () -> LivingEntity.createLivingAttributes()
                    .add(EntityAttributes.MAX_HEALTH, 30.0)
                    .add(EntityAttributes.FLYING_SPEED, 0.8f)
                    .add(EntityAttributes.MOVEMENT_SPEED, 0.15f)
                    .add(EntityAttributes.ATTACK_DAMAGE, 0.5)
                    .add(EntityAttributes.SCALE, 0.6f)
                    .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.1)
                    .add(EntityAttributes.FOLLOW_RANGE, 16.0)
                    .add(EntityAttributes.TEMPT_RANGE, 10.0)
                    .add(EntityAttributes.ENTITY_INTERACTION_RANGE, 3)
                    .build());
    public static final EntityType<GoblinEntity> GOBLIN_ENTITY_TYPE = registerEntityWithSpawnEgg("goblin",
            EntityType.Builder.<GoblinEntity>create(GoblinEntity::new, SpawnGroup.MONSTER)
                    .build(of("goblin")),
            () -> LivingEntity.createLivingAttributes()
                    .add(EntityAttributes.MAX_HEALTH, 30.0)
                    .add(EntityAttributes.FLYING_SPEED, 0.8f)
                    .add(EntityAttributes.MOVEMENT_SPEED, 0.2f)
                    .add(EntityAttributes.ATTACK_DAMAGE, 1f)
                    .add(EntityAttributes.SCALE, 1f)
                    .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.1f)
                    .add(EntityAttributes.FOLLOW_RANGE, 16.0f)
                    .add(EntityAttributes.TEMPT_RANGE, 10.0f)
                    .add(EntityAttributes.ENTITY_INTERACTION_RANGE, 3)
                    .build());
    public static final EntityType<MagicBroomEntity> BROOM_ENTITY_TYPE = registerEntityWithSpawnEgg("broom",
            EntityType.Builder.<MagicBroomEntity>create(MagicBroomEntity::new, SpawnGroup.MISC)
                    .build(of("broom")),
            MagicBroomEntity::createAttributes);
    public static final EntityType<HairballEntity> HAIRBALL_ENTITY_TYPE = registerEntityWithSpawnEgg("hairball",
            EntityType.Builder.<HairballEntity>create(HairballEntity::new, SpawnGroup.MONSTER)
                    .build(of("hairball")),
            HairballEntity::createAttributes
    );

    public static void registerEntities() {
        NPCEntityTypes.init();
        YouseiTypes.init();
    }

    private static RegistryKey<EntityType<?>> of(String name) {
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, Touhou.id(name));
    }

    private static <T extends Entity> EntityType<T> registerEntity(String path, EntityType<T> entityType) {
        EntityType<T> entityTypeRef = Registry.register(Registries.ENTITY_TYPE, Touhou.id(path), entityType);
        PolymerEntityUtils.registerType(entityTypeRef);
        return entityTypeRef;
    }

    private static <T extends Entity> EntityType<T> registerEntityWithSpawnEgg(String path, EntityType<T> entityType, CreateAttributesFunction createAttributesFunction) {
        EntityType<T> entityTypeRef = Registry.register(Registries.ENTITY_TYPE, Touhou.id(path), entityType);
        FabricDefaultAttributeRegistry.register((EntityType<? extends MobEntity>) entityTypeRef, createAttributesFunction.apply());
        Item item = registerSpawnEggItem(new BasicPolymerSpawnEggItem(path + "_spawn_egg", (EntityType<? extends MobEntity>) entityTypeRef, new Item.Settings().modelId(Touhou.id("spawn_egg"))));
        PolymerEntityUtils.registerType(entityTypeRef);
        SPAWN_EGG_ITEM_LIST.add(item);
        return entityTypeRef;
    }

    @FunctionalInterface
    public interface CreateAttributesFunction {
        DefaultAttributeContainer apply();
    }
}
