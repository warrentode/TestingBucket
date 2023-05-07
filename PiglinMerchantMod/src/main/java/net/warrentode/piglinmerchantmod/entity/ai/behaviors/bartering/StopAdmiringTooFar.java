package net.warrentode.piglinmerchantmod.entity.ai.behaviors.bartering;

import com.mojang.datafixers.util.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.item.*;
import net.tslat.smartbrainlib.api.core.behaviour.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class StopAdmiringTooFar<E extends LivingEntity> extends ExtendedBehaviour<E> {
    private final int maxDistanceToItem;
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS =
            ObjectArrayList.of(
                    Pair.of(MemoryModuleType.ADMIRING_ITEM, MemoryStatus.VALUE_PRESENT),
                    Pair.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryStatus.REGISTERED)
            );

    public StopAdmiringTooFar(int pMaxDistanceToItem) {
        this.maxDistanceToItem = pMaxDistanceToItem;
    }

    protected boolean checkExtraStartConditions(@NotNull ServerLevel pLevel, @NotNull LivingEntity livingEntity) {
        if (!livingEntity.getOffhandItem().isEmpty()) {
            return false;
        } else {
            Optional<ItemEntity> optional = livingEntity.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
            return optional.map(itemEntity -> !itemEntity.closerThan(livingEntity, this.maxDistanceToItem)).orElse(true);
        }
    }

    protected void start(@NotNull ServerLevel pLevel, @NotNull LivingEntity livingEntity, long pGameTime) {
        livingEntity.getBrain().eraseMemory(MemoryModuleType.ADMIRING_ITEM);
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
