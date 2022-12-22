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
import com.google.gson.*;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

import static io.github.realyusufismail.realyusufismailcore.recipe.YusufCraftingRecipeBuilder.determineBookCategory;

/**
 * @see net.minecraft.data.recipes.ShapedRecipeBuilder
 */
@SuppressWarnings("unused")
public class EnchantmentRecipeProvider implements RecipeBuilder {
    private final RecipeCategory category;
    private final Item result;
    private final int count;
    private int hideFlags;
    private int level;
    private Enchantment enchantment;
    private final List<String> rows = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();

    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    @Nullable
    private String group;

    public EnchantmentRecipeProvider(RecipeCategory category, @NotNull ItemLike itemLike,
            int count) {
        this.category = category;
        this.result = itemLike.asItem();
        this.count = count;
    }

    public static @NotNull EnchantmentRecipeProvider shaped(RecipeCategory category,
            ItemLike itemLike) {
        return shaped(category, itemLike, 1);
    }

    public static @NotNull EnchantmentRecipeProvider shaped(RecipeCategory category,
            ItemLike itemLike, int count) {
        return new EnchantmentRecipeProvider(category, itemLike, count);
    }


    public EnchantmentRecipeProvider define(Character character, TagKey<Item> item) {
        return this.define(character, Ingredient.of(item));
    }

    public EnchantmentRecipeProvider define(Character character, ItemLike itemLike) {
        return this.define(character, Ingredient.of(itemLike));
    }

    public EnchantmentRecipeProvider define(Character character, Ingredient ingredient) {
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

    public EnchantmentRecipeProvider pattern(String pattern) {
        if (!this.rows.isEmpty() && pattern.length() != this.rows.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(pattern);
            return this;
        }
    }

    @NotNull
    public EnchantmentRecipeProvider unlockedBy(@NotNull String creterionId,
            @NotNull CriterionTriggerInstance criterionTriggerInstance) {
        this.advancement.addCriterion(creterionId, criterionTriggerInstance);
        return this;
    }

    @NotNull
    public EnchantmentRecipeProvider setEnchantment(Enchantment enchantment, int level) {
        this.enchantment = enchantment;
        this.level = level;
        return this;
    }

    @NotNull
    public EnchantmentRecipeProvider setHideFlags(int hideFlags) {
        this.hideFlags = hideFlags;
        return this;
    }

    public @NotNull EnchantmentRecipeProvider group(@Nullable String group) {
        this.group = group;
        return this;
    }

    public @NotNull Item getResult() {
        return this.result;
    }

    public void save(@NotNull Consumer<FinishedRecipe> finishedRecipeConsumer,
            @NotNull ResourceLocation resourceLocation) {
        this.ensureValid(resourceLocation);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT)
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
            .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
            .requirements(RequirementsStrategy.OR);
        finishedRecipeConsumer.accept(new ShapedRecipeBuilder.Result(resourceLocation, this.result,
                this.count, this.group == null ? "" : this.group,
                determineBookCategory(this.category), this.rows, this.key, this.advancement,
                resourceLocation.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation resourceLocation) throws IllegalStateException {
        if (!this.rows.isEmpty()) {
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
            } else if (this.advancement.getCriteria().isEmpty()) {
                throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
            }
        } else {
            throw new IllegalStateException(
                    "No pattern is defined for shaped recipe " + resourceLocation + "!");
        }
    }

    public record Result(CraftingBookCategory category, ResourceLocation id, Item result, int count,
            String group, List<String> pattern, Map<Character, Ingredient> key,
            Advancement.Builder advancement, Enchantment enchantment, int enchantmentLevel,
            int hideFlags, ResourceLocation advancementId) implements FinishedRecipe {

        @Override
        public void serializeRecipeData(@NotNull JsonObject jsonObject) {

            if (!this.group.isEmpty()) {
                jsonObject.addProperty("group", this.group);
            }
            jsonObject.addProperty("category", this.category.getSerializedName());
            JsonArray jsonArray = new JsonArray();

            for (String s : this.pattern) {
                jsonArray.add(s);
            }

            jsonObject.add("pattern", jsonArray);
            JsonObject jsonobject = new JsonObject();

            for (Map.Entry<Character, Ingredient> entry : this.key.entrySet()) {
                jsonobject.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
            }

            jsonObject.add("key", jsonobject);
            JsonObject jsonObject1 = new JsonObject();
            jsonObject1.addProperty("item",
                    Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());
            if (this.count > 1) {
                jsonObject1.addProperty("count", this.count);
            }

            // enchantment
            JsonArray jsonArray1 = new JsonArray();
            JsonObject jsonObject2 = new JsonObject();
            JsonObject jsonObject3 = new JsonObject();
            jsonObject3.addProperty("id",
                    Objects.requireNonNull(ForgeRegistries.ENCHANTMENTS.getKey(this.enchantment))
                        .toString());
            jsonObject3.addProperty("lvl", enchantmentLevel);
            jsonArray1.add(jsonObject3);
            jsonObject2.add("Enchantments", jsonArray1);
            jsonObject2.addProperty("HideFlags", hideFlags);
            jsonObject1.add("nbt", jsonObject2);

            jsonObject.add("result", jsonObject1);
        }

        public @NotNull RecipeSerializer<?> getType() {
            return RecipeSerializer.SHAPED_RECIPE;
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

