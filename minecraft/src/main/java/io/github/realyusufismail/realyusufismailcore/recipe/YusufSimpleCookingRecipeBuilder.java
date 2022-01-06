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
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Taken from
 * 
 * @see SimpleCookingRecipeBuilder
 */
@SuppressWarnings("unused")
public class YusufSimpleCookingRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final Ingredient ingredient;
    private final float experience;
    private final int cookingTime;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    @Nullable
    private String group;
    private final SimpleCookingSerializer<?> serializer;

    private YusufSimpleCookingRecipeBuilder(@NotNull ItemLike itemLike, Ingredient ingredient,
            float experience, int cookingTime, SimpleCookingSerializer<?> serializer) {
        this.result = itemLike.asItem();
        this.ingredient = ingredient;
        this.experience = experience;
        this.cookingTime = cookingTime;
        this.serializer = serializer;
    }

    public static @NotNull YusufSimpleCookingRecipeBuilder cooking(Ingredient ingredient,
            ItemLike itemLike, float experience, int cookingTime,
            SimpleCookingSerializer<?> serializer) {
        return new YusufSimpleCookingRecipeBuilder(itemLike, ingredient, experience, cookingTime,
                serializer);
    }

    public static @NotNull YusufSimpleCookingRecipeBuilder campfireCooking(Ingredient ingredient,
            ItemLike itemLike, float experience, int cookingTime) {
        return cooking(ingredient, itemLike, experience, cookingTime,
                RecipeSerializer.CAMPFIRE_COOKING_RECIPE);
    }

    public static @NotNull YusufSimpleCookingRecipeBuilder blasting(Ingredient ingredient,
            ItemLike itemLike, float experience, int cookingTime) {
        return cooking(ingredient, itemLike, experience, cookingTime,
                RecipeSerializer.BLASTING_RECIPE);
    }

    public static @NotNull YusufSimpleCookingRecipeBuilder smelting(Ingredient ingredient,
            ItemLike itemLike, float experience, int cookingTime) {
        return cooking(ingredient, itemLike, experience, cookingTime,
                RecipeSerializer.SMELTING_RECIPE);
    }

    public static @NotNull YusufSimpleCookingRecipeBuilder smoking(Ingredient ingredient,
            ItemLike itemLike, float experience, int cookingTime) {
        return cooking(ingredient, itemLike, experience, cookingTime,
                RecipeSerializer.SMOKING_RECIPE);
    }

    public @NotNull YusufSimpleCookingRecipeBuilder unlockedBy(@NotNull String creterionId,
            @NotNull CriterionTriggerInstance criterionTriggerInstance) {
        this.advancement.addCriterion(creterionId, criterionTriggerInstance);
        return this;
    }

    public @NotNull YusufSimpleCookingRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    public @NotNull Item getResult() {
        return this.result;
    }

    public void save(@NotNull Consumer<FinishedRecipe> finishedRecipeConsumer,
            @NotNull ResourceLocation resourceLocation) {
        this.ensureValid(resourceLocation);
        this.advancement.parent(new ResourceLocation("recipes/root"))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
            .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
            .requirements(RequirementsStrategy.OR);
        finishedRecipeConsumer.accept(new YusufSimpleCookingRecipeBuilder.Result(resourceLocation,
                this.group == null ? "" : this.group, this.ingredient, this.result, this.experience,
                this.cookingTime, this.advancement,
                new ResourceLocation(resourceLocation.getNamespace(),
                        "recipes/" + Objects.requireNonNull(this.result.getItemCategory())
                            .getRecipeFolderName() + "/" + resourceLocation.getPath()),
                this.serializer));
    }

    private void ensureValid(ResourceLocation resourceLocation) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
        }
    }

    public record Result(ResourceLocation id, String group, Ingredient ingredient, Item result,
            float experience, int cookingTime, Advancement.Builder advancement,
            ResourceLocation advancementId,
            RecipeSerializer<? extends AbstractCookingRecipe> serializer)
            implements
                FinishedRecipe {

        public void serializeRecipeData(@NotNull JsonObject jsonObject) {
            if (!this.group.isEmpty()) {
                jsonObject.addProperty("group", this.group);
            }

            jsonObject.add("ingredient", this.ingredient.toJson());
            jsonObject.addProperty("result",
                    Objects
                        .requireNonNull(ForgeRegistries.ITEMS.getKey(this.result), "Item is null")
                        .toString());
            jsonObject.addProperty("experience", this.experience);
            jsonObject.addProperty("cookingtime", this.cookingTime);
        }

        public @NotNull RecipeSerializer<?> getType() {
            return this.serializer;
        }

        public @NotNull ResourceLocation getId() {
            return this.id;
        }

        public @NotNull JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
