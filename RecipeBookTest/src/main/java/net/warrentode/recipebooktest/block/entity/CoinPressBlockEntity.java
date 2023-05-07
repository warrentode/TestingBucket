package net.warrentode.recipebooktest.block.entity;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.warrentode.recipebooktest.block.custom.CoinPressBlock;
import net.warrentode.recipebooktest.block.entity.container.inventory.CoinPressItemHandler;
import net.warrentode.recipebooktest.gui.CoinPressMenu;
import net.warrentode.recipebooktest.mixin.RecipeManagerAccessor;
import net.warrentode.recipebooktest.recipe.CoinPressRecipe;
import net.warrentode.recipebooktest.recipe.ModRecipes;
import net.warrentode.recipebooktest.util.tags.ModTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class CoinPressBlockEntity extends BlockEntity implements MenuProvider, Nameable, RecipeHolder {
    public final ItemStackHandler inventory;
    public static final int RESULT_SLOT = 2;
    public static final int INVENTORY_SIZE = 3;
    private int stampingTime;
    private int totalStampingTime;
    private Component customName;
    protected final ContainerData coinpressData;
    private final Object2IntOpenHashMap<ResourceLocation> usedRecipes;
    private ResourceLocation lastRecipeID;
    private ItemStack lastItemCrafted;
    private boolean checkNewRecipe;
    public LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final ItemStackHandler itemHandler = new ItemStackHandler(INVENTORY_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            if (slot >= 0 && slot < RESULT_SLOT) {
                checkNewRecipe = true;
            }
            if (level != null) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
            }
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0, 1 -> true;
                case 2 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private final Map<Direction, LazyOptional<CoinPressItemHandler>> directionWrappedHandlerMap =
            Map.of(Direction.DOWN, LazyOptional.of(() -> new CoinPressItemHandler(itemHandler, (i) -> i == 2, (i, s) -> false)),
                    Direction.NORTH, LazyOptional.of(() -> new CoinPressItemHandler(itemHandler, (i) -> i == 2, (i, s) -> false)),
                    Direction.WEST, LazyOptional.of(() -> new CoinPressItemHandler(itemHandler, (index) -> index == 0,
                            (index, stack) -> itemHandler.isItemValid(0, stack))),
                    Direction.EAST, LazyOptional.of(() -> new CoinPressItemHandler(itemHandler, (i) -> i == 1,
                            (index, stack) -> itemHandler.isItemValid(1, stack))),
                    Direction.SOUTH, LazyOptional.of(() -> new CoinPressItemHandler(itemHandler, (index) -> index == 0 || index == 1,
                            (index, stack) -> itemHandler.isItemValid(0, stack) || itemHandler.isItemValid(1, stack))));

    public CoinPressBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.COINPRESS_ENTITY.get(), pPos, pBlockState);
        this.inventory = itemHandler;
        this.lastItemCrafted = ItemStack.EMPTY;
        this.usedRecipes = new Object2IntOpenHashMap<>();
        this.checkNewRecipe = true;
        this.coinpressData = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> CoinPressBlockEntity.this.stampingTime;
                    case 1 -> CoinPressBlockEntity.this.totalStampingTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> CoinPressBlockEntity.this.stampingTime = pValue;
                    case 1 -> CoinPressBlockEntity.this.totalStampingTime = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull Component getName() {
        return customName != null ? customName : Component.translatable("container.coin_press_block_gui");
    }

    @Override
    public boolean hasCustomName() {
        return this.getCustomName() != null;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return getName();
    }

    @Nullable
    @Override
    public Component getCustomName() {
        return customName;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        return new CoinPressMenu(pContainerId, pPlayerInventory, this, this.coinpressData);
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }

            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(CoinPressBlock.FACING);

                if (side == Direction.UP || side == Direction.DOWN) {
                    return directionWrappedHandlerMap.get(side).cast();
                }

                return switch (localDir) {
                    default -> directionWrappedHandlerMap.get(side).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("inventory", inventory.serializeNBT());
        nbt.putInt("stampingTime", this.stampingTime);
        nbt.putInt("totalStampingTime", totalStampingTime);
        lastItemCrafted = ItemStack.of(nbt.getCompound("lastRecipeUsed"));
        if (customName != null) {
            nbt.putString("CustomName", Component.Serializer.toJson(customName));
        }
        CompoundTag compoundRecipes = new CompoundTag();
        usedRecipes.forEach((recipeId, craftedAmount) -> compoundRecipes.putInt(recipeId.toString(), craftedAmount));
        nbt.put("usedRecipes", compoundRecipes);

    }

    private CompoundTag writeItems(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("lastRecipeUsed", lastItemCrafted.serializeNBT());
        nbt.put("inventory", inventory.serializeNBT());
        return nbt;
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return writeItems(new CompoundTag());
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        inventory.deserializeNBT(nbt.getCompound("inventory"));
        stampingTime = nbt.getInt("stampingTime");
        totalStampingTime = nbt.getInt("totalStampingTime");
        nbt.put("lastRecipeUsed", lastItemCrafted.serializeNBT());
        if (nbt.contains("CustomName", 8)) {
            customName = Component.Serializer.fromJson(nbt.getString("CustomName"));
        }
        CompoundTag compoundRecipes = nbt.getCompound("usedRecipes");
        for (String key : compoundRecipes.getAllKeys()) {
            usedRecipes.put(new ResourceLocation(key), compoundRecipes.getInt(key));
        }
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, @NotNull CoinPressBlockEntity coinPress) {
        boolean didInventoryChange = false;
        boolean flag = coinPress.isStamping();
        boolean flag1 = false;

        if (coinPress.hasInput()) {
            Optional<CoinPressRecipe> recipe = coinPress.getMatchingRecipe(new RecipeWrapper(coinPress.inventory));
            if (recipe.isPresent() && coinPress.canStamp(recipe.get())) {
                didInventoryChange = coinPress.processStamping(recipe.get(), coinPress);
            }
            else {
                coinPress.resetProgress();
                setChanged(level, pos, state);
            }
        }
        else if (coinPress.stampingTime > 0) {
            coinPress.resetProgress();
            setChanged(level, pos, state);
        }

        if (didInventoryChange) {
            setChanged(level, pos, state);
        }

        if (flag != coinPress.isStamping()) {
            flag1 = true;
            state = state.setValue(CoinPressBlock.CONDITIONAL, coinPress.isStamping());
            level.setBlock(pos, state, 3);
        }

        if (flag1) {
            setChanged(level, pos, state);
        }
    }

    private boolean processStamping(CoinPressRecipe recipe, CoinPressBlockEntity coinPress) {
        if (level == null) {
            return false;
        }
        ++stampingTime;
        totalStampingTime = recipe.getStampingTime();
        if (stampingTime >= totalStampingTime) {
            coinPress.craftItem(recipe, coinPress);
        }
        return true;
    }

    private void craftItem(CoinPressRecipe recipe, CoinPressBlockEntity coinPress) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, inventory.getStackInSlot(i));
        }

        // check first input slot for stamp, remove if at max dmg, if not, dmg it - otherwise shrink stack
        if (inventory.getStackInSlot(0).getDamageValue() == inventory.getStackInSlot(0).getMaxDamage()
                && inventory.getStackInSlot(0).is(ModTags.Items.CURRENCY_STAMPS)) {
            inventory.extractItem(0, 1, false);
        }
        else if (inventory.getStackInSlot(0).getDamageValue() != inventory.getStackInSlot(0).getMaxDamage()
                && inventory.getStackInSlot(0).is(ModTags.Items.CURRENCY_STAMPS)) {
            inventory.getStackInSlot(0).hurt(1, new SingleThreadedRandomSource(1), null);
        }
        else {
            inventory.extractItem(0, 1, false);
        }

        // check second input slot for stamp, remove if at max dmg, if not, dmg it - otherwise shrink stack
        if (inventory.getStackInSlot(1).getDamageValue() == inventory.getStackInSlot(1).getMaxDamage()
                && inventory.getStackInSlot(1).is(ModTags.Items.CURRENCY_STAMPS)) {
            inventory.extractItem(1, 1, false);
        }
        else if (inventory.getStackInSlot(1).getDamageValue() != inventory.getStackInSlot(1).getMaxDamage()
                && inventory.getStackInSlot(1).is(ModTags.Items.CURRENCY_STAMPS)) {
            inventory.getStackInSlot(1).hurt(1, new SingleThreadedRandomSource(1), null);
        }
        else {
            inventory.extractItem(1, 1, false);
        }

        inventory.setStackInSlot(RESULT_SLOT, new ItemStack(recipe.getResultItem().getItem(),
                inventory.getStackInSlot(RESULT_SLOT).getCount() + (recipe.getResultItem().getCount())));

        lastItemCrafted = recipe.getResultItem();
        coinPress.setRecipeUsed(recipe);
        coinPress.resetProgress();
        setChanged();
    }

    private boolean canStamp(CoinPressRecipe recipe) {
        if (hasInput()) {
            ItemStack resultStack = recipe.getResultItem();
            return !resultStack.isEmpty() && canInsertAmountIntoOutputSlot(inventory) && canInsertItemIntoOutputSlot(inventory, recipe.getResultItem());
        }
        else {
            return false;
        }
    }

    private Optional<CoinPressRecipe> getMatchingRecipe(RecipeWrapper inventory) {
        if (level == null) return Optional.empty();

        if (lastRecipeID != null) {
            Recipe<RecipeWrapper> recipe = ((RecipeManagerAccessor) level.getRecipeManager()).getRecipeMap(ModRecipes.RECIPE_TYPE_COINPRESS.get()).get(lastRecipeID);
            if (recipe instanceof CoinPressRecipe) {
                if (recipe.matches(inventory, level)) {
                    return Optional.of((CoinPressRecipe) recipe);
                }
                if (recipe.getResultItem().sameItem(lastItemCrafted)) {
                    return Optional.empty();
                }
            }
        }

        if (checkNewRecipe) {
            Optional<CoinPressRecipe> recipe = level.getRecipeManager().getRecipeFor(ModRecipes.RECIPE_TYPE_COINPRESS.get(), inventory, level);
            if (recipe.isPresent()) {
                lastRecipeID = recipe.get().getId();
                return recipe;
            }
        }

        checkNewRecipe = false;
        return Optional.empty();
    }

    private boolean hasInput() {
        for (int i = 0; i < RESULT_SLOT; ++i) {
            if (!inventory.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void resetProgress() {
        this.stampingTime = 0;
    }

    private static boolean canInsertItemIntoOutputSlot(@NotNull ItemStackHandler inventory, @NotNull ItemStack stack) {
        return inventory.getStackInSlot(2).getItem() == stack.getItem() || inventory.getStackInSlot(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(@NotNull ItemStackHandler inventory) {
        return inventory.getStackInSlot(2).getMaxStackSize() > inventory.getStackInSlot(2).getCount();
    }

    public boolean isStamping() {
        return this.stampingTime > 0;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation recipeID = recipe.getId();
            usedRecipes.addTo(recipeID, 1);
        }
    }

    @Nullable
    @Override
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void awardUsedRecipes(@NotNull Player player) {
        List<Recipe<?>> usedRecipes = getUsedRecipesAndPopExperience(player.level, player.position());
        player.awardRecipes(usedRecipes);
        usedRecipes.clear();
    }

    public List<Recipe<?>> getUsedRecipesAndPopExperience(Level level, Vec3 pos) {
        List<Recipe<?>> list = Lists.newArrayList();

        for (Object2IntMap.Entry<ResourceLocation> entry : usedRecipes.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                splitAndSpawnExperience((ServerLevel) level, pos, entry.getIntValue(), ((CoinPressRecipe) recipe).getExperience());
            });
        }

        return list;
    }

    private static void splitAndSpawnExperience(ServerLevel level, Vec3 pos, int craftedAmount, float experience) {
        int expTotal = Mth.floor((float) craftedAmount * experience);
        float expFraction = Mth.frac((float) craftedAmount * experience);
        if (expFraction != 0.0F && Math.random() < (double) expFraction) {
            ++expTotal;
        }

        ExperienceOrb.award(level, pos, expTotal);
    }

    @Override
    public boolean setRecipeUsed(@NotNull Level pLevel, @NotNull ServerPlayer pPlayer, @NotNull Recipe<?> pRecipe) {
        return RecipeHolder.super.setRecipeUsed(pLevel, pPlayer, pRecipe);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(Objects.requireNonNull(pkt.getTag()));
    }
}