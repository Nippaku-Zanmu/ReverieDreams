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

public class TouhouCommand implements CommandInit.CommandRegistration {
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
        MutableText text = Text.empty().append("使用 /touhou help 获取更多帮助");
        context.getSource().sendFeedback(() -> text.setStyle(Style.EMPTY.withColor(Formatting.YELLOW)), false);
        return 0;
    }

    private int help(CommandContext<ServerCommandSource> context) {
        List<MutableText> mutableTexts = new LinkedList<>();
        mutableTexts.add(Text.empty().append("帮助菜单："));
        mutableTexts.add(Text.empty().append(" - 帮助菜单：/touhou help"));
        mutableTexts.add(Text.empty().append(" - 合成管理器：/touhou recipe"));
        mutableTexts.add(Text.empty().append(" - 关于模组信息：/touhou about"));
        mutableTexts.add(Text.empty().append(""));
        mutableTexts.forEach((text) -> context.getSource().sendFeedback(() -> text.setStyle(Style.EMPTY.withColor(Formatting.WHITE)), false));
        return 0;
    }

    private int about(CommandContext<ServerCommandSource> context) {
        Class<?> clazz = Touhou.class;
        ImageToTextScanner instance = ImageToTextScanner.createInstance(clazz);
        String path = ImageToTextScanner.ofNamespace(Touhou.MOD_ID, "icon.png");
        BufferedImage iconBuffer = instance
                .loadImageFromJar(path);
        List<Text> iconText = instance
                .renderImageToText(iconBuffer, 16, 16);

        String[] infoLines = new String[]{
                "",
                "",
                "",
                "",
                "",
                "",
                "幻想乡追忆录 ～ Reverie of Lost Dreams",
                "版本：" + Touhou.VERSION,
                "作者：稀神灵梦",
                "",
                "",
        };


        List<String> rightTexts = new ArrayList<>(Arrays.asList(infoLines));
        while (rightTexts.size() < iconText.size()) {
            rightTexts.add("");
        }

        for (int i = 0; i < iconText.size(); i++) {
            Text left = iconText.get(i);
            String rightRaw = rightTexts.get(i);
            Text right = Text.literal("  " + rightRaw).setStyle(Style.EMPTY.withColor(Formatting.WHITE));

            context.getSource().sendFeedback(() -> Text.empty().append(left).append(right), false);
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
