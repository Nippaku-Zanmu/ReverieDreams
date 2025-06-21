package cc.thonly.reverie_dreams.util;

import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Slf4j
public class ItemStackCheckUtils {
    public static void test() {
        Stream<Item> stream = Registries.ITEM.stream();
        List<Item> items = stream.toList();
        try {
            for (Item item : items) {
                if (item== Items.AIR) continue;
                ItemStack stack = item.getDefaultStack();
                DataResult<JsonElement> element = ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, stack);
                JsonElement jsonElement = element.getOrThrow();
                Optional<JsonElement> result = element.result();
            }
        } catch (Exception e) {
            log.error("Error: ",e);
        }
    }
}
