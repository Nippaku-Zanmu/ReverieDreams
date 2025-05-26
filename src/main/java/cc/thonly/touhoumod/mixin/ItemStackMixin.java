package cc.thonly.touhoumod.mixin;

import cc.thonly.touhoumod.interfaces.ItemStackImpl;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.component.ComponentHolder;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemStackImpl,
        ComponentHolder,
        FabricItemStack {

    @Unique
    @Override
    public boolean isFood() {
        ComponentMap components = this.getComponents();
        return components.get(DataComponentTypes.FOOD) != null;
    }
}
