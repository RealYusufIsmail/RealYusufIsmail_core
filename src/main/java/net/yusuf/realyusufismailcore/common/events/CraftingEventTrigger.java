package net.yusuf.realyusufismailcore.common.events;


import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;

public class CraftingEventTrigger extends PlayerEvent {
    @Nonnull
    private final ItemStack left; // The left side of the input
    @Nonnull
    private final ItemStack right; // The right side of the input
    @Nonnull
    private final ItemStack output; // Set this to set the output stack

    public CraftingEventTrigger(Player player, @Nonnull ItemStack left, @Nonnull ItemStack right,
                                @Nonnull ItemStack output) {
        super(player);
        this.output = output;
        this.left = left;
        this.right = right;
    }

    @Nonnull
    public ItemStack getItemResult() {
        return output;
    }

    /**
     * Get the first item input into the anvil
     *
     * @return the first input slot
     */
    @Nonnull
    public ItemStack getItemInput() {
        return left;
    }

    /**
     * Get the second item input into the anvil
     *
     * @return the second input slot
     */
    @Nonnull
    public ItemStack getIngredientInput() {
        return right;
    }

}

