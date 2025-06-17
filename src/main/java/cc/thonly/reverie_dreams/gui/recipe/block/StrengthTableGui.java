package cc.thonly.reverie_dreams.gui.recipe.block;

import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.block.entity.StrengthenTableBlockEntity;
import cc.thonly.reverie_dreams.gui.GuiCommon;
import cc.thonly.reverie_dreams.recipe.RecipeManager;
import cc.thonly.reverie_dreams.recipe.entry.StrengthTableRecipe;
import cc.thonly.reverie_dreams.recipe.slot.ItemStackRecipeWrapper;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.AnvilInputGui;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

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
        ItemStack mainStack = this.blockEntity.getInventory().getStack(0);
        ItemStack offStack = this.blockEntity.getInventory().getStack(1);
        ItemStackRecipeWrapper mainSlot = new ItemStackRecipeWrapper(mainStack);
        ItemStackRecipeWrapper offSlot = new ItemStackRecipeWrapper(offStack);
        List<StrengthTableRecipe> entries = RecipeManager.STRENGTH_TABLE.getMatches(List.of(mainSlot, offSlot));
        if (entries.isEmpty()) {
            return;
        }
        StrengthTableRecipe entry = entries.getFirst();
        if (entry != null) {
            ItemStackRecipeWrapper resultItem = entry.getOutput();
            this.output.setItem(resultItem.getItem());
        } else {
            this.output.setItem(Items.AIR);
        }
    }

    public void click() {
        if (this.blockEntity == null) return;
        ItemStack mainStack = this.blockEntity.getInventory().getStack(0);
        ItemStack offStack = this.blockEntity.getInventory().getStack(1);
        ItemStackRecipeWrapper mainSlot = new ItemStackRecipeWrapper(mainStack);
        ItemStackRecipeWrapper offSlot = new ItemStackRecipeWrapper(offStack);
        List<StrengthTableRecipe> entries = RecipeManager.STRENGTH_TABLE.getMatches(List.of(mainSlot, offSlot));
        if (entries.isEmpty()) {
            return;
        }
        StrengthTableRecipe entry = entries.getFirst();
        if (entry != null) {
            ItemStackRecipeWrapper mainItem = entry.getMainItem();
            ItemStackRecipeWrapper offItem = entry.getOffItem();
            ItemStackRecipeWrapper resultItem = entry.getOutput();

            this.blockEntity.getInventory().removeItem(mainItem.getItem(), mainItem.getCount());
            this.blockEntity.getInventory().removeItem(offItem.getItem(), offItem.getCount());
            this.player.giveItemStack(resultItem.getItemStack());
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
