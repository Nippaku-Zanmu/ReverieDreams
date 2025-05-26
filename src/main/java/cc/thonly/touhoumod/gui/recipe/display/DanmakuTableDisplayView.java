package cc.thonly.touhoumod.gui.recipe.display;

import cc.thonly.touhoumod.gui.PlayerHeadInfo;
import cc.thonly.touhoumod.gui.recipe.GuiOpeningPrevCallback;
import cc.thonly.touhoumod.item.ModGuiItems;
import cc.thonly.touhoumod.recipe.SimpleRecipeRegistryBase;
import cc.thonly.touhoumod.recipe.entry.DanmakuRecipe;
import cc.thonly.touhoumod.recipe.slot.CountRecipeSlot;
import eu.pb4.sgui.api.ClickType;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SimpleGui;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Getter
@Slf4j
@ToString(callSuper = true)
public class DanmakuTableDisplayView extends SimpleGui implements DisplayView {
    public final SimpleRecipeRegistryBase<DanmakuRecipe.Entry>.Key2ValueEntry key2ValueEntry;
    public final Identifier key;
    public final DanmakuRecipe.Entry value;
    public final GuiElementBuilder back = new GuiElementBuilder().setItem(ModGuiItems.BACK).setSkullOwner(PlayerHeadInfo.GUI_ADD).setItemName(Text.of("Back")).setCallback(this::back);
    public final GuiOpeningPrevCallback prevGuiCallback;

    public DanmakuTableDisplayView(ServerPlayerEntity player, SimpleRecipeRegistryBase<DanmakuRecipe.Entry>.Key2ValueEntry key2ValueEntry, GuiOpeningPrevCallback prevGuiCallback) {
        super(ScreenHandlerType.GENERIC_9X6, player, false);
        this.key2ValueEntry = key2ValueEntry;
        this.key = this.key2ValueEntry.getKey();
        this.value = this.key2ValueEntry.getValue();
        this.prevGuiCallback = prevGuiCallback;
        this.init();
    }

    @Override
    public void init() {
        this.setTitle(this.key2ValueEntry.getValue().getOutput().getStack().getName());
        List<CountRecipeSlot> inputs = new LinkedList<>();
        inputs.add(this.value.getDye());
        inputs.add(this.value.getCore());
        inputs.add(this.value.getPower());
        inputs.add(this.value.getPoint());
        inputs.add(this.value.getMaterial());
        Iterator<CountRecipeSlot> slotIterator = inputs.iterator();

        String[][] grid = this.getGrid();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                String c = grid[row][col];
                int slot = row * 9 + col;
                if (c.equalsIgnoreCase("X")) {
                    GuiElementBuilder builder = new GuiElementBuilder().setItem(ModGuiItems.EMPTY_SLOT);
                    this.setSlot(slot, builder);
                }
                if (c.equalsIgnoreCase("T")) {
                    GuiElementBuilder builder = new GuiElementBuilder().setItem(ModGuiItems.PROGRESS_TO_RESULT);
                    this.setSlot(slot, builder);
                }
                if (c.equalsIgnoreCase("B")) {
                    this.setSlot(slot, this.back);
                }
                if (c.equalsIgnoreCase("W")) {
                    this.setSlot(slot, new GuiElementBuilder().setItem(Items.WHITE_STAINED_GLASS_PANE));
                }
                if (c.equalsIgnoreCase("I")) {
                    if(slotIterator.hasNext()) {
                        CountRecipeSlot next = slotIterator.next();
                        this.setSlot(slot, this.getGuiElementBuilder(next));
                    }
                }
                if (c.equalsIgnoreCase("O")) {
                    CountRecipeSlot output = this.value.getOutput();
                    this.setSlot(slot, this.getGuiElementBuilder(output));
                }
            }
        }
    }

    public void back(int index, ClickType clickType, SlotActionType action) {
        this.player.playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
        this.close();
        if (this.prevGuiCallback != null) {
            SimpleGui applyGui = this.prevGuiCallback.apply();
            applyGui.open();
        }
    }

    @Override
    public String[][] getGrid() {
        return new String[][]{
                {"B", "X", "X", "X", "X", "X", "X", "X", "X"},
                {"X", "X", "X", "I", "X", "X", "X", "X", "X"},
                {"X", "X", "X", "I", "X", "X", "X", "X", "X"},
                {"X", "X", "X", "I", "T", "O", "X", "X", "X"},
                {"X", "X", "X", "I", "X", "X", "X", "X", "X"},
                {"X", "X", "X", "I", "X", "X", "X", "X", "X"},
        };
    }
}
