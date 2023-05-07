package net.warrentode.recipebooktest.datagen;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.warrentode.recipebooktest.RecipeBookTest;
import static net.warrentode.recipebooktest.RecipeBookTest.MODID;
import net.warrentode.recipebooktest.block.ModBlocks;
import net.warrentode.recipebooktest.item.ModItems;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Advancements extends AdvancementProvider {
    private final Path PATH;
    
    public Advancements(DataGenerator generatorIn, ExistingFileHelper fileHelperIn) {
        super(generatorIn, fileHelperIn);
        PATH = generatorIn.getOutputFolder();
    }
    
    @Override
    public void run(CachedOutput cache) {
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (advancement) -> {
            if (!set.add(advancement.getId())) {
                throw new IllegalStateException("Duplicate advancement " + advancement.getId());
            }
            else {
                Path path1 = getPath(PATH, advancement);
                try {
                    DataProvider.saveStable(cache, advancement.deconstruct().serializeToJson(), path1);
                }
                catch (IOException ioException) {
                    RecipeBookTest.LOGGER.error("Couldn't save advancement {}", path1, ioException);
                }
            }
        };
        
        new TodeCoinsAdvancements().accept(consumer);
    }
    
    private static Path getPath(Path pathIn, Advancement advancementIn) {
        return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    }
    
    public static class TodeCoinsAdvancements implements Consumer<Consumer<Advancement>> {
        public void accept(Consumer<Advancement> consumer) {
            Advancement todeCoins = Advancement.Builder.advancement().display(ModItems.GOLD_COIN.get(),
                    Component.translatable("advancement.root"), Component.translatable("advancement.root.desc"),
                    new ResourceLocation("minecraft:textures/block/emerald_block.png"),
                    FrameType.TASK, false, false, false).addCriterion("trade",
                    InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] {})).save(consumer, getPath("main/root"));
            
            // Minting Coins
            Advancement mintingCoins = getAdvancement(todeCoins, ModItems.GOLD_COIN.get(),
                    "mintingCoins", FrameType.TASK, true, true, false)
                                            .addCriterion("craft_coin_press",InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CURRENCY_STAMP.get()))
                                            .addCriterion("craft_currency_stamp",InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CURRENCY_STAMP.get()))
                                            .addCriterion("craft_copper_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COPPER_COIN.get()))
                                            .addCriterion("craft_iron_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.IRON_COIN.get()))
                                            .addCriterion("craft_gold_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GOLD_COIN.get()))
                                            .addCriterion("craft_netherite_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.NETHERITE_COIN.get()))
                                            .requirements(RequirementsStrategy.OR).save(consumer, getPath("main/minting_coins"));
            
            // Lucky Pennies
            Advancement luckyPenny = getAdvancement(todeCoins, ModItems.LUCKY_COIN.get(),
                    "luckyPenny", FrameType.TASK, true, true, false)
                                            .addCriterion("craft_lucky_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LUCKY_COIN.get()))
                                            .requirements(RequirementsStrategy.OR).save(consumer, getPath("main/lucky_penny"));
            // Lucky Clover
            Advancement luckyClover = getAdvancement(todeCoins, ModItems.FOUR_LEAF_CLOVER.get(),
                    "luckyClover", FrameType.TASK, true, true, true)
                                             .addCriterion("find_four_leaf_clover", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.FOUR_LEAF_CLOVER.get()))
                                             .requirements(RequirementsStrategy.OR).save(consumer, getPath("main/lucky_clover"));
            
            // Rainbow's End
            Advancement rainbowEnd = getAdvancement(todeCoins, ModBlocks.POT_OF_GOLD.get(),
                    "rainbowEnd", FrameType.TASK, true, true, false)
                                             .addCriterion("craft_pot_of_gold", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.POT_OF_GOLD.get()))
                                             .requirements(RequirementsStrategy.OR).save(consumer, getPath("main/rainbows_end"));
            
            // Nether Dough
            Advancement netherDough = getAdvancement(todeCoins, ModItems.NETHER_GOLD_COIN.get(),
                    "netherDough", FrameType.TASK, true, true, false)
                                              .addCriterion("craft_nether_currency_stamp", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.NETHER_CURRENCY_STAMP.get()))
                                              .addCriterion("craft_nether_gold_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.NETHER_GOLD_COIN.get()))
                                              .requirements(RequirementsStrategy.OR).save(consumer, getPath("main/nether_dough"));
            
            // Printing Notes
            Advancement printingMoney = getAdvancement(todeCoins, ModItems.EMERALD_BANK_NOTE.get(),
                    "printingMoney", FrameType.TASK, true, true, false)
                                            .addCriterion("craft_emerald_quarter_bank_note", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.EMERALD_QUARTER_BANK_NOTE.get()))
                                            .addCriterion("craft_emerald_half_bank_note", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.EMERALD_HALF_BANK_NOTE.get()))
                                            .addCriterion("craft_emerald_bank_note", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.EMERALD_BANK_NOTE.get()))
                                            .requirements(RequirementsStrategy.OR).save(consumer, getPath("main/printing_money"));
            
            // Money to Burn
            Advancement baggingDough = getAdvancement(todeCoins, ModBlocks.GOLD_COIN_BAG.get(),
                    "baggingDough", FrameType.TASK, true, true, false)
                                               .addCriterion("craft_copper_coin_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.COPPER_COIN_BAG.get()))
                                               .addCriterion("craft_iron_coin_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.IRON_COIN_BAG.get()))
                                               .addCriterion("craft_gold_coin_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.GOLD_COIN_BAG.get()))
                                               .addCriterion("craft_netherite_coin_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.NETHERITE_COIN_BAG.get()))
                                               .addCriterion("craft_emerald_quarter_bank_note_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.EMERALD_QUARTER_BANK_NOTE_BAG.get()))
                                               .addCriterion("craft_emerald_half_bank_note_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.EMERALD_HALF_BANK_NOTE_BAG.get()))
                                               .addCriterion("craft_emerald_bank_note_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.EMERALD_BANK_NOTE_BAG.get()))
                                               .addCriterion("craft_nether_gold_coin_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.NETHER_GOLD_COIN_BAG.get()))
                                               .requirements(RequirementsStrategy.OR).save(consumer, getPath("main/bagging_dough"));
            
            // Lucky Armor Set
            Advancement luckyArmor = getAdvancement(todeCoins, ModItems.LUCKY_SHIRT.get(),
                    "luckyArmor", FrameType.TASK, true, true, false)
                                             .addCriterion("craft_lucky_shirt", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LUCKY_SHIRT.get()))
                                             .addCriterion("craft_lucky_pants", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LUCKY_PANTS.get()))
                                             .addCriterion("craft_lucky_hat", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LUCKY_HAT.get()))
                                             .addCriterion("craft_lucky_boots", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LUCKY_BOOTS.get()))
                                               .requirements(RequirementsStrategy.OR).save(consumer, getPath("main/lucky_armor"));
            
            // Textile Engineering
            Advancement textileEngineering = getAdvancement(todeCoins, ModItems.LUCKY_SHIRT.get(),
                    "textileEngineering", FrameType.TASK, true, true, false)
                                                     .addCriterion("craft_emerald_fiber", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.EMERALD_FIBER.get()))
                                                     .addCriterion("craft_coarse_wove_paper", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.EMERALD_COARSE_WOVE_PAPER.get()))
                                                     .addCriterion("craft_emerald_wove_paper", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.EMERALD_WOVE_PAPER.get()))
                                                     .addCriterion("craft_emerald_smooth_paper", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.EMERALD_SMOOTH_WOVE_PAPER.get()))
                                                     .addCriterion("craft_lucky_fiber", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LUCKY_FIBER.get()))
                                                     .addCriterion("craft_lucky_thread", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LUCKY_THREAD.get()))
                                                     .addCriterion("craft_lucky_fabric", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LUCKY_FABRIC.get()))
                                                     .requirements(RequirementsStrategy.OR).save(consumer, getPath("main/textile_engineering"));
        }
        
        protected static Advancement.Builder getAdvancement(Advancement parent, ItemLike display, String name, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
            return Advancement.Builder.advancement()
                           .parent(parent).display(display,
                            Component.translatable("advancement." + name),
                            Component.translatable("advancement." + name + ".desc"),
                            null, frame, showToast, announceToChat, hidden);
        }
        
        private String getPath(String id) {
            return MODID + ":" + id;
        }
    }
}