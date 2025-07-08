package cc.thonly.reverie_dreams.gui.recipe.display;

import cc.thonly.reverie_dreams.gui.recipe.GuiOpeningPrevCallback;
import cc.thonly.reverie_dreams.recipe.view.RecipeEntryWrapper;
import cc.thonly.reverie_dreams.recipe.entry.StrengthTableRecipe;
import eu.pb4.sgui.api.ClickType;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.AnvilInputGui;
import eu.pb4.sgui.api.gui.SimpleGui;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

@Getter
@Slf4j
@ToString(callSuper = true)
public class StrengthTableDisplayView extends AnvilInputGui implements DisplayView {
    public final RecipeEntryWrapper<StrengthTableRecipe> key2ValueEntry;
    public final Identifier key;
    public final StrengthTableRecipe value;
    public final GuiOpeningPrevCallback prevGuiCallback;

    public StrengthTableDisplayView(ServerPlayerEntity player, RecipeEntryWrapper<StrengthTableRecipe> key2ValueEntry, GuiOpeningPrevCallback prevGuiCallback) {
        super(player, false);
        this.key2ValueEntry = key2ValueEntry;
        this.key = this.key2ValueEntry.getKey();
        this.value = this.key2ValueEntry.getValue();
        this.prevGuiCallback = prevGuiCallback;
        this.init();
    }
    @Override
    public void init() {
        this.setSlot(0, new GuiElementBuilder().setItem(this.getValue().getMainItem().getItem()));
        this.setSlot(1, new GuiElementBuilder().setItem(this.getValue().getOffItem().getItem()));
        this.setSlot(2, new GuiElementBuilder().setItem(this.getValue().getOutput().getItem()));
    }

    @Override
    public void onClose() {
        super.onClose();
        this.back(0,null,null);
    }

    public void back(int index, ClickType clickType, SlotActionType action) {
        this.player.playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
        this.close();
        if (this.prevGuiCallback != null) {
            SimpleGui applyGui = this.prevGuiCallback.apply();
            applyGui.open();
        }
    }
}
