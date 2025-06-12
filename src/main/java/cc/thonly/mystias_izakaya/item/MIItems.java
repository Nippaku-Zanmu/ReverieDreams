package cc.thonly.mystias_izakaya.item;

import cc.thonly.mystias_izakaya.item.base.IngredientItem;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class MIItems {
    public static final List<Item> INGREDIENTS = new ArrayList<>();

    public static final Item BAMBOO_SHOOTER = registerIngredient(new IngredientItem("ingredient/bamboo_shooter", 0, 0f, new Item.Settings()));
    public static final Item BLACK_PORK = registerIngredient(new IngredientItem("ingredient/black_pork", 0, 0f, new Item.Settings()));
    public static final Item BRAISED_MEAT = registerIngredient(new IngredientItem("ingredient/braised_meat", 0, 0f, new Item.Settings()));
    public static final Item BUTTER = registerIngredient(new IngredientItem("ingredient/butter", 0, 0f, new Item.Settings()));
    public static final Item CAPSAICIN = registerIngredient(new IngredientItem("ingredient/capsaicin", 0, 0f, new Item.Settings()));
    public static final Item CHEESE = registerIngredient(new IngredientItem("ingredient/cheese", 0, 0f, new Item.Settings()));
    public static final Item CHESTNUT = registerIngredient(new IngredientItem("ingredient/chestnut", 0, 0f, new Item.Settings()));
    public static final Item CHILI = registerIngredient(new IngredientItem("ingredient/chili", 0, 0f, new Item.Settings()));
    public static final Item CRAB = registerIngredient(new IngredientItem("ingredient/crab", 0, 0f, new Item.Settings()));
    public static final Item CREAM = registerIngredient(new IngredientItem("ingredient/cream", 0, 0f, new Item.Settings()));
    public static final Item CUCUMBER = registerIngredient(new IngredientItem("ingredient/cucumber", 0, 0f, new Item.Settings()));
    public static final Item DEW = registerIngredient(new IngredientItem("ingredient/dew", 0, 0f, new Item.Settings()));
    public static final Item FLOUR = registerIngredient(new IngredientItem("ingredient/flour", 0, 0f, new Item.Settings()));
    public static final Item GINKGO = registerIngredient(new IngredientItem("ingredient/ginkgo", 0, 0f, new Item.Settings()));
    public static final Item GRAPE = registerIngredient(new IngredientItem("ingredient/grape", 0, 0f, new Item.Settings()));
    public static final Item HAGFISH = registerIngredient(new IngredientItem("ingredient/hagfish", 0, 0f, new Item.Settings()));
    public static final Item LEMON = registerIngredient(new IngredientItem("ingredient/lemon", 0, 0f, new Item.Settings()));
    public static final Item LOTUS_NUTS = registerIngredient(new IngredientItem("ingredient/lotus_nuts", 0, 0f, new Item.Settings()));
    public static final Item MOONFLOWER = registerIngredient(new IngredientItem("ingredient/moonflower", 0, 0f, new Item.Settings()));
    public static final Item OCTOPUS = registerIngredient(new IngredientItem("ingredient/octopus", 0, 0f, new Item.Settings()));
    public static final Item ONION = registerIngredient(new IngredientItem("ingredient/onion", 0, 0f, new Item.Settings()));
    public static final Item PEACH = registerIngredient(new IngredientItem("ingredient/peach", 0, 0f, new Item.Settings()));
    public static final Item PINE_NUT = registerIngredient(new IngredientItem("ingredient/pine_nut", 0, 0f, new Item.Settings()));
    public static final Item PUFF_YO_FRUIT = registerIngredient(new IngredientItem("ingredient/puff_yo_fruit", 0, 0f, new Item.Settings()));
    public static final Item RED_BEANS = registerIngredient(new IngredientItem("ingredient/red_beans", 0, 0f, new Item.Settings()));
    public static final Item SALMON = registerIngredient(new IngredientItem("ingredient/salmon", 0, 0f, new Item.Settings()));
    public static final Item SEA_URCHIN = registerIngredient(new IngredientItem("ingredient/sea_urchin", 0, 0f, new Item.Settings()));
    public static final Item SHRIMP = registerIngredient(new IngredientItem("ingredient/shrimp", 0, 0f, new Item.Settings()));
    public static final Item STICKY_RICE = registerIngredient(new IngredientItem("ingredient/sticky_rice", 0, 0f, new Item.Settings()));
    public static final Item SUPREME_TUNA = registerIngredient(new IngredientItem("ingredient/supreme_tuna", 0, 0f, new Item.Settings()));
    public static final Item SWEET_POTATO = registerIngredient(new IngredientItem("ingredient/sweet_potato", 0, 0f, new Item.Settings()));
    public static final Item TOFU = registerIngredient(new IngredientItem("ingredient/tofu", 0, 0f, new Item.Settings()));
    public static final Item TOMATO = registerIngredient(new IngredientItem("ingredient/tomato", 0, 0f, new Item.Settings()));
    public static final Item TOON = registerIngredient(new IngredientItem("ingredient/toon", 0, 0f, new Item.Settings()));
    public static final Item TREMELLA = registerIngredient(new IngredientItem("ingredient/tremella", 0, 0f, new Item.Settings()));
    public static final Item TROUT = registerIngredient(new IngredientItem("ingredient/trout", 0, 0f, new Item.Settings()));
    public static final Item TRUFFLE = registerIngredient(new IngredientItem("ingredient/truffle", 0, 0f, new Item.Settings()));
    public static final Item TUNA = registerIngredient(new IngredientItem("ingredient/tuna", 0, 0f, new Item.Settings()));
    public static final Item TWIN_LOTUS = registerIngredient(new IngredientItem("ingredient/twin_lotus", 0, 0f, new Item.Settings()));
    public static final Item WAGYU_BEEF = registerIngredient(new IngredientItem("ingredient/wagyu_beef", 0, 0f, new Item.Settings()));
    public static final Item WHITE_RADISH = registerIngredient(new IngredientItem("ingredient/white_radish", 0, 0f, new Item.Settings()));
    public static final Item WILD_BOAR_MEAT = registerIngredient(new IngredientItem("ingredient/wild_boar_meat", 0, 0f, new Item.Settings()));

    public static void registerItems() {

    }

    public static Item registerIngredient(IdentifierGetter item) {
        Item tItem = registerItem(item);
        INGREDIENTS.add(tItem);
        return tItem;
    }

    public static Item registerItem(IdentifierGetter item) {
        Registry.register(Registries.ITEM, item.getIdentifier(), (Item) item);
        return (Item) item;
    }
}
