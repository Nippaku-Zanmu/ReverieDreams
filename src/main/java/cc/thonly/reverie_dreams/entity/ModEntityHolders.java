package cc.thonly.reverie_dreams.entity;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.component.ModDataComponentTypes;
import cc.thonly.reverie_dreams.item.BasicItem;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class ModEntityHolders {
    public static final List<Item> HOLDERS = new ArrayList<>();
    public static final Item YOUSEI_WINGS = register(new BasicItem("holder/yousei_wing_holder", new Item.Settings().translationKey("Entity Holder")));
    public static final Item KNIFE_DISPLAY = register(new BasicItem("holder/knife_display", new Item.Settings().maxCount(1).component(ModDataComponentTypes.Danmaku.TEMPLATE, Touhou.id("single").toString()).component(ModDataComponentTypes.Danmaku.DAMAGE, 2.0f).component(ModDataComponentTypes.Danmaku.SPEED, 0.5f).component(ModDataComponentTypes.Danmaku.SCALE, 0.8f).component(ModDataComponentTypes.Danmaku.COUNT, 1).component(ModDataComponentTypes.Danmaku.TILE, false).component(ModDataComponentTypes.Danmaku.INFINITE, false).translationKey("Entity Holder")));
    public static final Item MAGIC_BROOM_DISPLAY = register(new BasicItem("holder/magic_broom_display", new Item.Settings().maxCount(1).translationKey("Entity Holder")));

    public static void registerHolders() {
    }

    public static Item register(IdentifierGetter item) {
        HOLDERS.add(registerItem(item));
        return (Item) item;
    }

    public static Item registerItem(IdentifierGetter item) {
        return (Item) Registry.register(Registries.ITEM, item.getIdentifier(), (Item) item);
    }

}
