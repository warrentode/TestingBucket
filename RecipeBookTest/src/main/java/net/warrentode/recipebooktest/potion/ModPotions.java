package net.warrentode.recipebooktest.potion;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import static net.warrentode.recipebooktest.RecipeBookTest.MODID;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS
            = DeferredRegister.create(ForgeRegistries.POTIONS, MODID);

    public static final RegistryObject<Potion> LUCK_POTION_2 = POTIONS.register("luck_potion_2",
            () -> new Potion(new MobEffectInstance(MobEffects.LUCK, 6000, 1)));
    public static final RegistryObject<Potion> LUCK_POTION_3 = POTIONS.register("luck_potion_3",
            () -> new Potion(new MobEffectInstance(MobEffects.LUCK, 6000, 2)));
    public static final RegistryObject<Potion> LUCK_POTION_4 = POTIONS.register("luck_potion_4",
            () -> new Potion(new MobEffectInstance(MobEffects.LUCK, 6000, 3)));
    public static final RegistryObject<Potion> LUCK_POTION_5 = POTIONS.register("luck_potion_5",
            () -> new Potion(new MobEffectInstance(MobEffects.LUCK, 6000, 4)));
    public static final RegistryObject<Potion> LUCK_POTION_6 = POTIONS.register("luck_potion_6",
            () -> new Potion(new MobEffectInstance(MobEffects.LUCK, 6000, 5)));

    public static final RegistryObject<Potion> LONG_LUCK_POTION = POTIONS.register("long_luck_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.LUCK, 9600, 1)));
    public static final RegistryObject<Potion> LONG_LUCK_POTION_2 = POTIONS.register("long_luck_potion_2",
            () -> new Potion(new MobEffectInstance(MobEffects.LUCK, 9600, 1)));
    public static final RegistryObject<Potion> LONG_LUCK_POTION_3 = POTIONS.register("long_luck_potion_3",
            () -> new Potion(new MobEffectInstance(MobEffects.LUCK, 9600, 2)));
    public static final RegistryObject<Potion> LONG_LUCK_POTION_4 = POTIONS.register("long_luck_potion_4",
            () -> new Potion(new MobEffectInstance(MobEffects.LUCK, 9600, 3)));
    public static final RegistryObject<Potion> LONG_LUCK_POTION_5 = POTIONS.register("long_luck_potion_5",
            () -> new Potion(new MobEffectInstance(MobEffects.LUCK, 9600, 4)));
    public static final RegistryObject<Potion> LONG_LUCK_POTION_6 = POTIONS.register("long_luck_potion_6",
            () -> new Potion(new MobEffectInstance(MobEffects.LUCK, 9600, 5)));

    public static final RegistryObject<Potion> UNLUCK_POTION = POTIONS.register("unluck_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.UNLUCK, 6000, 0)));
    public static final RegistryObject<Potion> UNLUCK_POTION_2 = POTIONS.register("unluck_potion_2",
            () -> new Potion(new MobEffectInstance(MobEffects.UNLUCK, 6000, 1)));
    public static final RegistryObject<Potion> UNLUCK_POTION_3 = POTIONS.register("unluck_potion_3",
            () -> new Potion(new MobEffectInstance(MobEffects.UNLUCK, 6000, 2)));
    public static final RegistryObject<Potion> UNLUCK_POTION_4 = POTIONS.register("unluck_potion_4",
            () -> new Potion(new MobEffectInstance(MobEffects.UNLUCK, 6000, 3)));
    public static final RegistryObject<Potion> UNLUCK_POTION_5 = POTIONS.register("unluck_potion_5",
            () -> new Potion(new MobEffectInstance(MobEffects.UNLUCK, 6000, 4)));
    public static final RegistryObject<Potion> UNLUCK_POTION_6 = POTIONS.register("unluck_potion_6",
            () -> new Potion(new MobEffectInstance(MobEffects.UNLUCK, 6000, 5)));

    public static final RegistryObject<Potion> LONG_UNLUCK_POTION = POTIONS.register("long_unluck_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.UNLUCK, 9600, 0)));
    public static final RegistryObject<Potion> LONG_UNLUCK_POTION_2 = POTIONS.register("long_unluck_potion_2",
            () -> new Potion(new MobEffectInstance(MobEffects.UNLUCK, 9600, 1)));
    public static final RegistryObject<Potion> LONG_UNLUCK_POTION_3 = POTIONS.register("long_unluck_potion_3",
            () -> new Potion(new MobEffectInstance(MobEffects.UNLUCK, 9600, 2)));
    public static final RegistryObject<Potion> LONG_UNLUCK_POTION_4 = POTIONS.register("long_unluck_potion_4",
            () -> new Potion(new MobEffectInstance(MobEffects.UNLUCK, 9600, 3)));
    public static final RegistryObject<Potion> LONG_UNLUCK_POTION_5 = POTIONS.register("long_unluck_potion_5",
            () -> new Potion(new MobEffectInstance(MobEffects.UNLUCK, 9600, 4)));
    public static final RegistryObject<Potion> LONG_UNLUCK_POTION_6 = POTIONS.register("long_unluck_potion_6",
            () -> new Potion(new MobEffectInstance(MobEffects.UNLUCK, 9600, 5)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}