package io.github.realyusufismail.realyusufismailcore.recipe;

import com.google.gson.JsonObject;
import io.github.realyusufismail.realyusufismailcore.core.init.RecipeSerializerInit;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class LegacySmithingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Ingredient base;
    private final Ingredient addition;
    private final Item result;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
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

    public LegacySmithingRecipeBuilder unlocks(String creterionId, Criterion<?> criterion) {
        this.criteria.put(creterionId, criterion);
        return this;
    }

    public void save(RecipeOutput recipeOutput, String resourceLocation) {
        this.save(recipeOutput, new ResourceLocation(resourceLocation));
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(String pCriterionName,
            @NotNull Criterion<?> criterion) {
        criteria.put(pCriterionName, criterion);
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

    public void save(@NotNull RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        this.ensureValid(resourceLocation);

        Advancement.Builder advancementBuilder = recipeOutput.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
            .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
            .requirements(AdvancementRequirements.Strategy.OR);

        this.criteria.forEach(advancementBuilder::addCriterion);

        recipeOutput.accept(new Result(resourceLocation, this.type, this.base, this.addition,
                this.result, advancementBuilder.build(resourceLocation
                    .withPrefix("recipes/" + this.category.getFolderName() + "/")),
                group));
    }

    private void ensureValid(ResourceLocation resourceLocation) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
        }
    }

    public record Result(ResourceLocation id, RecipeSerializer<?> type, Ingredient base,
            Ingredient addition, Item result, AdvancementHolder advancement,
            String group) implements FinishedRecipe {

        public void serializeRecipeData(@NotNull JsonObject jsonObject) {
            if (!group.isEmpty()) {
                jsonObject.addProperty("group", group);
            }

            jsonObject.add("base", this.base.toJson(false));
            jsonObject.add("addition", this.addition.toJson(false));
            JsonObject jsonObject1 = new JsonObject();
            jsonObject1.addProperty("item",
                    Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());
            jsonObject.add("result", jsonObject1);
        }

        @Override
        public ResourceLocation id() {
            return advancement.id();
        }

        @Override
        public RecipeSerializer<?> type() {
            return type;
        }

        @Nullable
        @Override
        public AdvancementHolder advancement() {
            return advancement;
        }
    }
}
