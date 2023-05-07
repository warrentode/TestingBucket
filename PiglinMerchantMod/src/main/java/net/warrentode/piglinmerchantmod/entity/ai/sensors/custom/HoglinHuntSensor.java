package net.warrentode.piglinmerchantmod.entity.ai.sensors.custom;

import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.ai.sensing.*;
import net.minecraft.world.entity.monster.hoglin.*;
import net.tslat.smartbrainlib.api.core.sensor.*;
import net.tslat.smartbrainlib.util.*;
import net.warrentode.piglinmerchantmod.entity.ai.*;
import net.warrentode.piglinmerchantmod.entity.custom.*;
import org.jetbrains.annotations.*;

import java.util.*;

import static net.warrentode.piglinmerchantmod.entity.ai.behaviors.hunting.RememberDeadHuntingTarget.dontKillAnyMoreHoglinsForAWhile;
import static net.warrentode.piglinmerchantmod.entity.ai.sensors.custom.PiglinMerchantSpecificSensor.getVisibleAdultAllies;

public class HoglinHuntSensor<E extends LivingEntity> extends ExtendedSensor<E> {
    private static final List<MemoryModuleType<?>> MEMORIES =
            ObjectArrayList.of(
                    MemoryModuleType.NEAREST_LIVING_ENTITIES,
                    MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
                    MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN,
                    MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT,
                    MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN
            );
    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES;
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return ModSensorTypes.HOGLIN_HUNT_SENSOR.get();
    }

    @Override
    protected void doTick(ServerLevel level, E entity) {
        Brain<?> brain = entity.getBrain();

        BrainUtils.withMemory(brain, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, entities -> {
            List<LivingEntity> visibleAdultAllies = new ObjectArrayList<>();
            Hoglin nearestHuntableHoglin = null;
            Hoglin nearestBabyHoglin = null;
            int adultHoglinCount = 0;

            for (LivingEntity target : entities.findAll(obj -> true)) {
                if (target instanceof Hoglin hoglin) {
                    if (hoglin.isBaby() && nearestBabyHoglin == null) {
                        nearestBabyHoglin = hoglin;
                    }
                    else if (hoglin.isAdult()) {
                        adultHoglinCount++;

                        if (adultHoglinCount > 2) {
                            if (nearestHuntableHoglin == null && hoglin.canBeHunted()) {
                                nearestHuntableHoglin = hoglin;
                            }
                        }
                    }
                }
            }

            BrainUtils.setMemory(brain, MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, nearestHuntableHoglin);
            BrainUtils.setMemory(brain, MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, nearestBabyHoglin);
            BrainUtils.setMemory(brain, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, adultHoglinCount);
        });
    }

    public static boolean hoglinsOutnumberPiglins(@NotNull PiglinMerchantEntity piglinMerchant) {
        int i = piglinMerchant.getBrain().getMemory(ModMemoryTypes.VISIBLE_ADULT_ALLIES_COUNT.get()).orElse(0) + 1;
        int j = piglinMerchant.getBrain().getMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT).orElse(0);
        return j > i;
    }
    public static void setAvoidTargetAndDontHuntForAWhile(@NotNull PiglinMerchantEntity piglinMerchant, LivingEntity target) {
        BrainUtils.clearMemory(piglinMerchant, MemoryModuleType.ANGRY_AT);
        BrainUtils.clearMemory(piglinMerchant, MemoryModuleType.ATTACK_TARGET);
        BrainUtils.clearMemory(piglinMerchant, MemoryModuleType.WALK_TARGET);
        BrainUtils.setForgettableMemory(piglinMerchant,
                MemoryModuleType.AVOID_TARGET,target, PiglinMerchantEntity.RETREAT_DURATION.sample(piglinMerchant.level.random));
        dontKillAnyMoreHoglinsForAWhile(piglinMerchant);
    }
    public static void broadcastRetreat(PiglinMerchantEntity todePiglinMerchant, LivingEntity retreatTarget) {
        getVisibleAdultAllies(todePiglinMerchant).stream().filter((todePiglinMerchant1) ->
                todePiglinMerchant != null).forEach((nearestTarget) ->
                retreatFromNearestTarget((PiglinMerchantEntity) nearestTarget, retreatTarget));
    }
    private static void retreatFromNearestTarget(@NotNull PiglinMerchantEntity piglinMerchant, LivingEntity retreatTarget) {
        BrainUtils.getMemory(piglinMerchant, MemoryModuleType.ATTACK_TARGET);
        LivingEntity nearestTarget;
        nearestTarget = retreatTarget;
        BrainUtils.setMemory(piglinMerchant, MemoryModuleType.AVOID_TARGET, retreatTarget);
        if (retreatTarget instanceof Hoglin) {
            setAvoidTargetAndDontHuntForAWhile(piglinMerchant, nearestTarget);
        }
    }
}
