package cc.thonly.touhoumod.gui.recipe.entry;

import cc.thonly.touhoumod.block.ModBlocks;
import cc.thonly.touhoumod.block.entity.DanmakuCraftingTableBlockEntity;
import cc.thonly.touhoumod.gui.GuiCommon;
import cc.thonly.touhoumod.interfaces.ComponentMapBuilderImpl;
import cc.thonly.touhoumod.interfaces.ItemSettingsAccessorImpl;
import cc.thonly.touhoumod.interfaces.ItemStackImpl;
import cc.thonly.touhoumod.item.ModGuiItems;
import cc.thonly.touhoumod.recipe.DanmakuRecipes;
import cc.thonly.touhoumod.recipe.entry.DanmakuRecipe;
import cc.thonly.touhoumod.recipe.slot.CountRecipeSlot;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SimpleGui;
import lombok.Getter;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DanmakuCraftingTableGui extends SimpleGui implements GuiCommon {
    private final DanmakuCraftingTableBlockEntity blockEntity;
    private static final String[] layout = {
            "CCCCC###X",
            "CCCCC###X",
            "CCCCC#B#X",
            "CCCCC###X",
            "CCCCC###X",
    };

    private final List<Integer> cSlots = new ArrayList<>();

    public DanmakuCraftingTableGui(ServerPlayerEntity player, World world, BlockPos pos) {
        super(ScreenHandlerType.GENERIC_9X5, player, false);
        this.setTitle(Text.translatable(ModBlocks.DANMAKU_CRAFTING_TABLE.getTranslationKey()));
        this.blockEntity = (DanmakuCraftingTableBlockEntity) world.getBlockEntity(pos);
        this.init();
    }

    @Override
    public void init() {
        Inventory inventory = this.blockEntity.getInventory();

        for (int row = 0; row < layout.length; ++row) {
            for (int col = 0; col < layout[row].length(); ++col) {
                int slot = row * 9 + col;
                char slotType = layout[row].charAt(col);
                switch (slotType) {
                    case '#' -> {
                        this.setSlot(slot, new GuiElementBuilder()
                                .setItem(ModGuiItems.EMPTY_SLOT));
                    }
                    case 'C' -> {
                        cSlots.add(slot);
                        this.setSlot(slot, new GuiElementBuilder()
                                .setItem(Items.AIR)
                        );
                    }
                    case 'T' -> {
                        this.setSlot(slot, new GuiElementBuilder()
                                .setItem(ModGuiItems.PROGRESS_TO_RESULT)
                                .setCallback((index, type, action) -> {

                                })
                        );
                    }
                    case 'X' -> {
                        int invSlot = row;
                        this.setSlotRedirect(slot, new Slot(inventory, invSlot, 0, 0));
                    }
                    case 'B' -> {
                        this.setSlot(slot, new GuiElementBuilder()
                                .setItem(ModGuiItems.PROGRESS_TO_RESULT_REVERSE)
                        );
                    }
                }
            }
        }
    }

    int tick = 0;

    @Override
    public void onTick() {
        super.onTick();
        if (blockEntity.getWorld() != null && blockEntity.getWorld().getBlockState(blockEntity.getPos()).getBlock() != ModBlocks.DANMAKU_CRAFTING_TABLE) {
            this.close();
            return;
        }
        tick++;

        if (tick > 2) {
            List<CountRecipeSlot> countRecipeSlots = getRecipeSlotsForX();
            List<DanmakuRecipe.Entry> recipeEntries = DanmakuRecipes.getRecipeRegistryRef().tryGetRecipes(countRecipeSlots);
            int resultIndex = 0;

            for (int slot : cSlots) {
                if (resultIndex < recipeEntries.size()) {
                    DanmakuRecipe.Entry recipeEntry = recipeEntries.get(resultIndex++);
                    CountRecipeSlot resultSlot = recipeEntry.getOutput();
                    ItemStack resultItemStack = new ItemStack(resultSlot.getItem(), resultSlot.getCount());
                    ItemSettingsAccessorImpl itemSettings = resultSlot.getItemSettings();
                    if (resultSlot.getItemSettings() != null) {
                        ComponentMap.Builder components = itemSettings.getComponents();
                        ComponentMapBuilderImpl componentsImpl = (ComponentMapBuilderImpl) (Object) components;
                        for (var set : componentsImpl.getComponents().entrySet()) {
                            ComponentType<?> key = set.getKey();
                            Object value = set.getValue();
                            ItemStackImpl.setComponentSafe(resultItemStack, key, value);
                        }
                    }
                    this.setSlot(slot, new GuiElementBuilder()
                            .setItem(resultItemStack.getItem())
                            .setCount(resultItemStack.getCount())
                            .setCallback((index, type, action) -> handleCrafting(index, recipeEntry))
                    );
                } else {
                    this.setSlot(slot, new GuiElementBuilder()
                            .setItem(Items.AIR)
                    );
                }
            }

            tick = 0;
        }
    }

    private void handleCrafting(int index, DanmakuRecipe.Entry recipeEntry) {
        SimpleInventory inventory = blockEntity.getInventory();
        // 移除配方中所需的物品
        for (CountRecipeSlot countRecipeSlot : List.of(recipeEntry.getDye(), recipeEntry.getCore(), recipeEntry.getPower(), recipeEntry.getPoint(), recipeEntry.getMaterial())) {
            if (countRecipeSlot.getItem() != Items.AIR) {
                Item item = countRecipeSlot.getItem();
                int count = countRecipeSlot.getCount();
                inventory.removeItem(item, count);
            }
        }

        // 获取合成结果并给予玩家
        CountRecipeSlot resultSlot = recipeEntry.getOutput();
        ItemStack resultItemStack = new ItemStack(resultSlot.getItem(), resultSlot.getCount());

        player.giveItemStack(resultItemStack);
    }

    private List<CountRecipeSlot> getRecipeSlotsForX() {
        List<CountRecipeSlot> countRecipeSlotList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ItemStack itemStack = blockEntity.getInventory().getStack(i);
            countRecipeSlotList.add(new CountRecipeSlot(itemStack.getItem(), itemStack.getCount()));
        }
        return countRecipeSlotList;
    }

    @Override
    public void onClose() {
        super.onClose();

        blockEntity.markDirty();
    }
}
