/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.realyusufismailcore.recipe;

import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Taken from
 *
 * @see SimpleCookingRecipeBuilder
 */
@SuppressWarnings("unused")
public class YusufSimpleCookingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final CookingBookCategory bookCategory;
    private final Item result;
    private final Ingredient ingredient;
    private final float experience;
    private final int cookingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;
    private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

    private YusufSimpleCookingRecipeBuilder(RecipeCategory category,
            CookingBookCategory bookCategory, @NotNull ItemLike result, Ingredient ingredient,
            float experience, int cookingTime,
            RecipeSerializer<? extends AbstractCookingRecipe> serializer) {
        this.category = category;
        this.bookCategory = bookCategory;
        this.result = result.asItem();
        this.ingredient = ingredient;
        this.experience = experience;
        this.cookingTime = cookingTime;
        this.serializer = serializer;
    }

    public static @NotNull YusufSimpleCookingRecipeBuilder cooking(RecipeCategory category,
            Ingredient ingredient, ItemLike result, float experience, int cookingTime,
            RecipeSerializer<? extends AbstractCookingRecipe> serializer) {
        return new YusufSimpleCookingRecipeBuilder(category,
                determineRecipeCategory(serializer, result), result, ingredient, experience,
                cookingTime, serializer);
    }

    public static @NotNull YusufSimpleCookingRecipeBuilder campfireCooking(RecipeCategory category,
            Ingredient ingredient, ItemLike result, float experience, int cookingTime) {
        return new YusufSimpleCookingRecipeBuilder(category, CookingBookCategory.FOOD, result,
                ingredient, experience, cookingTime, RecipeSerializer.CAMPFIRE_COOKING_RECIPE);
    }

    public static @NotNull YusufSimpleCookingRecipeBuilder blasting(RecipeCategory category,
            Ingredient ingredient, ItemLike result, float experience, int cookingTime) {
        return new YusufSimpleCookingRecipeBuilder(category,
                determineBlastingRecipeCategory(result), result, ingredient, experience,
                cookingTime, RecipeSerializer.BLASTING_RECIPE);
    }

    public static @NotNull YusufSimpleCookingRecipeBuilder smelting(RecipeCategory category,
            Ingredient ingredient, ItemLike result, float experience, int cookingTime) {
        return new YusufSimpleCookingRecipeBuilder(category,
                determineSmeltingRecipeCategory(result), result, ingredient, experience,
                cookingTime, RecipeSerializer.SMELTING_RECIPE);
    }

    public static @NotNull YusufSimpleCookingRecipeBuilder smoking(RecipeCategory category,
            Ingredient ingredient, ItemLike result, float experience, int cookingTime) {
        return new YusufSimpleCookingRecipeBuilder(category, CookingBookCategory.FOOD, result,
                ingredient, experience, cookingTime, RecipeSerializer.SMOKING_RECIPE);
    }

    private static CookingBookCategory determineRecipeCategory(
            RecipeSerializer<? extends AbstractCookingRecipe> pSerializer, ItemLike pResult) {
        if (pSerializer == RecipeSerializer.SMELTING_RECIPE) {
            return determineSmeltingRecipeCategory(pResult);
        } else if (pSerializer == RecipeSerializer.BLASTING_RECIPE) {
            return determineBlastingRecipeCategory(pResult);
        } else if (pSerializer != RecipeSerializer.SMOKING_RECIPE
                && pSerializer != RecipeSerializer.CAMPFIRE_COOKING_RECIPE) {
            throw new IllegalStateException("Unknown cooking recipe type");
        } else {
            return CookingBookCategory.FOOD;
        }
    }

    private static CookingBookCategory determineSmeltingRecipeCategory(ItemLike result) {
        if (result.asItem().isEdible()) {
            return CookingBookCategory.FOOD;
        } else {
            return result.asItem() instanceof BlockItem ? CookingBookCategory.BLOCKS
                    : CookingBookCategory.MISC;
        }
    }

    private static CookingBookCategory determineBlastingRecipeCategory(ItemLike result) {
        return result.asItem() instanceof BlockItem ? CookingBookCategory.BLOCKS
                : CookingBookCategory.MISC;
    }


    public @NotNull YusufSimpleCookingRecipeBuilder unlockedBy(@NotNull String creterionId,
            @NotNull Criterion<?> criterion) {
        this.criteria.put(creterionId, criterion);
        return this;
    }

    public @NotNull YusufSimpleCookingRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    public @NotNull Item getResult() {
        return this.result;
    }

    public void save(@NotNull RecipeOutput recipeOutput,
            @NotNull ResourceLocation resourceLocation) {
        this.ensureValid(resourceLocation);

        Advancement.Builder advancementBuilder = recipeOutput.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
            .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
            .requirements(AdvancementRequirements.Strategy.OR);

        recipeOutput.accept(new Result(resourceLocation, this.group == null ? "" : this.group,
                this.bookCategory, this.ingredient, this.result, this.experience, this.cookingTime,
                advancementBuilder.build(resourceLocation
                    .withPrefix("recipes/" + this.category.getFolderName() + "/")),
                this.serializer));
    }

    private void ensureValid(ResourceLocation resourceLocation) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
        }
    }

    public record Result(ResourceLocation id, String group, CookingBookCategory bookCategory,
            Ingredient ingredient, Item result, float experience, int cookingTime,
            AdvancementHolder advancement,
            RecipeSerializer<? extends AbstractCookingRecipe> serializer)
            implements
                FinishedRecipe {

        public void serializeRecipeData(@NotNull JsonObject jsonObject) {
            if (!this.group.isEmpty()) {
                jsonObject.addProperty("group", this.group);
            }

            jsonObject.add("ingredient", this.ingredient.toJson(false));
            jsonObject.addProperty("result",
                    Objects
                        .requireNonNull(ForgeRegistries.ITEMS.getKey(this.result), "Item is null")
                        .toString());
            jsonObject.addProperty("experience", this.experience);
            jsonObject.addProperty("cookingtime", this.cookingTime);
        }

        public @NotNull RecipeSerializer<?> type() {
            return this.serializer;
        }

        public @NotNull JsonObject serializeAdvancement() {
            return this.advancement.value().serializeToJson();
        }
    }
}
