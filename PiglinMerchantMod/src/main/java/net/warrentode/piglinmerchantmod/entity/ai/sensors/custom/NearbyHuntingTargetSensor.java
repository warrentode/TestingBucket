package net.warrentode.piglinmerchantmod.entity.ai.sensors.custom;

import com.mojang.datafixers.util.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.ai.sensing.*;
import net.tslat.smartbrainlib.api.core.sensor.*;
import net.warrentode.piglinmerchantmod.entity.ai.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

public class NearbyHuntingTargetSensor<E extends LivingEntity> extends EntityFilteringSensor<LivingEntity, E> {
    private final Map<EntityType<?>, Float> huntingtargetMap = new Object2FloatOpenHashMap<>(2);

    public NearbyHuntingTargetSensor() {
        //noinspection unchecked
        setHuntingTargets(
                Pair.of(EntityType.HOGLIN, 12f)
        );
    }

    /**
     * Clear the hostile types map, and add all the given entries.
     *
     * @param entries The collection of entity types and distances to set the
     *                hostile types map to
     * @return this
     */
    @SuppressWarnings({"unchecked", "UnusedReturnValue"})
    public NearbyHuntingTargetSensor<E> setHuntingTargets(Pair<EntityType<?>, Float> @NotNull ... entries) {
        this.huntingtargetMap.clear();

        for (Pair<EntityType<?>, Float> entry : entries) {
            this.huntingtargetMap.put(entry.getFirst(), entry.getSecond());
        }

        return this;
    }

    /**
     * Add an entity type to the hostile types map.
     *
     * @param entry The entity type and distance to which it should be considered.
     * @return this
     */
    public NearbyHuntingTargetSensor<E> addHuntingTarget(@NotNull Pair<EntityType<?>, Float> entry) {
        this.huntingtargetMap.put(entry.getFirst(), entry.getSecond());

        return this;
    }

    @Override
    public MemoryModuleType<LivingEntity> getMemory() {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return ModSensorTypes.HOGLIN_HUNT_SENSOR.get();
    }

    @Override
    protected BiPredicate<LivingEntity, E> predicate() {
        return (target, entity) -> {
            Float distance = this.huntingtargetMap.get(target.getType());

            return distance != null && target.distanceToSqr(entity) <= distance * distance;
        };
    }

    @Nullable
    @Override
    protected LivingEntity findMatches(E entity, NearestVisibleLivingEntities matcher) {
        return matcher.findClosest(target -> predicate().test(target, entity)).orElse(null);
    }
}
