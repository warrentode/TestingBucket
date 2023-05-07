package net.warrentode.piglinmerchantmod.entity.ai.behaviors.bartering;

import com.mojang.datafixers.util.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.*;
import net.minecraft.world.entity.ai.memory.*;
import net.tslat.smartbrainlib.api.core.behaviour.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class StopAdmireTired<E extends LivingEntity> extends ExtendedBehaviour<E> {
    private final int maxTimeToReachItem;
    private final int disableTime;
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS =
            ObjectArrayList.of(
                    Pair.of(MemoryModuleType.ADMIRING_ITEM, MemoryStatus.VALUE_PRESENT),
                    Pair.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryStatus.VALUE_PRESENT),
                    Pair.of(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, MemoryStatus.REGISTERED),
                    Pair.of(MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM, MemoryStatus.REGISTERED)
            );

    public StopAdmireTired(int pMaxTimeToReachItem, int pDisableTime) {
        this.maxTimeToReachItem = pMaxTimeToReachItem;
        this.disableTime = pDisableTime;
    }

    protected boolean checkExtraStartConditions(@NotNull ServerLevel pLevel, @NotNull LivingEntity livingEntity) {
        return livingEntity.getOffhandItem().isEmpty();
    }

    protected void start(@NotNull ServerLevel pLevel, @NotNull LivingEntity livingEntity, long pGameTime) {
        Brain<?> todePiglinMerchantBrain = livingEntity.getBrain();
        Optional<Integer> optional = todePiglinMerchantBrain.getMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
        if (optional.isEmpty()) {
            todePiglinMerchantBrain.setMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, 0);
        } else {
            int i = optional.get();
            if (i > this.maxTimeToReachItem) {
                todePiglinMerchantBrain.eraseMemory(MemoryModuleType.ADMIRING_ITEM);
                todePiglinMerchantBrain.eraseMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
                todePiglinMerchantBrain.setMemoryWithExpiry(MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM, true, this.disableTime);
            } else {
                todePiglinMerchantBrain.setMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, i + 1);
            }
        }
    }
    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
