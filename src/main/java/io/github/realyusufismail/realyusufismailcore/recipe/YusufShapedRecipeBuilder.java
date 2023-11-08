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
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
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
import java.util.*;

import static io.github.realyusufismail.realyusufismailcore.recipe.YusufCraftingRecipeBuilder.determineBookCategory;

/**
 * Taken from
 *
 * @see ShapedRecipeBuilder
 */
@SuppressWarnings("unused")
public class YusufShapedRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Item result;
    private final int count;
    private final List<String> rows = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;
    private boolean showNotification = true;


    public YusufShapedRecipeBuilder(RecipeCategory category, ItemLike itemLike, int count) {
        this.category = category;
        this.result = itemLike.asItem();
        this.count = count;
    }

    public static YusufShapedRecipeBuilder shaped(RecipeCategory category, ItemLike itemLike) {
        return shaped(category, itemLike, 1);
    }

    public static YusufShapedRecipeBuilder shaped(RecipeCategory category, ItemLike itemLike,
            int count) {
        return new YusufShapedRecipeBuilder(category, itemLike, count);
    }

    public YusufShapedRecipeBuilder define(Character character, TagKey<Item> itemTag) {
        return this.define(character, Ingredient.of(itemTag));
    }

    public YusufShapedRecipeBuilder define(Character character, ItemLike itemLike) {
        return this.define(character, Ingredient.of(itemLike));
    }

    public YusufShapedRecipeBuilder define(Character character, Ingredient ingredient) {
        if (this.key.containsKey(character)) {
            throw new IllegalArgumentException("Symbol '" + character + "' is already defined!");
        } else if (character == ' ') {
            throw new IllegalArgumentException(
                    "Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(character, ingredient);
            return this;
        }
    }

    public YusufShapedRecipeBuilder pattern(String pattern) {
        if (!this.rows.isEmpty() && pattern.length() != this.rows.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(pattern);
            return this;
        }
    }

    public @NotNull YusufShapedRecipeBuilder unlockedBy(@NotNull String creterionId,
            @NotNull Criterion<?> criterion) {
        this.criteria.put(creterionId, criterion);
        return this;
    }

    public @NotNull YusufShapedRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    public YusufShapedRecipeBuilder showNotification(boolean p_273326_) {
        this.showNotification = p_273326_;
        return this;
    }

    public @NotNull Item getResult() {
        return this.result;
    }

    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        this.ensureValid(resourceLocation);

        Advancement.Builder advancementBuilder = recipeOutput.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
            .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
            .requirements(AdvancementRequirements.Strategy.OR);

        recipeOutput.accept(new Result(determineBookCategory(this.category), resourceLocation,
                this.result, this.count, this.group == null ? "" : this.group, this.rows, this.key,
                advancementBuilder.build(resourceLocation
                    .withPrefix("recipes/" + this.category.getFolderName() + "/")),
                this.showNotification));
    }

    private void ensureValid(ResourceLocation resourceLocation) {
        if (this.rows.isEmpty()) {
            throw new IllegalStateException(
                    "No pattern is defined for shaped recipe " + resourceLocation + "!");
        } else {
            Set<Character> set = Sets.newHashSet(this.key.keySet());
            set.remove(' ');

            for (String s : this.rows) {
                for (int i = 0; i < s.length(); ++i) {
                    char c0 = s.charAt(i);
                    if (!this.key.containsKey(c0) && c0 != ' ') {
                        throw new IllegalStateException("Pattern in recipe " + resourceLocation
                                + " uses undefined symbol '" + c0 + "'");
                    }

                    set.remove(c0);
                }
            }

            if (!set.isEmpty()) {
                throw new IllegalStateException(
                        "Ingredients are defined but not used in pattern for recipe "
                                + resourceLocation);
            } else if (this.rows.size() == 1 && this.rows.get(0).length() == 1) {
                throw new IllegalStateException("Shaped recipe " + resourceLocation
                        + " only takes in a single item - should it be a shapeless recipe instead?");
            } else if (this.criteria.isEmpty()) {
                throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
            }
        }
    }

    public record Result(CraftingBookCategory category, ResourceLocation id, Item result, int count,
            String group, List<String> pattern, Map<Character, Ingredient> key,
            AdvancementHolder advancement, boolean showNotification) implements FinishedRecipe {

        public void serializeRecipeData(@NotNull JsonObject jsonObject) {
            if (!this.group.isEmpty()) {
                jsonObject.addProperty("group", this.group);
            }
            jsonObject.addProperty("category", this.category.getSerializedName());
            JsonArray jsonarray = new JsonArray();

            for (String s : this.pattern) {
                jsonarray.add(s);
            }

            jsonObject.add("pattern", jsonarray);
            JsonObject jsonObject1 = new JsonObject();

            for (Map.Entry<Character, Ingredient> entry : this.key.entrySet()) {
                jsonObject1.add(String.valueOf(entry.getKey()), entry.getValue().toJson(false));
            }

            jsonObject.add("key", jsonObject1);
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("item",
                    Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());
            if (this.count > 1) {
                jsonObject2.addProperty("count", this.count);
            }

            jsonObject.add("result", jsonObject2);
            jsonObject.addProperty("show_notification", this.showNotification);
        }

        public @NotNull RecipeSerializer<?> type() {
            return RecipeSerializer.SHAPED_RECIPE;
        }

        public @NotNull JsonObject serializeAdvancement() {
            return this.advancement.value().serializeToJson();
        }
    }
}
