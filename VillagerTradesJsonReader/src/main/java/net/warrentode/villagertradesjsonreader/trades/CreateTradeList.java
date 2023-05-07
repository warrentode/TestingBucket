package net.warrentode.villagertradesjsonreader.trades;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CreateTradeList {
    @Nullable
    static MerchantOffers offers;
    static Villager trader;
    AbstractVillager abstractVillager;

    static RandomSource random = RandomSource.create();
    public static RandomSource getRandom() {
        return random;
    }

    public static @NotNull MerchantOffers getOffers() {
        if (offers == null) {
            offers = new ModMerchantOffers();
            populateTradeData();
        }
        return offers;
    }
    // AUTHOR: MrCrayfish https://github.com/MrCrayfish/GoblinTraders/tree/1.19.X
    public static void populateTradeData() {
        MerchantOffers offers = getOffers();
        VillagerData data = trader.getVillagerData();
        VillagerProfession profession = data.getProfession();
        EntityTrades entityTrades = TradeManager.instance().getTrades(profession);
        if (entityTrades != null) {
            Map<TradeLevel, List<VillagerTrades.ItemListing>> tradeMap = entityTrades.tradeMap();
            for (TradeLevel tradeLevel : TradeLevel.values()) {
                List<VillagerTrades.ItemListing> trades = tradeMap.get(tradeLevel);
                int min = 2;
                int count = min + trader.getRandom().nextInt(1);
                addTrades(offers, trades, count, tradeLevel.shouldShuffle());
            }
        }
    }

    // AUTHOR: MrCrayfish https://github.com/MrCrayfish/GoblinTraders/tree/1.19.X
    public static void addTrades(MerchantOffers offers, @Nullable List<VillagerTrades.ItemListing> trades, int max, boolean shuffle) {
        VillagerData data = trader.getVillagerData();
        VillagerProfession profession = data.getProfession();
        if (trades == null) {
            return;
        }

        List<Integer> randomIndexes = IntStream.range(0, trades.size()).boxed().collect(Collectors.toList());
        if (shuffle) {
            Collections.shuffle(randomIndexes);
        }

        randomIndexes = randomIndexes.subList(0, Math.min(trades.size(), max));
        for (Integer index : randomIndexes) {
            VillagerTrades.ItemListing trade = trades.get(index);
            MerchantOffer offer = trade.getOffer(trader, getRandom());
            if (offer != null) {
                offers.add(offer);
            }
        }
    }
}