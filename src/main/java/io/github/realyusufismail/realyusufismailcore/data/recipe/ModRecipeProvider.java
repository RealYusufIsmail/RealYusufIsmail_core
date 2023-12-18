/*
 * Copyright 2023 RealYusufIsmail.
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
package io.github.realyusufismail.realyusufismailcore.data.recipe;

import java.util.function.Consumer;
import net.minecraft.data.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.core.init.BlockInitCore;
import io.github.realyusufismail.realyusufismailcore.core.init.ItemInitCore;
import io.github.realyusufismail.realyusufismailcore.core.init.TagsInit;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(RealYusufIsmailCore.MOD_ID, path);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
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

        CookingRecipeBuilder.smelting(
                        Ingredient.of(BlockInitCore.COPPER_ORE.get()), ItemInitCore.COPPER.get(), 0.6f, 500)
                .unlockedBy("has_item", has(BlockInitCore.COPPER_ORE.get()))
                .save(consumer, modId("copper_ore_smelt"));

        CookingRecipeBuilder.blasting(
                        Ingredient.of(BlockInitCore.COPPER_ORE.get()), ItemInitCore.COPPER.get(), 0.2938392f, 500)
                .unlockedBy("has_item", has(BlockInitCore.COPPER_ORE.get()))
                .save(consumer, modId("copper_ore_blasting_smelt"));
    }
}
