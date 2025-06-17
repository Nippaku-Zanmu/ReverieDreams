package cc.thonly.reverie_dreams.gui;

import cc.thonly.mystias_izakaya.block.MIBlocks;
import cc.thonly.mystias_izakaya.recipe.entry.KitchenRecipe;
import cc.thonly.mystias_izakaya.recipe.type.KitchenRecipeType;
import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.gui.recipe.RecipeTypeGuiInfo;
import cc.thonly.reverie_dreams.gui.recipe.display.DanmakuTableDisplayView;
import cc.thonly.reverie_dreams.gui.recipe.display.GensokyoAltarDisplayView;
import cc.thonly.reverie_dreams.gui.recipe.display.KitchenBlockDisplayView;
import cc.thonly.reverie_dreams.gui.recipe.display.StrengthTableDisplayView;
import cc.thonly.reverie_dreams.gui.server.BasePageGui;
import cc.thonly.reverie_dreams.item.ModItems;
import cc.thonly.reverie_dreams.recipe.RecipeKey2ValueEntry;
import cc.thonly.reverie_dreams.recipe.entry.DanmakuRecipe;
import cc.thonly.reverie_dreams.recipe.entry.GensokyoAltarRecipe;
import cc.thonly.reverie_dreams.recipe.entry.StrengthTableRecipe;
import cc.thonly.reverie_dreams.recipe.type.DanmakuRecipeType;
import cc.thonly.reverie_dreams.recipe.type.GensokyoAltarRecipeType;
import cc.thonly.reverie_dreams.recipe.type.StrengthTableRecipeType;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import java.util.LinkedList;
import java.util.List;

public class RecipeTypeCategoryManager {

    public static final List<RecipeTypeGuiInfo<? extends BasePageGui>> CATEGORY_ENTRIES = new LinkedList<>();

    public static void registerCategory(RecipeTypeGuiInfo<? extends BasePageGui> type) {
        CATEGORY_ENTRIES.add(type);
    }

    @SuppressWarnings("unchecked")
    public static void setup() {
        registerCategory(new RecipeTypeGuiInfo<>(new ItemStack(ModItems.POWER), Touhou.id("recipe/danmaku_table"), BasePageGui.class,
                DanmakuRecipeType::getInstance,
                ((gui, slotIndex) -> {
                    RecipeKey2ValueEntry<DanmakuRecipe> key2ValueEntry = (RecipeKey2ValueEntry<DanmakuRecipe>) gui.getEntries().get(slotIndex + gui.getPage() * BasePageGui.PER_PAGE_SIZE);
                    GuiElementBuilder icon = new GuiElementBuilder()
                            .setItem(key2ValueEntry.getValue().getOutput().getItem())
                            .setItemName(key2ValueEntry.getValue().getOutput().getItemStack().getName())
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
                GensokyoAltarRecipeType::getInstance,
                ((gui, slotIndex) -> {
                    RecipeKey2ValueEntry<GensokyoAltarRecipe> key2ValueEntry = (RecipeKey2ValueEntry<GensokyoAltarRecipe>) gui.getEntries().get(slotIndex + gui.getPage() * BasePageGui.PER_PAGE_SIZE);
                    GuiElementBuilder icon = new GuiElementBuilder()
                            .setItem(key2ValueEntry.getValue().getOutput().getItem())
                            .setItemName(key2ValueEntry.getValue().getOutput().getItemStack().getName())
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
                StrengthTableRecipeType::getInstance,
                ((gui, slotIndex) -> {
                    RecipeKey2ValueEntry<StrengthTableRecipe> key2ValueEntry = (RecipeKey2ValueEntry<StrengthTableRecipe>) gui.getEntries().get(slotIndex + gui.getPage() * BasePageGui.PER_PAGE_SIZE);
                    GuiElementBuilder icon = new GuiElementBuilder()
                            .setItem(key2ValueEntry.getValue().getOutput().getItem())
                            .setItemName(key2ValueEntry.getValue().getOutput().getItemStack().getName())
                            .setCallback((slot, click, action) -> {
                                gui.close();
                                gui.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
                                SimpleGui view = new StrengthTableDisplayView(gui.getPlayer(), key2ValueEntry, () -> new BasePageGui(gui.getPlayer(), gui.getRecipeGuiInfo(), gui.getRecipeTypeInfo(), gui.getPrevGuiCallback()));
                                view.open();
                            });
                    gui.setSlot(gui.getGridSlot(slotIndex), icon);
                })
        ));
        registerCategory(new RecipeTypeGuiInfo<>(new ItemStack(MIBlocks.COOKING_POT), Touhou.id("recipe/kitchen"), BasePageGui.class,
                KitchenRecipeType::getInstance,
                ((gui, slotIndex) -> {
                    RecipeKey2ValueEntry<KitchenRecipe> key2ValueEntry = (RecipeKey2ValueEntry<KitchenRecipe>) gui.getEntries().get(slotIndex + gui.getPage() * BasePageGui.PER_PAGE_SIZE);
                    GuiElementBuilder icon = new GuiElementBuilder()
                            .setItem(key2ValueEntry.getValue().getOutput().getItem())
                            .setItemName(key2ValueEntry.getValue().getOutput().getItemStack().getName())
                            .setCallback((slot, click, action) -> {
                                gui.close();
                                gui.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
                                SimpleGui view = new KitchenBlockDisplayView(gui.getPlayer(), key2ValueEntry, () -> new BasePageGui(gui.getPlayer(), gui.getRecipeGuiInfo(), gui.getRecipeTypeInfo(), gui.getPrevGuiCallback()));
                                view.open();
                            });
                    gui.setSlot(gui.getGridSlot(slotIndex), icon);
                })
        ));
    }
}
