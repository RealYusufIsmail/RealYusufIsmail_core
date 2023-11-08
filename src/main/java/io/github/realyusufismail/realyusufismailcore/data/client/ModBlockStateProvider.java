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

package io.github.realyusufismail.realyusufismailcore.data.client;

import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.core.init.BlockInitCore;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), RealYusufIsmailCore.MOD_ID, exFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "RealYusufIsmail Core - Block States/Models";
    }

    @Override
    protected void registerStatesAndModels() {
        customSmithingTable(BlockInitCore.LEGACY_SMITHING_TABLE.get());
    }

    protected void customSmithingTable(Block block) {
        ResourceLocation name = ForgeRegistries.BLOCKS.getKey(block);

        if (name == null) {
            throw new NullPointerException("Block " + block + " has null name");
        }

        BlockModelBuilder builder = models().withExistingParent(name.getPath(), "block/cube");

        builder.texture("down", modLoc("block/" + name.getPath() + "_bottom"));
        builder.texture("east", modLoc("block/" + name.getPath() + "_side"));
        builder.texture("north", modLoc("block/" + name.getPath() + "_front"));
        builder.texture("particle", modLoc("block/" + name.getPath() + "_front"));
        builder.texture("south", modLoc("block/" + name.getPath() + "_side"));
        builder.texture("up", modLoc("block/" + name.getPath() + "_top"));
        builder.texture("west", modLoc("block/" + name.getPath() + "_side"));
        simpleBlockItem(block, builder);
        simpleBlock(block, builder);
    }

}
