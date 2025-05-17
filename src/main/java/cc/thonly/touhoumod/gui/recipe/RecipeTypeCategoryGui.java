package cc.thonly.touhoumod.gui.recipe;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.block.ModBlocks;
import cc.thonly.touhoumod.gui.PlayerHeadInfo;
import cc.thonly.touhoumod.gui.recipe.display.DanmakuTableDisplayView;
import cc.thonly.touhoumod.gui.recipe.display.GensokyoAltarDisplayView;
import cc.thonly.touhoumod.gui.recipe.display.StrengthTableDisplayView;
import cc.thonly.touhoumod.gui.server.BasePageGui;
import cc.thonly.touhoumod.item.ModItems;
import cc.thonly.touhoumod.recipe.DanmakuRecipes;
import cc.thonly.touhoumod.recipe.GensokyoAltarRecipes;
import cc.thonly.touhoumod.recipe.SimpleRegistryInstance;
import cc.thonly.touhoumod.recipe.StrengthTableRecipes;
import cc.thonly.touhoumod.recipe.entry.DanmakuRecipe;
import cc.thonly.touhoumod.recipe.entry.GensokyoAltarRecipe;
import cc.thonly.touhoumod.recipe.entry.StrengthTableRecipe;
import eu.pb4.sgui.api.ClickType;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.LinkedList;
import java.util.List;

public class RecipeTypeCategoryGui extends SimpleGui {
    public static final String[][] GRID = {
            {"X", "X", "X", "X", "X", "X", "X", "X", "X"},
            {"X", "X", "X", "X", "X", "X", "X", "X", "X"},
            {"X", "X", "X", "X", "X", "X", "X", "X", "X"},
            {"X", "X", "X", "X", "X", "X", "X", "X", "X"},
            {"X", "X", "X", "X", "X", "X", "X", "X", "X"},
            {"P", "W", "W", "W", "W", "W", "W", "W", "N"},
    };
    public static final List<RecipeTypeGuiInfo<? extends BasePageGui>> ENTRIES = new LinkedList<>();
    public static final int PER_PAGE_SIZE = 5 * 9;

    public static RecipeTypeGuiInfo<? extends BasePageGui> registerCategory(RecipeTypeGuiInfo<? extends BasePageGui> type) {
        ENTRIES.add(type);
        return type;
    }

    public final List<GuiElementBuilder> recipeElements = new LinkedList<>();
    public final GuiElementBuilder next = new GuiElementBuilder().setItem(Items.PLAYER_HEAD).setSkullOwner(PlayerHeadInfo.GUI_NEXT_PAGE).setItemName(Text.of("Next Page")).setCallback(this::next);
    public final GuiElementBuilder prev = new GuiElementBuilder().setItem(Items.PLAYER_HEAD).setSkullOwner(PlayerHeadInfo.GUI_PREVIOUS_PAGE).setItemName(Text.of("Prev Page")).setCallback(this::prev);
    public int page = 0;

    public RecipeTypeCategoryGui(ServerPlayerEntity player) {
        super(ScreenHandlerType.GENERIC_9X6, player, false);
        this.init();
    }

    public RecipeTypeCategoryGui(ServerPlayerEntity player, int page) {
        this(player);
        this.page = page;
    }

    public static RecipeTypeCategoryGui create(ServerPlayerEntity player) {
        RecipeTypeCategoryGui recipeTypeCategoryGui = new RecipeTypeCategoryGui(player);
        recipeTypeCategoryGui.open();
        return recipeTypeCategoryGui;
    }

    public void init() {
        for (int row = 0; row < GRID.length; row++) {
            for (int col = 0; col < GRID[row].length; col++) {
                String c = GRID[row][col];
                int slot = row * 9 + col;
                if (c.equalsIgnoreCase("X")) {
                    GuiElementBuilder builder = new GuiElementBuilder().setItem(Items.AIR);
                    builder.setCallback(this::clickIcon);
                    this.recipeElements.add(builder);
                    this.setSlot(slot, builder);
                }
                if (c.equalsIgnoreCase("N")) {
                    this.setSlot(slot, this.next);
                }
                if (c.equalsIgnoreCase("P")) {
                    this.setSlot(slot, this.prev);
                }
                if (c.equalsIgnoreCase("W")) {
                    this.setSlot(slot, new GuiElementBuilder().setItem(Items.WHITE_STAINED_GLASS_PANE));
                }
            }
        }
    }

    public void clickIcon(int index, ClickType clickType, SlotActionType action) {
        int iconIndex = this.page * PER_PAGE_SIZE + index;
        if (ENTRIES.size() > iconIndex) {
            RecipeTypeGuiInfo<? extends BasePageGui> info = ENTRIES.get(iconIndex);
        }
    }

    public void next(int index, ClickType clickType, SlotActionType action) {
        this.player.playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
        if (this.page < getMaxPage()) {
            this.page++;
        }
    }

    public void prev(int index, ClickType clickType, SlotActionType action) {
        this.player.playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);

        if (this.page > getMinPage()) {
            this.page--;
        }
    }

    public int getMinPage() {
        return 0;
    }

    public int getMaxPage() {
        return Math.max(0, (this.recipeElements.size() - 1) / PER_PAGE_SIZE);
    }

    @Override
    public void onTick() {
        super.onTick();
        this.setTitle(Text.of("Recipes Display" + " " + "(" + (this.page + 1) + "/" + (getMaxPage() + 1) + ")"));

        int start = this.page * PER_PAGE_SIZE;

        for (int i = 0; i < PER_PAGE_SIZE; i++) {
            int slotIndex = i;
            int recipeIndex = start + i;

            if (recipeIndex < ENTRIES.size()) {
                RecipeTypeGuiInfo<? extends BasePageGui> recipeTypeGuiInfo = ENTRIES.get(recipeIndex + this.page * PER_PAGE_SIZE);
                GuiElementBuilder icon = new GuiElementBuilder()
                        .setItem(recipeTypeGuiInfo.getIcon().getItem())
                        .setItemName(Text.translatable(recipeTypeGuiInfo.getId().toTranslationKey()))
                        .setLore(List.of())
                        .setCallback((slot, click, action) -> {
                            this.close();
                            this.player.playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
                            recipeTypeGuiInfo.create(player, () -> new RecipeTypeCategoryGui(player, this.page));
                        });
                this.setSlot(getGridSlot(slotIndex), icon);
            } else {
                this.setSlot(getGridSlot(slotIndex), new GuiElementBuilder().setItem(Items.AIR));
            }
        }
    }

    private int getGridSlot(int index) {
        int count = 0;
        for (int row = 0; row < GRID.length; row++) {
            for (int col = 0; col < GRID[row].length; col++) {
                if (GRID[row][col].equalsIgnoreCase("X")) {
                    if (count == index) {
                        return row * 9 + col;
                    }
                    count++;
                }
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    public static void setup() {
        registerCategory(new RecipeTypeGuiInfo<>(new ItemStack(ModItems.POWER), Touhou.id("recipe/danmaku_table"), BasePageGui.class,
                DanmakuRecipes::getRecipeRegistryRef,
                ((gui, slotIndex) -> {
                    SimpleRegistryInstance<DanmakuRecipe.Entry>.Key2ValueEntry key2ValueEntry = (SimpleRegistryInstance<DanmakuRecipe.Entry>.Key2ValueEntry) gui.getEntries().get(slotIndex + gui.getPage() * BasePageGui.PER_PAGE_SIZE);
                    GuiElementBuilder icon = new GuiElementBuilder()
                            .setItem(key2ValueEntry.getValue().getOutput().getItem())
                            .setItemName(key2ValueEntry.getValue().getOutput().getStack().getName())
                            .setCallback((slot, click, action) -> {
                                gui.close();
                                gui.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
                                SimpleGui view = new DanmakuTableDisplayView(gui.getPlayer(), key2ValueEntry, () -> new BasePageGui(gui.getPlayer(), gui.getRecipeGuiInfo(), gui.getRecipeTypeInfo(), gui.getPrevGuiCallback()));
                                view.open();
                            });
                    gui.setSlot(gui.getGridSlot(slotIndex), icon);
                })
        ));
        registerCategory(new RecipeTypeGuiInfo<>(new ItemStack(ModItems.ICON), Touhou.id("recipe/gensokyo_altar"), BasePageGui.class,
                GensokyoAltarRecipes::getRecipeRegistryRef,
                ((gui, slotIndex) -> {
                    SimpleRegistryInstance<GensokyoAltarRecipe.Entry>.Key2ValueEntry key2ValueEntry = (SimpleRegistryInstance<GensokyoAltarRecipe.Entry>.Key2ValueEntry) gui.getEntries().get(slotIndex + gui.getPage() * BasePageGui.PER_PAGE_SIZE);
                    GuiElementBuilder icon = new GuiElementBuilder()
                            .setItem(key2ValueEntry.getValue().getOutput().getItem())
                            .setItemName(key2ValueEntry.getValue().getOutput().getStack().getName())
                            .setCallback((slot, click, action) -> {
                                gui.close();
                                gui.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
                                SimpleGui view = new GensokyoAltarDisplayView(gui.getPlayer(), key2ValueEntry, () -> new BasePageGui(gui.getPlayer(), gui.getRecipeGuiInfo(), gui.getRecipeTypeInfo(), gui.getPrevGuiCallback()));
                                view.open();
                            });
                    gui.setSlot(gui.getGridSlot(slotIndex), icon);
                })
        ));
        registerCategory(new RecipeTypeGuiInfo<>(new ItemStack(ModBlocks.STRENGTH_TABLE), Touhou.id("recipe/strength_table"), BasePageGui.class,
                StrengthTableRecipes::getRecipeRegistryRef,
                ((gui, slotIndex) -> {
                    SimpleRegistryInstance<StrengthTableRecipe.Entry>.Key2ValueEntry key2ValueEntry = (SimpleRegistryInstance<StrengthTableRecipe.Entry>.Key2ValueEntry) gui.getEntries().get(slotIndex + gui.getPage() * BasePageGui.PER_PAGE_SIZE);
                    GuiElementBuilder icon = new GuiElementBuilder()
                            .setItem(key2ValueEntry.getValue().getOutput().getItem())
                            .setItemName(key2ValueEntry.getValue().getOutput().getStack().getName())
                            .setCallback((slot, click, action) -> {
                                gui.close();
                                gui.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
                                SimpleGui view = new StrengthTableDisplayView(gui.getPlayer(), key2ValueEntry, () -> new BasePageGui(gui.getPlayer(), gui.getRecipeGuiInfo(), gui.getRecipeTypeInfo(), gui.getPrevGuiCallback()));
                            });
                    gui.setSlot(gui.getGridSlot(slotIndex), icon);
                })
        ));
    }
}
