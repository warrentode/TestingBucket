package net.warrentode.piglinmerchantmod.entity.ai.behaviors.bartering;

import com.mojang.datafixers.util.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.item.*;
import net.tslat.smartbrainlib.api.core.behaviour.*;
import net.tslat.smartbrainlib.util.*;
import net.warrentode.piglinmerchantmod.util.ModTags.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class StartAdmiring<E extends LivingEntity> extends ExtendedBehaviour<E> {
    private final int admireDuration;
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS =
            ObjectArrayList.of(
                    Pair.of(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, MemoryStatus.VALUE_PRESENT),
                    Pair.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryStatus.VALUE_PRESENT),
                    Pair.of(MemoryModuleType.ADMIRING_ITEM, MemoryStatus.VALUE_ABSENT),
                    Pair.of(MemoryModuleType.ADMIRING_DISABLED, MemoryStatus.VALUE_ABSENT),
                    Pair.of(MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM, MemoryStatus.VALUE_ABSENT)
            );

    public StartAdmiring(int admireDuration) {
        this.admireDuration = admireDuration;
    }

    protected boolean checkExtraStartConditions(@NotNull ServerLevel pLevel, @NotNull LivingEntity livingEntity) {
        if (BrainUtils.hasMemory(livingEntity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM)
                && BrainUtils.getMemory(livingEntity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM) != null) {
            ItemEntity itemEntity = BrainUtils.getMemory(livingEntity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
            if (itemEntity != null) {
                return itemEntity.getItem().is(Items.PIGLIN_WANTED_ITEMS);
            }
        }
        return false;
    }

    protected void start(@NotNull ServerLevel pLevel, @NotNull LivingEntity livingEntity, long pGameTime) {
        livingEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.ADMIRING_ITEM, true, this.admireDuration);
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
