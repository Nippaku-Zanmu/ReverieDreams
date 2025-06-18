package cc.thonly.reverie_dreams;

import cc.thonly.reverie_dreams.armor.ModArmorMaterials;
import cc.thonly.reverie_dreams.block.FumoBlocks;
import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.block.entity.ModBlockEntities;
import cc.thonly.reverie_dreams.command.CommandInit;
import cc.thonly.reverie_dreams.compat.ModCompats;
import cc.thonly.reverie_dreams.component.ModDataComponentTypes;
import cc.thonly.reverie_dreams.config.TouhouConfiguration;
import cc.thonly.reverie_dreams.data.ModLoots;
import cc.thonly.reverie_dreams.data.ModResourceManager;
import cc.thonly.reverie_dreams.data.ModTags;
import cc.thonly.reverie_dreams.datafixer.ModDataFixer;
import cc.thonly.reverie_dreams.effect.ModStatusEffects;
import cc.thonly.reverie_dreams.entity.ModEntities;
import cc.thonly.reverie_dreams.entity.ModEntityHolders;
import cc.thonly.reverie_dreams.gui.RecipeTypeCategoryManager;
import cc.thonly.reverie_dreams.item.ModGuiItems;
import cc.thonly.reverie_dreams.item.ModItemGroups;
import cc.thonly.reverie_dreams.item.ModItems;
import cc.thonly.reverie_dreams.networking.CSVersionPayload;
import cc.thonly.reverie_dreams.networking.HelloPayload;
import cc.thonly.reverie_dreams.networking.RegistrySyncPayload;
import cc.thonly.reverie_dreams.recipe.*;
import cc.thonly.reverie_dreams.registry.RegistrySchemas;
import cc.thonly.reverie_dreams.server.DelayedTask;
import cc.thonly.reverie_dreams.server.ItemDescriptionManager;
import cc.thonly.reverie_dreams.server.ParticleTickerManager;
import cc.thonly.reverie_dreams.server.PlayerInputManager;
import cc.thonly.reverie_dreams.sound.ModJukeboxSongs;
import cc.thonly.reverie_dreams.sound.ModSoundEvents;
import cc.thonly.reverie_dreams.state.ModBlockStateTemplates;
import cc.thonly.reverie_dreams.util.ImageToTextScanner;
import cc.thonly.reverie_dreams.world.data.BlockPosStorage;
import cc.thonly.reverie_dreams.world.gen.ModWorldGeneration;
import eu.midnightdust.lib.config.MidnightConfig;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.resourcepack.extras.api.ResourcePackExtras;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Setter
@Getter
public class Touhou implements ModInitializer {
    public static final String MOD_NAME = "Gensokyo: Reverie of Lost Dreams";
    public static final String MOD_ID = "reverie_dreams";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final String VERSION = FabricLoader.getInstance()
            .getModContainer(MOD_ID)
            .map(container -> container.getMetadata().getVersion().getFriendlyString())
            .orElse("unknown");
    private static final boolean DEV_ENV = FabricLoader.getInstance().isDevelopmentEnvironment();
    private static final boolean DEV_MODE = VERSION.contains("-dev.") || DEV_ENV;
    private static final boolean HAS_BUKKIT_API = isModLoaded("arclight") || isModLoaded("cardboard") || isModLoaded("banner");
    private static final boolean HAS_CONNECTOR = isModLoaded("connector");
    private static final boolean HAS_FORGE_API = isModLoaded("kilt");
    private static final boolean HAS_OPTIFINE = isModLoaded("optifabric");
    private static String SYSTEM_LANGUAGE = null;
    private static MinecraftServer server;
    private static final Set<ServerPlayerEntity> playersWithMod = new HashSet<>();
    private static final Map<ServerPlayerEntity, String> playerSideVersion = new WeakHashMap<>();

    static {
        Locale locale = Locale.getDefault();
        SYSTEM_LANGUAGE = (locale.getLanguage() + "_" + locale.getCountry()).toLowerCase();
    }

    @Override
    public void onInitialize() {
        MidnightConfig.init(MOD_ID, TouhouConfiguration.class);
        if (isDevMode()) {
            LOGGER.warn("=====================================================");
            LOGGER.warn("You are using development version of Gensokyo: Reverie of Lost Dreams!");
            LOGGER.warn("Support is limited, as features might be unfinished!");
            LOGGER.warn("You are on your own!");
            LOGGER.warn("=====================================================");
        }
        if (hasBukkitApi()) {
            LOGGER.warn("Please don't use hybrid Bukkit, it will crash your game");
        }
        if (hasForgeApi()) {
            LOGGER.warn("Cars cannot be placed on bicycles");
        }
        if (hasOptifine()) {
            LOGGER.warn("It must be Optifine’s fault!");
        }
        if (isHasConnector()) {
            LOGGER.warn("It cannot connect...");
        }
        LOGGER.info("Loaded " + MOD_NAME);

        // 初始化静态注册表
        ModSoundEvents.init();
        ModJukeboxSongs.init();
        ModDataComponentTypes.init();
        ModArmorMaterials.init();
        ModGuiItems.init();
        ModBlockStateTemplates.bootstrap();
        ModBlockEntities.registerBlockEntities();
        ModBlocks.registerBlocks();
        FumoBlocks.registerFumoBlocks();
        ModItems.registerItems();
        ModItemGroups.registerItemGroups();
        ModEntityHolders.registerHolders();
        ModEntities.registerEntities();
        ModStatusEffects.init();
        ModTags.registerTags();
        ModWorldGeneration.registerModGen();
        ModDataFixer.bootstrap();

        // 初始化其他注册内容
        CommandInit.init();
        ModRecipeTypes.init();
        ModRecipeSerializer.init();
        RecipeManager.bootstrap();
        ModResourceManager.init();
        RegistrySchemas.bootstrap();
        ModLoots.register();
        RecipeTypeCategoryManager.setup();

        ImageToTextScanner.bootstrap();
        ItemDescriptionManager.bootstrap();

        PayloadTypeRegistry.playC2S().register(HelloPayload.PACKET_ID, HelloPayload.codec);
        ServerPlayNetworking.registerGlobalReceiver(HelloPayload.PACKET_ID, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            if (player != null) {
                playersWithMod.add(player);
            }
        });
        PayloadTypeRegistry.playC2S().register(RegistrySyncPayload.PACKET_ID, RegistrySyncPayload.codec);
        ServerPlayNetworking.registerGlobalReceiver(RegistrySyncPayload.PACKET_ID, (payload, context) -> {});
        ServerPlayConnectionEvents.DISCONNECT.register((playNetworkHandler, server) -> {
            playersWithMod.remove(playNetworkHandler.player);
            playerSideVersion.remove(playNetworkHandler.player);
        });
        PayloadTypeRegistry.playC2S().register(CSVersionPayload.PACKET_ID, CSVersionPayload.codec);
        ServerPlayNetworking.registerGlobalReceiver(CSVersionPayload.PACKET_ID, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            String version = payload.version();
            if (player != null) {
                playerSideVersion.put(player, version);
            }
        });

        ServerLivingEntityEvents.ALLOW_DEATH.register((livingEntity, damageSource, v) -> {
            return !livingEntity.hasStatusEffect(ModStatusEffects.ELIXIR_OF_LIFE);
        });
        ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
            PlayerInputManager inputManager = PlayerInputManager.getInstance();
            inputManager.reload();
        });
        ServerLifecycleEvents.SERVER_STARTED.register(BlockPosStorage::loadToFile);
        ServerLifecycleEvents.SERVER_STOPPED.register(BlockPosStorage::saveToFile);
        ServerLifecycleEvents.AFTER_SAVE.register((server, flush, force) -> BlockPosStorage.saveToFile(server));
        ServerTickEvents.END_SERVER_TICK.register(DelayedTask::tick);
        ServerTickEvents.END_SERVER_TICK.register(ParticleTickerManager::tick);
        ServerTickEvents.END_SERVER_TICK.register(PlayerInputManager::tick);

        ModCompats.init();

        PolymerResourcePackUtils.addModAssets(MOD_ID);
        PolymerResourcePackUtils.markAsRequired();
        ResourcePackExtras.forDefault().addBridgedModelsFolder(id("block"), id("item"), id("entity"));
    }

    public static String getSystemLanguage() {
        return SYSTEM_LANGUAGE;
    }

    public static Identifier id(String id) {
        return Identifier.of(MOD_ID, id);
    }

    public static boolean isModLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }

    public static boolean isDevMode() {
        return DEV_MODE || TouhouConfiguration.DEBUG_MODE;
    }

    public static boolean isHasConnector() {
        return HAS_CONNECTOR;
    }

    public static boolean hasBukkitApi() {
        return HAS_BUKKIT_API;
    }

    public static boolean hasForgeApi() {
        return HAS_FORGE_API;
    }

    public static boolean hasOptifine() {
        return HAS_OPTIFINE;
    }

    public static boolean hasModOnClient(ServerPlayerEntity player) {
        if (player == null) return false;
        return playersWithMod.contains(player);
    }

    public static void setServer(MinecraftServer server) {
        Touhou.server = server;
    }

    @Nullable
    public static MinecraftServer getServer() {
        return server;
    }


}