package net.warrentode.piglinmerchantmod.entity.ai.behaviors.vanilla;

import com.google.common.collect.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.item.*;

import java.util.function.*;

/** this is a direct copy of the vanilla behavior from before 1.19.3 **/
public class GoToWantedItem<E extends LivingEntity> extends Behavior<E> {
    private final Predicate<E> predicate;
    private final int maxDistToWalk;
    private final float speedModifier;

    public GoToWantedItem(float pSpeedModifier, boolean pHasTarget, int pMaxDistToWalk) {
        this((p_23158_) -> {
            return true;
        }, pSpeedModifier, pHasTarget, pMaxDistToWalk);
    }

    public GoToWantedItem(Predicate<E> pPredicate, float pSpeedModifier, boolean pHasTarget, int pMaxDistToWalk) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.WALK_TARGET, pHasTarget ? MemoryStatus.REGISTERED : MemoryStatus.VALUE_ABSENT, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryStatus.VALUE_PRESENT));
        this.predicate = pPredicate;
        this.maxDistToWalk = pMaxDistToWalk;
        this.speedModifier = pSpeedModifier;
    }

    protected boolean checkExtraStartConditions(ServerLevel pLevel, E pOwner) {
        return !this.isOnPickupCooldown(pOwner) && this.predicate.test(pOwner) && this.getClosestLovedItem(pOwner).closerThan(pOwner, (double)this.maxDistToWalk);
    }

    protected void start(ServerLevel pLevel, E pEntity, long pGameTime) {
        BehaviorUtils.setWalkAndLookTargetMemories(pEntity, this.getClosestLovedItem(pEntity), this.speedModifier, 0);
    }

    private boolean isOnPickupCooldown(E pEntity) {
        return pEntity.getBrain().checkMemory(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, MemoryStatus.VALUE_PRESENT);
    }

    private ItemEntity getClosestLovedItem(E pEntity) {
        return pEntity.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM).get();
    }
}