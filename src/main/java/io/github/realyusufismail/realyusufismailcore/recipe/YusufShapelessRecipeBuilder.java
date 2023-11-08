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

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.github.realyusufismail.realyusufismailcore.recipe.YusufCraftingRecipeBuilder.determineBookCategory;

/**
 * Taken from
 *
 * @see ShapelessRecipeBuilder
 */
@SuppressWarnings("unused")
public class YusufShapelessRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Item result;
    private final int count;
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    public YusufShapelessRecipeBuilder(RecipeCategory category, @NotNull ItemLike itemLike,
            int count) {
        this.category = category;
        this.result = itemLike.asItem();
        this.count = count;
    }

    public static @NotNull YusufShapelessRecipeBuilder shapeless(RecipeCategory category,
            @NotNull ItemLike itemLike) {
        return new YusufShapelessRecipeBuilder(category, itemLike, 1);
    }

    public static @NotNull YusufShapelessRecipeBuilder shapeless(RecipeCategory category,
            @NotNull ItemLike itemLike, int count) {
        return new YusufShapelessRecipeBuilder(category, itemLike, count);
    }

    public YusufShapelessRecipeBuilder requires(TagKey<Item> itemTag) {
        return this.requires(Ingredient.of(itemTag));
    }

    public YusufShapelessRecipeBuilder requires(ItemLike itemLike) {
        return this.requires(itemLike, 1);
    }

    public YusufShapelessRecipeBuilder requires(ItemLike itemLike, int count) {
        for (int i = 0; i < count; ++i) {
            this.requires(Ingredient.of(itemLike));
        }

        return this;
    }

    public YusufShapelessRecipeBuilder requires(Ingredient ingredient) {
        return this.requires(ingredient, 1);
    }

    public YusufShapelessRecipeBuilder requires(Ingredient ingredient, int count) {
        for (int i = 0; i < count; ++i) {
            this.ingredients.add(ingredient);
        }

        return this;
    }

    public @NotNull YusufShapelessRecipeBuilder unlockedBy(@NotNull String creterionId,
            @NotNull Criterion<?> criterion) {
        this.criteria.put(creterionId, criterion);
        return this;
    }

    public @NotNull YusufShapelessRecipeBuilder group(@Nullable String group) {
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

        recipeOutput.accept(new Result(determineBookCategory(this.category), resourceLocation,
                this.result, this.count, this.group == null ? "" : this.group, this.ingredients,
                advancementBuilder.build(resourceLocation
                    .withPrefix("recipes/" + this.category.getFolderName() + "/"))));
    }

    private void ensureValid(ResourceLocation resourceLocation) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
        }
    }

    public record Result(CraftingBookCategory category, ResourceLocation id, Item result, int count,
            String group, List<Ingredient> ingredients,
            AdvancementHolder advancement) implements FinishedRecipe {

        public void serializeRecipeData(@NotNull JsonObject jsonObject) {
            if (!this.group.isEmpty()) {
                jsonObject.addProperty("group", this.group);
            }

            jsonObject.addProperty("category", this.category.getSerializedName());
            JsonArray jsonArray = new JsonArray();

            for (Ingredient ingredient : this.ingredients) {
                jsonArray.add(ingredient.toJson(false));
            }

            jsonObject.add("ingredients", jsonArray);
            JsonObject jsonObjectTwo = new JsonObject();
            jsonObjectTwo.addProperty("item",
                    Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());
            if (this.count > 1) {
                jsonObjectTwo.addProperty("count", this.count);
            }

            jsonObject.add("result", jsonObjectTwo);
        }

        public @NotNull RecipeSerializer<?> type() {
            return RecipeSerializer.SHAPELESS_RECIPE;
        }

        public @NotNull JsonObject serializeAdvancement() {
            return this.advancement.value().serializeToJson();
        }
    }
}
