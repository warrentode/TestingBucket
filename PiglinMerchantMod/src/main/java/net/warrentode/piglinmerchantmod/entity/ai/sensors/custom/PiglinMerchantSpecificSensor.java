package net.warrentode.piglinmerchantmod.entity.ai.sensors.custom;

import com.google.common.collect.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.client.player.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.ai.sensing.*;
import net.minecraft.world.entity.boss.wither.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.piglin.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.tslat.smartbrainlib.api.core.*;
import net.tslat.smartbrainlib.api.core.sensor.*;
import net.tslat.smartbrainlib.util.*;
import net.warrentode.piglinmerchantmod.entity.ai.*;
import net.warrentode.piglinmerchantmod.entity.custom.*;
import org.jetbrains.annotations.*;

import java.util.*;

import static net.warrentode.piglinmerchantmod.entity.ai.behaviors.food.EatFood.*;
import static net.warrentode.piglinmerchantmod.entity.custom.PiglinMerchantEntity.*;

public class PiglinMerchantSpecificSensor<E extends LivingEntity> extends ExtendedSensor<E> {
    private static final ImmutableList<MemoryModuleType<?>> MEMORIES =
            ImmutableList.of(
                    // vanilla memory types
                    MemoryModuleType.LOOK_TARGET,
                    MemoryModuleType.NEAREST_LIVING_ENTITIES,
                    MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
                    MemoryModuleType.NEAREST_VISIBLE_PLAYER,
                    MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
                    MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS,
                    MemoryModuleType.NEARBY_ADULT_PIGLINS,
                    MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
                    MemoryModuleType.HURT_BY,
                    MemoryModuleType.HURT_BY_ENTITY,
                    MemoryModuleType.WALK_TARGET,
                    MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                    MemoryModuleType.ATTACK_TARGET,
                    MemoryModuleType.ATTACK_COOLING_DOWN,
                    MemoryModuleType.INTERACTION_TARGET,
                    MemoryModuleType.PATH,
                    MemoryModuleType.ANGRY_AT,
                    MemoryModuleType.UNIVERSAL_ANGER,
                    MemoryModuleType.AVOID_TARGET,
                    MemoryModuleType.ADMIRING_ITEM,
                    MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM,
                    MemoryModuleType.ADMIRING_DISABLED,
                    MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM,
                    MemoryModuleType.CELEBRATE_LOCATION,
                    MemoryModuleType.DANCING,
                    MemoryModuleType.HUNTED_RECENTLY,
                    MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN,
                    MemoryModuleType.NEAREST_VISIBLE_NEMESIS,
                    MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED,
                    MemoryModuleType.RIDE_TARGET,
                    MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT,
                    MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT,
                    MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN,
                    MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD,
                    MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM,
                    MemoryModuleType.ATE_RECENTLY,
                    MemoryModuleType.NEAREST_REPELLENT,
                    // custom memory types
                    ModMemoryTypes.NEARBY_ADULT_ALLIES.get(),
                    ModMemoryTypes.NEAREST_VISIBLE_ADULT_ALLIES.get(),
                    ModMemoryTypes.VISIBLE_ADULT_ALLIES_COUNT.get()
            );

    public ImmutableList<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES;
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return ModSensorTypes.PIGLINMERCHANT_SPECIFIC_SENSOR.get();
    }

    @Override
    protected void doTick(ServerLevel level, @NotNull E entity) {
        SmartBrain<?> brain = (SmartBrain<?>) entity.getBrain();
        PiglinMerchantEntity piglinMerchantEntity = (PiglinMerchantEntity) entity;
        ItemStack stack = ItemStack.EMPTY.getItem().getDefaultInstance();
        countFoodPointsInInventory(piglinMerchantEntity, stack);

        List<LivingEntity> adultAllies = new ObjectArrayList<>();

        BrainUtils.withMemory(brain, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, nearestVisible -> {
            Mob nemesis = null;
            LivingEntity zombified = null;
            Player playerNoFriendlyGear = null;
            Player playerWithGoodie = null;

            List<LivingEntity> visibleAdultAllies = new ObjectArrayList<>();

            for (LivingEntity target : nearestVisible.findAll(obj -> true)) {
                // DETECT COMMUNITY & ALLIES
                if (target instanceof PiglinBrute brute) {
                    visibleAdultAllies.add(brute);
                }
                else if (target instanceof Piglin piglin) {
                    if (piglin.isAdult()) {
                        visibleAdultAllies.add(piglin);
                    }
                }
                else if (target instanceof PiglinMerchantEntity) {
                    visibleAdultAllies.add(piglinMerchantEntity);
                }
                // PLAYER DETECTION
                else if (target instanceof LocalPlayer player) {
                    if (playerNoFriendlyGear == null && !isWearingFriendlyGear(player) && entity.canAttack(player)) {
                        playerNoFriendlyGear = player;
                    }

                    if (playerWithGoodie == null && !player.isSpectator() && isPlayerHoldingLovedItem(player)) {
                        BrainUtils.setMemory(brain, MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, player);
                        playerWithGoodie = player;
                    }
                }
                // DETECT ZOMBIFIED
                else if (nemesis != null || !(target instanceof WitherSkeleton) && !(target instanceof WitherBoss) && !(target instanceof AbstractIllager)) {
                    if (zombified == null && isZombified(target.getType())) {
                        zombified = target;
                    }
                }
                // DETECT MORTAL ENEMIES
                else {
                    nemesis = (Mob) target;
                }

            }

            // set memories for nemesis and zombies
            BrainUtils.setMemory(brain, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, nemesis);
            BrainUtils.setMemory(brain, MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, zombified);
            // set memories for players
            BrainUtils.setMemory(brain, MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, playerNoFriendlyGear);
            BrainUtils.setMemory(brain, MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, playerWithGoodie);
            // set memories for allied community
            BrainUtils.setMemory(entity, ModMemoryTypes.NEAREST_VISIBLE_ADULT_ALLIES.get(), visibleAdultAllies);
            BrainUtils.setMemory(entity, ModMemoryTypes.VISIBLE_ADULT_ALLIES_COUNT.get(), visibleAdultAllies.size());

            // DETECT REPELLENTS
            BrainUtils.setMemory(brain, MemoryModuleType.NEAREST_REPELLENT,
                    BlockPos.findClosestMatch(
                            entity.blockPosition(),
                            PiglinMerchantEntity.REPELLENT_DETECTION_RANGE_HORIZONTAL,
                            PiglinMerchantEntity.REPELLENT_DETECTION_RANGE_VERTICAL,
                            pos -> {
                                BlockState state = level.getBlockState(pos);
                                boolean isRepellent = state.is(BlockTags.PIGLIN_REPELLENTS);
                                return isRepellent && state.is(Blocks.SOUL_CAMPFIRE) ? CampfireBlock.isLitCampfire(state) : isRepellent;
                            }
                    ).orElse(null));
        });

        BrainUtils.withMemory(brain, MemoryModuleType.NEAREST_LIVING_ENTITIES, entities -> {
            // adding community entities to their lists
            for (LivingEntity target : entities) {
                if (target instanceof AbstractPiglin abstractPiglin && abstractPiglin.isAdult()){
                    // add to the list the nearest adult piglins
                    adultAllies.add(abstractPiglin);
                }
                else if (target instanceof PiglinMerchantEntity) {
                    // add to the list the nearest adult todepiglins
                    adultAllies.add(piglinMerchantEntity);
                }
            }
        });
        // custom ally community
        BrainUtils.setMemory(entity, ModMemoryTypes.NEARBY_ADULT_ALLIES.get(), adultAllies);
    }

    public static List<LivingEntity> getAdultAllies(@NotNull PiglinMerchantEntity piglinMerchant) {
        return BrainUtils.getMemory(piglinMerchant, ModMemoryTypes.NEARBY_ADULT_ALLIES.get());
    }
    public static List<LivingEntity> getVisibleAdultAllies(PiglinMerchantEntity piglinMerchant) {
        return BrainUtils.getMemory(piglinMerchant, ModMemoryTypes.NEAREST_VISIBLE_ADULT_ALLIES.get());
    }

    private boolean isZombified(EntityType<?> type) {
        return type == EntityType.ZOMBIFIED_PIGLIN
                || type == EntityType.ZOGLIN
                || type == EntityType.ZOMBIE
                || type == EntityType.ZOMBIE_VILLAGER
                || type == EntityType.ZOMBIE_HORSE;
    }
    public static boolean isNearRepellent(@NotNull PiglinMerchantEntity piglinMerchant) {
        if (BrainUtils.hasMemory(piglinMerchant, MemoryModuleType.NEAREST_REPELLENT)) {
            return piglinMerchant.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_REPELLENT);
        }
        return false;
    }
    public static Optional<LivingEntity> getAvoidTarget(@NotNull PiglinMerchantEntity piglinMerchant) {
        return piglinMerchant.getBrain().hasMemoryValue(MemoryModuleType.AVOID_TARGET) ?
                piglinMerchant.getBrain().getMemory(MemoryModuleType.AVOID_TARGET) : Optional.empty();
    }
    public static boolean isNearAvoidTarget(PiglinMerchantEntity piglinMerchant) {
        if (BrainUtils.hasMemory(piglinMerchant, MemoryModuleType.AVOID_TARGET)) {
            return Objects.requireNonNull(BrainUtils.getMemory(piglinMerchant, MemoryModuleType.AVOID_TARGET))
                    .closerThan(piglinMerchant, DESIRED_AVOID_DISTANCE);
        }
        return false;
    }

    public static boolean isPlayerHoldingLovedItem(@NotNull Player player) {
        return player.getType() == EntityType.PLAYER && player.isHolding(PiglinMerchantEntity::isLovedItem);
    }
    public static boolean seesPlayerHoldingLovedItem(PiglinMerchantEntity todePiglinMerchant) {
        return BrainUtils.hasMemory(todePiglinMerchant, MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM);
    }
    public static boolean isWearingFriendlyGear(@NotNull Player player) {
        for(ItemStack itemstack : player.getArmorSlots()) {
            itemstack.getItem();
            if (itemstack.makesPiglinsNeutral(player)) {
                return true;
            }
        }
        return false;
    }
    public static @Nullable Player getNearestVisibleTargetablePlayer(@NotNull PiglinMerchantEntity piglinMerchant) {
        if (piglinMerchant.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER).isPresent()) {
            piglinMerchant.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
        }
        return null;
    }
}