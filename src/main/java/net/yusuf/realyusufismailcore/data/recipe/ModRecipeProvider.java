package net.yusuf.realyusufismailcore.data.recipe;

import net.minecraft.data.*;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.yusuf.realyusufismailcore.RealYusufIsmailCore;
import net.yusuf.realyusufismailcore.core.init.BlockInitCore;
import net.yusuf.realyusufismailcore.core.init.ItemInitCore;
import net.yusuf.realyusufismailcore.core.init.TagsInit;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(RealYusufIsmailCore.MOD_ID, path);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(ItemInitCore.COPPER.get(), 9)

                .requires(BlockInitCore.COPPER_BLOCK.get())
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_COPPER))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInitCore.COPPER_BLOCK.get())
                .define('#', TagsInit.Items.INGOTS_COPPER)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_COPPER))
                .save(consumer);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockInitCore.COPPER_ORE.get()), ItemInitCore.COPPER.get(), 0.6f, 500)
                .unlockedBy("has_item", has(BlockInitCore.COPPER_ORE.get()))
                .save(consumer, modId("copper_ore_smelt"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(BlockInitCore.COPPER_ORE.get()), ItemInitCore.COPPER.get(), 0.2938392f, 500)
                .unlockedBy("has_item", has(BlockInitCore.COPPER_ORE.get()))
                .save(consumer, modId("copper_ore_blasting_smelt"));
    }
}
