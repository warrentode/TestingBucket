package net.warrentode.piglinmerchantmod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.warrentode.piglinmerchantmod.PiglinMerchantMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {
    public static CreativeModeTab PIGLINMERCHANT_TAB;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        PIGLINMERCHANT_TAB = event.registerCreativeModeTab(new ResourceLocation(MODID, "piglinmerchant_tab"),
                builder -> builder.icon(() -> new ItemStack(Items.GOLD_INGOT))
                        .title(Component.translatable("itemGroup.piglinmerchantmod.piglinmerchant_tab")));
    }
}