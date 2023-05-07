package net.warrentode.recipebooktest.datagen.loot;

import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.warrentode.recipebooktest.block.ModBlocks;
import org.jetbrains.annotations.NotNull;

public class ModBlockLootTables extends BlockLoot {
    @Override
    protected void addTables() {
        this.dropSelf(ModBlocks.COINPRESSBLOCK.get());
        this.dropSelf(ModBlocks.POT_OF_GOLD.get());
        
        this.dropSelf(ModBlocks.COPPER_COIN_BAG.get());
        this.dropSelf(ModBlocks.IRON_COIN_BAG.get());
        this.dropSelf(ModBlocks.GOLD_COIN_BAG.get());
        this.dropSelf(ModBlocks.NETHER_GOLD_COIN_BAG.get());
        this.dropSelf(ModBlocks.NETHERITE_COIN_BAG.get());
        
        this.dropSelf(ModBlocks.EMERALD_QUARTER_BANK_NOTE_BAG.get());
        this.dropSelf(ModBlocks.EMERALD_HALF_BANK_NOTE_BAG.get());
        this.dropSelf(ModBlocks.EMERALD_BANK_NOTE_BAG.get());
    }
    
    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}