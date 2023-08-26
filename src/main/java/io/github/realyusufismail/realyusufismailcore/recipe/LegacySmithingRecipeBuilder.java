package io.github.realyusufismail.realyusufismailcore.recipe;

import com.google.gson.JsonObject;
import io.github.realyusufismail.realyusufismailcore.core.init.RecipeSerializerInit;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class LegacySmithingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Ingredient base;
    private final Ingredient addition;
    private final Item result;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private final RecipeSerializer<?> type;
    private String group;

    public LegacySmithingRecipeBuilder(RecipeCategory category, RecipeSerializer<?> type,
            Ingredient base, Ingredient addition, Item result) {
        this.category = category;
        this.type = type;
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    public static @NotNull LegacySmithingRecipeBuilder smithing(RecipeCategory category,
            Ingredient base, Ingredient addition, Item result) {
        return new LegacySmithingRecipeBuilder(category, RecipeSerializerInit.LEGACY_SMITHING.get(),
                base, addition, result);
    }

    public LegacySmithingRecipeBuilder unlocks(String creterionId,
            CriterionTriggerInstance criterionTriggerInstance) {
        this.advancement.addCriterion(creterionId, criterionTriggerInstance);
        return this;
    }

    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, String resourceLocation) {
        this.save(finishedRecipeConsumer, new ResourceLocation(resourceLocation));
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(String pCriterionName,
            CriterionTriggerInstance pCriterionTrigger) {
        advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return result;
    }

    public void save(@NotNull Consumer<FinishedRecipe> finishedRecipeConsumer,
            ResourceLocation resourceLocation) {
        this.ensureValid(resourceLocation);
        this.advancement.parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT)
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
            .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
            .requirements(RequirementsStrategy.OR);
        finishedRecipeConsumer.accept(new LegacySmithingRecipeBuilder.Result(resourceLocation,
                this.type, this.base, this.addition, this.result, this.advancement,
                resourceLocation.withPrefix("recipes/" + this.category.getFolderName() + "/"),
                group));
    }

    private void ensureValid(ResourceLocation resourceLocation) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
        }
    }

    public record Result(ResourceLocation id, RecipeSerializer<?> type, Ingredient base,
            Ingredient addition, Item result, Advancement.Builder advancement,
            ResourceLocation advancementId, String group) implements FinishedRecipe {

        public void serializeRecipeData(@NotNull JsonObject jsonObject) {
            if (!group.isEmpty()) {
                jsonObject.addProperty("group", group);
            }

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
