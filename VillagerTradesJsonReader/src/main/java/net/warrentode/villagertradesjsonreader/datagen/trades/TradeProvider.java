package net.warrentode.villagertradesjsonreader.datagen.trades;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.warrentode.villagertradesjsonreader.trades.TradeLevel;
import net.warrentode.villagertradesjsonreader.trades.type.ITradeType;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static net.warrentode.villagertradesjsonreader.VillagerTradesJSONReader.MODID;

// AUTHOR: MrCrayfish https://github.com/MrCrayfish/GoblinTraders/tree/1.19.X
public abstract class TradeProvider implements DataProvider {
    private final DataGenerator generator;
    private final DataGenerator.PathProvider pathProvider;
    private final String pathName = MODID + ":" + "trades";
    private final Map<VillagerProfession, EnumMap<TradeLevel, List<ITradeType<?>>>> trades = new HashMap<>();

    protected TradeProvider(DataGenerator generator, DataGenerator.PathProvider pathProvider) {
        this.generator = generator;
        this.pathProvider = generator.createPathProvider(DataGenerator.Target.DATA_PACK, pathName);
    }

    protected abstract void registerTrades();

    protected final void addTrade(VillagerProfession profession, TradeLevel tradeLevel, ITradeType<?> trade) {
        this.trades.putIfAbsent(profession, new EnumMap<>(TradeLevel.class));
        this.trades.get(profession).putIfAbsent(tradeLevel, new ArrayList<>());
        this.trades.get(profession).get(tradeLevel).add(trade);
    }
    @Override
    public void run(@NotNull CachedOutput cache) {
        this.trades.clear();
        this.registerTrades();
        this.trades.forEach((profession, tradeLevelListEnumMap) -> tradeLevelListEnumMap.forEach((tradeLevel, tradeList) -> {
            JsonObject object = new JsonObject();
            object.addProperty("replace", false);
            JsonArray tradeArray = new JsonArray();
            tradeList.forEach(trade -> tradeArray.add(trade.serialize()));
            object.add("trades", tradeArray);
            ResourceLocation id = ResourceLocation.tryParse(profession.name());
            Path path = null;
            if (id != null) {
                path = this.generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/trades/" + id.getPath() + "/" + tradeLevel.getKey() + ".json");
            }
            try {
                if (path != null) {
                    DataProvider.saveStable(cache, object, path);
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    // Gets a name for this provider, to use in logging.
    @Override
    public @NotNull String getName() {
        return "TradeProvider: " + MODID;
    }
}