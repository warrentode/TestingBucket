package net.warrentode.villagertradesjsonreader.datagen.trades;

import com.google.common.collect.ImmutableMap;
import net.minecraft.data.DataGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.warrentode.villagertradesjsonreader.trades.TradeLevel;
import net.warrentode.villagertradesjsonreader.trades.type.BasicType;

import static org.apache.commons.lang3.RandomUtils.nextInt;

// AUTHOR: MrCrayfish https://github.com/MrCrayfish/GoblinTraders/tree/1.19.X
public class VillagerTradeProvider extends TradeProvider {
    private static DataGenerator generator;
    public static DataGenerator.PathProvider pathProvider;
    public VillagerTradeProvider(DataGenerator generator, DataGenerator.PathProvider pathProvider) {
        super(generator, pathProvider);
        VillagerTradeProvider.generator = generator;
        VillagerTradeProvider.pathProvider = pathProvider;
    }
    public static VillagerProfession profession;
    public static TradeLevel tradeLevel;
    public static int paymentStackAmount;
    private static ItemStack secondaryPaymentStack;
    public static int secondaryPaymentStackAmount;
    public static int minPrice;
    public static int maxPrice;
    public static int setPrice;
    public static ItemStack bannerSelect;
    public static final int commonUses = 16;
    public static final int defaultUses = 12;
    private static final int rareUses = 3;
    public static final int lv1xpSell = 1;
    public static final int lv1xpBuy = 2;
    public static final int lv2xpSell = 5;
    public static final int lv2xpBuy = 10;
    public static final int lv3xpSell = 10;
    public static final int lv3xpBuy = 20;
    private static final int lv4xpSell = 15;
    private static final int lv4xpBuy = 30;
    private static final int lv5xpTrade = 30;
    public static final float lowTierMult = 0.05F;
    public static final float highTierMult = 0.2F;

    static int setPrice() {
        return setPrice = nextInt(minPrice, maxPrice);
    }
    public static int minDiamondLeggingsPrice = 19;
    public static int maxDiamondLeggingsPrice = 33;
    public static int setDiamondLeggingsPrice;
    public static int getSetDiamondLeggingsPrice() {
        minPrice = minDiamondLeggingsPrice;
        maxPrice = maxDiamondLeggingsPrice;
        setDiamondLeggingsPrice = setPrice();
        return setDiamondLeggingsPrice;
    }
    public static int minDiamondBootsPrice = 13;
    public static int maxDiamondBootsPrice = 27;
    public static int setDiamondBootsPrice;
    public static int getSetDiamondBootsPrice() {
        minPrice = minDiamondBootsPrice;
        maxPrice = maxDiamondBootsPrice;
        setDiamondBootsPrice = setPrice();
        return setDiamondBootsPrice;
    }
    public static int minDiamondHelmetPrice = 13;
    public static int maxDiamondHelmetPrice = 27;
    public static int setDiamondHelmetPrice;
    public static int getSetDiamondHelmetPrice() {
        minPrice = minDiamondHelmetPrice;
        maxPrice = maxDiamondHelmetPrice;
        setDiamondHelmetPrice = setPrice();
        return setDiamondHelmetPrice;
    }
    public static int minDiamondChestplatePrice = 21;
    public static int maxDiamondChestplatePrice = 35;
    public static int setDiamondChestplatePrice;
    public static int getSetDiamondChestplatePrice() {
        minPrice = minDiamondChestplatePrice;
        maxPrice = maxDiamondChestplatePrice;
        setDiamondChestplatePrice = setPrice();
        return setDiamondChestplatePrice;
    }
    CompoundTag overworldMapBoxTag = new CompoundTag();
    public CompoundTag getOverworldMapBoxTag(CompoundTag overworldMapBoxTag) {
        this.overworldMapBoxTag.putString("nbt", "{BlockEntityTag:{LootTable:\"todecoins:overworld_mapbox\"},display:{Lore:['\"Map Box\"']}}");
        return this.overworldMapBoxTag;
    }

    public static String color;

    @Override
    public void registerTrades() {
        /** TodeVillager Mod not part of this test right now
        this.registerBankerTrades();
        this.registerLeprechaunTrades();
         **/
        this.registerArmorerTrades();
        this.registerButcherTrades();
        this.registerCartographerTrades();
    }

    private void registerCartographerTrades() {
        // NOVICE
        addTrade(VillagerProfession.CARTOGRAPHER, TradeLevel.NOVICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 1))
                .setPaymentStack(new ItemStack(Items.PAPER, 24))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(commonUses)
                .setExperience(lv1xpBuy)
                .build());
        addTrade(VillagerProfession.CARTOGRAPHER, TradeLevel.NOVICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.MAP, 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 7))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(commonUses)
                .setExperience(lv1xpSell)
                .build());
        // APPRENTICE
        addTrade(VillagerProfession.CARTOGRAPHER, TradeLevel.APPRENTICE, BasicType.Builder.create()
                .setPaymentStack(new ItemStack(Items.GLASS_PANE, 11))
                .setOfferStack(new ItemStack(Items.EMERALD, 1))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(commonUses)
                .setExperience(lv2xpBuy)
                .build());
        addTrade(VillagerProfession.CARTOGRAPHER, TradeLevel.APPRENTICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.CHEST, 1, getOverworldMapBoxTag(overworldMapBoxTag)))
                .setPaymentStack(new ItemStack(Items.EMERALD, 13))
                .setSecondaryPaymentStack(new ItemStack(Items.COMPASS, 1))
                .setPriceMultiplier(highTierMult)
                .setMaxTrades(commonUses)
                .setExperience(lv2xpSell)
                .build());
        // JOURNEYMAN
        addTrade(VillagerProfession.CARTOGRAPHER, TradeLevel.JOURNEYMAN, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.COMPASS, 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 1))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(commonUses)
                .setExperience(lv3xpSell)
                .build());
        addTrade(VillagerProfession.CARTOGRAPHER, TradeLevel.JOURNEYMAN, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.CHEST, 1, getOverworldMapBoxTag(overworldMapBoxTag)))
                .setPaymentStack(new ItemStack(Items.EMERALD, 14))
                .setSecondaryPaymentStack(new ItemStack(Items.COMPASS, 1))
                .setPriceMultiplier(highTierMult)
                .setMaxTrades(commonUses)
                .setExperience(lv3xpSell)
                .build());
        // EXPERT
        addTrade(VillagerProfession.CARTOGRAPHER, TradeLevel.EXPERT, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.ITEM_FRAME, 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 7))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(commonUses)
                .setExperience(lv4xpSell)
                .build());
        selectColor();
        addTrade(VillagerProfession.CARTOGRAPHER, TradeLevel.EXPERT, BasicType.Builder.create()
                .setOfferStack(bannerSelect)
                .setPaymentStack(new ItemStack(Items.EMERALD, 3))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(commonUses)
                .setExperience(lv4xpSell)
                .build());

        // MASTER
        addTrade(VillagerProfession.CARTOGRAPHER, TradeLevel.MASTER, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.GLOBE_BANNER_PATTERN))
                .setPaymentStack(new ItemStack(Items.EMERALD, 8))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(commonUses)
                .setExperience(lv4xpSell)
                .build());
    }
    private void registerButcherTrades() {
        // NOVICE
        addTrade(VillagerProfession.BUTCHER, TradeLevel.NOVICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 1))
                .setPaymentStack(new ItemStack(Items.CHICKEN, 14))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(commonUses)
                .setExperience(lv1xpBuy)
                .build());
        addTrade(VillagerProfession.BUTCHER, TradeLevel.NOVICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 1))
                .setPaymentStack(new ItemStack(Items.PORKCHOP, 7))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(commonUses)
                .setExperience(lv1xpBuy)
                .build());
        addTrade(VillagerProfession.BUTCHER, TradeLevel.NOVICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 1))
                .setPaymentStack(new ItemStack(Items.RABBIT, 4))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(commonUses)
                .setExperience(lv1xpBuy)
                .build());
        addTrade(VillagerProfession.BUTCHER, TradeLevel.NOVICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.RABBIT_STEW, 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 1))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(defaultUses)
                .setExperience(lv1xpSell)
                .build());
        addTrade(VillagerProfession.BUTCHER, TradeLevel.APPRENTICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 1))
                .setPaymentStack(new ItemStack(Items.COAL, 15))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(commonUses)
                .setExperience(lv2xpBuy)
                .build());
        addTrade(VillagerProfession.BUTCHER, TradeLevel.APPRENTICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.COOKED_PORKCHOP, 5))
                .setPaymentStack(new ItemStack(Items.EMERALD, 1))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(commonUses)
                .setExperience(lv2xpSell)
                .build());
        addTrade(VillagerProfession.BUTCHER, TradeLevel.APPRENTICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.COOKED_CHICKEN, 8))
                .setPaymentStack(new ItemStack(Items.EMERALD, 1))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(commonUses)
                .setExperience(lv2xpSell)
                .build());
        addTrade(VillagerProfession.BUTCHER, TradeLevel.JOURNEYMAN, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 1))
                .setPaymentStack(new ItemStack(Items.MUTTON, 7))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(commonUses)
                .setExperience(lv3xpBuy)
                .build());
        addTrade(VillagerProfession.BUTCHER, TradeLevel.JOURNEYMAN, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 1))
                .setPaymentStack(new ItemStack(Items.BEEF, 10))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(commonUses)
                .setExperience(lv3xpBuy)
                .build());
        addTrade(VillagerProfession.BUTCHER, TradeLevel.EXPERT, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 1))
                .setPaymentStack(new ItemStack(Items.DRIED_KELP_BLOCK, 10))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(defaultUses)
                .setExperience(lv4xpBuy)
                .build());
        addTrade(VillagerProfession.BUTCHER, TradeLevel.MASTER, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 1))
                .setPaymentStack(new ItemStack(Items.SWEET_BERRIES, 10))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(defaultUses)
                .setExperience(lv5xpTrade)
                .build());
    }
    private void registerArmorerTrades() {
        // NOVICE
        addTrade(VillagerProfession.ARMORER, TradeLevel.NOVICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 1))
                .setPaymentStack(new ItemStack(Items.COAL, 15))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(commonUses)
                .setExperience(lv1xpBuy)
                .build());
        addTrade(VillagerProfession.ARMORER, TradeLevel.NOVICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.IRON_HELMET, 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 5))
                .setPriceMultiplier(highTierMult)
                .setMaxTrades(defaultUses)
                .setExperience(lv1xpSell)
                .build());
        addTrade(VillagerProfession.ARMORER, TradeLevel.NOVICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.IRON_CHESTPLATE, 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 9))
                .setPriceMultiplier(highTierMult)
                .setMaxTrades(defaultUses)
                .setExperience(lv1xpSell)
                .build());
        addTrade(VillagerProfession.ARMORER, TradeLevel.NOVICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.IRON_LEGGINGS, 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 7))
                .setPriceMultiplier(highTierMult)
                .setMaxTrades(defaultUses)
                .setExperience(lv1xpSell)
                .build());
        addTrade(VillagerProfession.ARMORER, TradeLevel.NOVICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.IRON_BOOTS, 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 4))
                .setPriceMultiplier(highTierMult)
                .setMaxTrades(defaultUses)
                .setExperience(lv1xpSell)
                .build());
        // APPRENTICE
        addTrade(VillagerProfession.ARMORER, TradeLevel.APPRENTICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 1))
                .setPaymentStack(new ItemStack(Items.IRON_INGOT, 4))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(defaultUses)
                .setExperience(lv3xpSell)
                .build());
        addTrade(VillagerProfession.ARMORER, TradeLevel.APPRENTICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.BELL, 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 36))
                .setPriceMultiplier(highTierMult)
                .setMaxTrades(defaultUses)
                .setExperience(lv2xpSell)
                .build());
        addTrade(VillagerProfession.ARMORER, TradeLevel.APPRENTICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.CHAINMAIL_LEGGINGS, 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 3))
                .setPriceMultiplier(highTierMult)
                .setMaxTrades(defaultUses)
                .setExperience(lv2xpSell)
                .build());
        addTrade(VillagerProfession.ARMORER, TradeLevel.APPRENTICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.CHAINMAIL_BOOTS, 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 1))
                .setPriceMultiplier(highTierMult)
                .setMaxTrades(defaultUses)
                .setExperience(lv2xpSell)
                .build());
        addTrade(VillagerProfession.ARMORER, TradeLevel.JOURNEYMAN, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.LAVA_BUCKET, 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 1))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(defaultUses)
                .setExperience(lv3xpBuy)
                .build());
        addTrade(VillagerProfession.ARMORER, TradeLevel.JOURNEYMAN, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.DIAMOND, 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 1))
                .setPriceMultiplier(lowTierMult)
                .setMaxTrades(defaultUses)
                .setExperience(lv3xpBuy)
                .build());
        addTrade(VillagerProfession.ARMORER, TradeLevel.JOURNEYMAN, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.CHAINMAIL_HELMET, 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 1))
                .setPriceMultiplier(highTierMult)
                .setMaxTrades(defaultUses)
                .setExperience(lv3xpSell)
                .build());
        addTrade(VillagerProfession.ARMORER, TradeLevel.JOURNEYMAN, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.CHAINMAIL_CHESTPLATE, 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 4))
                .setPriceMultiplier(highTierMult)
                .setMaxTrades(defaultUses)
                .setExperience(lv3xpSell)
                .build());
        addTrade(VillagerProfession.ARMORER, TradeLevel.JOURNEYMAN, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.SHIELD, 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 5))
                .setPriceMultiplier(highTierMult)
                .setMaxTrades(defaultUses)
                .setExperience(lv3xpSell)
                .build());

        ItemStack armorPiece1 = Items.DIAMOND_LEGGINGS.getDefaultInstance();
        for (EnchantmentInstance armorEnchantment : getLeggingsEnchantments()) {
            EnchantmentHelper.setEnchantments(ImmutableMap.of(armorEnchantment.enchantment, armorEnchantment.level), armorPiece1);
            addTrade(VillagerProfession.ARMORER, TradeLevel.EXPERT, BasicType.Builder.create()
                    .setOfferStack(new ItemStack(armorPiece1.getItem()))
                    .setPaymentStack(new ItemStack(Items.EMERALD, getSetDiamondLeggingsPrice()))
                    .setPriceMultiplier(highTierMult)
                    .setMaxTrades(rareUses)
                    .setExperience(lv4xpSell)
                    .addEnchantment(new EnchantmentInstance(armorEnchantment.enchantment, armorEnchantment.level))
                    .build());
        }
        ItemStack armorPiece2 = Items.DIAMOND_BOOTS.getDefaultInstance();
        for (EnchantmentInstance armorEnchantment : getBootsEnchantments()) {
            EnchantmentHelper.setEnchantments(ImmutableMap.of(armorEnchantment.enchantment, armorEnchantment.level), armorPiece2);
            addTrade(VillagerProfession.ARMORER, TradeLevel.EXPERT, BasicType.Builder.create()
                    .setOfferStack(new ItemStack(armorPiece2.getItem()))
                    .setPaymentStack(new ItemStack(Items.EMERALD, getSetDiamondBootsPrice()))
                    .setPriceMultiplier(highTierMult)
                    .setMaxTrades(rareUses)
                    .setExperience(lv4xpSell)
                    .addEnchantment(new EnchantmentInstance(armorEnchantment.enchantment, armorEnchantment.level))
                    .build());
        }
        ItemStack armorPiece3 = Items.DIAMOND_HELMET.getDefaultInstance();
        for (EnchantmentInstance armorEnchantment : getHelmetEnchantments()) {
            EnchantmentHelper.setEnchantments(ImmutableMap.of(armorEnchantment.enchantment, armorEnchantment.level), armorPiece3);
            addTrade(VillagerProfession.ARMORER, TradeLevel.MASTER, BasicType.Builder.create()
                    .setOfferStack(new ItemStack(armorPiece3.getItem()))
                    .setPaymentStack(new ItemStack(Items.EMERALD, getSetDiamondHelmetPrice()))
                    .setPriceMultiplier(highTierMult)
                    .setMaxTrades(rareUses)
                    .setExperience(lv4xpSell)
                    .addEnchantment(new EnchantmentInstance(armorEnchantment.enchantment, armorEnchantment.level))
                    .build());
        }
        ItemStack armorPiece4 = Items.DIAMOND_CHESTPLATE.getDefaultInstance();
        for (EnchantmentInstance armorEnchantment : getChestplateEnchantments()) {
            EnchantmentHelper.setEnchantments(ImmutableMap.of(armorEnchantment.enchantment, armorEnchantment.level), armorPiece4);
            addTrade(VillagerProfession.ARMORER, TradeLevel.MASTER, BasicType.Builder.create()
                    .setOfferStack(new ItemStack(armorPiece4.getItem()))
                    .setPaymentStack(new ItemStack(Items.EMERALD, getSetDiamondChestplatePrice()))
                    .setPriceMultiplier(highTierMult)
                    .setMaxTrades(rareUses)
                    .setExperience(lv4xpSell)
                    .addEnchantment(new EnchantmentInstance(armorEnchantment.enchantment, armorEnchantment.level))
                    .build());
        }
    }

    /** from the TodeVillagers Mod - on hold for now until this reader is figured out and functional
    private void registerLeprechaunTrades() {
        // NOVICE
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.NOVICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(ModItems.EMERALD_QUARTER_BANK_NOTE.get(), 1))
                .setPaymentStack(new ItemStack(ModBlocks.POT_OF_GOLD.get(), 11))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.NOVICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(ModBlocks.POT_OF_GOLD.get(), 11))
                .setPaymentStack(new ItemStack(ModItems.EMERALD_QUARTER_BANK_NOTE.get(), 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        // APPRENTICE
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.APPRENTICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(ModItems.EMERALD_HALF_BANK_NOTE.get(), 1))
                .setPaymentStack(new ItemStack(ModBlocks.POT_OF_GOLD.get(), 21))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.APPRENTICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(ModBlocks.POT_OF_GOLD.get(), 21))
                .setPaymentStack(new ItemStack(ModItems.EMERALD_HALF_BANK_NOTE.get(), 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        // JOURNEYMAN
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.JOURNEYMAN, BasicType.Builder.create()
                .setOfferStack(new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 1))
                .setPaymentStack(new ItemStack(ModBlocks.POT_OF_GOLD.get(), 43))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.JOURNEYMAN, BasicType.Builder.create()
                .setOfferStack(new ItemStack(ModBlocks.POT_OF_GOLD.get(), 43))
                .setPaymentStack(new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        // EXPERT
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.EXPERT, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.PRISMARINE_SHARD, 1))
                .setPaymentStack(new ItemStack(ModItems.EMERALD_QUARTER_BANK_NOTE.get(), 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.EXPERT, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Blocks.REDSTONE_BLOCK, 2))
                .setPaymentStack(new ItemStack(ModItems.EMERALD_QUARTER_BANK_NOTE.get(), 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.EXPERT, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.NAME_TAG, 1))
                .setPaymentStack(new ItemStack(ModItems.EMERALD_QUARTER_BANK_NOTE.get(), 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.EXPERT, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.PRISMARINE_CRYSTALS, 1))
                .setPaymentStack(new ItemStack(ModItems.EMERALD_HALF_BANK_NOTE.get(), 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.EXPERT, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.BELL, 1))
                .setPaymentStack(new ItemStack(ModItems.EMERALD_HALF_BANK_NOTE.get(), 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.EXPERT, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.DRAGON_HEAD, 1))
                .setPaymentStack(new ItemStack(ModItems.EMERALD_HALF_BANK_NOTE.get(), 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.EXPERT, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.DRAGON_EGG, 1))
                .setPaymentStack(new ItemStack(ModItems.EMERALD_HALF_BANK_NOTE.get(), 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.EXPERT, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.ELYTRA, 1))
                .setPaymentStack(new ItemStack(ModItems.EMERALD_HALF_BANK_NOTE.get(), 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.EXPERT, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.NETHERITE_SCRAP, 1))
                .setPaymentStack(new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.EXPERT, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.PRISMARINE, 1))
                .setPaymentStack(new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.EXPERT, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.SEA_LANTERN, 1))
                .setPaymentStack(new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 3))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.EXPERT, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.NETHERITE_INGOT, 1))
                .setPaymentStack(new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 9))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        // MASTER
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.MASTER, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.NETHER_STAR, 1))
                .setPaymentStack(new ItemStack(ModItems.LUCKY_COIN.get(), 3))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.LEPRECHAUN.get(), TradeLevel.MASTER, BasicType.Builder.create()
                .setOfferStack(new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 3))
                .setPaymentStack(new ItemStack(ModItems.LUCKY_COIN.get(), 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
    }
    private void registerBankerTrades() {
        // NOVICE
        addTrade(ModVillagers.BANKER.get(), TradeLevel.NOVICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.BANKER.get(), TradeLevel.NOVICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        // APPRENTICE
        addTrade(ModVillagers.BANKER.get(), TradeLevel.APPRENTICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(ModItems.IRON_COIN.get(), 1))
                .setPaymentStack(new ItemStack(ModItems.COPPER_COIN.get(), 2))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.BANKER.get(), TradeLevel.APPRENTICE, BasicType.Builder.create()
                .setOfferStack(new ItemStack(ModItems.COPPER_COIN.get(), 2))
                .setPaymentStack(new ItemStack(ModItems.IRON_COIN.get(), 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        // JOURNEYMAN
        addTrade(ModVillagers.BANKER.get(), TradeLevel.JOURNEYMAN, BasicType.Builder.create()
                .setOfferStack(new ItemStack(ModItems.GOLD_COIN.get(), 1))
                .setPaymentStack(new ItemStack(ModItems.IRON_COIN.get(), 2))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.BANKER.get(), TradeLevel.JOURNEYMAN, BasicType.Builder.create()
                .setOfferStack(new ItemStack(ModItems.IRON_COIN.get(), 2))
                .setPaymentStack(new ItemStack(ModItems.GOLD_COIN.get(), 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        // EXPERT
        addTrade(ModVillagers.BANKER.get(), TradeLevel.EXPERT, BasicType.Builder.create()
                .setOfferStack(new ItemStack(ModItems.EMERALD_QUARTER_BANK_NOTE.get(), 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 16))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.BANKER.get(), TradeLevel.EXPERT, BasicType.Builder.create()
                .setOfferStack(new ItemStack(ModItems.EMERALD_HALF_BANK_NOTE.get(), 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 32))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        // MASTER
        addTrade(ModVillagers.BANKER.get(), TradeLevel.MASTER, BasicType.Builder.create()
                .setOfferStack(new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 1))
                .setPaymentStack(new ItemStack(Items.EMERALD, 64))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
        addTrade(ModVillagers.BANKER.get(), TradeLevel.MASTER, BasicType.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 64))
                .setPaymentStack(new ItemStack(ModItems.EMERALD_BANK_NOTE.get(), 1))
                .setPriceMultiplier(0.02F)
                .setMaxTrades(16)
                .setExperience(8)
                .build());
    }
     **/

    // DEFAULT VANILLA SWORD ENCHANTMENTS LIST
    public static EnchantmentInstance[] swordEnchantments = new EnchantmentInstance[] {
            new EnchantmentInstance(Enchantments.MENDING, 1),
            new EnchantmentInstance(Enchantments.UNBREAKING, 3),
            new EnchantmentInstance(Enchantments.FIRE_ASPECT, 2),
            new EnchantmentInstance(Enchantments.MOB_LOOTING, 3),
            new EnchantmentInstance(Enchantments.KNOCKBACK, 2),
            new EnchantmentInstance(Enchantments.SHARPNESS, 5),
            new EnchantmentInstance(Enchantments.SMITE, 5),
            new EnchantmentInstance(Enchantments.BANE_OF_ARTHROPODS, 5),
            new EnchantmentInstance(Enchantments.SWEEPING_EDGE, 3)
    };
    public static EnchantmentInstance[] getAxeEnchantments() {
        return swordEnchantments;
    }
    // DEFAULT VANILLA SWORD ENCHANTMENTS LIST
    public static EnchantmentInstance[] axeEnchantments = new EnchantmentInstance[] {
            new EnchantmentInstance(Enchantments.MENDING, 1),
            new EnchantmentInstance(Enchantments.UNBREAKING, 3),
            new EnchantmentInstance(Enchantments.BLOCK_EFFICIENCY, 5),
            new EnchantmentInstance(Enchantments.BLOCK_FORTUNE, 3),
            new EnchantmentInstance(Enchantments.SILK_TOUCH, 1),
            new EnchantmentInstance(Enchantments.BLOCK_FORTUNE, 3),
            new EnchantmentInstance(Enchantments.SHARPNESS, 5),
            new EnchantmentInstance(Enchantments.SMITE, 5),
            new EnchantmentInstance(Enchantments.BANE_OF_ARTHROPODS, 5)
    };
    public static EnchantmentInstance[] getSwordEnchantments() {
        return axeEnchantments;
    }
    // DEFAULT VANILLA TOOL ENCHANTMENTS LIST
    public static EnchantmentInstance[] toolEnchantments = new EnchantmentInstance[] {
            new EnchantmentInstance(Enchantments.UNBREAKING, 3),
            new EnchantmentInstance(Enchantments.BLOCK_EFFICIENCY, 5),
            new EnchantmentInstance(Enchantments.BLOCK_FORTUNE, 3),
            new EnchantmentInstance(Enchantments.SILK_TOUCH, 1),
            new EnchantmentInstance(Enchantments.BLOCK_FORTUNE, 3),
            new EnchantmentInstance(Enchantments.MENDING, 1)
    };
    public static EnchantmentInstance[] getToolEnchantments() {
        return toolEnchantments;
    }
    // DEFAULT VANILLA BOW ENCHANTMENTS LIST
    public static EnchantmentInstance[] bowEnchantments = new EnchantmentInstance[] {
            new EnchantmentInstance(Enchantments.UNBREAKING, 3),
            new EnchantmentInstance(Enchantments.POWER_ARROWS, 5),
            new EnchantmentInstance(Enchantments.PUNCH_ARROWS, 2),
            new EnchantmentInstance(Enchantments.FLAMING_ARROWS, 1),
            new EnchantmentInstance(Enchantments.INFINITY_ARROWS, 1),
            new EnchantmentInstance(Enchantments.MENDING, 1)
    };
    public static EnchantmentInstance[] getBowEnchantments() {
        return bowEnchantments;
    }
    // DEFAULT VANILLA FISHING ROD ENCHANTMENTS LIST
    public static EnchantmentInstance[] fishingEnchantments = new EnchantmentInstance[] {
            new EnchantmentInstance(Enchantments.UNBREAKING, 3),
            new EnchantmentInstance(Enchantments.FISHING_LUCK, 3),
            new EnchantmentInstance(Enchantments.FISHING_SPEED, 2),
            new EnchantmentInstance(Enchantments.MENDING, 1)
    };
    public static EnchantmentInstance[] getFishingEnchantments() {
        return fishingEnchantments;
    }
    // DEFAULT VANILLA TRIDENT ENCHANTMENTS LIST
    public static EnchantmentInstance[] tridentEnchantments = new EnchantmentInstance[] {
            new EnchantmentInstance(Enchantments.MENDING, 1),
            new EnchantmentInstance(Enchantments.UNBREAKING, 3),
            new EnchantmentInstance(Enchantments.IMPALING, 5),
            new EnchantmentInstance(Enchantments.CHANNELING, 1),
            new EnchantmentInstance(Enchantments.LOYALTY, 3),
            new EnchantmentInstance(Enchantments.RIPTIDE, 3)
    };
    public static EnchantmentInstance[] getTridentEnchantments() {
        return tridentEnchantments;
    }
    // DEFAULT VANILLA CROSSBOW ENCHANTMENTS LIST
    public static EnchantmentInstance[] crossbowEnchantments = new EnchantmentInstance[] {
            new EnchantmentInstance(Enchantments.MENDING, 1),
            new EnchantmentInstance(Enchantments.UNBREAKING, 3),
            new EnchantmentInstance(Enchantments.QUICK_CHARGE, 3),
            new EnchantmentInstance(Enchantments.PIERCING, 4),
            new EnchantmentInstance(Enchantments.MULTISHOT, 1)
    };
    public static EnchantmentInstance[] getCrossbowEnchantments() {
        return crossbowEnchantments;
    }
    // DEFAULT VANILLA HELMET ENCHANTMENTS LIST
    public static EnchantmentInstance[] helmetEnchantments = new EnchantmentInstance[]{
            new EnchantmentInstance(Enchantments.ALL_DAMAGE_PROTECTION, 4),
            new EnchantmentInstance(Enchantments.BLAST_PROTECTION, 4),
            new EnchantmentInstance(Enchantments.FIRE_PROTECTION, 4),
            new EnchantmentInstance(Enchantments.MENDING, 1),
            new EnchantmentInstance(Enchantments.PROJECTILE_PROTECTION, 4),
            new EnchantmentInstance(Enchantments.THORNS, 3),
            new EnchantmentInstance(Enchantments.RESPIRATION, 3),
            new EnchantmentInstance(Enchantments.UNBREAKING, 3),
            new EnchantmentInstance(Enchantments.AQUA_AFFINITY, 1),
            new EnchantmentInstance(Enchantments.RESPIRATION, 3)
    };
    public static EnchantmentInstance[] getHelmetEnchantments() {
        return helmetEnchantments;
    }
    // DEFAULT VANILLA LEGGINGS ENCHANTMENTS LIST
    public static EnchantmentInstance[] leggingsEnchantments = new EnchantmentInstance[]{
            new EnchantmentInstance(Enchantments.ALL_DAMAGE_PROTECTION, 4),
            new EnchantmentInstance(Enchantments.BLAST_PROTECTION, 4),
            new EnchantmentInstance(Enchantments.FIRE_PROTECTION, 4),
            new EnchantmentInstance(Enchantments.MENDING, 1),
            new EnchantmentInstance(Enchantments.PROJECTILE_PROTECTION, 4),
            new EnchantmentInstance(Enchantments.THORNS, 3),
            new EnchantmentInstance(Enchantments.UNBREAKING, 3),
            new EnchantmentInstance(Enchantments.SWIFT_SNEAK, 3)
    };
    public static EnchantmentInstance[] getLeggingsEnchantments() {
        return leggingsEnchantments;
    }
    // DEFAULT VANILLA BOOTS ENCHANTMENTS LIST
    public static EnchantmentInstance[] bootsEnchantments = new EnchantmentInstance[]{
            new EnchantmentInstance(Enchantments.ALL_DAMAGE_PROTECTION, 4),
            new EnchantmentInstance(Enchantments.BLAST_PROTECTION, 4),
            new EnchantmentInstance(Enchantments.FIRE_PROTECTION, 4),
            new EnchantmentInstance(Enchantments.MENDING, 1),
            new EnchantmentInstance(Enchantments.PROJECTILE_PROTECTION, 4),
            new EnchantmentInstance(Enchantments.THORNS, 3),
            new EnchantmentInstance(Enchantments.UNBREAKING, 3),
            new EnchantmentInstance(Enchantments.DEPTH_STRIDER, 3),
            new EnchantmentInstance(Enchantments.FALL_PROTECTION, 4),
            new EnchantmentInstance(Enchantments.FROST_WALKER, 2),
            new EnchantmentInstance(Enchantments.SOUL_SPEED, 3)
    };
    public static EnchantmentInstance[] getBootsEnchantments() {
        return bootsEnchantments;
    }
    // DEFAULT VANILLA ARMOR ENCHANTMENTS LIST
    public static EnchantmentInstance[] chestplateEnchantments = new EnchantmentInstance[]{
            new EnchantmentInstance(Enchantments.ALL_DAMAGE_PROTECTION, 4),
            new EnchantmentInstance(Enchantments.BLAST_PROTECTION, 4),
            new EnchantmentInstance(Enchantments.FIRE_PROTECTION, 4),
            new EnchantmentInstance(Enchantments.MENDING, 1),
            new EnchantmentInstance(Enchantments.PROJECTILE_PROTECTION, 4),
            new EnchantmentInstance(Enchantments.THORNS, 3),
            new EnchantmentInstance(Enchantments.UNBREAKING, 3)
    };
    public static EnchantmentInstance[] getChestplateEnchantments() {
        return chestplateEnchantments;
    }

    // COLORED ITEMS SELECTION
    public static DyeColor[] colors = new DyeColor[]{
            DyeColor.WHITE, DyeColor.ORANGE, DyeColor.MAGENTA, DyeColor.LIGHT_BLUE, DyeColor.YELLOW, DyeColor.LIME,
            DyeColor.PINK, DyeColor.GRAY, DyeColor.LIGHT_GRAY, DyeColor.CYAN, DyeColor.PURPLE, DyeColor.BLUE,
            DyeColor.BROWN, DyeColor.GREEN, DyeColor.RED, DyeColor.BLACK
    };
    public static DyeColor[] getColors() {
        return colors;
    }
    public ItemStack selectColor() {
        int colorSelected;
        switch (colorSelected = RandomSource.create().nextInt(0, 15)) {
            case 0: DyeColor white = DyeColor.WHITE;
            case 1: DyeColor orange = DyeColor.ORANGE;
            case 2: DyeColor magenta = DyeColor.MAGENTA;
            case 3: DyeColor light_blue = DyeColor.LIGHT_BLUE;
            case 4: DyeColor yellow = DyeColor.YELLOW;
            case 5: DyeColor lime = DyeColor.LIME;
            case 6: DyeColor pink = DyeColor.PINK;
            case 7: DyeColor gray = DyeColor.GRAY;
            case 8: DyeColor light_gray = DyeColor.LIGHT_GRAY;
            case 9: DyeColor cyan = DyeColor.CYAN;
            case 10: DyeColor purple = DyeColor.PURPLE;
            case 11: DyeColor blue = DyeColor.BLUE;
            case 12: DyeColor brown = DyeColor.BROWN;
            case 13: DyeColor green = DyeColor.GREEN;
            case 14: DyeColor red = DyeColor.RED;
            case 15: DyeColor black = DyeColor.BLACK;
        }
        if (colorSelected == 0) {
            return bannerSelect = new ItemStack(Items.WHITE_BANNER, 1);
        }
        else if (colorSelected == 1) {
            return bannerSelect = new ItemStack(Items.ORANGE_BANNER, 1);
        }
        else if (colorSelected == 2) {
            return bannerSelect = new ItemStack(Items.MAGENTA_BANNER, 1);
        }
        else if (colorSelected == 3) {
            return bannerSelect = new ItemStack(Items.LIGHT_BLUE_BANNER, 1);
        }
        else if (colorSelected == 4) {
            return bannerSelect = new ItemStack(Items.YELLOW_BANNER, 1);
        }
        else if (colorSelected == 5) {
            return bannerSelect = new ItemStack(Items.LIME_BANNER, 1);
        }
        else if (colorSelected == 6) {
            return bannerSelect = new ItemStack(Items.PINK_BANNER, 1);
        }
        else if (colorSelected == 7) {
            return bannerSelect = new ItemStack(Items.GRAY_BANNER, 1);
        }
        else if (colorSelected == 8) {
            return bannerSelect = new ItemStack(Items.LIGHT_GRAY_BANNER, 1);
        }
        else if (colorSelected == 9) {
            return bannerSelect = new ItemStack(Items.CYAN_BANNER, 1);
        }
        else if (colorSelected == 10) {
            return bannerSelect = new ItemStack(Items.PURPLE_BANNER, 1);
        }
        else if (colorSelected == 11) {
            return bannerSelect = new ItemStack(Items.BLUE_BANNER, 1);
        }
        else if (colorSelected == 12) {
            return bannerSelect = new ItemStack(Items.BROWN_BANNER, 1);
        }
        else if (colorSelected == 13) {
            return bannerSelect = new ItemStack(Items.GREEN_BANNER, 1);
        }
        else if (colorSelected == 14) {
            return bannerSelect = new ItemStack(Items.RED_BANNER, 1);
        }
        else if (colorSelected == 15) {
            return bannerSelect = new ItemStack(Items.BLACK_BANNER, 1);
        }
        return ItemStack.EMPTY;
    }
}