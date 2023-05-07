package net.warrentode.recipebooktest.util.tags;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

@SuppressWarnings("unused")
public class ForgeTags {
    public static class Blocks {
        public static final TagKey<Block> STORAGE_BLOCKS = forgeBlockTag("storage_blocks");
        public static final TagKey<Block> CURRENCY_BLOCKS = forgeBlockTag("storage_blocks/currency_blocks");
    }

    public static class Items {
        public static final TagKey<Item> BANKNOTES = forgeItemTag("banknotes");
        public static final TagKey<Item> COINS = forgeItemTag("coins");
        public static final TagKey<Item> CURRENCY = forgeItemTag("currency");
        public static final TagKey<Item> CURRENCY_MATERIALS = forgeItemTag("currency_materials");
        public static final TagKey<Item> CURRENCY_STAMPS = forgeItemTag("currency_stamps");

        public static final TagKey<Item> PIGLIN_BARTER_ITEMS = forgeItemTag("piglin_barter_items");

        public static final TagKey<Item> TEXTILES = forgeItemTag("textiles");
        public static final TagKey<Item> BARK = forgeItemTag("textiles/bark");
        public static final TagKey<Item> FIBRE = forgeItemTag("textiles/fibre");
        public static final TagKey<Item> STRING = forgeItemTag("textiles/string");
        public static final TagKey<Item> PAPER = forgeItemTag("textiles/paper");
        public static final TagKey<Item> FABRIC = forgeItemTag("textiles/fabric");
        public static final TagKey<Item> THREAD = forgeItemTag("textiles/thread");
        public static final TagKey<Item> CURRENCY_FIBER = forgeItemTag("textiles/currency_fiber");
        public static final TagKey<Item> CURRENCY_PAPER = forgeItemTag("textiles/currency_paper");

        public static final TagKey<Item> GEMS = forgeItemTag("gems");
        public static final TagKey<Item> AMETHYST = forgeItemTag("gems/amethyst");
        public static final TagKey<Item> DIAMOND = forgeItemTag("gems/diamond");
        public static final TagKey<Item> ECHO_SHARD = forgeItemTag("gems/echo_shard");
        public static final TagKey<Item> EMERALD = forgeItemTag("gems/emerald");
        public static final TagKey<Item> END_CRYSTAL = forgeItemTag("gems/end_crystal");
        public static final TagKey<Item> ENDER_EYE = forgeItemTag("gems/ender_eye");
        public static final TagKey<Item> ENDER_PEARL = forgeItemTag("gems/ender_pearl");
        public static final TagKey<Item> GLOWSTONE = forgeItemTag("gems/glowstone");
        public static final TagKey<Item> LAPIS_LAZULI = forgeItemTag("gems/lapis_lazuli");
        public static final TagKey<Item> NETHER_STAR = forgeItemTag("gems/nether_star");
        public static final TagKey<Item> PRISMARINE = forgeItemTag("gems/prismarine");
        public static final TagKey<Item> QUARTZ = forgeItemTag("gems/quartz");
        public static final TagKey<Item> REDSTONE = forgeItemTag("gems/redstone");

        public static final TagKey<Item> NUGGETS = forgeItemTag("nuggets");
        public static final TagKey<Item> COPPER_NUGGET = forgeItemTag("nuggets/copper");
        public static final TagKey<Item> IRON_NUGGET = forgeItemTag("nuggets/iron");
        public static final TagKey<Item> GOLD_NUGGET = forgeItemTag("nuggets/gold");
        public static final TagKey<Item> LUCKY_NUGGET = forgeItemTag("nuggets/lucky");
        public static final TagKey<Item> NETHERITE_NUGGET = forgeItemTag("nuggets/netherite");

        public static final TagKey<Item> INGOTS = forgeItemTag("ingots");
        public static final TagKey<Item> COPPER_INGOT = forgeItemTag("ingots/copper");
        public static final TagKey<Item> IRON_INGOT = forgeItemTag("ingots/iron");
        public static final TagKey<Item> GOLD_INGOT = forgeItemTag("ingots/gold");
        public static final TagKey<Item> NETHERITE_INGOT = forgeItemTag("ingots/netherite");
    }

    public interface StructureTags {
        TagKey<Structure> ON_ANCIENT_CITY_EXPLORER_MAPS = forgeStructureTag("worldgen/structure");
        TagKey<Structure> ON_BASTION_REMNANT_EXPLORER_MAPS = forgeStructureTag("worldgen/structure");
        TagKey<Structure> ON_DESERT_PYRAMID_EXPLORER_MAPS = forgeStructureTag("worldgen/structure");
        TagKey<Structure> ON_END_CITY_EXPLORER_MAPS = forgeStructureTag("worldgen/structure");
        TagKey<Structure> ON_IGLOO_EXPLORER_MAPS = forgeStructureTag("worldgen/structure");
        TagKey<Structure> ON_JUNGLE_TEMPLE_EXPLORER_MAPS = forgeStructureTag("worldgen/structure");
        TagKey<Structure> ON_MINESHAFT_EXPLORER_MAPS = forgeStructureTag("worldgen/structure");
        TagKey<Structure> ON_NETHER_FORTRESS_EXPLORER_MAPS = forgeStructureTag("worldgen/structure");
        TagKey<Structure> ON_NETHER_FOSSIL_EXPLORER_MAPS = forgeStructureTag("worldgen/structure");
        TagKey<Structure> ON_NETHER_RUINED_PORTAL_EXPLORER_MAPS = forgeStructureTag("worldgen/structure");
        TagKey<Structure> ON_OCEAN_RUIN_EXPLORER_MAPS = forgeStructureTag("worldgen/structure");
        TagKey<Structure> ON_OVERWORLD_RUINED_PORTAL_EXPLORER_MAPS = forgeStructureTag("worldgen/structure");
        TagKey<Structure> ON_PILLAGER_OUTPOST_EXPLORER_MAPS = forgeStructureTag("worldgen/structure");
        TagKey<Structure> ON_SHIPWRECK_EXPLORER_MAPS = forgeStructureTag("worldgen/structure");
        TagKey<Structure> ON_STRONGHOLD_EXPLORER_MAPS = forgeStructureTag("worldgen/structure");
        TagKey<Structure> ON_SWAMP_HUT_EXPLORER_MAPS = forgeStructureTag("worldgen/structure");
        TagKey<Structure> ON_VILLAGE_EXPLORER_MAPS = forgeStructureTag("worldgen/structure");
    }

    private static TagKey<Block> forgeBlockTag(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("forge", path));
    }
    private static TagKey<Item> forgeItemTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", path));
    }
    private static TagKey<Structure> forgeStructureTag(String path) {
        return TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation("forge", path));
    }
}