package net.warrentode.recipebooktest.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import static net.warrentode.recipebooktest.RecipeBookTest.MODID;
import net.warrentode.recipebooktest.block.ModBlocks;
import net.warrentode.recipebooktest.item.ModItems;
import net.warrentode.recipebooktest.villager.ModVillagers;

@Mod.EventBusSubscriber(modid = MODID)
public class ModEvents {
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {

        // BANKER TRADES SET
        if(event.getType() == ModVillagers.BANKER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModBlocks.COPPER_COIN_BAG.get(), 18);
            int villagerLevel = 1;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    stack,16,8,0.02F));
        }
        if(event.getType() == ModVillagers.BANKER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(Items.EMERALD, 1);
            int villagerLevel = 1;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModBlocks.COPPER_COIN_BAG.get(), 18),
                    stack, 16, 8, 0.02F));
        }

        if(event.getType() == ModVillagers.BANKER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.IRON_COIN.get(), 1);
            int villagerLevel = 2;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.COPPER_COIN.get(), 2),
                    stack,16,8,0.02F));
        }
        if(event.getType() == ModVillagers.BANKER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.COPPER_COIN.get(), 2);
            int villagerLevel = 2;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.IRON_COIN.get(), 1),
                    stack, 16, 8, 0.02F));
        }

        if(event.getType() == ModVillagers.BANKER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.GOLD_COIN.get(), 1);
            int villagerLevel = 3;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.IRON_COIN.get(), 2),
                    stack,16,8,0.02F));
        }
        if(event.getType() == ModVillagers.BANKER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.IRON_COIN.get(), 2);
            int villagerLevel = 3;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.GOLD_COIN.get(), 1),
                    stack, 16, 8, 0.02F));
        }

        if(event.getType() == ModVillagers.BANKER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 1);
            int villagerLevel = 4;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 64),
                    stack,16,8,0.02F));
        }
        if(event.getType() == ModVillagers.BANKER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(Items.EMERALD, 64);
            int villagerLevel = 4;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 1),
                    stack, 16, 8, 0.02F));
        }

        if(event.getType() == ModVillagers.BANKER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.NETHERITE_COIN.get(), 2);
            int villagerLevel = 5;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 1),
                    stack, 16, 8, 0.02F));
        }
        if(event.getType() == ModVillagers.BANKER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 1);
            int villagerLevel = 5;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.NETHERITE_COIN.get(), 2),
                    stack,16,8,0.02F));
        }

        // LEPRECHAUN TRADES SET
        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.EMERALD_QUARTER_BANK_NOTE.get(), 1);
            int villagerLevel = 1;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                                                  new ItemStack(ModBlocks.POT_OF_GOLD.get(), 11),
                                                  stack, 16, 8, 0.02F)
                                         );
        }
        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModBlocks.POT_OF_GOLD.get(), 11);
            int villagerLevel = 1;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_QUARTER_BANK_NOTE.get(), 1),
                    stack,16,8,0.02F));
        }

        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.EMERALD_HALF_BANK_NOTE.get(), 1);
            int villagerLevel = 2;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModBlocks.POT_OF_GOLD.get(), 21),
                    stack, 16, 8, 0.02F));
        }
        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModBlocks.POT_OF_GOLD.get(), 21);
            int villagerLevel = 2;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_HALF_BANK_NOTE.get(), 1),
                    stack,16,8,0.02F));
        }

        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 1);
            int villagerLevel = 3;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModBlocks.POT_OF_GOLD.get(), 43),
                    stack, 16, 8, 0.02F));
        }
        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModBlocks.POT_OF_GOLD.get(), 43);
            int villagerLevel = 3;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 1),
                    stack,16,8,0.02F));
        }


        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(Items.PRISMARINE_SHARD, 1);
            int villagerLevel = 4;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_QUARTER_BANK_NOTE.get(), 1),
                    stack,16,8,0.02F));
        }
        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(Items.REDSTONE_BLOCK, 2);
            int villagerLevel = 4;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_QUARTER_BANK_NOTE.get(), 1),
                    stack,16,8,0.02F));
        }
        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(Items.NAME_TAG, 1);
            int villagerLevel = 4;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_QUARTER_BANK_NOTE.get(), 1),
                    stack,16,8,0.02F));
        }
        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(Items.PRISMARINE_CRYSTALS, 1);
            int villagerLevel = 4;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_HALF_BANK_NOTE.get(), 1),
                    stack,16,8,0.02F));
        }
        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(Items.BELL, 1);
            int villagerLevel = 4;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_HALF_BANK_NOTE.get(), 1),
                    stack,16,8,0.02F));
        }
        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(Items.DRAGON_HEAD, 1);
            int villagerLevel = 4;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_HALF_BANK_NOTE.get(), 1),
                    stack,16,8,0.02F));
        }
        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(Items.DRAGON_EGG, 1);
            int villagerLevel = 4;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_HALF_BANK_NOTE.get(), 1),
                    stack,16,8,0.02F));
        }
        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(Items.ELYTRA, 1);
            int villagerLevel = 4;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_HALF_BANK_NOTE.get(), 1),
                    stack,16,8,0.02F));
        }
        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(Items.NETHERITE_SCRAP, 1);
            int villagerLevel = 4;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 1),
                    stack,16,8,0.02F));
        }
        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(Items.PRISMARINE, 1);
            int villagerLevel = 4;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 4),
                    stack,16,8,0.02F));
        }
        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(Items.SEA_LANTERN, 1);
            int villagerLevel = 4;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 24),
                    stack,16,8,0.02F));
        }
        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(Items.NETHER_STAR, 1);
            int villagerLevel = 4;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 9),
                    stack,16,8,0.02F));
        }
        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(Items.NETHERITE_INGOT, 1);
            int villagerLevel = 4;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 36),
                    stack,16,8,0.02F));
        }

        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.LUCKY_COIN.get(), 1);
            int villagerLevel = 5;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                                                  new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 3),
                                                  stack, 16, 8, 0.02F)
                                         );
        }
        if(event.getType() == ModVillagers.LEPRECHAUN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 3);
            int villagerLevel = 5;
            //price
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.LUCKY_COIN.get(), 1),
                    stack,16,8,0.02F));
        }

    }
}