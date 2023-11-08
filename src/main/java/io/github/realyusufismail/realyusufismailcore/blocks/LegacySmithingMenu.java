package io.github.realyusufismail.realyusufismailcore.blocks;

import io.github.realyusufismail.realyusufismailcore.core.init.BlockInitCore;
import io.github.realyusufismail.realyusufismailcore.core.init.MenuTypeInit;
import io.github.realyusufismail.realyusufismailcore.core.init.RecipeTypeInit;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class LegacySmithingMenu extends ItemCombinerMenu {
    private final Level level;
    @Nullable
    private ILegacySmithingRecipe selectedRecipe;
    private final List<RecipeHolder<ILegacySmithingRecipe>> recipes;

    public LegacySmithingMenu(int p_267173_, Inventory p_267175_) {
        this(p_267173_, p_267175_, ContainerLevelAccess.NULL);
    }

    public LegacySmithingMenu(int p_266937_, Inventory p_267213_, ContainerLevelAccess p_266723_) {
        super(MenuTypeInit.LEGACY_SMITHING_TABLE.get(), p_266937_, p_267213_, p_266723_);
        this.level = p_267213_.player.level();
        this.recipes = this.level.getRecipeManager()
            .getAllRecipesFor(RecipeTypeInit.LEGACY_SMITHING.get())
            .stream()
            .filter(Objects::nonNull)
            .toList();
    }

    protected @NotNull ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create()
            .withSlot(0, 27, 47, (p_266883_) -> true)
            .withSlot(1, 76, 47, (p_267323_) -> true)
            .withResultSlot(2, 134, 47)
            .build();
    }

    protected boolean isValidBlock(BlockState p_266887_) {
        return p_266887_.is(BlockInitCore.LEGACY_SMITHING_TABLE.get());
    }

    protected boolean mayPickup(Player p_267240_, boolean p_266679_) {
        return this.selectedRecipe != null
                && this.selectedRecipe.matches(this.inputSlots, this.level);
    }

    protected void onTake(Player p_267006_, ItemStack p_266731_) {
        p_266731_.onCraftedBy(p_267006_.level(), p_267006_, p_266731_.getCount());
        this.resultSlots.awardUsedRecipes(p_267006_, List.of(p_266731_));
        this.shrinkStackInSlot(0);
        this.shrinkStackInSlot(1);
        this.access.execute((p_267191_, p_267098_) -> {
            p_267191_.levelEvent(1044, p_267098_, 0);
        });
    }

    private void shrinkStackInSlot(int p_267273_) {
        ItemStack itemstack = this.inputSlots.getItem(p_267273_);
        itemstack.shrink(1);
        this.inputSlots.setItem(p_267273_, itemstack);
    }

    /**
     * Called when the Anvil Input Slot changes, calculates the new result and puts it in the output
     * slot.
     */
    public void createResult() {
        List<RecipeHolder<ILegacySmithingRecipe>> list = this.level.getRecipeManager()
            .getRecipesFor(RecipeTypeInit.LEGACY_SMITHING.get(), this.inputSlots, this.level)
            .stream()
            .filter(Objects::nonNull)
            .toList();

        if (list.isEmpty()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        } else {
            RecipeHolder<ILegacySmithingRecipe> oldSmithingRecipe = list.get(0);

            ItemStack itemstack = oldSmithingRecipe.value()
                .assemble(this.inputSlots, this.level.registryAccess());

            if (itemstack.isItemEnabled(this.level.enabledFeatures())) {
                this.selectedRecipe = oldSmithingRecipe.value();
                this.resultSlots.setRecipeUsed(oldSmithingRecipe);
                this.resultSlots.setItem(0, itemstack);
            }

        }

    }

    public int getSlotToQuickMoveTo(ItemStack p_267241_) {
        return this.shouldQuickMoveToAdditionalSlot(p_267241_) ? 1 : 0;
    }

    protected boolean shouldQuickMoveToAdditionalSlot(ItemStack p_267176_) {
        return this.recipes.stream()
            .anyMatch((p_267065_) -> p_267065_.value().isAdditionIngredient(p_267176_));
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code.
     * The stack passed in is null for the initial slot that was double-clicked.
     */
    public boolean canTakeItemForPickAll(ItemStack p_266810_, Slot p_267252_) {
        return p_267252_.container != this.resultSlots
                && super.canTakeItemForPickAll(p_266810_, p_267252_);
    }
}
