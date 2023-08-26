package io.github.realyusufismail.realyusufismailcore.blocks.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class OldSmithingRecipe implements SmithingRecipe {
    final Ingredient base;
    final Ingredient addition;
    final ItemStack result;
    private final ResourceLocation id;

    public OldSmithingRecipe(ResourceLocation p_266733_, Ingredient p_267056_, Ingredient p_267062_, ItemStack p_267137_) {
        this.id = p_266733_;
        this.base = p_267056_;
        this.addition = p_267062_;
        this.result = p_267137_;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(Container p_267029_, Level p_267244_) {
        return this.base.test(p_267029_.getItem(0)) && this.addition.test(p_267029_.getItem(1));
    }

    public ItemStack assemble(Container p_266770_, RegistryAccess p_267229_) {
        ItemStack itemstack = this.result.copy();
        CompoundTag compoundtag = p_266770_.getItem(0).getTag();
        if (compoundtag != null) {
            itemstack.setTag(compoundtag.copy());
        }

        return itemstack;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canCraftInDimensions(int p_267193_, int p_266967_) {
        return p_267193_ * p_266967_ >= 2;
    }

    public @NotNull ItemStack getResultItem(RegistryAccess p_267153_) {
        return this.result;
    }

    public boolean isTemplateIngredient(ItemStack p_267003_) {
        return false;
    }

    public boolean isBaseIngredient(ItemStack p_266836_) {
        return this.base.test(p_266836_);
    }

    public boolean isAdditionIngredient(ItemStack p_267128_) {
        return this.addition.test(p_267128_);
    }

    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeSerializer.SMITHING;
    }

    public boolean isIncomplete() {
        return Stream.of(this.base, this.addition).anyMatch(ForgeHooks::hasNoElements);
    }

    public static class Serializer implements RecipeSerializer<OldSmithingRecipe> {
        public @NotNull OldSmithingRecipe fromJson(ResourceLocation p_267011_, JsonObject p_267297_) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(p_267297_, "base"));
            Ingredient ingredient1 = Ingredient.fromJson(GsonHelper.getAsJsonObject(p_267297_, "addition"));
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(p_267297_, "result"));
            return new OldSmithingRecipe(p_267011_, ingredient, ingredient1, itemstack);
        }

        public OldSmithingRecipe fromNetwork(ResourceLocation p_266671_, FriendlyByteBuf p_266826_) {
            Ingredient ingredient = Ingredient.fromNetwork(p_266826_);
            Ingredient ingredient1 = Ingredient.fromNetwork(p_266826_);
            ItemStack itemstack = p_266826_.readItem();
            return new OldSmithingRecipe(p_266671_, ingredient, ingredient1, itemstack);
        }

        public void toNetwork(FriendlyByteBuf p_266918_, OldSmithingRecipe p_266728_) {
            p_266728_.base.toNetwork(p_266918_);
            p_266728_.addition.toNetwork(p_266918_);
            p_266918_.writeItem(p_266728_.result);
        }
    }
}
