package net.warrentode.piglinmerchantmod.entity.ai.sensors.vanilla;

import com.google.common.collect.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.ai.sensing.*;
import net.minecraft.world.entity.item.*;
import net.tslat.smartbrainlib.api.core.sensor.*;
import net.warrentode.piglinmerchantmod.entity.ai.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class NearestWantedItemSensor<E extends Mob> extends ExtendedSensor<E> {
   private static final ImmutableList<MemoryModuleType<?>> MEMORIES = ImmutableList.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
   private static final double XZ_RANGE = 32.0D;
   private static final double Y_RANGE = 16.0D;
   public static final double MAX_DISTANCE_TO_WANTED_ITEM = 32.0D;

   @Override
   public ImmutableList<MemoryModuleType<?>> memoriesUsed() {
      return MEMORIES;
   }

   @Override
   public SensorType<? extends ExtendedSensor<?>> type() {
      return ModSensorTypes.NEAREST_WANTED_ITEM_SENSOR.get();
   }

   protected void doTick(@NotNull ServerLevel serverLevel, @NotNull Mob mob) {
      Brain<?> brain = mob.getBrain();
      List<ItemEntity> list = serverLevel.getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(XZ_RANGE, Y_RANGE, XZ_RANGE), (itemEntity) -> true);
      list.sort(Comparator.comparingDouble(mob::distanceToSqr));
      Optional<ItemEntity> optional = list.stream().filter((itemEntity) ->
              mob.wantsToPickUp(itemEntity.getItem())).filter((itemEntity1) ->
              itemEntity1.closerThan(mob, MAX_DISTANCE_TO_WANTED_ITEM)).filter(mob::hasLineOfSight).findFirst();
      brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, optional);
   }
}