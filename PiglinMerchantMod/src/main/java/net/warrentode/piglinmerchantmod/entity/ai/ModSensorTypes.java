package net.warrentode.piglinmerchantmod.entity.ai;

import net.minecraft.world.entity.ai.sensing.*;
import net.tslat.smartbrainlib.api.core.sensor.*;
import net.warrentode.piglinmerchantmod.entity.ai.sensors.custom.*;
import net.warrentode.piglinmerchantmod.entity.ai.sensors.vanilla.*;
import net.warrentode.piglinmerchantmod.util.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

public class ModSensorTypes {
    public static void init() {}

    public static final Supplier<SensorType<NearestWantedItemSensor<?>>> NEAREST_WANTED_ITEM_SENSOR
            = register("nearest_wanted_item_sensor", NearestWantedItemSensor::new);
    public static final Supplier<SensorType<NearbyHuntingTargetSensor<?>>> NEARBY_HUNTING_TARGET_SENSOR
            = register("nearby_hunting_target_sensor", NearbyHuntingTargetSensor::new);
    public static final Supplier<SensorType<HoglinHuntSensor<?>>> HOGLIN_HUNT_SENSOR
            = register("hoglin_hunt_sensor", HoglinHuntSensor::new);
    public static final Supplier<SensorType<PiglinMerchantSpecificSensor<?>>> PIGLINMERCHANT_SPECIFIC_SENSOR
            = register("piglinmerchant_specific_sensor", PiglinMerchantSpecificSensor::new);

    private static <T extends ExtendedSensor<?>> @NotNull Supplier<SensorType<T>> register(String id, Supplier<T> sensor) {
        return BrainLoader.registerSensorType(id, sensor);
    }
}