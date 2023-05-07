package net.warrentode.piglinmerchantmod.entity.ai.behaviors.bartering;

import com.mojang.datafixers.util.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraftforge.common.*;
import net.tslat.smartbrainlib.api.core.behaviour.*;
import net.warrentode.piglinmerchantmod.entity.custom.*;
import net.warrentode.piglinmerchantmod.util.ModTags.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class StopHoldingAndBarter<E extends LivingEntity> extends ExtendedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS =
            ObjectArrayList.of(
                    Pair.of(MemoryModuleType.ADMIRING_ITEM, MemoryStatus.VALUE_ABSENT)
            );

    protected boolean checkExtraStartConditions(@NotNull ServerLevel pLevel, @NotNull LivingEntity livingEntity) {
        return !livingEntity.getOffhandItem().isEmpty()
                && !livingEntity.getOffhandItem().canPerformAction(ToolActions.SHIELD_BLOCK);
    }

    protected void start(@NotNull ServerLevel pLevel, @NotNull LivingEntity livingEntity, long pGameTime) {
        livingEntity.getOffhandItem().is(Items.PIGLIN_BARTER_ITEMS);
        PiglinMerchantEntity.stopHoldingOffHandItem((PiglinMerchantEntity) livingEntity, true);
    }
    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
