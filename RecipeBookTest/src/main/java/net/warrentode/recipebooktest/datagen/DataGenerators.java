package net.warrentode.recipebooktest.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import static net.warrentode.recipebooktest.RecipeBookTest.MODID;
import net.warrentode.recipebooktest.datagen.recipes.Recipes;
import net.warrentode.recipebooktest.datagen.tags.BlockTags;
import net.warrentode.recipebooktest.datagen.tags.ItemTags;
import net.warrentode.recipebooktest.datagen.tags.PoiTypeTags;
import net.warrentode.recipebooktest.datagen.tags.StructureTags;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    private static final String pathName = MODID + ":" + "trades";
    @SubscribeEvent
    public static void gatherData(@NotNull GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        DataGenerator.PathProvider pathProvider = event.getGenerator().createPathProvider(DataGenerator.Target.DATA_PACK, pathName);

        BlockTags blockTags = new BlockTags(generator, MODID, helper);
        generator.addProvider(event.includeServer(), blockTags);

        ItemTags itemTags = new ItemTags(generator, blockTags, MODID, helper);
        generator.addProvider(event.includeServer(), itemTags);

        PoiTypeTags poiTypeTags = new PoiTypeTags(generator, MODID, helper);
        generator.addProvider(event.includeServer(), poiTypeTags);

        StructureTags structureTags = new StructureTags(generator, MODID, helper);
        generator.addProvider(event.includeServer(), structureTags);

        generator.addProvider(event.includeServer(), new Recipes(generator));
        generator.addProvider(event.includeServer(), new Advancements(generator, helper));
        generator.addProvider(event.validate(), new ModLootTableProvider(generator));
    }
}