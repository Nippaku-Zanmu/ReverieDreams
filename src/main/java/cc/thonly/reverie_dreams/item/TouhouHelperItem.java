package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.gui.recipe.RecipeTypeCategoryGui;
import cc.thonly.reverie_dreams.item.base.BasicPolymerItem;
import cc.thonly.reverie_dreams.server.DelayedTask;
import eu.pb4.sgui.api.elements.BookElementBuilder;
import eu.pb4.sgui.api.gui.BookGui;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WrittenBookContentComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TouhouHelperItem extends BasicPolymerItem {
    public TouhouHelperItem(String path, Settings settings) {
        super(path,
                settings.maxCount(1)
                        .rarity(Rarity.EPIC)
                        .component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
                        ,
                Items.KNOWLEDGE_BOOK
        );
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient() && user instanceof ServerPlayerEntity player) {
            ItemStack itemStack = user.getStackInHand(hand);
            user.useBook(itemStack, hand);
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            HelperGui helperGui = new HelperGui(player);
            helperGui.open();
            return ActionResult.SUCCESS_SERVER;
        }
        return ActionResult.SUCCESS;
    }

    public static boolean resolve(ItemStack book, ServerCommandSource commandSource, @Nullable PlayerEntity player) {
        WrittenBookContentComponent writtenBookContentComponent = book.get(DataComponentTypes.WRITTEN_BOOK_CONTENT);
        if (writtenBookContentComponent != null && !writtenBookContentComponent.resolved()) {
            WrittenBookContentComponent writtenBookContentComponent2 = writtenBookContentComponent.resolve(commandSource, player);
            if (writtenBookContentComponent2 != null) {
                book.set(DataComponentTypes.WRITTEN_BOOK_CONTENT, writtenBookContentComponent2);
                return true;
            }
            book.set(DataComponentTypes.WRITTEN_BOOK_CONTENT, writtenBookContentComponent.asResolved());
        }
        return false;
    }

    public static class HelperGui extends BookGui {

        public HelperGui(ServerPlayerEntity player) {
            super(player, getDefaultBookElement());
            this.init();
        }

        public void init() {
            this.player.playSoundToPlayer(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }

        public static BookElementBuilder getDefaultBookElement() {
            BookElementBuilder builder = new BookElementBuilder();
            // 1
            builder.addPage(
                    Text.empty().append("欢迎游玩东方Project模组，本书将介绍模组的游玩指南"),
                    Text.empty(),
                    Text.empty().append("导航目录："),
                    Text.empty().append("- [祭坛摆放]").setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, "3"))),
                    Text.empty().append("- [弹幕合成]").setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, "5"))),
                    Text.empty().append("- [物品合成]"),
                    Text.empty().append("- [Fumo制作]"),
                    Text.empty().append("- [配方管理器]").setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/touhou ui_relay_recipe")))
            );
            // 2
            builder.addPage(Text.empty());
            // 3
            builder.addPage(
                    Text.literal("祭坛摆放："),
                    Text.empty().append("§b⏹§b⏹§b⏹§d⏹§b⏹§b⏹§b⏹"),
                    Text.empty().append("§b⏹§c⏹§b⏹§b⏹§b⏹§c⏹§b⏹"),
                    Text.empty().append("§b⏹§b⏹§b⏹§b⏹§b⏹§b⏹§b⏹"),
                    Text.empty().append("§d⏹§b⏹§b⏹§e⏹§b⏹§b⏹§d⏹"),
                    Text.empty().append("§b⏹§b⏹§b⏹§b⏹§b⏹§b⏹§b⏹"),
                    Text.empty().append("§b⏹§c⏹§b⏹§b⏹§b⏹§c⏹§b⏹"),
                    Text.empty().append("§b⏹§b⏹§b⏹§d⏹§b⏹§b⏹§b⏹"),
                    Text.empty(),
                    Text.empty().append("§b⏹：").append(Text.translatable(Items.AIR.getTranslationKey())),
                    Text.empty().append("§c⏹：").append(Text.translatable(ModBlocks.STRIPPED_SPIRITUAL_LOG.getTranslationKey())),
                    Text.empty().append("§e⏹：").append(Text.translatable(ModBlocks.GENSOKYO_ALTAR.getTranslationKey())),
                    Text.empty().append("注意：").append("§c⏹§r一共摆放三层")
            );
            // 4
            builder.addPage(Text.empty());
            // 5
            builder.addPage(
                    Text.empty().append("弹幕工作台："),
                    Text.empty().append("弹幕工作台是一个合成弹幕的工作方块，里面共有5个槽位可以摆放物品合成，合成类型为有序合成"),
                    Text.empty(),
                    Text.empty().append("合成配方："),
                    Text.empty().append("§b⏹⏹⏹"),
                    Text.empty().append("§b⏹⏹⏹"),
                    Text.empty().append("§b⏹⏹⏹")
            );
            builder.addPage(
                    Text.empty().append("通用弹幕配方："),
                    Text.empty().append("§a⏹ -> ").append("任意颜料"),
                    Text.empty().append("§b⏹ -> ").append(Text.translatable(Items.FIREWORK_STAR.getTranslationKey())),
                    Text.empty().append("§c⏹ -> ").append(Text.translatable(ModItems.POWER.getTranslationKey())).append("*20"),
                    Text.empty().append("§d⏹ -> ").append(Text.translatable(ModItems.POINT.getTranslationKey())).append("*20"),
                    Text.empty().append("§e⏹ -> ")
            );
            builder.signed();
            return builder;
        }

        @Override
        public boolean onCommand(String command) {
            if (command.equals("/touhou ui_relay_recipe")) {
                this.close();
                DelayedTask.createFromSecond(this.player.getServer(), 0.1f, () -> RecipeTypeCategoryGui.create(this.player));
            }
            return false;
        }

        @Override
        public void onPreviousPageButton() {
            super.onPreviousPageButton();
            this.player.playSoundToPlayer(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }

        @Override
        public void onNextPageButton() {
            super.onNextPageButton();
            this.player.playSoundToPlayer(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }

        @Override
        public void onTakeBookButton() {
            super.onTakeBookButton();
            this.close();
        }

    }
}
