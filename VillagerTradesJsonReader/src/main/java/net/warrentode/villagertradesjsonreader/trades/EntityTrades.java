package net.warrentode.villagertradesjsonreader.trades;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.*;

// AUTHOR: MrCrayfish https://github.com/MrCrayfish/GoblinTraders/tree/1.19.X
public record EntityTrades(Map<TradeLevel, List<VillagerTrades.ItemListing>> tradeMap) {
    public EntityTrades(Map<TradeLevel, List<VillagerTrades.ItemListing>> tradeMap) {
        this.tradeMap = ImmutableMap.copyOf(tradeMap);
    }

    public static class Builder {
        private final Map<TradeLevel, List<VillagerTrades.ItemListing>> tradeMap = Util.make(() -> {
            Map<TradeLevel, List<VillagerTrades.ItemListing>> map = new EnumMap<>(TradeLevel.class);
            Arrays.stream(TradeLevel.values()).forEach(tradeLevel -> map.put(tradeLevel, new ArrayList<>()));
            return map;
        });

        private Builder() {}

        public EntityTrades build() {
            return new EntityTrades(this.tradeMap);
        }

        void deserialize(TradeLevel tradeLevel, JsonObject object) {
            List<VillagerTrades.ItemListing> trades = this.tradeMap.get(tradeLevel);

            if (GsonHelper.getAsBoolean(object, "replace", false)) {
                trades.clear();
            }

            JsonArray tradeArray = GsonHelper.getAsJsonArray(object, "trades");
            for (JsonElement tradeElement : tradeArray) {
                JsonObject tradeObject = tradeElement.getAsJsonObject();
                String rawType = GsonHelper.getAsString(tradeObject, "type");
                ResourceLocation typeKey = ResourceLocation.tryParse(rawType);
                if (typeKey == null) {
                    throw new JsonParseException("Resource Location for Type not found");
                }
                TradeSerializer<?> serializer = TradeManager.instance().getTradeSerializer(typeKey);
                if (serializer == null) {
                    throw new JsonParseException(String.format("Invalid trade type: %s", typeKey));
                }
                trades.add(serializer.deserialize(tradeObject).createVillagerTrade());
            }
        }

        static Builder create() {
            return new Builder();
        }
    }
}