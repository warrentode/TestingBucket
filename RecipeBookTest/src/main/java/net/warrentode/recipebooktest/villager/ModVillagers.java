package net.warrentode.recipebooktest.villager;

import com.google.common.collect.ImmutableSet;
import java.lang.reflect.InvocationTargetException;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import static net.warrentode.recipebooktest.RecipeBookTest.MODID;
import net.warrentode.recipebooktest.block.ModBlocks;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, MODID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, MODID);

    public static final RegistryObject<PoiType> BANKER_POI = POI_TYPES.register("banker_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.COINPRESSBLOCK.get().getStateDefinition().getPossibleStates()),
                    1, 1));
    public static final RegistryObject<VillagerProfession> BANKER = VILLAGER_PROFESSIONS.register("banker",
            () -> new VillagerProfession("banker", x -> x.get() == BANKER_POI.get(),
                    x -> x.get() == BANKER_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.CHAIN_STEP));

    public static final RegistryObject<PoiType> LEPRECHAUN_POI = POI_TYPES.register("leprechaun_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.POT_OF_GOLD.get().getStateDefinition().getPossibleStates()),
                    1, 1));
    public static final RegistryObject<VillagerProfession> LEPRECHAUN = VILLAGER_PROFESSIONS.register("leprechaun",
            () -> new VillagerProfession("leprechaun", x -> x.get() == LEPRECHAUN_POI.get(),
                    x -> x.get() == LEPRECHAUN_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.CHAIN_PLACE));

    public static void registerPOIs() {
        try {
            ObfuscationReflectionHelper.findMethod(PoiType.class,
                    "registerBlockStates", PoiType.class).invoke(null, BANKER_POI.get());
            ObfuscationReflectionHelper.findMethod(PoiType.class,
                    "registerBlockStates", PoiType.class).invoke(null, LEPRECHAUN_POI.get());
        } catch (InvocationTargetException | IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}