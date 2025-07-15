package cc.thonly.mystias_izakaya.villager;

import cc.thonly.mystias_izakaya.item.MIItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

public class MIVillagerTradeModifier {
    public static void bootstrap() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.BUTCHER, 2, factories -> {
            // Lv.3
            factories.add((e, r) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 6),
                    new ItemStack(MIItems.VENISON, 5),
                    4, 10, 0.05f
            ));

        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.BUTCHER, 3, factories -> {
            // Lv.4
            factories.add((e, r) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 10),
                    new ItemStack(MIItems.WAGYU_BEEF, 6),
                    4, 10, 0.05f
            ));
            factories.add((e, r) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 8),
                    new ItemStack(MIItems.WILD_BOAR_MEAT, 5),
                    4, 10, 0.05f
            ));

        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 2, factories -> {
            // Lv.2：常见农作物，便宜交易
            factories.add((e, r) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 2),
                    new ItemStack(MIItems.STICKY_RICE, 4),
                    6, 3, 0.04f
            ));
            factories.add((e, r) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 3),
                    new ItemStack(MIItems.PLUM, 4),
                    6, 3, 0.04f
            ));
            factories.add((e, r) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 7),
                    new ItemStack(MIItems.PINE_NUT, 3),
                    6, 3, 0.04f
            ));
        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 3, factories -> {
            // Lv.3：中等水果
            factories.add((e, r) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 4),
                    new ItemStack(MIItems.CHESTNUT, 5),
                    5, 5, 0.05f
            ));
            factories.add((e, r) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 5),
                    new ItemStack(MIItems.PLUM, 6),
                    5, 5, 0.05f
            ));
        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 4, factories -> {
            // Lv.4：稀有水果
            factories.add((e, r) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 11),
                    new ItemStack(MIItems.PUFF_YO_FRUIT, 4),
                    3, 10, 0.05f
            ));
            factories.add((e, r) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 15),
                    new ItemStack(MIItems.FICUS_MICROCARPA, 3),
                    2, 15, 0.05f
            ));
        });
    }
}
