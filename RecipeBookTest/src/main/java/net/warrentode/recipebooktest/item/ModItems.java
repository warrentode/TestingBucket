package net.warrentode.recipebooktest.item;

import java.util.Collection;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import static net.warrentode.recipebooktest.RecipeBookTest.MODID;
import net.warrentode.recipebooktest.item.custom.*;
import net.warrentode.recipebooktest.util.customtabs.ModCreativeModeTab;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    
    public static final RegistryObject<Item> CURRENCY_STAMP = ITEMS.register("currency_stamp",
            ()-> new CurrencyStampItem(new Item.Properties().tab(ModCreativeModeTab.RECIPEBOOKTESTTAB).defaultDurability(64).setNoRepair()));
    public static final RegistryObject<Item> NETHER_CURRENCY_STAMP = ITEMS.register("nether_currency_stamp",
            ()-> new CurrencyStampItem(new Item.Properties().tab(ModCreativeModeTab.RECIPEBOOKTESTTAB).defaultDurability(64).setNoRepair()));
    
    public static final RegistryObject<Item> FOUR_LEAF_CLOVER = ITEMS.register("four_leaf_clover",
            ()-> new FourLeafCloverItem(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget",
            ()-> new Item(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> NETHERITE_NUGGET = ITEMS.register("netherite_nugget",
            ()-> new Item(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> LUCKY_NUGGET = ITEMS.register("lucky_nugget",
            ()-> new Item(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    
    public static final RegistryObject<Item> COPPER_COIN = ITEMS.register("copper_coin",
            ()-> new CoinItem(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> IRON_COIN = ITEMS.register("iron_coin",
            ()-> new CoinItem(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> GOLD_COIN = ITEMS.register("gold_coin",
            ()-> new CoinItem(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> NETHERITE_COIN = ITEMS.register("netherite_coin",
            ()-> new BankNoteItem(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> EMERALD_QUARTER_BANK_NOTE = ITEMS.register("emerald_quarter_bank_note",
            ()-> new BankNoteItem(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> EMERALD_HALF_BANK_NOTE = ITEMS.register("emerald_half_bank_note",
            ()-> new BankNoteItem(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> EMERALD_BANK_NOTE = ITEMS.register("emerald_bank_note",
            ()-> new BankNoteItem(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> LUCKY_COIN = ITEMS.register("lucky_coin",
            ()-> new LuckyCoinItem(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    
    public static final RegistryObject<Item> NETHER_GOLD_COIN = ITEMS.register("nether_gold_coin",
            ()-> new BarterCoinItem(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    
    public static final RegistryObject<Item> EMERALD_FIBER = ITEMS.register("emerald_fiber",
            ()-> new TextileItem(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> EMERALD_COARSE_WOVE_PAPER = ITEMS.register("emerald_coarse_wove_paper",
            ()-> new TextileItem(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> EMERALD_WOVE_PAPER = ITEMS.register("emerald_wove_paper",
            ()-> new TextileItem(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> EMERALD_SMOOTH_WOVE_PAPER = ITEMS.register("emerald_smooth_wove_paper",
            ()-> new TextileItem(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> LUCKY_FIBER = ITEMS.register("lucky_fiber",
            ()-> new TextileItem(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> LUCKY_THREAD = ITEMS.register("lucky_thread",
            ()-> new TextileItem(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> LUCKY_FABRIC = ITEMS.register("lucky_fabric",
            ()-> new TextileItem(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    
    public static final RegistryObject<Item> LUCKY_BOOTS = ITEMS.register("lucky_boots",
            ()-> new ModArmorItem(ModArmorMaterials.LUCKY, EquipmentSlot.FEET,
                    new Item.Properties().tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> LUCKY_PANTS = ITEMS.register("lucky_pants",
            ()-> new ModArmorItem(ModArmorMaterials.LUCKY, EquipmentSlot.LEGS,
                    new Item.Properties().tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> LUCKY_SHIRT = ITEMS.register("lucky_shirt",
            ()-> new ModArmorItem(ModArmorMaterials.LUCKY, EquipmentSlot.CHEST,
                    new Item.Properties().tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    public static final RegistryObject<Item> LUCKY_HAT = ITEMS.register("lucky_hat",
            ()-> new ModArmorItem(ModArmorMaterials.LUCKY, EquipmentSlot.HEAD,
                    new Item.Properties().tab(ModCreativeModeTab.RECIPEBOOKTESTTAB)));
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
    /**
     @return A collection of this mod's items in the order of their registration.
     **/
    public static Collection<RegistryObject<Item>> orderedItems() {
        return ITEMS.getEntries();
    }
}