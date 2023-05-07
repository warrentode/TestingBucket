package net.warrentode.piglinmerchantmod.entity.ai.behaviors.combat;

import com.mojang.datafixers.util.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.ai.sensing.*;
import net.minecraft.world.entity.monster.hoglin.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.schedule.*;
import net.minecraft.world.level.*;
import net.tslat.smartbrainlib.api.core.behaviour.*;
import net.tslat.smartbrainlib.util.*;
import net.warrentode.piglinmerchantmod.entity.ai.behaviors.hunting.*;
import net.warrentode.piglinmerchantmod.entity.ai.sensors.custom.*;
import net.warrentode.piglinmerchantmod.entity.custom.*;
import org.jetbrains.annotations.*;

import java.util.*;

import static net.minecraft.world.entity.monster.piglin.PiglinAi.*;

public class SetAngerTarget extends ExtendedBehaviour<PiglinMerchantEntity> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS =
            ObjectArrayList.of(
                    Pair.of(MemoryModuleType.NEAREST_ATTACKABLE, MemoryStatus.VALUE_PRESENT),
                    Pair.of(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryStatus.VALUE_PRESENT),
                    Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT),
                    Pair.of(MemoryModuleType.ANGRY_AT, MemoryStatus.VALUE_ABSENT),
                    Pair.of(MemoryModuleType.UNIVERSAL_ANGER, MemoryStatus.VALUE_ABSENT),
                    Pair.of(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, MemoryStatus.VALUE_PRESENT)
            );

    public static void setAngerTarget(PiglinMerchantEntity piglinMerchant, LivingEntity angerTarget) {
        if (Sensor.isEntityAttackableIgnoringLineOfSight(piglinMerchant, angerTarget)) {
            piglinMerchant.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            BrainUtils.setForgettableMemory(piglinMerchant, MemoryModuleType.ANGRY_AT, angerTarget.getUUID(), PiglinMerchantEntity.ANGER_DURATION);
            if (angerTarget.getType() == EntityType.HOGLIN && piglinMerchant.canHunt()) {
                broadcastDontKillAnyMoreHoglinsForAWhile(piglinMerchant);
            }
            if (angerTarget.getType() == EntityType.PLAYER && piglinMerchant.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                piglinMerchant.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, true, PiglinMerchantEntity.ANGER_DURATION);
            }
        }
    }
    public static void broadcastAngerTarget(PiglinMerchantEntity piglinMerchant, LivingEntity angerTarget) {
        PiglinMerchantSpecificSensor.getAdultAllies(piglinMerchant).forEach((TodePiglinMerchant) -> {
            if (angerTarget.getType() != EntityType.HOGLIN || piglinMerchant.canHunt() && ((Hoglin)angerTarget).canBeHunted()) {
                setAngerTargetIfCloserThanCurrent(piglinMerchant, angerTarget);
            }
        });
    }
    private static void setAngerTargetIfCloserThanCurrent(PiglinMerchantEntity piglinMerchant, LivingEntity currentTarget) {
        Optional<LivingEntity> optional = getAngerTarget(piglinMerchant);
        LivingEntity livingentity = BehaviorUtils.getNearestTarget(piglinMerchant, optional, currentTarget);
        if (optional.isEmpty() || optional.get() != livingentity) {
            setAngerTarget(piglinMerchant, livingentity);
        }
    }
    public static void broadcastDontKillAnyMoreHoglinsForAWhile(PiglinMerchantEntity piglinMerchant) {
        PiglinMerchantSpecificSensor.getVisibleAdultAllies(piglinMerchant).forEach(RememberDeadHuntingTarget::dontKillAnyMoreHoglinsForAWhile);
    }
    public static void dontKillAnyMoreHoglinsForAWhile(PiglinMerchantEntity piglinMerchant) {
        BrainUtils.setForgettableMemory(piglinMerchant,
                MemoryModuleType.HUNTED_RECENTLY, true, PiglinMerchantEntity.TIME_BETWEEN_HUNTS.sample(piglinMerchant.level.random));
    }
    private static @NotNull Optional<LivingEntity> getAngerTarget(PiglinMerchantEntity piglinMerchant) {
        return BehaviorUtils.getLivingEntityFromUUIDMemory(piglinMerchant, MemoryModuleType.ANGRY_AT);
    }
    /** this is based on player block interaction that is written within the block files - I may need to write an event interception
     * or some other type of behavior like a sensor to activate this method in order to replicate this vanilla piglin behavior with this entity
     * otherwise I will need it to trigger on some other interesting thing that still feels piglin appropriate in response to the player
     * need to figure out event handling for this most likely **/
    // I know that calling this via the start method in here greatly alters the behavior from the vanilla version
    public static void angerNearbyTodePiglins(@NotNull Player targetPlayer, boolean onlyIfTargetSeen) {
        List<PiglinMerchantEntity> list = targetPlayer.level.getEntitiesOfClass(PiglinMerchantEntity.class,
                targetPlayer.getBoundingBox().inflate(PiglinMerchantEntity.PLAYER_ANGER_RANGE));
        list.stream().filter(PiglinMerchantEntity::isIdle).filter((nearestTarget) -> !onlyIfTargetSeen ||
                BrainUtils.canSee(nearestTarget, targetPlayer)).forEach((angryTodePiglin) -> {
            if (angryTodePiglin.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                setAngerTargetToNearestTargetablePlayerIfFound(angryTodePiglin, targetPlayer);
            } else {
                setAngerTarget(angryTodePiglin, targetPlayer);
            }
        });
    }
    public static void setAngerTargetToNearestTargetablePlayerIfFound(@NotNull PiglinMerchantEntity piglinMerchant, LivingEntity currentTarget) {
        if (Objects.requireNonNull(BrainUtils.getMemory(piglinMerchant, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER))
                .closerThan(currentTarget, PiglinMerchantEntity.PLAYER_ANGER_RANGE)) {
            setAngerTarget(piglinMerchant, currentTarget);
        } else {
            setAngerTarget(piglinMerchant, currentTarget);
        }
    }
    public static void broadcastUniversalAnger(PiglinMerchantEntity piglinMerchant) {
        PiglinMerchantSpecificSensor.getAdultAllies(piglinMerchant).forEach((visibleTargetablePlayer) ->
                getNearestVisibleTargetablePlayer((PiglinMerchantEntity) visibleTargetablePlayer).ifPresent((player) ->
                        SetAngerTarget.setAngerTarget((PiglinMerchantEntity) visibleTargetablePlayer, player)));
    }
    public static Optional<Player> getNearestVisibleTargetablePlayer(@NotNull PiglinMerchantEntity piglinMerchant) {
        return piglinMerchant.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) ?
                piglinMerchant.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) : Optional.empty();
    }
    public static void maybeRetaliate(@NotNull PiglinMerchantEntity piglinMerchant, LivingEntity angerTarget) {
        if (!piglinMerchant.getBrain().isActive(Activity.AVOID)) {
            if (Sensor.isEntityAttackableIgnoringLineOfSight(piglinMerchant, angerTarget)) {
                if (!BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(piglinMerchant, angerTarget, 4.0D)) {
                    if (angerTarget.getType() == EntityType.PLAYER && piglinMerchant.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                        SetAngerTarget.setAngerTargetToNearestTargetablePlayerIfFound(piglinMerchant, angerTarget);
                        SetAngerTarget.broadcastUniversalAnger(piglinMerchant);
                    } else {
                        SetAngerTarget.setAngerTarget(piglinMerchant, angerTarget);
                        SetAngerTarget.broadcastAngerTarget(piglinMerchant, angerTarget);
                    }
                }
            }
        }
    }

    protected boolean checkExtraStartConditions(@NotNull ServerLevel pLevel, @NotNull PiglinMerchantEntity piglinMerchant) {
        return Objects.requireNonNull(BrainUtils.getMemory(piglinMerchant, MemoryModuleType.ATTACK_TARGET))
                .closerThan(piglinMerchant, PiglinMerchantEntity.DESIRED_AVOID_DISTANCE);
    }

    protected void start(PiglinMerchantEntity todePiglinMerchant) {
        Player targetPlayer = PiglinMerchantSpecificSensor.getNearestVisibleTargetablePlayer(todePiglinMerchant);

        if (targetPlayer == null) {
            BrainUtils.clearMemory(todePiglinMerchant, MemoryModuleType.ATTACK_TARGET);
            BrainUtils.clearMemory(todePiglinMerchant, MemoryModuleType.ANGRY_AT);
        }
        else {
            BrainUtils.setMemory(todePiglinMerchant, MemoryModuleType.ANGRY_AT, targetPlayer.getUUID());
            BrainUtils.setMemory(todePiglinMerchant, MemoryModuleType.ATTACK_TARGET, targetPlayer);
            BrainUtils.clearMemory(todePiglinMerchant, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            angerNearbyTodePiglins(targetPlayer, true);
            angerNearbyPiglins(targetPlayer, true);
        }
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}