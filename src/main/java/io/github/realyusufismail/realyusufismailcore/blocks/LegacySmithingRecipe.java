package io.github.realyusufismail.realyusufismailcore.blocks;

import com.google.gson.JsonObject;
import io.github.realyusufismail.realyusufismailcore.core.init.RecipeSerializerInit;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class LegacySmithingRecipe implements LegacySmithingShapedRecipe {
    final Ingredient base;
    final Ingredient addition;
    final ItemStack result;
    private final ResourceLocation id;

    public LegacySmithingRecipe(ResourceLocation resourceLocation, Ingredient base,
            Ingredient addition, ItemStack result) {
        this.id = resourceLocation;
        this.base = base;
        this.addition = addition;
        this.result = result;
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
        return RecipeSerializerInit.LEGACY_SMITHING.get();
    }

    public boolean isIncomplete() {
        return Stream.of(this.base, this.addition).anyMatch(ForgeHooks::hasNoElements);
    }

    @Override
    public int getRecipeWidth() {
        return 0;
    }

    @Override
    public int getRecipeHeight() {
        return 0;
    }

    public static class Serializer implements RecipeSerializer<LegacySmithingRecipe> {
        public LegacySmithingRecipe fromJson(ResourceLocation p_267011_, JsonObject p_267297_) {
            Ingredient ingredient =
                    Ingredient.fromJson(GsonHelper.getAsJsonObject(p_267297_, "base"));
            Ingredient ingredient1 =
                    Ingredient.fromJson(GsonHelper.getAsJsonObject(p_267297_, "addition"));
            ItemStack itemstack =
                    ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(p_267297_, "result"));
            return new LegacySmithingRecipe(p_267011_, ingredient, ingredient1, itemstack);
        }

        public LegacySmithingRecipe fromNetwork(ResourceLocation p_266671_,
                FriendlyByteBuf p_266826_) {
            Ingredient ingredient = Ingredient.fromNetwork(p_266826_);
            Ingredient ingredient1 = Ingredient.fromNetwork(p_266826_);
            ItemStack itemstack = p_266826_.readItem();
            return new LegacySmithingRecipe(p_266671_, ingredient, ingredient1, itemstack);
        }

        public void toNetwork(FriendlyByteBuf p_266918_, LegacySmithingRecipe p_266728_) {
            p_266728_.base.toNetwork(p_266918_);
            p_266728_.addition.toNetwork(p_266918_);
            p_266918_.writeItem(p_266728_.result);
        }
    }
}
