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
import io.github.realyusufismail.realyusufismailcore.MinecraftClass;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Taken from
 * 
 * @see UpgradeRecipeBuilder
 */
@MinecraftClass
@SuppressWarnings("unused")
public class YusufUpgradeRecipeBuilder {
    private final Ingredient base;
    private final Ingredient addition;
    private final Item result;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private final RecipeSerializer<?> type;

    public YusufUpgradeRecipeBuilder(RecipeSerializer<?> type, Ingredient base, Ingredient addition,
            Item result) {
        this.type = type;
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    public static @NotNull YusufUpgradeRecipeBuilder smithing(Ingredient base, Ingredient addition,
            Item result) {
        return new YusufUpgradeRecipeBuilder(RecipeSerializer.SMITHING, base, addition, result);
    }

    public YusufUpgradeRecipeBuilder unlocks(String creterionId,
            CriterionTriggerInstance criterionTriggerInstance) {
        this.advancement.addCriterion(creterionId, criterionTriggerInstance);
        return this;
    }

    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, String resourceLocation) {
        this.save(finishedRecipeConsumer, new ResourceLocation(resourceLocation));
    }

    public void save(@NotNull Consumer<FinishedRecipe> finishedRecipeConsumer,
            ResourceLocation resourceLocation) {
        this.ensureValid(resourceLocation);
        this.advancement.parent(new ResourceLocation("recipes/root"))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
            .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
            .requirements(RequirementsStrategy.OR);
        finishedRecipeConsumer
            .accept(new YusufUpgradeRecipeBuilder.Result(resourceLocation, this.type, this.base,
                    this.addition, this.result, this.advancement,
                    new ResourceLocation(resourceLocation.getNamespace(),
                            "recipes/"
                                    + Objects.requireNonNull(this.result.getItemCategory())
                                        .getRecipeFolderName()
                                    + "/" + resourceLocation.getPath())));
    }

    private void ensureValid(ResourceLocation resourceLocation) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
        }
    }

    public record Result(ResourceLocation id, RecipeSerializer<?> type, Ingredient base,
            Ingredient addition, Item result, Advancement.Builder advancement,
            ResourceLocation advancementId) implements FinishedRecipe {

        public void serializeRecipeData(@NotNull JsonObject jsonObject) {
            jsonObject.add("base", this.base.toJson());
            jsonObject.add("addition", this.addition.toJson());
            JsonObject jsonObject1 = new JsonObject();
            jsonObject1.addProperty("item",
                    Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());
            jsonObject.add("result", jsonObject1);
        }

        public @NotNull ResourceLocation getId() {
            return this.id;
        }

        public @NotNull RecipeSerializer<?> getType() {
            return this.type;
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
