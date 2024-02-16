/*
 * Copyright 2024 RealYusufIsmail.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package io.github.realyusufismail.realyusufismailcore.recipe;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @see ShapelessRecipeBuilder
 */
public class YusufShapelessRecipeBuilder {
    private final Item result;
    private final int count;
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private String group;

    public YusufShapelessRecipeBuilder(IItemProvider pResult, int pCount) {
        this.result = pResult.asItem();
        this.count = pCount;
    }

    /**
     * Creates a new builder for a shapeless recipe.
     */
    public static YusufShapelessRecipeBuilder shapeless(IItemProvider pResult) {
        return new YusufShapelessRecipeBuilder(pResult, 1);
    }

    /**
     * Creates a new builder for a shapeless recipe.
     */
    public static YusufShapelessRecipeBuilder shapeless(IItemProvider pResult, int pCount) {
        return new YusufShapelessRecipeBuilder(pResult, pCount);
    }

    /**
     * Adds an ingredient that can be any item in the given tag.
     */
    public YusufShapelessRecipeBuilder requires(ITag<Item> pTag) {
        return this.requires(Ingredient.of(pTag));
    }

    /**
     * Adds an ingredient of the given item.
     */
    public YusufShapelessRecipeBuilder requires(IItemProvider pItem) {
        return this.requires(pItem, 1);
    }

    /**
     * Adds the given ingredient multiple times.
     */
    public YusufShapelessRecipeBuilder requires(IItemProvider pItem, int pQuantity) {
        for (int i = 0; i < pQuantity; ++i) {
            this.requires(Ingredient.of(pItem));
        }

        return this;
    }

    /**
     * Adds an ingredient.
     */
    public YusufShapelessRecipeBuilder requires(Ingredient pIngredient) {
        return this.requires(pIngredient, 1);
    }

    /**
     * Adds an ingredient multiple times.
     */
    public YusufShapelessRecipeBuilder requires(Ingredient pIngredient, int pQuantity) {
        for (int i = 0; i < pQuantity; ++i) {
            this.ingredients.add(pIngredient);
        }

        return this;
    }

    public YusufShapelessRecipeBuilder unlockedBy(String p_200483_1_, ICriterionInstance p_200483_2_) {
        this.advancement.addCriterion(p_200483_1_, p_200483_2_);
        return this;
    }

    public YusufShapelessRecipeBuilder group(String p_200490_1_) {
        this.group = p_200490_1_;
        return this;
    }

    public void save(Consumer<IFinishedRecipe> p_200482_1_) {
        this.save(p_200482_1_, ForgeRegistries.ITEMS.getKey(this.result));
    }

    public void save(Consumer<IFinishedRecipe> p_200484_1_, String p_200484_2_) {
        ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(this.result);
        if ((new ResourceLocation(p_200484_2_)).equals(resourcelocation)) {
            throw new IllegalStateException("Shapeless Recipe " + p_200484_2_ + " should remove its 'save' argument");
        } else {
            this.save(p_200484_1_, new ResourceLocation(p_200484_2_));
        }
    }

    public void save(Consumer<IFinishedRecipe> p_200485_1_, ResourceLocation p_200485_2_) {
        this.ensureValid(p_200485_2_);
        this.advancement
                .parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_200485_2_))
                .rewards(AdvancementRewards.Builder.recipe(p_200485_2_))
                .requirements(IRequirementsStrategy.OR);
        p_200485_1_.accept(new Result(
                p_200485_2_,
                this.result,
                this.count,
                this.group == null ? "" : this.group,
                this.ingredients,
                this.advancement,
                new ResourceLocation(
                        p_200485_2_.getNamespace(),
                        "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/"
                                + p_200485_2_.getPath())));
    }

    /**
     * Makes sure that this recipe is valid and obtainable.
     */
    private void ensureValid(ResourceLocation pId) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }

    private static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final String group;
        private final List<Ingredient> ingredients;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(
                ResourceLocation pId,
                Item pResult,
                int pCount,
                String pGroup,
                List<Ingredient> pIngredients,
                Advancement.Builder pAdvancement,
                ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.count = pCount;
            this.group = pGroup;
            this.ingredients = pIngredients;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        public void serializeRecipeData(JsonObject pJson) {
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }

            JsonArray jsonarray = new JsonArray();

            for (Ingredient ingredient : this.ingredients) {
                jsonarray.add(ingredient.toJson());
            }

            pJson.add("ingredients", jsonarray);
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty(
                    "item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            if (this.count > 1) {
                jsonobject.addProperty("count", this.count);
            }

            pJson.add("result", jsonobject);
        }

        public IRecipeSerializer<?> getType() {
            return IRecipeSerializer.SHAPELESS_RECIPE;
        }

        /**
         * Gets the ID for the recipe.
         */
        public ResourceLocation getId() {
            return this.id;
        }

        /**
         * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
         */
        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        /**
         * Gets the ID for the advancement associated with this recipe. Should not be null if {@link #serializeAdvancement}
         * is non-null.
         */
        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
