package cc.thonly.reverie_dreams.compat;

import cc.thonly.reverie_dreams.networking.CustomBytePayload;
import eu.pb4.polydex.api.v1.recipe.PolydexEntry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import eu.pb4.polydex.api.v1.recipe.PolydexPageUtils;

import java.nio.charset.StandardCharsets;

public class EIVCompatNetworkingImpl {

    public static void bootstrap() {
        CustomBytePayload.Receiver.registerHook("on_click_eiv_stack", (player, command, data) -> {
            String stringId = new String(data, StandardCharsets.UTF_8);
            Item item = Registries.ITEM.get(Identifier.of(stringId));
            if (item == Items.AIR) {
                return;
            }
            ItemStack itemStack = item.getDefaultStack();
            PolydexPageUtils.openRecipeListUi(player, itemStack, () -> {
            });
        });

    }
}
