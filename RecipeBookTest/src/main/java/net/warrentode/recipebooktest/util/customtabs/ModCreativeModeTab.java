package net.warrentode.recipebooktest.util.customtabs;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import net.warrentode.recipebooktest.block.ModBlocks;
import net.warrentode.recipebooktest.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class ModCreativeModeTab extends CreativeModeTab {
    private static ItemSorter itemSorter = new ItemSorter();
    public ModCreativeModeTab(String label) {
        super(label);
    }
    public @NotNull ItemStack makeIcon() {
        return getIconItem();
    }

    public static final CreativeModeTab RECIPEBOOKTESTTAB = new CreativeModeTab("recipebooktesttab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.COPPER_COIN.get());
        }
        @Override
        public void fillItemList(final @NotNull NonNullList<ItemStack> items) {
            items.sort(itemSorter);
            super.fillItemList(items);
        }
    };
    public static void preInit() {
        for (RegistryObject<Item> registryObject : ModItems.ITEMS.getEntries()) {
            ItemStack itemStack = registryObject.get().asItem().getDefaultInstance();
            itemSorter.getItemPriority(itemStack);
        }
        for (RegistryObject<Item> registryObject : ModBlocks.ITEMS.getEntries()) {
            ItemStack itemStack = registryObject.get().asItem().getDefaultInstance();
            itemSorter.getItemPriority(itemStack);
        }
    }
}