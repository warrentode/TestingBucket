package net.warrentode.piglinmerchantmod.entity.ai;

import com.mojang.serialization.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.*;
import net.warrentode.piglinmerchantmod.util.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

public class ModMemoryTypes {
    public static void init() {}

    public static final Supplier<MemoryModuleType<List<LivingEntity>>> NEARBY_ADULT_ALLIES = register("nearby_adult_allies");
    public static final Supplier<MemoryModuleType<List<LivingEntity>>> NEAREST_VISIBLE_ADULT_ALLIES = register("nearest_visible_adult_allies");
    public static final Supplier<MemoryModuleType<Integer>> VISIBLE_ADULT_ALLIES_COUNT = register("visible_adult_allies_count");

    private static <T> Supplier<MemoryModuleType<T>> register(String id) {
        return BrainLoader.registerMemoryType(id, null);
    }

    private static <T> Supplier<MemoryModuleType<T>> register(String id, @Nullable Codec<T> codec) {
        return BrainLoader.registerMemoryType(id, codec);
    }
}