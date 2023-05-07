package net.warrentode.piglinmerchantmod.util;

import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;

import static net.warrentode.piglinmerchantmod.PiglinMerchantMod.*;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> PIGLIN_BARTER_ITEMS = modItemTag("piglin_barter_items");
        public static final TagKey<Item> PIGLIN_WANTED_ITEMS = modItemTag("piglin_wanted_items");
    }

    private static TagKey<Item> modItemTag(String path) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(MODID, path));
    }
}
