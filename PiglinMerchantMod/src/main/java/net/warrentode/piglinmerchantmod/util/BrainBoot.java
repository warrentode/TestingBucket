package net.warrentode.piglinmerchantmod.util;

import com.mojang.serialization.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.ai.sensing.*;
import net.minecraft.world.entity.schedule.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.javafmlmod.*;
import net.minecraftforge.registries.*;
import net.tslat.smartbrainlib.api.core.sensor.*;
import net.warrentode.piglinmerchantmod.entity.ai.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

import static net.warrentode.piglinmerchantmod.PiglinMerchantMod.*;

@SuppressWarnings("unused")
public class BrainBoot implements BrainLoader {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_TYPES = DeferredRegister.create(ForgeRegistries.Keys.MEMORY_MODULE_TYPES, MODID);
    public static final DeferredRegister<SensorType<?>> SENSORS = DeferredRegister.create(ForgeRegistries.Keys.SENSOR_TYPES, MODID);
    public static final DeferredRegister<Activity> ACTIVITIES = DeferredRegister.create(ForgeRegistries.Keys.ACTIVITIES, MODID);

    public static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MEMORY_TYPES.register(modEventBus);
        SENSORS.register(modEventBus);
        ACTIVITIES.register(modEventBus);

        ModMemoryTypes.init();
        ModSensorTypes.init();
        ModActivity.init();
    }

    @SuppressWarnings("unused")
    public <T> Supplier<MemoryModuleType<T>> registerMemoryType(String id) {
        return registerMemoryType(id, null);
    }

    public static <T> Supplier<MemoryModuleType<T>> registerMemoryType(String id, @Nullable Codec<T> codec) {
        //noinspection Convert2Diamond
        return MEMORY_TYPES.register(id, () -> new MemoryModuleType<T>(Optional.ofNullable(codec)));
    }

    public static <T extends ExtendedSensor<?>> Supplier<SensorType<T>> registerSensorType(String id, Supplier<T> sensor) {
        return SENSORS.register(id, () -> new SensorType<>(sensor));
    }

    static <V> RegistryObject<Activity> registerActivities(ForgeRegistry<V> registry, String id, Activity activity) {
        return ACTIVITIES.register(id, ()-> activity);
    }
}