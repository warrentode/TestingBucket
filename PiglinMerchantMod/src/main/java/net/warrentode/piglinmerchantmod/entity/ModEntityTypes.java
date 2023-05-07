package net.warrentode.piglinmerchantmod.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.warrentode.piglinmerchantmod.entity.custom.PiglinMerchantEntity;

import static net.warrentode.piglinmerchantmod.PiglinMerchantMod.MODID;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<PiglinMerchantEntity>> PIGLINMERCHANT =
            ENTITY_TYPES.register("piglinmerchant",
                    () -> EntityType.Builder.of(PiglinMerchantEntity::new, MobCategory.MISC)
                            .sized(0.6f, 1.95f)
                            .build(new ResourceLocation(MODID, "piglinmerchant").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}