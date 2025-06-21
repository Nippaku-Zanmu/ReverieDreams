package cc.thonly.reverie_dreams.mixin.eiv;

import cc.thonly.reverie_dreams.Touhou;
import de.crafty.eiv.common.overlay.ItemViewOverlay;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = ItemViewOverlay.class, remap = false, priority = 1)
@Pseudo
public class ItemViewOverlayMixin {
    @ModifyVariable(
            method = "openRecipeView",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0
    )
    private ItemStack modifyStack(ItemStack stack) {
        MinecraftServer server = Touhou.getServer();
        if (server != null) {
            DynamicRegistryManager.Immutable registryManager = server.getRegistryManager();
            return PolymerItemUtils.getRealItemStack(stack, registryManager);
        }
        return stack;
    }
}
