package net.warrentode.piglinmerchantmod.entity.ai.behaviors.hunting;

import com.mojang.datafixers.util.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.monster.hoglin.*;
import net.tslat.smartbrainlib.api.core.behaviour.*;
import net.tslat.smartbrainlib.util.*;
import net.warrentode.piglinmerchantmod.entity.ai.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class StartHunting<E extends LivingEntity> extends ExtendedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS =
            ObjectArrayList.of(
                    Pair.of(MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, MemoryStatus.VALUE_PRESENT),
                    Pair.of(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, MemoryStatus.VALUE_PRESENT),
                    Pair.of(MemoryModuleType.HUNTED_RECENTLY, MemoryStatus.VALUE_ABSENT),
                    Pair.of(ModMemoryTypes.NEAREST_VISIBLE_ADULT_ALLIES.get(), MemoryStatus.REGISTERED)
            );

    protected boolean checkExtraStartConditions(@NotNull ServerLevel pLevel, @NotNull LivingEntity livingEntity) {
        if (BrainUtils.hasMemory(livingEntity, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT) &&
                BrainUtils.getMemory(livingEntity, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT) != null) {
            Integer hoglinCount = BrainUtils.getMemory(livingEntity, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT);
            //* no idea why Intellij insists there's an issue here even though there are anti-null checks in place for this *//
            //noinspection DataFlowIssue
            return hoglinCount > 2;
        }
        return false;
    }

    protected void start(@NotNull ServerLevel pLevel, @NotNull LivingEntity livingEntity, long pGameTime) {
        if (BrainUtils.hasMemory(livingEntity, MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN) &&
                BrainUtils.getMemory(livingEntity, MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN) != null) {
            Hoglin huntingTarget = BrainUtils.getMemory(livingEntity, MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN);
            BrainUtils.setMemory(livingEntity, MemoryModuleType.NEAREST_ATTACKABLE, huntingTarget);
        }
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
