package net.warrentode.piglinmerchantmod.entity.ai.behaviors.vanilla;

import com.google.common.collect.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.ai.util.*;
import net.minecraft.world.phys.*;
import net.warrentode.piglinmerchantmod.entity.custom.*;

import java.util.function.*;

/** this is a direct copy of the vanilla behavior from before 1.19.3 **/
public class SetWalkTargetAwayFrom<T> extends Behavior<PiglinMerchantEntity> {
    private final MemoryModuleType<T> walkAwayFromMemory;
    private final float speedModifier;
    private final int desiredDistance;
    private final Function<T, Vec3> toPosition;

    public SetWalkTargetAwayFrom(MemoryModuleType<T> pWalkTargetAwayFromMemory, float pSpeedModifier, int pDesiredDistance, boolean pHasTarget, Function<T, Vec3> pToPosition) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, pHasTarget ? MemoryStatus.REGISTERED : MemoryStatus.VALUE_ABSENT, pWalkTargetAwayFromMemory, MemoryStatus.VALUE_PRESENT));
        this.walkAwayFromMemory = pWalkTargetAwayFromMemory;
        this.speedModifier = pSpeedModifier;
        this.desiredDistance = pDesiredDistance;
        this.toPosition = pToPosition;
    }

    public static SetWalkTargetAwayFrom<BlockPos> pos(MemoryModuleType<BlockPos> pWalkTargetAwayFromMemory, float pSpeedModifier, int pDesiredDistance, boolean pHasTarget) {
        return new SetWalkTargetAwayFrom<>(pWalkTargetAwayFromMemory, pSpeedModifier, pDesiredDistance, pHasTarget, Vec3::atBottomCenterOf);
    }

    public static SetWalkTargetAwayFrom<? extends Entity> entity(MemoryModuleType<? extends Entity> pWalkTargetAwayFromMemory, float pSpeedModifier, int pDesiredDistance, boolean pHasTarget) {
        return new SetWalkTargetAwayFrom<>(pWalkTargetAwayFromMemory, pSpeedModifier, pDesiredDistance, pHasTarget, Entity::position);
    }

    protected boolean checkExtraStartConditions(ServerLevel pLevel, PathfinderMob pOwner) {
        return this.alreadyWalkingAwayFromPosWithSameSpeed(pOwner) ? false : pOwner.position().closerThan(this.getPosToAvoid(pOwner), (double)this.desiredDistance);
    }

    private Vec3 getPosToAvoid(PathfinderMob pPathfinder) {
        return this.toPosition.apply(pPathfinder.getBrain().getMemory(this.walkAwayFromMemory).get());
    }

    private boolean alreadyWalkingAwayFromPosWithSameSpeed(PathfinderMob pPathfinder) {
        if (!pPathfinder.getBrain().hasMemoryValue(MemoryModuleType.WALK_TARGET)) {
            return false;
        } else {
            WalkTarget walktarget = pPathfinder.getBrain().getMemory(MemoryModuleType.WALK_TARGET).get();
            if (walktarget.getSpeedModifier() != this.speedModifier) {
                return false;
            } else {
                Vec3 vec3 = walktarget.getTarget().currentPosition().subtract(pPathfinder.position());
                Vec3 vec31 = this.getPosToAvoid(pPathfinder).subtract(pPathfinder.position());
                return vec3.dot(vec31) < 0.0D;
            }
        }
    }

    protected void start(ServerLevel pLevel, PathfinderMob pEntity, long pGameTime) {
        moveAwayFrom(pEntity, this.getPosToAvoid(pEntity), this.speedModifier);
    }

    private static void moveAwayFrom(PathfinderMob pPathfinder, Vec3 pPos, float pSpeedModifier) {
        for(int i = 0; i < 10; ++i) {
            Vec3 vec3 = LandRandomPos.getPosAway(pPathfinder, 16, 7, pPos);
            if (vec3 != null) {
                pPathfinder.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(vec3, pSpeedModifier, 0));
                return;
            }
        }

    }
}