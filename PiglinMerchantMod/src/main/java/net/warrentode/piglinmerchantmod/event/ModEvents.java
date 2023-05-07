package net.warrentode.piglinmerchantmod.event;

import net.minecraft.world.entity.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.warrentode.piglinmerchantmod.entity.*;
import net.warrentode.piglinmerchantmod.entity.custom.*;
import org.jetbrains.annotations.*;

import static net.warrentode.piglinmerchantmod.PiglinMerchantMod.*;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(@NotNull EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.PIGLINMERCHANT.get(), PiglinMerchantEntity.setAttributes());
    }

    @SubscribeEvent
    public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
        event.register(ModEntityTypes.PIGLINMERCHANT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PiglinMerchantEntity::checkPiglinMerchantSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}