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

package io.github.realyusufismail.realyusufismailcore.data.recipe;

import net.minecraft.data.*;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.core.init.BlockInitCore;
import io.github.realyusufismail.realyusufismailcore.core.init.ItemInitCore;
import io.github.realyusufismail.realyusufismailcore.core.init.TagsInit;

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

        SimpleCookingRecipeBuilder
            .smelting(Ingredient.of(BlockInitCore.COPPER_ORE.get()), ItemInitCore.COPPER.get(),
                    0.6f, 500)
            .unlockedBy("has_item", has(BlockInitCore.COPPER_ORE.get()))
            .save(consumer, modId("copper_ore_smelt"));

        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(BlockInitCore.COPPER_ORE.get()), ItemInitCore.COPPER.get(),
                    0.2938392f, 500)
            .unlockedBy("has_item", has(BlockInitCore.COPPER_ORE.get()))
            .save(consumer, modId("copper_ore_blasting_smelt"));
    }
}
