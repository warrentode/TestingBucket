package net.warrentode.recipebooktest.datagen.tags;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.warrentode.recipebooktest.block.ModBlocks;
import net.warrentode.recipebooktest.util.tags.ForgeTags;
import net.warrentode.recipebooktest.util.tags.ModTags;
import org.jetbrains.annotations.Nullable;

public class BlockTags extends BlockTagsProvider {
    public BlockTags(DataGenerator pGenerator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, modId, existingFileHelper);
    }
    @Override
    protected void addTags() {
        this.registerModTags();
        this.registerMinecraftTags();
        this.registerForgeTags();
        this.registerBlockMineables();
    }
    protected void registerBlockMineables() {
        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.POT_OF_GOLD.get()
        );
    }
    protected void registerMinecraftTags() {
        tag(net.minecraft.tags.BlockTags.NEEDS_STONE_TOOL).add(
                ModBlocks.POT_OF_GOLD.get()
        );
        tag(net.minecraft.tags.BlockTags.NEEDS_STONE_TOOL).add(
                ModBlocks.POT_OF_GOLD.get()
        );
    }
    protected void registerForgeTags() {
        tag(ForgeTags.Blocks.STORAGE_BLOCKS)
                .addTag(ForgeTags.Blocks.CURRENCY_BLOCKS);
        tag(ForgeTags.Blocks.CURRENCY_BLOCKS)
                .addTag(ModTags.Blocks.CURRENCY_BLOCKS);
    }
    protected void registerModTags() {
        tag(ModTags.Blocks.STORAGE_BLOCKS)
                .addTag(ModTags.Blocks.CURRENCY_BLOCKS);
        tag(ModTags.Blocks.CURRENCY_BLOCKS).add(
                ModBlocks.COPPER_COIN_BAG.get(),
                ModBlocks.IRON_COIN_BAG.get(),
                ModBlocks.GOLD_COIN_BAG.get(),
                ModBlocks.NETHERITE_COIN_BAG.get(),
                ModBlocks.NETHERITE_COIN_BAG.get(),
                ModBlocks.EMERALD_QUARTER_BANK_NOTE_BAG.get(),
                ModBlocks.EMERALD_HALF_BANK_NOTE_BAG.get(),
                ModBlocks.EMERALD_BANK_NOTE_BAG.get(),
                ModBlocks.POT_OF_GOLD.get()
        );
        tag(ModTags.Blocks.JOB_BLOCKS).add(
                ModBlocks.COINPRESSBLOCK.get(),
                ModBlocks.POT_OF_GOLD.get()
        );
    }
}