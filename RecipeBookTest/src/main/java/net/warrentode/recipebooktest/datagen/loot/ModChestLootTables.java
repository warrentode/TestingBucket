package net.warrentode.recipebooktest.datagen.loot;

import java.util.function.BiConsumer;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.data.loot.ChestLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.warrentode.recipebooktest.block.ModBlocks;
import net.warrentode.recipebooktest.item.ModItems;


import static net.warrentode.recipebooktest.RecipeBookTest.MODID;

public class ModChestLootTables extends ChestLoot {
    public static final LootItemCondition.Builder IN_NETHER_WASTES = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.NETHER_WASTES));
    public static final LootItemCondition.Builder IN_WARPED_FOREST = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.WARPED_FOREST));
    public static final LootItemCondition.Builder IN_CRIMSON_FOREST = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.CRIMSON_FOREST));
    public static final LootItemCondition.Builder IN_BASALT_DELTAS = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.BASALT_DELTAS));
    public static final LootItemCondition.Builder IN_SOUL_SAND_VALLEY = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.SOUL_SAND_VALLEY));
    
    private static final ResourceLocation RECIPEBOOKTEST_CHEST_LOOT = new ResourceLocation(MODID, "chests/recipebooktest/recipebooktest_chest");
    private static final ResourceLocation RECIPEBOOKTEST_COPPER_COIN_LOOT = new ResourceLocation(MODID, "chests/recipebooktest/recipebooktest_copper_coin_loot");
    private static final ResourceLocation RECIPEBOOKTEST_IRON_COIN_LOOT = new ResourceLocation(MODID, "chests/recipebooktest/recipebooktest_iron_coin_loot");
    private static final ResourceLocation RECIPEBOOKTEST_GOLD_COIN_LOOT = new ResourceLocation(MODID, "chests/recipebooktest/recipebooktest_gold_coin_loot");
    private static final ResourceLocation RECIPEBOOKTEST_LUCKY_COIN_LOOT = new ResourceLocation(MODID, "chests/recipebooktest/recipebooktest_lucky_coin_loot");
    private static final ResourceLocation RECIPEBOOKTEST_NETHERITE_COIN_LOOT = new ResourceLocation(MODID, "chests/recipebooktest/recipebooktest_netherite_coin_loot");
    private static final ResourceLocation RECIPEBOOKTEST_NETHER_GOLD_COIN_LOOT = new ResourceLocation(MODID, "chests/recipebooktest/recipebooktest_nether_gold_coin_loot");
    private static final ResourceLocation RECIPEBOOKTEST_EMERALD_QUARTER_BANK_NOTE_LOOT = new ResourceLocation(MODID, "chests/recipebooktest/recipebooktest_emerald_quarter_bank_note_loot");
    private static final ResourceLocation RECIPEBOOKTEST_EMERALD_HALF_BANK_NOTE_LOOT = new ResourceLocation(MODID, "chests/recipebooktest/recipebooktest_emerald_half_bank_note_loot");
    private static final ResourceLocation RECIPEBOOKTEST_EMERALD_BANK_NOTE_LOOT = new ResourceLocation(MODID, "chests/recipebooktest/recipebooktest_emerald_bank_note_loot");
    
    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(RECIPEBOOKTEST_CHEST_LOOT,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                          .setRolls(ConstantValue.exactly(1.0F))
                                          .setBonusRolls(ConstantValue.exactly(1.0F))
                                          .add(LootTableReference.lootTableReference(RECIPEBOOKTEST_COPPER_COIN_LOOT)
                                                       .setWeight(7).setQuality(0))
                                          .add(LootTableReference.lootTableReference(RECIPEBOOKTEST_IRON_COIN_LOOT)
                                                       .setWeight(6).setQuality(1))
                                          .add(LootTableReference.lootTableReference(RECIPEBOOKTEST_GOLD_COIN_LOOT)
                                                       .setWeight(5).setQuality(2))
                                          .add(LootTableReference.lootTableReference(RECIPEBOOKTEST_NETHER_GOLD_COIN_LOOT)
                                                       .when(IN_NETHER_WASTES.or(IN_CRIMSON_FOREST).or(IN_WARPED_FOREST).or(IN_BASALT_DELTAS).or(IN_SOUL_SAND_VALLEY))
                                                       .setWeight(5).setQuality(2))
                                          .add(LootTableReference.lootTableReference(RECIPEBOOKTEST_EMERALD_QUARTER_BANK_NOTE_LOOT)
                                                       .setWeight(4).setQuality(3))
                                          .add(LootTableReference.lootTableReference(RECIPEBOOKTEST_NETHERITE_COIN_LOOT)
                                                       .setWeight(4).setQuality(4))
                                          .add(LootTableReference.lootTableReference(RECIPEBOOKTEST_EMERALD_HALF_BANK_NOTE_LOOT)
                                                       .setWeight(3).setQuality(4))
                                          .add(LootTableReference.lootTableReference(RECIPEBOOKTEST_EMERALD_BANK_NOTE_LOOT)
                                                       .setWeight(2).setQuality(5))
                                          .add(LootTableReference.lootTableReference(RECIPEBOOKTEST_LUCKY_COIN_LOOT)
                                                       .setWeight(1).setQuality(6))
                        )
        );
        consumer.accept(RECIPEBOOKTEST_COPPER_COIN_LOOT,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                          .setRolls(UniformGenerator.between(1.0F, 7.0F))
                                          .setBonusRolls(ConstantValue.exactly(1.0F))
                                          .add(LootItem.lootTableItem(ModItems.COPPER_COIN.get()).setWeight(1)
                                                       .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 7.0F))))
                                          .add(LootItem.lootTableItem(ModBlocks.COPPER_COIN_BAG.get()))
                )
        );
        consumer.accept(RECIPEBOOKTEST_IRON_COIN_LOOT,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                          .setRolls(UniformGenerator.between(1.0F, 6.0F))
                                          .setBonusRolls(ConstantValue.exactly(1.0F))
                                          .add(LootItem.lootTableItem(ModItems.IRON_COIN.get()).setWeight(1)
                                                       .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
                                          .add(LootItem.lootTableItem(ModBlocks.IRON_COIN_BAG.get()))
                        )
        );
        consumer.accept(RECIPEBOOKTEST_GOLD_COIN_LOOT,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                          .setRolls(UniformGenerator.between(1.0F, 5.0F))
                                          .setBonusRolls(ConstantValue.exactly(1.0F))
                                          .add(LootItem.lootTableItem(ModItems.GOLD_COIN.get()).setWeight(1)
                                                       .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                                          .add(LootItem.lootTableItem(ModBlocks.GOLD_COIN_BAG.get()))
                        )
        );
        consumer.accept(RECIPEBOOKTEST_NETHER_GOLD_COIN_LOOT,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                          .setRolls(UniformGenerator.between(1.0F, 5.0F))
                                          .setBonusRolls(ConstantValue.exactly(1.0F))
                                          .add(LootItem.lootTableItem(ModItems.NETHER_GOLD_COIN.get()).setWeight(1)
                                                       .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                                          .add(LootItem.lootTableItem(ModBlocks.NETHER_GOLD_COIN_BAG.get()))
                        )
        );
        consumer.accept(RECIPEBOOKTEST_EMERALD_QUARTER_BANK_NOTE_LOOT,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                          .setRolls(UniformGenerator.between(1.0F, 4.0F))
                                          .setBonusRolls(ConstantValue.exactly(1.0F))
                                          .add(LootItem.lootTableItem(ModItems.EMERALD_QUARTER_BANK_NOTE.get()).setWeight(1)
                                                       .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                          .add(LootItem.lootTableItem(ModBlocks.EMERALD_QUARTER_BANK_NOTE_BAG.get()))
                        )
        );
        consumer.accept(RECIPEBOOKTEST_NETHERITE_COIN_LOOT,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                          .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                          .setBonusRolls(ConstantValue.exactly(1.0F))
                                          .add(LootItem.lootTableItem(ModItems.NETHER_GOLD_COIN.get()).setWeight(1)
                                                       .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                          .add(LootItem.lootTableItem(ModBlocks.NETHER_GOLD_COIN_BAG.get()))
                        )
        );
        consumer.accept(RECIPEBOOKTEST_EMERALD_HALF_BANK_NOTE_LOOT,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                          .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                          .setBonusRolls(ConstantValue.exactly(1.0F))
                                          .add(LootItem.lootTableItem(ModItems.EMERALD_HALF_BANK_NOTE.get()).setWeight(1)
                                                       .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                          .add(LootItem.lootTableItem(ModBlocks.EMERALD_HALF_BANK_NOTE_BAG.get()))
                        )
        );
        consumer.accept(RECIPEBOOKTEST_EMERALD_BANK_NOTE_LOOT,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                          .setRolls(UniformGenerator.between(1.0F, 2.0F))
                                          .setBonusRolls(ConstantValue.exactly(1.0F))
                                          .add(LootItem.lootTableItem(ModItems.EMERALD_BANK_NOTE.get()).setWeight(1)
                                                       .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                          .add(LootItem.lootTableItem(ModBlocks.EMERALD_BANK_NOTE_BAG.get()))
                        )
        );
        consumer.accept(RECIPEBOOKTEST_LUCKY_COIN_LOOT,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                          .setRolls(ConstantValue.exactly(1.0F))
                                          .add(LootItem.lootTableItem(ModItems.LUCKY_COIN.get()))
                        )
        );
    }
}