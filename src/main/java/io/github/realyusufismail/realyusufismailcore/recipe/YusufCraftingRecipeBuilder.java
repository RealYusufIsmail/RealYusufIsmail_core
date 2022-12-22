package io.github.realyusufismail.realyusufismailcore.recipe;

import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.crafting.CraftingBookCategory;

public abstract class YusufCraftingRecipeBuilder {
    protected static CraftingBookCategory determineBookCategory(RecipeCategory p_250736_) {

        return switch (p_250736_) {
            case BUILDING_BLOCKS -> CraftingBookCategory.BUILDING;
            case TOOLS, COMBAT -> CraftingBookCategory.EQUIPMENT;
            case REDSTONE -> CraftingBookCategory.REDSTONE;
            default -> CraftingBookCategory.MISC;
        };
    }

    protected abstract static class CraftingResult implements FinishedRecipe {
        private final CraftingBookCategory category;

        protected CraftingResult(CraftingBookCategory pCategory) {
            this.category = pCategory;
        }

        public void serializeRecipeData(JsonObject pJson) {
            pJson.addProperty("category", this.category.getSerializedName());
        }
    }
}
