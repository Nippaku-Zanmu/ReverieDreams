package cc.thonly.touhoumod.mixin;

import cc.thonly.touhoumod.gui.access.GuiElementBuilderAccessor;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.elements.GuiElementBuilderInterface;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;

@Pseudo
@Mixin(value = GuiElementBuilder.class, remap = false)
public abstract class GuiElementBuilderMixin implements GuiElementBuilderInterface<GuiElementBuilder>, GuiElementBuilderAccessor {
    @Shadow protected ItemStack itemStack;

    @Override
    public ItemStack setItemStack(ItemStack stack) {
        this.itemStack = stack;
        return stack;
    }
    @Override
    public ItemStack getItemStack() {
        return this.itemStack;
    }
}
