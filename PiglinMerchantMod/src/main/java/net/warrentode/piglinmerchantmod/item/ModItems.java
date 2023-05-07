package net.warrentode.piglinmerchantmod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.warrentode.piglinmerchantmod.entity.ModEntityTypes;

import static net.warrentode.piglinmerchantmod.PiglinMerchantMod.MODID;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<ForgeSpawnEggItem> PIGLINMERCHANT_SPAWN_EGG = ITEMS.register("piglinmerchant_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.PIGLINMERCHANT, 0x800000, 0xF9F3A4,
                    new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
