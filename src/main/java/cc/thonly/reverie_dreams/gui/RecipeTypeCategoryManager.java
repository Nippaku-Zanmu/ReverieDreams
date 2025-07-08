package cc.thonly.reverie_dreams.gui;

import cc.thonly.mystias_izakaya.block.MIBlocks;
import cc.thonly.mystias_izakaya.recipe.entry.KitchenRecipe;
import cc.thonly.mystias_izakaya.recipe.type.KitchenRecipeType;
import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.gui.access.GuiElementBuilderAccessor;
import cc.thonly.reverie_dreams.gui.recipe.GuiOpeningPrevCallback;
import cc.thonly.reverie_dreams.gui.recipe.RecipeTypeGetter;
import cc.thonly.reverie_dreams.gui.recipe.RecipeTypeGuiInfo;
import cc.thonly.reverie_dreams.gui.recipe.display.*;
import cc.thonly.reverie_dreams.gui.server.BasePageGui;
import cc.thonly.reverie_dreams.item.ModItems;
import cc.thonly.reverie_dreams.recipe.BaseRecipe;
import cc.thonly.reverie_dreams.recipe.BaseRecipeType;
import cc.thonly.reverie_dreams.recipe.view.RecipeEntryWrapper;
import cc.thonly.reverie_dreams.recipe.entry.DanmakuRecipe;
import cc.thonly.reverie_dreams.recipe.entry.GensokyoAltarRecipe;
import cc.thonly.reverie_dreams.recipe.entry.StrengthTableRecipe;
import cc.thonly.reverie_dreams.recipe.type.DanmakuRecipeType;
import cc.thonly.reverie_dreams.recipe.type.GensokyoAltarRecipeType;
import cc.thonly.reverie_dreams.recipe.type.StrengthTableRecipeType;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SimpleGui;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@SuppressWarnings("unchecked")
public class RecipeTypeCategoryManager {
    public static final Map<Identifier, RecipeTypeGuiInfo<? extends BasePageGui>> REGISTRIES = new Object2ObjectOpenHashMap<>();
    public static final List<RecipeTypeGuiInfo<? extends BasePageGui>> CATEGORY_ENTRIES = new LinkedList<>();

    public static void registerCategory(RecipeTypeGuiInfo<? extends BasePageGui> type) {
        CATEGORY_ENTRIES.add(type);
        REGISTRIES.put(type.getId(), type);
    }

    public static void open(Identifier categoryRecipeTypeId, ServerPlayerEntity player, GuiOpeningPrevCallback prevGuiCallback) {
        player.closeHandledScreen();
        RecipeTypeGuiInfo<BasePageGui> category = getCategory(categoryRecipeTypeId);
        if (category != null) {
            category.create(player, prevGuiCallback);
        }
    }

    public static void open(Identifier categoryRecipeTypeId, Identifier recipeId, ServerPlayerEntity player, GuiOpeningPrevCallback prevGuiCallback) {
        player.closeHandledScreen();
        RecipeTypeGuiInfo<BasePageGui> category = getCategory(categoryRecipeTypeId);
        if (category != null) {
            RecipeTypeGetter registryGetter = category.getRegistryGetter();
            BaseRecipeType<?> baseRecipeType = registryGetter.get();
            BaseRecipe recipe = baseRecipeType.getRecipeById(recipeId);
            if (recipe == null) {
                return;
            }
            RecipeEntryWrapper<?> key2ValueEntry = new RecipeEntryWrapper<>(recipe.getId(), recipe);
            Class<? extends DisplayView> viewClazz = category.getViewClazz();
            try {
                SimpleGui recipeView = DisplayView.create((Class<? extends SimpleGui>) viewClazz, player, key2ValueEntry, prevGuiCallback);
                if (recipeView != null) {
                    recipeView.open();
                }
            } catch (Exception e) {
                log.error("Can't create view instance", e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends BasePageGui> RecipeTypeGuiInfo<T> getCategory(Identifier categoryRecipeTypeId) {
        return (RecipeTypeGuiInfo<T>) REGISTRIES.get(categoryRecipeTypeId);
    }

    @SuppressWarnings("unchecked")
    public static void registerCategories() {
        registerCategory(new RecipeTypeGuiInfo<>(new ItemStack(ModItems.POWER), Touhou.id("recipe/danmaku_table"), BasePageGui.class,
                DanmakuTableDisplayView.class,
                DanmakuRecipeType::getInstance,
                ((gui, slotIndex) -> {
                    RecipeEntryWrapper<DanmakuRecipe> key2ValueEntry = (RecipeEntryWrapper<DanmakuRecipe>) gui.getEntries().get(slotIndex + gui.getPage() * BasePageGui.PER_PAGE_SIZE);
                    GuiElementBuilder icon = new GuiElementBuilder()
                            .setItem(key2ValueEntry.getValue().getOutput().getItem())
                            .setItemName(key2ValueEntry.getValue().getOutput().getItemStack().getName())
                            .setCallback((slot, click, action) -> {
                                gui.close();
                                gui.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
                                SimpleGui view = new DanmakuTableDisplayView(gui.getPlayer(), key2ValueEntry, () -> new BasePageGui(gui.getPlayer(), gui.getRecipeGuiInfo(), gui.getRecipeTypeInfo(), gui.getPrevGuiCallback()));
                                view.open();
                            });
                    GuiElementBuilderAccessor accessor = (GuiElementBuilderAccessor) icon;
                    accessor.setItemStack(key2ValueEntry.getValue().getOutput().getItemStack());
                    gui.setSlot(gui.getGridSlot(slotIndex), icon);
                })
        ));
        registerCategory(new RecipeTypeGuiInfo<>(new ItemStack(ModItems.ICON), Touhou.id("recipe/gensokyo_altar"), BasePageGui.class,
                GensokyoAltarDisplayView.class,
                GensokyoAltarRecipeType::getInstance,
                ((gui, slotIndex) -> {
                    RecipeEntryWrapper<GensokyoAltarRecipe> key2ValueEntry = (RecipeEntryWrapper<GensokyoAltarRecipe>) gui.getEntries().get(slotIndex + gui.getPage() * BasePageGui.PER_PAGE_SIZE);
                    GuiElementBuilder icon = new GuiElementBuilder()
                            .setItem(key2ValueEntry.getValue().getOutput().getItem())
                            .setItemName(key2ValueEntry.getValue().getOutput().getItemStack().getName())
                            .setCallback((slot, click, action) -> {
                                gui.close();
                                gui.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
                                SimpleGui view = new GensokyoAltarDisplayView(gui.getPlayer(), key2ValueEntry, () -> new BasePageGui(gui.getPlayer(), gui.getRecipeGuiInfo(), gui.getRecipeTypeInfo(), gui.getPrevGuiCallback()));
                                view.open();
                            });
                    GuiElementBuilderAccessor accessor = (GuiElementBuilderAccessor) icon;
                    accessor.setItemStack(key2ValueEntry.getValue().getOutput().getItemStack());
                    gui.setSlot(gui.getGridSlot(slotIndex), icon);
                })
        ));
        registerCategory(new RecipeTypeGuiInfo<>(new ItemStack(ModBlocks.STRENGTH_TABLE), Touhou.id("recipe/strength_table"), BasePageGui.class,
                StrengthTableDisplayView.class,
                StrengthTableRecipeType::getInstance,
                ((gui, slotIndex) -> {
                    RecipeEntryWrapper<StrengthTableRecipe> key2ValueEntry = (RecipeEntryWrapper<StrengthTableRecipe>) gui.getEntries().get(slotIndex + gui.getPage() * BasePageGui.PER_PAGE_SIZE);
                    GuiElementBuilder icon = new GuiElementBuilder()
                            .setItem(key2ValueEntry.getValue().getOutput().getItem())
                            .setItemName(key2ValueEntry.getValue().getOutput().getItemStack().getName())
                            .setCallback((slot, click, action) -> {
                                gui.close();
                                gui.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
                                SimpleGui view = new StrengthTableDisplayView(gui.getPlayer(), key2ValueEntry, () -> new BasePageGui(gui.getPlayer(), gui.getRecipeGuiInfo(), gui.getRecipeTypeInfo(), gui.getPrevGuiCallback()));
                                view.open();
                            });
                    GuiElementBuilderAccessor accessor = (GuiElementBuilderAccessor) icon;
                    accessor.setItemStack(key2ValueEntry.getValue().getOutput().getItemStack());
                    gui.setSlot(gui.getGridSlot(slotIndex), icon);
                })
        ));
        registerCategory(new RecipeTypeGuiInfo<>(new ItemStack(MIBlocks.COOKING_POT), Touhou.id("recipe/kitchen"), BasePageGui.class,
                KitchenBlockDisplayView.class,
                KitchenRecipeType::getInstance,
                ((gui, slotIndex) -> {
                    RecipeEntryWrapper<KitchenRecipe> key2ValueEntry = (RecipeEntryWrapper<KitchenRecipe>) gui.getEntries().get(slotIndex + gui.getPage() * BasePageGui.PER_PAGE_SIZE);
                    GuiElementBuilder icon = new GuiElementBuilder()
                            .setItem(key2ValueEntry.getValue().getOutput().getItem())
                            .setItemName(key2ValueEntry.getValue().getOutput().getItemStack().getName())
                            .setCallback((slot, click, action) -> {
                                gui.close();
                                gui.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
                                SimpleGui view = new KitchenBlockDisplayView(gui.getPlayer(), key2ValueEntry, () -> new BasePageGui(gui.getPlayer(), gui.getRecipeGuiInfo(), gui.getRecipeTypeInfo(), gui.getPrevGuiCallback()));
                                view.open();
                            });
                    GuiElementBuilderAccessor accessor = (GuiElementBuilderAccessor) icon;
                    accessor.setItemStack(key2ValueEntry.getValue().getOutput().getItemStack());
                    gui.setSlot(gui.getGridSlot(slotIndex), icon);
                })
        ));
    }
}
