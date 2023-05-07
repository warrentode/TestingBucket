package net.warrentode.recipebooktest.datagen.recipes.recipe;

import java.util.function.Consumer;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import static net.warrentode.recipebooktest.RecipeBookTest.MODID;
import net.warrentode.recipebooktest.block.ModBlocks;
import net.warrentode.recipebooktest.item.ModItems;
import net.warrentode.recipebooktest.util.tags.ModTags;

public class CraftingRecipes {
    public static void register(Consumer<FinishedRecipe> consumer) {
        armorRecipes(consumer);
        currencyStampsRecipes(consumer);
        jobBlockRecipes(consumer);
        nuggetRecipes(consumer);
        storageBlockRecipes(consumer);
        textileRecipes(consumer);
    }
    
    private static void armorRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModItems.LUCKY_HAT.get(), 1)
                .pattern("CCC")
                .pattern("C C")
                .define('C', ModItems.LUCKY_FABRIC.get())
                .unlockedBy("has_lucky_fabric", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LUCKY_FABRIC.get()))
                .save(consumer, new ResourceLocation(MODID, "armor/lucky_fabric/lucky_hat"));
        ShapedRecipeBuilder.shaped(ModItems.LUCKY_SHIRT.get(), 1)
                .pattern("C C")
                .pattern("CCC")
                .pattern("CCC")
                .define('C', ModItems.LUCKY_FABRIC.get())
                .unlockedBy("has_lucky_fabric", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LUCKY_FABRIC.get()))
                .save(consumer, new ResourceLocation(MODID, "armor/lucky_fabric/lucky_shirt"));
        ShapedRecipeBuilder.shaped(ModItems.LUCKY_BOOTS.get(), 1)
                .pattern("C C")
                .pattern("C C")
                .define('C', ModItems.LUCKY_FABRIC.get())
                .unlockedBy("has_lucky_fabric", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LUCKY_FABRIC.get()))
                .save(consumer, new ResourceLocation(MODID, "armor/lucky_fabric/lucky_boots"));
        ShapedRecipeBuilder.shaped(ModItems.LUCKY_PANTS.get(), 1)
                .pattern("CCC")
                .pattern("C C")
                .pattern("C C")
                .define('C', ModItems.LUCKY_FABRIC.get())
                .unlockedBy("has_lucky_fabric", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LUCKY_FABRIC.get()))
                .save(consumer, new ResourceLocation(MODID, "armor/lucky_fabric/lucky_pants"));
    }
    private static void currencyStampsRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModItems.CURRENCY_STAMP.get(), 1)
                .pattern(" S ")
                .pattern(" C ")
                .pattern(" S ")
                .define('S', Items.SMOOTH_STONE_SLAB)
                .define('C', Items.COBBLESTONE)
                .unlockedBy("has_smooth_stone", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SMOOTH_STONE_SLAB))
                .save(consumer, new ResourceLocation(MODID, "currency_stamps/currency_stamp"));
        ShapedRecipeBuilder.shaped(ModItems.NETHER_CURRENCY_STAMP.get(), 1)
                .pattern(" S ")
                .pattern(" C ")
                .pattern(" S ")
                .define('S', Items.BLACKSTONE_SLAB)
                .define('C', Items.CHISELED_POLISHED_BLACKSTONE)
                .unlockedBy("has_chiseled_polished_blackstone", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CHISELED_POLISHED_BLACKSTONE))
                .save(consumer, new ResourceLocation(MODID, "currency_stamps/nether_currency_stamp"));
    }
    private static void jobBlockRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModBlocks.COINPRESSBLOCK.get(), 1)
                .pattern("III")
                .pattern("DID")
                .pattern("PPP")
                .define('I', Items.IRON_INGOT)
                .define('D', Items.POLISHED_DEEPSLATE)
                .define('P', ItemTags.PLANKS)
                .unlockedBy("has_polished_deepslate", InventoryChangeTrigger.TriggerInstance.hasItems(Items.POLISHED_DEEPSLATE))
                .save(consumer, new ResourceLocation(MODID, "job_blocks/coin_press"));
        ShapedRecipeBuilder.shaped(ModBlocks.POT_OF_GOLD.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.GOLD_COIN_BAG.get())
                .unlockedBy("has_gold_coin_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.GOLD_COIN_BAG.get()))
                .save(consumer, new ResourceLocation(MODID, "job_blocks/pot_of_gold"));
        ShapedRecipeBuilder.shaped(ModBlocks.GOLD_COIN_BAG.get(), 9)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.POT_OF_GOLD.get())
                .unlockedBy("has_pot_of_gold", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.POT_OF_GOLD.get()))
                .save(consumer, new ResourceLocation(MODID, "job_blocks/bags_of_gold_from_pot_of_gold"));
    }
    private static void nuggetRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(Items.COPPER_INGOT, 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModTags.Items.COPPER_NUGGET)
                .unlockedBy("has_copper_nugget", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COPPER_NUGGET.get()))
                .save(consumer, new ResourceLocation(MODID, "ingots/ingot_from_nuggets/copper_ingot"));
        ShapedRecipeBuilder.shaped(Items.NETHERITE_INGOT, 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModTags.Items.NETHERITE_NUGGET)
                .unlockedBy("has_netherite_nugget", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.NETHERITE_NUGGET.get()))
                .save(consumer, new ResourceLocation(MODID, "ingots/ingot_from_nuggets/netherite_ingot"));
        
        ShapedRecipeBuilder.shaped(ModItems.LUCKY_NUGGET.get(), 1)
                .pattern("###")
                .pattern(" # ")
                .pattern("###")
                .define('#', ModItems.FOUR_LEAF_CLOVER.get())
                .unlockedBy("has_four_leaf_clover", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.FOUR_LEAF_CLOVER.get()))
                .save(consumer, new ResourceLocation(MODID, "nuggets/nugget_from_other/lucky_nugget"));
        
        ShapelessRecipeBuilder.shapeless(ModItems.COPPER_NUGGET.get(), 9)
                .requires(ModTags.Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_INGOT))
                .save(consumer, new ResourceLocation(MODID, "nuggets/nuggets_from_ingots/copper_nugget"));
        ShapelessRecipeBuilder.shapeless(ModItems.NETHERITE_NUGGET.get(), 9)
                .requires(Items.NETHERITE_INGOT)
                .unlockedBy("has_netherite_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_INGOT))
                .save(consumer, new ResourceLocation(MODID, "nuggets/nuggets_from_ingots/netherite_nugget"));
        
        
        ShapelessRecipeBuilder.shapeless(ModItems.COPPER_NUGGET.get())
                .requires(ModItems.COPPER_COIN.get(), 2)
                .unlockedBy("has_copper_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COPPER_NUGGET.get()))
                .save(consumer, new ResourceLocation(MODID, "nuggets/nugget_from_coins/copper_nugget"));
        ShapelessRecipeBuilder.shapeless(Items.IRON_NUGGET)
                .requires(ModItems.IRON_COIN.get(), 2)
                .unlockedBy("has_iron_coin", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_NUGGET))
                .save(consumer, new ResourceLocation(MODID, "nuggets/nugget_from_coins/iron_nugget"));
        ShapelessRecipeBuilder.shapeless(Items.GOLD_NUGGET)
                .requires(ModItems.GOLD_COIN.get(), 2)
                .unlockedBy("has_gold_coin", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_NUGGET))
                .save(consumer, new ResourceLocation(MODID, "nuggets/nugget_from_coins/gold_nugget"));
        ShapelessRecipeBuilder.shapeless(ModItems.NETHERITE_NUGGET.get())
                .requires(ModItems.NETHERITE_COIN.get(), 2)
                .unlockedBy("has_netherite_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.NETHERITE_NUGGET.get()))
                .save(consumer, new ResourceLocation(MODID, "nuggets/nugget_from_coins/netherite_nugget"));
    }
    private static void storageBlockRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModBlocks.COPPER_COIN_BAG.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.COPPER_COIN.get())
                .unlockedBy("has_copper_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COPPER_COIN.get()))
                .save(consumer, new ResourceLocation(MODID, "storage_blocks/coins_to_bags/copper_bag"));
        ShapedRecipeBuilder.shaped(ModBlocks.IRON_COIN_BAG.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.IRON_COIN.get())
                .unlockedBy("has_iron_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.IRON_COIN.get()))
                .save(consumer, new ResourceLocation(MODID, "storage_blocks/coins_to_bags/iron_bag"));
        ShapedRecipeBuilder.shaped(ModBlocks.GOLD_COIN_BAG.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.GOLD_COIN.get())
                .unlockedBy("has_gold_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GOLD_COIN.get()))
                .save(consumer, new ResourceLocation(MODID, "storage_blocks/coins_to_bags/gold_bag"));
        ShapedRecipeBuilder.shaped(ModBlocks.NETHERITE_COIN_BAG.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.NETHERITE_COIN.get())
                .unlockedBy("has_netherite_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.NETHERITE_COIN.get()))
                .save(consumer, new ResourceLocation(MODID, "storage_blocks/coins_to_bags/netherite_coin_bag"));
        ShapedRecipeBuilder.shaped(ModBlocks.NETHER_GOLD_COIN_BAG.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.NETHER_GOLD_COIN.get())
                .unlockedBy("has_nether_gold_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.NETHER_GOLD_COIN.get()))
                .save(consumer, new ResourceLocation(MODID, "storage_blocks/coins_to_bags/nether_gold_bag"));
        ShapedRecipeBuilder.shaped(ModBlocks.EMERALD_QUARTER_BANK_NOTE_BAG.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.EMERALD_QUARTER_BANK_NOTE.get())
                .unlockedBy("has_emerald_quarter_note", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.EMERALD_QUARTER_BANK_NOTE.get()))
                .save(consumer, new ResourceLocation(MODID, "storage_blocks/bank_notes_to_bags/emerald_quarter_bank_note_bag"));
        ShapedRecipeBuilder.shaped(ModBlocks.EMERALD_HALF_BANK_NOTE_BAG.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.EMERALD_HALF_BANK_NOTE.get())
                .unlockedBy("has_emerald_half_note", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.EMERALD_HALF_BANK_NOTE.get()))
                .save(consumer, new ResourceLocation(MODID, "storage_blocks/bank_notes_to_bags/emerald_half_bank_note_bag"));
        ShapedRecipeBuilder.shaped(ModBlocks.EMERALD_BANK_NOTE_BAG.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.EMERALD_BANK_NOTE.get())
                .unlockedBy("has_emerald_note", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.EMERALD_BANK_NOTE.get()))
                .save(consumer, new ResourceLocation(MODID, "storage_blocks/bank_notes_to_bags/emerald_bank_note_bag"));
        
        
        ShapelessRecipeBuilder.shapeless(ModItems.COPPER_COIN.get(), 9)
                .requires(ModBlocks.COPPER_COIN_BAG.get())
                .unlockedBy("has_copper_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.COPPER_COIN_BAG.get()))
                .save(consumer, new ResourceLocation(MODID, "storage_blocks/bags_to_coins/copper_coins"));
        ShapelessRecipeBuilder.shapeless(ModItems.IRON_COIN.get(), 9)
                .requires(ModBlocks.IRON_COIN_BAG.get())
                .unlockedBy("has_iron_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.IRON_COIN_BAG.get()))
                .save(consumer, new ResourceLocation(MODID, "storage_blocks/bags_to_coins/iron_coins"));
        ShapelessRecipeBuilder.shapeless(ModItems.GOLD_COIN.get(), 9)
                .requires(ModBlocks.GOLD_COIN_BAG.get())
                .unlockedBy("has_gold_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.GOLD_COIN_BAG.get()))
                .save(consumer, new ResourceLocation(MODID, "storage_blocks/bags_to_coins/gold_coins"));
        ShapelessRecipeBuilder.shapeless(ModItems.NETHERITE_COIN.get(), 9)
                .requires(ModBlocks.NETHERITE_COIN_BAG.get())
                .unlockedBy("has_netherite_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.NETHERITE_COIN_BAG.get()))
                .save(consumer, new ResourceLocation(MODID, "storage_blocks/bags_to_coins/netherite_coins"));
        ShapelessRecipeBuilder.shapeless(ModItems.NETHER_GOLD_COIN.get(), 9)
                .requires(ModBlocks.NETHER_GOLD_COIN_BAG.get())
                .unlockedBy("has_nether_gold_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.NETHER_GOLD_COIN_BAG.get()))
                .save(consumer, new ResourceLocation(MODID, "storage_blocks/bags_to_coins/nether_gold_coins"));
        ShapelessRecipeBuilder.shapeless(ModItems.EMERALD_QUARTER_BANK_NOTE.get(), 9)
                .requires(ModBlocks.EMERALD_QUARTER_BANK_NOTE_BAG.get())
                .unlockedBy("has_emerald_quarter_note_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.EMERALD_QUARTER_BANK_NOTE_BAG.get()))
                .save(consumer, new ResourceLocation(MODID, "storage_blocks/bags_to_bank_notes/emerald_quarter_bank_notes"));
        ShapelessRecipeBuilder.shapeless(ModItems.EMERALD_HALF_BANK_NOTE.get(), 9)
                .requires(ModBlocks.EMERALD_HALF_BANK_NOTE_BAG.get())
                .unlockedBy("has_emerald_half_note_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.EMERALD_HALF_BANK_NOTE_BAG.get()))
                .save(consumer, new ResourceLocation(MODID, "storage_blocks/bags_to_bank_notes/emerald_half_bank_notes"));
        ShapelessRecipeBuilder.shapeless(ModItems.EMERALD_BANK_NOTE.get(), 9)
                .requires(ModBlocks.EMERALD_BANK_NOTE_BAG.get())
                .unlockedBy("has_emerald_note_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.EMERALD_BANK_NOTE_BAG.get()))
                .save(consumer, new ResourceLocation(MODID, "storage_blocks/bags_to_bank_notes/emerald_bank_notes"));
    }
    private static void textileRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModItems.EMERALD_FIBER.get(), 1)
                .pattern("###")
                .pattern("#E#")
                .pattern("###")
                .define('#', ModTags.Items.CURRENCY_FIBER)
                .define('E', Items.EMERALD)
                .unlockedBy("has_emerald", InventoryChangeTrigger.TriggerInstance.hasItems(Items.EMERALD))
                .save(consumer, new ResourceLocation(MODID, "textiles/fiber/emerald_fiber"));
        ShapedRecipeBuilder.shaped(ModItems.LUCKY_FIBER.get(), 1)
                .pattern("###")
                .pattern("#C#")
                .pattern("###")
                .define('#', ModTags.Items.CURRENCY_FIBER)
                .define('C', ModItems.LUCKY_COIN.get())
                .unlockedBy("has_lucky_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LUCKY_COIN.get()))
                .save(consumer, new ResourceLocation(MODID, "textiles/fiber/lucky_fiber"));
        
        ShapelessRecipeBuilder.shapeless(ModItems.EMERALD_SMOOTH_WOVE_PAPER.get(), 1)
                .requires(ModItems.EMERALD_FIBER.get(), 8)
                .unlockedBy("has_emerald_fiber", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.EMERALD_FIBER.get()))
                .save(consumer, new ResourceLocation(MODID, "textiles/paper/emerald_smooth_wove_paper"));
        ShapedRecipeBuilder.shaped(ModItems.EMERALD_WOVE_PAPER.get(), 1)
                .pattern("##")
                .pattern("##")
                .define('#', ModItems.EMERALD_FIBER.get())
                .unlockedBy("has_emerald_fiber", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.EMERALD_FIBER.get()))
                .save(consumer, new ResourceLocation(MODID, "textiles/paper/emerald_wove_paper"));
        ShapedRecipeBuilder.shaped(ModItems.EMERALD_COARSE_WOVE_PAPER.get(), 1)
                .pattern("##")
                .define('#', ModItems.EMERALD_FIBER.get())
                .unlockedBy("has_emerald_fiber", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.EMERALD_FIBER.get()))
                .save(consumer, new ResourceLocation(MODID, "textiles/paper/emerald_coarse_wove_paper"));
        
        ShapedRecipeBuilder.shaped(ModItems.LUCKY_FABRIC.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.LUCKY_THREAD.get())
                .unlockedBy("has_lucky_thread", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LUCKY_THREAD.get()))
                .save(consumer, new ResourceLocation(MODID, "textiles/fabric/lucky_fabric"));
        
        ShapedRecipeBuilder.shaped(ModItems.LUCKY_THREAD.get(), 1)
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.LUCKY_FIBER.get())
                .unlockedBy("has_lucky_fiber", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LUCKY_FIBER.get()))
                .save(consumer, new ResourceLocation(MODID, "textiles/thread/lucky_thread"));
    }
}