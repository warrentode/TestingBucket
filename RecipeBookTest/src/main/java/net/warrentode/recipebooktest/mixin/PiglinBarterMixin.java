package net.warrentode.recipebooktest.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import static net.minecraft.world.entity.monster.piglin.PiglinAi.*;
import net.minecraft.world.item.ItemStack;
import net.warrentode.recipebooktest.util.tags.ForgeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public abstract class PiglinBarterMixin {
    @Inject(at = @At("HEAD"), method = "stopHoldingOffHandItem", cancellable = true)
    private static void todecoins_stopHoldingOffHandItem(Piglin pPiglin, boolean pShouldBarter, CallbackInfo ci) {
        if (pPiglin.isAdult()) {
            if (pPiglin.getItemInHand(InteractionHand.OFF_HAND).is(ForgeTags.Items.PIGLIN_BARTER_ITEMS)) {
                pPiglin.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                throwItems(pPiglin, getBarterResponseItems(pPiglin));
                ci.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "wantsToPickup", cancellable = true)
    private static void todecoins_wantsToPickup(Piglin pPiglin, ItemStack pStack, CallbackInfoReturnable<Boolean> cir) {
        if (pStack.is(ForgeTags.Items.PIGLIN_BARTER_ITEMS) && isNotHoldingLovedItemInOffHand(pPiglin)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "canAdmire", cancellable = true)
    private static void todecoins_canAdmire(Piglin pPiglin, ItemStack pStack, CallbackInfoReturnable<Boolean> cir) {
        if (!isAdmiringDisabled(pPiglin) && !isAdmiringItem(pPiglin) && pPiglin.isAdult() && (pStack.isPiglinCurrency() || pStack.is(ForgeTags.Items.PIGLIN_BARTER_ITEMS))) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "isBarterCurrency", cancellable = true)
    private static void todecoins_isBarterCurrency(ItemStack pStack, CallbackInfoReturnable<Boolean> cir) {
        if (pStack.is(ForgeTags.Items.PIGLIN_BARTER_ITEMS)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}