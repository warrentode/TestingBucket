package net.warrentode.recipebooktest.gui;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.warrentode.recipebooktest.block.ModBlocks;
import net.warrentode.recipebooktest.block.entity.CoinPressBlockEntity;
import net.warrentode.recipebooktest.block.entity.container.CoinPressResultSlot;
import net.warrentode.recipebooktest.recipe.ModRecipes;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CoinPressMenu extends RecipeBookMenu<RecipeWrapper> {
    public final CoinPressBlockEntity coinPress;
    private final Level level;
    private final ContainerData coinpressData;
    private final ContainerLevelAccess canInteractWithCallable;
    public final ItemStackHandler inventory;

    public CoinPressMenu(final int windowId, final Inventory playerInventory, final FriendlyByteBuf extraData) {
        this(windowId, playerInventory, getBlockEntity(playerInventory, extraData), new SimpleContainerData(2));
    }

    public CoinPressMenu(final int windowId, final Inventory playerInventory, final CoinPressBlockEntity blockEntity, ContainerData coinpressData) {
        super(ModMenuTypes.COIN_PRESS_MENU.get(), windowId);
        this.coinPress = blockEntity;
        this.inventory = coinPress.getInventory();
        this.coinpressData = coinpressData;
        this.level = playerInventory.player.level;
        this.canInteractWithCallable = ContainerLevelAccess.create(Objects.requireNonNull(coinPress.getLevel()), coinPress.getBlockPos());

        this.coinPress.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(inventory, 0, 34, 47));
            this.addSlot(new SlotItemHandler(inventory, 1, 70, 47));
            this.addSlot(new CoinPressResultSlot(playerInventory.player, coinPress, handler, 2, 142, 47));
        });

        this.addPlayerInventory(playerInventory);
        this.addPlayerHotbar(playerInventory);

        this.addDataSlots(coinpressData);
    }
    public ItemStackHandler getInventory() {
        return inventory;
    }
    private static CoinPressBlockEntity getBlockEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof CoinPressBlockEntity) {
            return (CoinPressBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }
    public boolean isCrafting() {
        return coinpressData.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.coinpressData.get(0);
        int maxProgress = this.coinpressData.get(1);  // Max Progress
        int progressArrowSize = 15; // This is the length in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 3;  // must be the number of slots you have!

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                                                                                     + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(level, coinPress.getBlockPos()),
                player, ModBlocks.COINPRESSBLOCK.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }

    @Override
    public void fillCraftSlotsStackedContents(@NotNull StackedContents helper) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            helper.accountSimpleStack(inventory.getStackInSlot(i));
        }
    }

    @Override
    public void clearCraftingContent() {
        for (int i = 0; i < 2; i++) {
            this.inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public boolean recipeMatches(Recipe<? super RecipeWrapper> recipe) {
        return recipe.matches(new RecipeWrapper(inventory), level);
    }

    @Override
    public int getResultSlotIndex() {
        return 2;
    }

    @Override
    public int getGridWidth() {
        return 2;
    }

    @Override
    public int getGridHeight() {
        return 1;
    }

    @Override
    public int getSize() {
        return 3;
    }

    @Override
    public @NotNull RecipeBookType getRecipeBookType() {
        return ModRecipes.COINPRESS_BOOK;
    }

    @Override
    public boolean shouldMoveToInventory(int slot) {
        return slot < (getGridWidth() * getGridHeight());
    }
}