package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.util.IdentifierGetter;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSets;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ModGuiItems {
    public static final List<Item> GUI_ITEM_LIST = new ArrayList<>();
    public static final Item NEXT = registerItem(new BasicGuiItem("sgui/elements/next", createSlotItemSettings()));
    public static final Item PREV = registerItem(new BasicGuiItem("sgui/elements/prev", createSlotItemSettings()));
    public static final Item BACK = registerItem(new BasicGuiItem("sgui/elements/back_slot", createSlotItemSettings()));
    public static final Item EMPTY_SLOT = registerItem(new BasicGuiItem("sgui/elements/empty_slot", createSlotItemSettings()));
    public static final Item PROGRESS_TO_RESULT = registerItem(new BasicGuiItem("sgui/elements/progress_to_result", createSlotItemSettings()));
    public static final Item PROGRESS_TO_RESULT_REVERSE = registerItem(new BasicGuiItem("sgui/elements/progress_to_result_reverse", createSlotItemSettings()));

    public static void init() {

    }

    public static Item registerItem(IdentifierGetter item) {
        Registry.register(Registries.ITEM, item.getIdentifier(), (Item) item);
        GUI_ITEM_LIST.add((Item) item);
        return (Item) item;
    }

    public static Item.Settings createSlotItemSettings() {
        return new Item.Settings()
                .maxCount(1)
                .translationKey("")
                .component(DataComponentTypes.ITEM_NAME, Text.of(""))
                .component(DataComponentTypes.TOOLTIP_DISPLAY, new TooltipDisplayComponent(
                                true,
                                ReferenceSortedSets.emptySet()
                        )
                );
    }

    public static List<Item> getGuiItemView() {
        return List.copyOf(GUI_ITEM_LIST);
    }
}
