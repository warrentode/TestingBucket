package net.warrentode.villagertradesjsonreader.trades;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static net.warrentode.villagertradesjsonreader.VillagerTradesJSONReader.MODID;

// AUTHOR: MrCrayfish https://github.com/MrCrayfish/GoblinTraders/tree/1.19.X
@Mod.EventBusSubscriber(modid = MODID)
public class TradeManager implements PreparableReloadListener {
    private static final int FILE_TYPE_LENGTH_VALUE = ".json".length();
    private static final Gson GSON = new GsonBuilder().create();
    private static TradeManager instance;

    public static TradeManager instance() {
        if (instance == null) {
            instance = new TradeManager();
        }
        return instance;
    }
    public static List<VillagerProfession> professions = new ArrayList<>();
    private List<EntityType<?>> traderTypes = new ArrayList<>();
    public static Map<VillagerProfession, EntityTrades> professionTradesMap = new HashMap<>();
    private Map<VillagerProfession, EntityTrades> tradeMap = new HashMap<>();
    private Map<ResourceLocation, TradeSerializer<?>> tradeSerializer = new HashMap<>();

    @SubscribeEvent
    public static void addReloadListener(@NotNull AddReloadListenerEvent event) {
        event.addListener(instance());
    }

    public void registerTrader(VillagerProfession profession) {
        if (!professions.contains(profession)) {
            professions.add(profession);
        }
    }

    @Nullable
    public EntityTrades getTrades(@NotNull VillagerProfession profession) {
        return this.tradeMap.get(profession);
    }

    public void registerTradeSerializer(TradeSerializer<?> serializer) {
        this.tradeSerializer.putIfAbsent(serializer.getId(), serializer);
    }

    @Nullable
    public TradeSerializer<?> getTradeSerializer(ResourceLocation id) {
        return this.tradeSerializer.get(id);
    }

    @Override
    public @NotNull CompletableFuture<Void> reload(@NotNull PreparationBarrier stage, @NotNull ResourceManager manager, @NotNull ProfilerFiller preparationsProfiler, @NotNull ProfilerFiller reloadProfiler, @NotNull Executor backgroundExecutor, @NotNull Executor gameExecutor) {
        return CompletableFuture.allOf(CompletableFuture.runAsync(() -> professions.forEach(profession -> {
            String folder = String.format("trades/%s", profession.name());
            List<ResourceLocation> resources = new ArrayList<>(manager.listResources(folder, (fileName) -> fileName.getPath().endsWith(".json")).keySet());
            resources.sort((r1, r2) -> {
                if (r1.getNamespace().equals(r2.getNamespace())) {return 0;}
                return r2.getNamespace().equals(MODID) ? 1 : -1;
            });

            Map<TradeLevel, LinkedHashSet<ResourceLocation>> tradeResources = new EnumMap<>(TradeLevel.class);
            Arrays.stream(TradeLevel.values()).forEach(tradeLevel -> tradeResources.put(tradeLevel, new LinkedHashSet<>()));
            resources.forEach(resource -> {
                String path = resource.getPath().substring(0, resource.getPath().length() - FILE_TYPE_LENGTH_VALUE);
                String[] splitPath = path.split("/");
                if (splitPath.length != 3) {return;}
                Arrays.stream(TradeLevel.values()).forEach(tradeLevel -> {
                    if (tradeLevel.getKey().equals(splitPath[2])) {
                        tradeResources.get(tradeLevel).add(resource);
                    }
                });
            });

            EntityTrades.Builder builder = EntityTrades.Builder.create();
            Arrays.stream(TradeLevel.values()).forEach(tradeLevel -> this.deserializeTrades(manager, builder, tradeLevel, tradeResources.get(tradeLevel)));
            professionTradesMap.put(profession, builder.build());
            this.tradeMap = ImmutableMap.copyOf(professionTradesMap);
        }), backgroundExecutor)).thenCompose(stage::wait);
    }

    private void deserializeTrades(ResourceManager manager, EntityTrades.Builder builder, TradeLevel tradeLevel, Iterable<ResourceLocation> resources) {
        for (ResourceLocation resourceLocation : resources) {
            manager.getResource(resourceLocation).ifPresent(resource -> {
                try (Reader reader = new BufferedReader(new InputStreamReader(resource.open(), StandardCharsets.UTF_8))) {
                    JsonObject object = GsonHelper.fromJson(GSON, reader, JsonObject.class);
                    builder.deserialize(tradeLevel, object);
                }
                catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
        }
    }
}