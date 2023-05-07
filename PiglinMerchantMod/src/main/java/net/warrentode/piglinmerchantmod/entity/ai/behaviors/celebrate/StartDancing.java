package net.warrentode.piglinmerchantmod.entity.ai.behaviors.celebrate;

import com.mojang.datafixers.util.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.*;
import net.tslat.smartbrainlib.api.core.behaviour.*;
import net.tslat.smartbrainlib.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class StartDancing<E extends LivingEntity> extends ExtendedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS =
            ObjectArrayList.of(
                    Pair.of(MemoryModuleType.CELEBRATE_LOCATION, MemoryStatus.VALUE_ABSENT),
                    Pair.of(MemoryModuleType.DANCING, MemoryStatus.REGISTERED)
            );

    private final int celebrateDuration;

    public StartDancing(int celebrateDuration) {
        this.celebrateDuration = celebrateDuration;
    }

    protected void start(@NotNull ServerLevel pLevel, @NotNull LivingEntity livingEntity, long pGameTime) {
        BrainUtils.setForgettableMemory(livingEntity, MemoryModuleType.DANCING, true, this.celebrateDuration);
        BrainUtils.setForgettableMemory(livingEntity, MemoryModuleType.CELEBRATE_LOCATION, livingEntity.blockPosition(), this.celebrateDuration);
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
