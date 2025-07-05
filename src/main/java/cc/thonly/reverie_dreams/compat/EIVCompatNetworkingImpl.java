package cc.thonly.reverie_dreams.compat;

import cc.thonly.reverie_dreams.networking.CustomBytePayload;
import eu.pb4.polydex.api.v1.recipe.PolydexEntry;
import eu.pb4.polydex.api.v1.recipe.PolydexPageUtils;
import eu.pb4.polydex.impl.PolydexImpl;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.nio.charset.StandardCharsets;

public class EIVCompatNetworkingImpl {

    public static void bootstrap() {
        CustomBytePayload.Receiver.registerHook("on_click_eiv_stack_input", (player, command, data) -> {
            String stringId = new String(data, StandardCharsets.UTF_8).intern();
            Identifier id = Identifier.tryParse(stringId);
            Item item = Registries.ITEM.get(id);
            if (item == Items.AIR) {
                return;
            }
            ItemStack itemStack = item.getDefaultStack();
            PolydexEntry entry = (PolydexEntry) PolydexImpl.getEntry(itemStack);
            if (entry == null) {
                entry = PolydexImpl.ITEM_ENTRIES.nonEmptyById().get(id);
            }
            if (entry != null) {
                PolydexPageUtils.openUsagesListUi(player, entry, null);
            }
        });
        CustomBytePayload.Receiver.registerHook("on_click_eiv_stack_result", (player, command, data) -> {
            String stringId = new String(data, StandardCharsets.UTF_8).intern();
            Identifier id = Identifier.of(stringId);
            Item item = Registries.ITEM.get(id);
            if (item == Items.AIR) {
                return;
            }
            ItemStack itemStack = item.getDefaultStack();
            PolydexEntry entry = (PolydexEntry) PolydexImpl.getEntry(itemStack);
            if (entry == null) {
                entry = PolydexImpl.ITEM_ENTRIES.nonEmptyById().get(id);
            }
            if (entry != null) {
                PolydexPageUtils.openRecipeListUi(player, entry, null);
            }
        });

    }
}
