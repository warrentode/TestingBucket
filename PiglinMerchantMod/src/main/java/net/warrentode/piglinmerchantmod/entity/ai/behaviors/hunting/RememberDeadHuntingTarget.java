package net.warrentode.piglinmerchantmod.entity.ai.behaviors.hunting;

import com.mojang.datafixers.util.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.*;
import net.tslat.smartbrainlib.api.core.behaviour.*;
import net.tslat.smartbrainlib.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

import static net.warrentode.piglinmerchantmod.entity.custom.PiglinMerchantEntity.*;

public class RememberDeadHuntingTarget<E extends LivingEntity> extends ExtendedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS =
            ObjectArrayList.of(
                    Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT),
                    Pair.of(MemoryModuleType.HUNTED_RECENTLY, MemoryStatus.REGISTERED)
            );

    protected void start(@NotNull ServerLevel pLevel, @NotNull LivingEntity livingEntity, long pGameTime) {
        if (this.isAttackTargetDeadHoglin(livingEntity)) {
            dontKillAnyMoreHoglinsForAWhile(livingEntity);
        }
    }

    private boolean isAttackTargetDeadHoglin(@NotNull LivingEntity livingEntity) {
        if (BrainUtils.hasMemory(livingEntity, MemoryModuleType.ATTACK_TARGET) && BrainUtils.getMemory(livingEntity, MemoryModuleType.ATTACK_TARGET) != null) {
            LivingEntity target = BrainUtils.getTargetOfEntity(livingEntity);
            if (target != null) {
                return target.getType() == EntityType.HOGLIN && target.isDeadOrDying();
            }
        }
        return false;
    }
    public static void dontKillAnyMoreHoglinsForAWhile(LivingEntity livingEntity) {
        BrainUtils.setForgettableMemory(livingEntity,
                MemoryModuleType.HUNTED_RECENTLY, true, TIME_BETWEEN_HUNTS.sample(livingEntity.level.random));
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
