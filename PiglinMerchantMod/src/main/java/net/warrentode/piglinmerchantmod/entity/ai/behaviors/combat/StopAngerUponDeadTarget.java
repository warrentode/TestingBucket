package net.warrentode.piglinmerchantmod.entity.ai.behaviors.combat;

import com.mojang.datafixers.util.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.level.*;
import net.tslat.smartbrainlib.api.core.behaviour.*;
import net.warrentode.piglinmerchantmod.entity.custom.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class StopAngerUponDeadTarget extends ExtendedBehaviour<PiglinMerchantEntity> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS =
            ObjectArrayList.of(
                    Pair.of(MemoryModuleType.ANGRY_AT, MemoryStatus.VALUE_PRESENT)
            );

    protected void start(@NotNull ServerLevel level, @NotNull PiglinMerchantEntity todePiglinMerchant, long pGameTime) {
        BehaviorUtils.getLivingEntityFromUUIDMemory(todePiglinMerchant, MemoryModuleType.ANGRY_AT).ifPresent((angerTarget) -> {
            if (angerTarget.isDeadOrDying() && (angerTarget.getType() != EntityType.PLAYER ||
                    level.getGameRules().getBoolean(GameRules.RULE_FORGIVE_DEAD_PLAYERS))) {
                todePiglinMerchant.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
            }

        });
    }
    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
