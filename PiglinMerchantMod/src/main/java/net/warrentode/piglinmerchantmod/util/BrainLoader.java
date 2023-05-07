package net.warrentode.piglinmerchantmod.util;

import com.mojang.serialization.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.ai.sensing.*;
import net.minecraft.world.entity.schedule.*;
import net.minecraftforge.registries.ForgeRegistries.*;
import net.minecraftforge.registries.*;
import net.tslat.smartbrainlib.api.core.sensor.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

public interface BrainLoader {

    static <T> Supplier<MemoryModuleType<T>> registerMemoryType(String id) {
        return BrainBoot.registerMemoryType(id, null);
    }
    static <T> Supplier<MemoryModuleType<T>> registerMemoryType(String id, @Nullable Codec<T> codec) {
        return BrainBoot.registerMemoryType(id, codec);
    }

    static <T extends ExtendedSensor<?>> Supplier<SensorType<T>> registerSensorType(String id, Supplier<T> sensor) {
        return BrainBoot.registerSensorType(id, sensor);
    }

    static <V> RegistryObject<Activity> registerActivities(ForgeRegistry<V> registry, String id, Activity activity) {
        return BrainBoot.registerActivities(RegistryManager.ACTIVE.getRegistry(Keys.ACTIVITIES), id, new Activity(id));
    }
}