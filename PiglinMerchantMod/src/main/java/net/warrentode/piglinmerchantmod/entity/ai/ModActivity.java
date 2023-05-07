package net.warrentode.piglinmerchantmod.entity.ai;

import net.minecraft.world.entity.schedule.*;
import net.minecraftforge.registries.ForgeRegistries.*;
import net.minecraftforge.registries.*;
import net.warrentode.piglinmerchantmod.util.*;

public class ModActivity {
    public static void init() {}
    public static final RegistryObject<Activity> HUNT = registerActivity("hunt");

    private static <V> RegistryObject<Activity> registerActivity(String id) {
        return BrainLoader.registerActivities(RegistryManager.ACTIVE.getRegistry(Keys.ACTIVITIES), id, new Activity(id));
    }
}