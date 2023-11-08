package io.github.realyusufismail.realyusufismailcore.blocks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.realyusufismail.realyusufismailcore.core.init.RecipeSerializerInit;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipeCodecs;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class LegacySmithingRecipe implements ILegacySmithingRecipe {
    final Ingredient base;
    final Ingredient addition;
    final ItemStack result;

    public LegacySmithingRecipe(Ingredient base, Ingredient addition, ItemStack result) {
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

    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.LEGACY_SMITHING.get();
    }

    public boolean isIncomplete() {
        return Stream.of(this.base, this.addition).anyMatch(CommonHooks::hasNoElements);
    }

    public static class Serializer implements RecipeSerializer<LegacySmithingRecipe> {
        private static final Codec<LegacySmithingRecipe> CODEC =
                RecordCodecBuilder.create((p_44108_) -> p_44108_
                    .group(Ingredient.CODEC.fieldOf("base").forGetter((p_44110_) -> p_44110_.base),
                            Ingredient.CODEC.fieldOf("addition")
                                .forGetter((p_44105_) -> p_44105_.addition),
                            CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf("result")
                                .forGetter((p_44103_) -> p_44103_.result))
                    .apply(p_44108_, LegacySmithingRecipe::new));

        @Override
        public @NotNull Codec<LegacySmithingRecipe> codec() {
            return CODEC;
        }

        @Override
        public @Nullable LegacySmithingRecipe fromNetwork(FriendlyByteBuf p_44106_) {
            Ingredient ingredient = Ingredient.fromNetwork(p_44106_);
            Ingredient ingredient1 = Ingredient.fromNetwork(p_44106_);
            ItemStack itemstack = p_44106_.readItem();
            return new LegacySmithingRecipe(ingredient, ingredient1, itemstack);
        }

        public void toNetwork(FriendlyByteBuf p_266918_, LegacySmithingRecipe p_266728_) {
            p_266728_.base.toNetwork(p_266918_);
            p_266728_.addition.toNetwork(p_266918_);
            p_266918_.writeItem(p_266728_.result);
        }
    }
}
