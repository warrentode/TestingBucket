package net.warrentode.recipebooktest.sounds;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.warrentode.recipebooktest.RecipeBookTest.MODID;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    public static final RegistryObject<SoundEvent> COINPRESS_USE = SOUNDS.register("coin_press_use",
            () -> new SoundEvent(new ResourceLocation("recipebooktest", "coin_press_use")));
    public static final RegistryObject<SoundEvent> COINPRESS_TAKE_RESULT = SOUNDS.register("coin_press_take",
            () -> new SoundEvent(new ResourceLocation("recipebooktest", "coin_press_take")));
}