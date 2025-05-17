package cc.thonly.touhoumod;

import cc.thonly.touhoumod.command.CommandInit;
import cc.thonly.touhoumod.compat.ModCompats;
import cc.thonly.touhoumod.config.TouhouConfiguration;
import cc.thonly.touhoumod.data.ModLoots;
import cc.thonly.touhoumod.data.ModResourceManager;
import cc.thonly.touhoumod.effect.ModStatusEffects;
import cc.thonly.touhoumod.gui.recipe.RecipeTypeCategoryGui;
import cc.thonly.touhoumod.networking.HelloPayload;
import cc.thonly.touhoumod.recipe.*;
import cc.thonly.touhoumod.registry.RegistryLists;
import cc.thonly.touhoumod.util.BlockPosStorage;
import cc.thonly.touhoumod.util.DelayedTask;
import cc.thonly.touhoumod.util.ImageToTextScanner;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Setter
@Getter
public class Touhou implements ModInitializer {
    public static final String MOD_NAME = "Gensokyo: Reverie of Lost Dreams";
    public static final String MOD_ID = "reverie_dreams";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final String VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getVersion().getFriendlyString();
    private static final boolean DEV_ENV = FabricLoader.getInstance().isDevelopmentEnvironment();
    private static final boolean DEV_MODE = VERSION.contains("-dev.") || DEV_ENV;
    private static final boolean HAS_BUKKIT_API = isModLoaded("arclight") || isModLoaded("cardboard") || isModLoaded("banner");
    private static final boolean HAS_CONNECTOR = isModLoaded("connector");
    private static final boolean HAS_FORGE_API = isModLoaded("kilt");
    private static final boolean HAS_OPTIFINE = isModLoaded("optifabric");
    private static String SYSTEM_LANGUAGE = null;
    private static MinecraftServer server;
    private static final List<ServerPlayerEntity> playersWithMod = new ArrayList<>();

    @Override
    public void onInitialize() {
        preInit();
        Beans.main(null);
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
        TouhouConfiguration.init(MOD_ID, TouhouConfiguration.class);
        ModInit.init();
        CommandInit.init();
        ModRecipeTypes.init();
        ModRecipeSerializer.init();
        DanmakuRecipes.registerRecipes();
        GensokyoAltarRecipes.registerRecipes();
        StrengthTableRecipes.registerRecipes();
        ModResourceManager.registerHooks();
        RegistryLists.bootstrap();
        ModLoots.register();
        RecipeTypeCategoryGui.setup();

        ImageToTextScanner.registerBuffer();

        PayloadTypeRegistry.playC2S().register(HelloPayload.PACKET_ID, HelloPayload.codec);
        ServerPlayNetworking.registerGlobalReceiver(HelloPayload.PACKET_ID, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            if (player != null) {
                playersWithMod.add(player);
            }
        });
        ServerPlayConnectionEvents.DISCONNECT.register((playNetworkHandler, server) -> {
            playersWithMod.remove(playNetworkHandler.player);
        });

        ServerLivingEntityEvents.ALLOW_DEATH.register((livingEntity, damageSource, v) -> {
            return !livingEntity.hasStatusEffect(ModStatusEffects.ELIXIR_OF_LIFE);
        });
        ServerLifecycleEvents.SERVER_STARTED.register(BlockPosStorage::loadToFile);
        ServerLifecycleEvents.SERVER_STOPPED.register(BlockPosStorage::saveToFile);
        ServerLifecycleEvents.AFTER_SAVE.register((server, flush, force) -> BlockPosStorage.saveToFile(server));
        ServerTickEvents.END_SERVER_TICK.register(DelayedTask::tick);

        ModCompats.init();

        PolymerResourcePackUtils.addModAssets(MOD_ID);
        PolymerResourcePackUtils.markAsRequired();
    }

    private static void preInit() {
        Locale locale = Locale.getDefault();
        SYSTEM_LANGUAGE = (locale.getLanguage() + "_" + locale.getCountry()).toLowerCase();
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
        return DEV_MODE;
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