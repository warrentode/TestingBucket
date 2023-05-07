package net.warrentode.villagertradesjsonreader.mixin;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.warrentode.villagertradesjsonreader.trades.CreateTradeList;
import net.warrentode.villagertradesjsonreader.trades.TradeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class VillagerTradesMixin {
    @Invoker("getVillagerData")
    public abstract VillagerData invokeGetVillagerData();
    @Inject(at = @At("HEAD"), method = "updateTrades", cancellable = true)
    private void villagertradesjsonreader_updateTrades(CallbackInfo ci) {
        VillagerData data = this.invokeGetVillagerData();
        VillagerProfession profession = data.getProfession();
        if (TradeManager.professions.contains(profession)) {
            CreateTradeList.populateTradeData();
            ci.cancel();
        }
    }
}