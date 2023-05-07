package net.warrentode.villagertradesjsonreader.trades;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.trading.MerchantOffers;
import org.jetbrains.annotations.NotNull;

public class ModMerchantOffers extends MerchantOffers {
    public ModMerchantOffers() {}
    public ModMerchantOffers(@NotNull CompoundTag compoundTag) {
        ListTag recipes = compoundTag.getList("Recipes", 10);
        for (int i = 0; i < recipes.size(); ++i) {
            this.add(new ModMerchantOffer(recipes.getCompound(i)));
        }
    }
}