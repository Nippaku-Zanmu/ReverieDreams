package cc.thonly.reverie_dreams.gui.recipe.entry;

import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.block.entity.StrengthenTableBlockEntity;
import cc.thonly.reverie_dreams.gui.GuiCommon;
import cc.thonly.reverie_dreams.recipe.StrengthTableRecipes;
import cc.thonly.reverie_dreams.recipe.entry.StrengthTableRecipe;
import cc.thonly.reverie_dreams.recipe.slot.CountRecipeSlot;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.AnvilInputGui;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;

public class StrengthTableGui extends AnvilInputGui implements GuiCommon {
    StrengthenTableBlockEntity blockEntity;
    GuiElementBuilder output;

    public StrengthTableGui(ServerPlayerEntity player, StrengthenTableBlockEntity blockEntity, boolean manipulatePlayerSlots) {
        super(player, manipulatePlayerSlots);
        this.blockEntity = blockEntity;
        this.init();
    }

    public void init() {
        this.setSlotRedirect(0, new Slot(this.blockEntity.getInventory(), 0, 0, 0));
        this.setSlotRedirect(1, new Slot(this.blockEntity.getInventory(), 1, 0, 0));
        this.output = new GuiElementBuilder()
                .setCallback((index, type, action) -> click()
                );
        this.setSlot(2, this.output);
    }

    @Override
    public void onTick() {
        super.onTick();
        if (this.blockEntity == null) return;
        if (this.blockEntity.getWorld() != null && this.blockEntity.getWorld().getBlockState(this.blockEntity.getPos()).getBlock() != ModBlocks.STRENGTH_TABLE) {
            this.close();
        }
        StrengthTableRecipes.RecipeRegistryBase<StrengthTableRecipe.Entry> registry = StrengthTableRecipes.getRecipeRegistryRef();
        ItemStack mainStack = this.blockEntity.getInventory().getStack(0);
        ItemStack offStack = this.blockEntity.getInventory().getStack(1);
        CountRecipeSlot mainSlot = new CountRecipeSlot(mainStack.getItem(), mainStack.getCount());
        CountRecipeSlot offSlot = new CountRecipeSlot(offStack.getItem(), offStack.getCount());
        StrengthTableRecipe.Entry entry = registry.tryGetRecipes(mainSlot, offSlot);
        if (entry != null) {
            CountRecipeSlot resultItem = entry.getOutput();
            this.output.setItem(resultItem.getItem());
        } else {
            this.output.setItem(Items.AIR);
        }
    }

    public void click() {
        if (this.blockEntity == null) return;
        StrengthTableRecipes.RecipeRegistryBase<StrengthTableRecipe.Entry> registry = StrengthTableRecipes.getRecipeRegistryRef();
        ItemStack mainStack = this.blockEntity.getInventory().getStack(0);
        ItemStack offStack = this.blockEntity.getInventory().getStack(1);
        CountRecipeSlot mainSlot = new CountRecipeSlot(mainStack.getItem(), mainStack.getCount());
        CountRecipeSlot offSlot = new CountRecipeSlot(offStack.getItem(), offStack.getCount());
        StrengthTableRecipe.Entry entry = registry.tryGetRecipes(mainSlot, offSlot);
        if (entry != null) {
            CountRecipeSlot mainItem = entry.getMainItem();
            CountRecipeSlot offItem = entry.getOffItem();
            CountRecipeSlot resultItem = entry.getOutput();

            this.blockEntity.getInventory().removeItem(mainItem.getItem(), mainItem.getCount());
            this.blockEntity.getInventory().removeItem(offItem.getItem(), offItem.getCount());
            this.player.giveItemStack(resultItem.getStack());
        } else {
            this.output.setItem(Items.AIR);
        }
    }

    @Override
    public void onOpen() {
        super.onOpen();
    }

    @Override
    public void onClose() {
        super.onClose();
        this.blockEntity.markDirty();
    }


}
