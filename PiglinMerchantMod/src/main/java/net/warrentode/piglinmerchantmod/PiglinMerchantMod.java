package net.warrentode.piglinmerchantmod;

import com.mojang.logging.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.server.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.*;
import net.warrentode.piglinmerchantmod.client.*;
import net.warrentode.piglinmerchantmod.entity.*;
import net.warrentode.piglinmerchantmod.item.*;
import net.warrentode.piglinmerchantmod.util.*;
import org.jetbrains.annotations.*;
import org.slf4j.*;

@Mod(PiglinMerchantMod.MODID)
public class PiglinMerchantMod {
    public static final String MODID = "piglinmerchantmod";
    private static final Logger LOGGER = LogUtils.getLogger();
    public PiglinMerchantMod() {
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BrainBoot.init();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);

        ModEntityTypes.register(modEventBus);
        ModItems.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(@NotNull CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == ModCreativeModeTabs.PIGLINMERCHANT_TAB) {
            event.accept(ModItems.PIGLINMERCHANT_SPAWN_EGG);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO - SERVER LAUNCHING");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("CLIENT SETUP - ENTITY RENDERERS");
            EntityRenderers.register(ModEntityTypes.PIGLINMERCHANT.get(), PiglinMerchantRenderer::new);
        }
    }
}