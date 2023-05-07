package net.warrentode.recipebooktest.datagen.tags;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.warrentode.recipebooktest.util.tags.ForgeTags;
import net.warrentode.recipebooktest.util.tags.ModTags;
import org.jetbrains.annotations.Nullable;

public class StructureTags extends StructureTagsProvider {
    public StructureTags(DataGenerator pGenerator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, modId, existingFileHelper);
    }
    @Override
    protected void addTags() {
        this.registerModTags();
        this.registerForgeTags();
    }
    
    private void registerModTags() {
        tag(ModTags.StructureTags.ON_ANCIENT_CITY_EXPLORER_MAPS);
        tag(ModTags.StructureTags.ON_BASTION_REMNANT_EXPLORER_MAPS);
        tag(ModTags.StructureTags.ON_DESERT_PYRAMID_EXPLORER_MAPS);
        tag(ModTags.StructureTags.ON_END_CITY_EXPLORER_MAPS);
        tag(ModTags.StructureTags.ON_IGLOO_EXPLORER_MAPS);
        tag(ModTags.StructureTags.ON_JUNGLE_TEMPLE_EXPLORER_MAPS);
        tag(ModTags.StructureTags.ON_MINESHAFT_EXPLORER_MAPS);
        tag(ModTags.StructureTags.ON_NETHER_FORTRESS_EXPLORER_MAPS);
        tag(ModTags.StructureTags.ON_NETHER_FOSSIL_EXPLORER_MAPS);
        tag(ModTags.StructureTags.ON_NETHER_RUINED_PORTAL_EXPLORER_MAPS);
        tag(ModTags.StructureTags.ON_OCEAN_RUIN_EXPLORER_MAPS);
        tag(ModTags.StructureTags.ON_OVERWORLD_RUINED_PORTAL_EXPLORER_MAPS);
        tag(ModTags.StructureTags.ON_PILLAGER_OUTPOST_EXPLORER_MAPS);
        tag(ModTags.StructureTags.ON_SHIPWRECK_EXPLORER_MAPS);
        tag(ModTags.StructureTags.ON_STRONGHOLD_EXPLORER_MAPS);
        tag(ModTags.StructureTags.ON_SWAMP_HUT_EXPLORER_MAPS);
        tag(ModTags.StructureTags.ON_VILLAGE_EXPLORER_MAPS);
    }
    private void registerForgeTags() {
        tag(ForgeTags.StructureTags.ON_ANCIENT_CITY_EXPLORER_MAPS);
        tag(ForgeTags.StructureTags.ON_BASTION_REMNANT_EXPLORER_MAPS);
        tag(ForgeTags.StructureTags.ON_DESERT_PYRAMID_EXPLORER_MAPS);
        tag(ForgeTags.StructureTags.ON_END_CITY_EXPLORER_MAPS);
        tag(ForgeTags.StructureTags.ON_IGLOO_EXPLORER_MAPS);
        tag(ForgeTags.StructureTags.ON_JUNGLE_TEMPLE_EXPLORER_MAPS);
        tag(ForgeTags.StructureTags.ON_MINESHAFT_EXPLORER_MAPS);
        tag(ForgeTags.StructureTags.ON_NETHER_FORTRESS_EXPLORER_MAPS);
        tag(ForgeTags.StructureTags.ON_NETHER_FOSSIL_EXPLORER_MAPS);
        tag(ForgeTags.StructureTags.ON_NETHER_RUINED_PORTAL_EXPLORER_MAPS);
        tag(ForgeTags.StructureTags.ON_OCEAN_RUIN_EXPLORER_MAPS);
        tag(ForgeTags.StructureTags.ON_OVERWORLD_RUINED_PORTAL_EXPLORER_MAPS);
        tag(ForgeTags.StructureTags.ON_PILLAGER_OUTPOST_EXPLORER_MAPS);
        tag(ForgeTags.StructureTags.ON_SHIPWRECK_EXPLORER_MAPS);
        tag(ForgeTags.StructureTags.ON_STRONGHOLD_EXPLORER_MAPS);
        tag(ForgeTags.StructureTags.ON_SWAMP_HUT_EXPLORER_MAPS);
        tag(ForgeTags.StructureTags.ON_VILLAGE_EXPLORER_MAPS);
    }
}