package net.warrentode.recipebooktest.block.entity.container;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.warrentode.recipebooktest.block.entity.CoinPressBlockEntity;
import net.warrentode.recipebooktest.sounds.ModSounds;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;

public class CoinPressResultSlot extends SlotItemHandler {
    public final CoinPressBlockEntity coinPress;
    private final Player player;
    private int removeCount;
    private final ContainerLevelAccess canInteractWithCallable;
    long lastSoundTime;

    public CoinPressResultSlot(Player player, CoinPressBlockEntity blockEntity, IItemHandler inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
        this.coinPress = blockEntity;
        this.canInteractWithCallable = ContainerLevelAccess.create(Objects.requireNonNull(coinPress.getLevel()), coinPress.getBlockPos());
        this.player = player;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    @Nonnull
    public ItemStack remove(int amount) {
        if (this.hasItem()) {
            this.removeCount += Math.min(amount, this.getItem().getCount());
        }

        return super.remove(amount);
    }

    @Override
    public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
        this.checkTakeAchievements(stack);
        canInteractWithCallable.execute((level, pos) -> {
            long l = level.getGameTime();
            if (this.lastSoundTime != l) {
                level.playSound(null, pos, ModSounds.COINPRESS_TAKE_RESULT.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                this.lastSoundTime = l;
            }
        });
        super.onTake(player, stack);
    }

    @Override
    protected void onQuickCraft(@NotNull ItemStack stack, int amount) {
        this.removeCount += amount;
        this.checkTakeAchievements(stack);
    }

    @Override
    protected void checkTakeAchievements(ItemStack stack) {
        stack.onCraftedBy(this.player.level, this.player, this.removeCount);

        if (!this.player.level.isClientSide) {
            coinPress.awardUsedRecipes(this.player);
        }

        this.removeCount = 0;
    }
}