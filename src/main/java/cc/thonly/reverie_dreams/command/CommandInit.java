package cc.thonly.reverie_dreams.command;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class CommandInit {
    public static void init() {
        registerCommand(new TouhouCommand());
    }

    public static void registerCommand(CommandRegistration registry) {
        CommandRegistrationCallback.EVENT.register(registry::register);
    }

    public interface CommandRegistration {
        void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment);
    }
}
