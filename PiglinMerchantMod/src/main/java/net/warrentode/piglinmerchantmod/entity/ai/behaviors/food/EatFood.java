package net.warrentode.piglinmerchantmod.entity.ai.behaviors.food;

import com.mojang.datafixers.util.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.tags.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.gameevent.*;
import net.tslat.smartbrainlib.api.core.behaviour.*;
import net.tslat.smartbrainlib.util.*;
import net.warrentode.piglinmerchantmod.entity.custom.*;
import org.jetbrains.annotations.*;

import java.util.*;

import static net.warrentode.piglinmerchantmod.entity.custom.PiglinMerchantEntity.*;

public class EatFood<E extends LivingEntity> extends ExtendedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS =
            ObjectArrayList.of(
                    Pair.of(MemoryModuleType.ATE_RECENTLY, MemoryStatus.VALUE_ABSENT),
                    Pair.of(MemoryModuleType.ADMIRING_ITEM, MemoryStatus.VALUE_ABSENT)
            );

    public static boolean isFood(@NotNull ItemStack pStack) {
        return pStack.is(ItemTags.PIGLIN_FOOD);
    }

    public static boolean hasNotEatenRecently(@NotNull PiglinMerchantEntity piglinMerchant) {
        return !piglinMerchant.getBrain().hasMemoryValue(MemoryModuleType.ATE_RECENTLY);
    }
    public static int countFoodPointsInInventory(@NotNull PiglinMerchantEntity piglinMerchant, @NotNull ItemStack stack) {
        if (stack.is(ItemTags.PIGLIN_FOOD)) {
            piglinMerchant.foodLevel = piglinMerchant.getInventory().countItem(stack.getItem());
        }
        else {
            piglinMerchant.foodLevel = 0;
        }
        return piglinMerchant.foodLevel;
    }

    public static boolean eat(@NotNull PiglinMerchantEntity piglinMerchant) {
        int i = 0;
        ItemStack stack = piglinMerchant.getInventory().getItem(i);
        if ((piglinMerchant.getHealth() < piglinMerchant.getMaxHealth())
                && !BrainUtils.hasMemory(piglinMerchant, MemoryModuleType.DANCING)) {
            if (hasNotEatenRecently(piglinMerchant) && countFoodPointsInInventory(piglinMerchant, stack) != 0) {
                for (i = 0; i < piglinMerchant.getInventory().getContainerSize(); ++i) {
                    if (!stack.isEmpty()) {
                        int integer = countFoodPointsInInventory(piglinMerchant, stack);
                        int j = stack.getCount();

                        for (int k = j; k > 0; --k) {
                            if (!hasNotEatenRecently(piglinMerchant)) {
                                ItemStack offHandItem = piglinMerchant.getItemInHand(InteractionHand.OFF_HAND);
                                piglinMerchant.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                                return true;
                            }
                            else {
                                piglinMerchant.setIsEating(true);
                                piglinMerchant.gameEvent(GameEvent.EAT);
                                piglinMerchant.foodLevel += integer;
                                BrainUtils.setForgettableMemory(piglinMerchant,MemoryModuleType.ATE_RECENTLY, true, EAT_COOLDOWN);
                                piglinMerchant.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 50, 0, false, false));
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
