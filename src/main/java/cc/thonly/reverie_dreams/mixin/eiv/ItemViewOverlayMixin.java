package cc.thonly.reverie_dreams.mixin.eiv;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.networking.CustomBytePayloadClient;
import de.crafty.eiv.common.overlay.ItemViewOverlay;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.charset.StandardCharsets;

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

    @Inject(method = "openRecipeView",
            at = @At(
                    value = "INVOKE",
                    target = "Lde/crafty/eiv/common/overlay/ItemViewOverlay$ItemViewOpenType$RecipeProvider;retrieveRecipes(Lnet/minecraft/item/ItemStack;)Ljava/util/List;"
            ),
            cancellable = true
    )
    public void beforeFound(ItemStack stack, ItemViewOverlay.ItemViewOpenType openType, CallbackInfo ci) {
        if (FabricLoader.getInstance().isModLoaded("polydex") && PolymerItemUtils.isPolymerServerItem(stack)) {
            Item item = stack.getItem();
            Identifier id = Registries.ITEM.getId(item);
            String stringId = id.toString();
            CustomBytePayloadClient.Sender.sendLargePayload("on_click_eiv_stack", stringId.getBytes(StandardCharsets.UTF_8));
            ci.cancel();
        }
    }

    @Inject(
            method = "openRecipeView",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V"
            ),
            cancellable = true
    )
    private void beforeSetScreen(ItemStack stack, ItemViewOverlay.ItemViewOpenType openType, CallbackInfo ci) {
        if (FabricLoader.getInstance().isModLoaded("polydex") && PolymerItemUtils.isPolymerServerItem(stack)) {
            Item item = stack.getItem();
            Identifier id = Registries.ITEM.getId(item);
            String stringId = id.toString();
            CustomBytePayloadClient.Sender.sendLargePayload("on_click_eiv_stack", stringId.getBytes(StandardCharsets.UTF_8));
            ci.cancel();
        }
    }

}
