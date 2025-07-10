package cc.thonly.reverie_dreams.command;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.gui.recipe.RecipeTypeCategoryGui;
import cc.thonly.reverie_dreams.util.ImageToTextScanner;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainCommand implements CommandInit.CommandRegistration {
    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher,
                         CommandRegistryAccess access,
                         CommandManager.RegistrationEnvironment environment
    ) {
        dispatcher.register(
                CommandManager.literal("touhou")
                        .executes(this::run)
                        .then(
                                CommandManager.literal("help")
                                        .executes(this::help)
                        )
                        .then(
                                CommandManager.literal("recipe")
                                        .executes(this::recipe)
                        )
                        .then(
                                CommandManager.literal("about")
                                        .executes(this::about)
                        )
                        .then(
                                CommandManager.literal("ui_relay_recipe")
                                        .executes((context) -> 0)
                        )
        );
    }

    private int run(CommandContext<ServerCommandSource> context) {
        MutableText text = Text.translatable("command.touhou.suggest_help");
        context.getSource().sendFeedback(() -> text.setStyle(Style.EMPTY.withColor(Formatting.YELLOW)), false);
        return 0;
    }

    private int help(CommandContext<ServerCommandSource> context) {
        List<String> keys = List.of(
                "command.touhou.help.title",
                "command.touhou.help.help",
                "command.touhou.help.recipe",
                "command.touhou.help.about",
                "command.touhou.help.empty"
        );

        for (String key : keys) {
            context.getSource().sendFeedback(() -> Text.translatable(key).setStyle(Style.EMPTY.withColor(Formatting.WHITE)), false);
        }
        return 0;
    }

    private int about(CommandContext<ServerCommandSource> context) {
        Class<?> clazz = Touhou.class;
        ImageToTextScanner instance = ImageToTextScanner.createInstance(clazz);
        String path = ImageToTextScanner.ofNamespace(Touhou.MOD_ID, "icon.png");
        BufferedImage iconBuffer = instance.loadImageFromJar(path);
        List<Text> iconText = instance.renderImageToText(iconBuffer, 16, 16);

        String[] infoKeys = new String[] {
                "command.touhou.about.line1",
                "command.touhou.about.line2",
                "command.touhou.about.line3",
                "command.touhou.about.line4",
                "command.touhou.about.line5",
                "command.touhou.about.line6",
                "command.touhou.about.title",
                "command.touhou.about.version",
                "command.touhou.about.author",
                "command.touhou.about.line10",
                "command.touhou.about.line11"
        };

        List<Text> rightTexts = new ArrayList<>();
        for (String key : infoKeys) {
            if (key.equals("command.touhou.about.version")) {
                rightTexts.add(Text.translatable(key, Touhou.VERSION));
            } else {
                rightTexts.add(Text.translatable(key));
            }
        }

        while (rightTexts.size() < iconText.size()) {
            rightTexts.add(Text.literal(""));
        }

        for (int i = 0; i < iconText.size(); i++) {
            Text left = iconText.get(i);
            Text right = rightTexts.get(i).copy().formatted(Formatting.WHITE);
            context.getSource().sendFeedback(() -> Text.empty().append(left).append(Text.literal("  ")).append(right), false);
        }

        return 0;
    }

    private int recipe(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayer();
        if (player != null) {
            RecipeTypeCategoryGui.create(player);
        }
        return 0;
    }
}
