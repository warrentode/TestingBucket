package net.warrentode.recipebooktest.util.tags;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import static net.warrentode.recipebooktest.RecipeBookTest.MODID;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> STORAGE_BLOCKS = modBlockTag("storage_blocks");
        public static final TagKey<Block> CURRENCY_BLOCKS = modBlockTag("storage_blocks/currency_blocks");
        public static final TagKey<Block> JOB_BLOCKS = modBlockTag("job_blocks");
    }

    public static class Items {
        public static final TagKey<Item> BANKNOTES = modItemTag("banknotes");
        public static final TagKey<Item> COINS = modItemTag("coins");
        public static final TagKey<Item> CURRENCY = modItemTag("currency");
        public static final TagKey<Item> CURRENCY_MATERIALS = modItemTag("currency_materials");
        public static final TagKey<Item> CURRENCY_STAMPS = modItemTag("currency_stamps");

        public static final TagKey<Item> TEXTILES = modItemTag("textiles");
        public static final TagKey<Item> FIBRE = modItemTag("textiles/fibre");
        public static final TagKey<Item> FABRIC = modItemTag("textiles/fabric");
        public static final TagKey<Item> THREAD = modItemTag("textiles/thread");
        public static final TagKey<Item> CURRENCY_FIBER = modItemTag("textiles/currency_fiber");
        public static final TagKey<Item> CURRENCY_PAPER = modItemTag("textiles/currency_paper");

        public static final TagKey<Item> GEMS = modItemTag("gems");

        public static final TagKey<Item> NUGGETS = modItemTag("nuggets");
        public static final TagKey<Item> COPPER_NUGGET = modItemTag("nuggets/copper");
        public static final TagKey<Item> IRON_NUGGET = modItemTag("nuggets/iron");
        public static final TagKey<Item> GOLD_NUGGET = modItemTag("nuggets/gold");
        public static final TagKey<Item> LUCKY_NUGGET = modItemTag("nuggets/lucky");
        public static final TagKey<Item> NETHERITE_NUGGET = modItemTag("nuggets/netherite");

        public static final TagKey<Item> INGOTS = modItemTag("ingots");
        public static final TagKey<Item> COPPER_INGOT = modItemTag("ingots/copper");
        public static final TagKey<Item> IRON_INGOT = modItemTag("ingots/iron");
        public static final TagKey<Item> GOLD_INGOT = modItemTag("ingots/gold");
        public static final TagKey<Item> NETHERITE_INGOT = modItemTag("ingots/netherite");
    }

    public interface StructureTags {
        TagKey<Structure> ON_ANCIENT_CITY_EXPLORER_MAPS = modStructureTag("worldgen/structure");
        TagKey<Structure> ON_BASTION_REMNANT_EXPLORER_MAPS = modStructureTag("worldgen/structure");
        TagKey<Structure> ON_DESERT_PYRAMID_EXPLORER_MAPS = modStructureTag("worldgen/structure");
        TagKey<Structure> ON_END_CITY_EXPLORER_MAPS = modStructureTag("worldgen/structure");
        TagKey<Structure> ON_IGLOO_EXPLORER_MAPS = modStructureTag("worldgen/structure");
        TagKey<Structure> ON_JUNGLE_TEMPLE_EXPLORER_MAPS = modStructureTag("worldgen/structure");
        TagKey<Structure> ON_MINESHAFT_EXPLORER_MAPS = modStructureTag("worldgen/structure");
        TagKey<Structure> ON_NETHER_FORTRESS_EXPLORER_MAPS = modStructureTag("worldgen/structure");
        TagKey<Structure> ON_NETHER_FOSSIL_EXPLORER_MAPS = modStructureTag("worldgen/structure");
        TagKey<Structure> ON_NETHER_RUINED_PORTAL_EXPLORER_MAPS = modStructureTag("worldgen/structure");
        TagKey<Structure> ON_OCEAN_RUIN_EXPLORER_MAPS = modStructureTag("worldgen/structure");
        TagKey<Structure> ON_OVERWORLD_RUINED_PORTAL_EXPLORER_MAPS = modStructureTag("worldgen/structure");
        TagKey<Structure> ON_PILLAGER_OUTPOST_EXPLORER_MAPS = modStructureTag("worldgen/structure");
        TagKey<Structure> ON_SHIPWRECK_EXPLORER_MAPS = modStructureTag("worldgen/structure");
        TagKey<Structure> ON_STRONGHOLD_EXPLORER_MAPS = modStructureTag("worldgen/structure");
        TagKey<Structure> ON_SWAMP_HUT_EXPLORER_MAPS = modStructureTag("worldgen/structure");
        TagKey<Structure> ON_VILLAGE_EXPLORER_MAPS = modStructureTag("worldgen/structure");
    }

    private static @NotNull TagKey<Block> modBlockTag(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(MODID, path));
    }
    private static @NotNull TagKey<Item> modItemTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(MODID, path));
    }
    private static TagKey<Structure> modStructureTag(String path) {
        return TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(MODID, path));
    }
}