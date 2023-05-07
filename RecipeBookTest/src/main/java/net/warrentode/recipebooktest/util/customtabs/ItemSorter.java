package net.warrentode.recipebooktest.util.customtabs;

import java.util.Comparator;
import java.util.HashMap;
import java.util.function.ToIntFunction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import net.warrentode.recipebooktest.block.ModBlocks;
import net.warrentode.recipebooktest.item.ModItems;

public class ItemSorter implements Comparator<ItemStack> {
    public static HashMap<ItemStack, ItemPriority> cache = new HashMap<>();
    public ItemPriority getItemPriority(ItemStack itemStack) {
        ItemPriority priorityName = cache.get(itemStack);
        if (priorityName != null) {
            return priorityName;
        }
        priorityName = ItemPriority.NONE;
        for (RegistryObject<Item> registryObject : ModItems.orderedItems()) {
            String typeName = ItemPriority.getTypeName(itemStack.getItem().getName(itemStack).toString());
            int typeID = priorityName.ordinal();
            if (typeName.endsWith("clover")) {
                priorityName = ItemPriority.CLOVER;
                break;
            }
            else if (typeName.endsWith("stamp")) {
                priorityName = ItemPriority.STAMPS;
                break;
            }
            else if (typeName.endsWith("nugget")) {
                priorityName = ItemPriority.NUGGETS;
                break;
            }
            else if (typeName.endsWith("coin")) {
                priorityName = ItemPriority.COINS;
                break;
            }
            else if (typeName.endsWith("note")) {
                priorityName = ItemPriority.NOTES;
                break;
            }
            else if (typeName.endsWith("bag")) {
                priorityName = ItemPriority.BAGS;
                break;
            }
            else if (typeName.endsWith("fiber")) {
                priorityName = ItemPriority.TEXTILES_FIBER;
                break;
            }
            else if (typeName.endsWith("thread")) {
                priorityName = ItemPriority.TEXTILES_THREAD;
                break;
            }
            else if (typeName.endsWith("paper")) {
                priorityName = ItemPriority.TEXTILES_PAPER;
                break;
            }
            else if (typeName.endsWith("fabric")) {
                priorityName = ItemPriority.TEXTILES_FABRIC;
                break;
            }
            else if (typeName.startsWith("lucky")) {
                priorityName = ItemPriority.LUCKY;
                break;
            }
            else {
                break;
            }
        }
        for (RegistryObject<Item> registryObject : ModBlocks.orderedItems()) {
            String typeName = ItemPriority.getTypeName(itemStack.getItem().getName(itemStack).toString());
            int typeID = priorityName.ordinal();
            if (typeName.endsWith("clover")) {
                priorityName = ItemPriority.CLOVER;
                break;
            }
            else if (typeName.endsWith("stamp")) {
                priorityName = ItemPriority.STAMPS;
                break;
            }
            else if (typeName.endsWith("nugget")) {
                priorityName = ItemPriority.NUGGETS;
                break;
            }
            else if (typeName.endsWith("coin")) {
                priorityName = ItemPriority.COINS;
                break;
            }
            else if (typeName.endsWith("note")) {
                priorityName = ItemPriority.NOTES;
                break;
            }
            else if (typeName.endsWith("bag")) {
                priorityName = ItemPriority.BAGS;
                break;
            }
            else if (typeName.endsWith("fiber")) {
                priorityName = ItemPriority.TEXTILES_FIBER;
                break;
            }
            else if (typeName.endsWith("thread")) {
                priorityName = ItemPriority.TEXTILES_THREAD;
                break;
            }
            else if (typeName.endsWith("paper")) {
                priorityName = ItemPriority.TEXTILES_PAPER;
                break;
            }
            else if (typeName.endsWith("fabric")) {
                priorityName = ItemPriority.TEXTILES_FABRIC;
                break;
            }
            else if (typeName.startsWith("lucky")) {
                priorityName = ItemPriority.LUCKY;
                break;
            }
            else {
                priorityName = ItemPriority.NONE;
                break;
            }
        }
        cache.put(itemStack, priorityName);
        return priorityName;
    }
    @Override
    public int compare(ItemStack stack1, ItemStack stack2) {
        int ordinal1 = getItemPriority(stack1).ordinal(), ordinal2 = getItemPriority(stack2).ordinal();
        ItemPriority priority1 = getItemPriority(stack1), priority2 = getItemPriority(stack2);

        if (ordinal1 == ordinal2) {
            String displayName1 = String.valueOf(stack1.getDisplayName());
            String displayName2 = String.valueOf(stack2.getDisplayName());
            return displayName1.compareToIgnoreCase(displayName2);
        }
        else {
            return priority1.compareTo(priority2);
        }
    }
    enum ItemPriority implements ToIntFunction<String> {
        CLOVER,
        STAMPS,
        NUGGETS,
        COINS,
        NOTES,
        BAGS,
        TEXTILES_FIBER,
        TEXTILES_THREAD,
        TEXTILES_PAPER,
        TEXTILES_FABRIC,
        LUCKY,
        NONE;
        public static String getTypeName(String typeName) {
            return typeName;
        }
        @Override
        public int applyAsInt(String value) {
            return ordinal();
        }
    }
}