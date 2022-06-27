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

package io.github.realyusufismail.realyusufismailcore.core.init;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;

import net.minecraft.tags.ItemTags;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;

public class TagsInit {
    public static final class Blocks {
        // ores
        public static final TagKey<Block> ORES_COPPER =
                BlockTags.create(ResourceLocation.tryParse("forge:ores/copper"));
        // blocks
        public static final TagKey<Block> STORAGE_COPPER =
                BlockTags.create(ResourceLocation.tryParse("forge:storage_blocks/copper"));


        private static TagKey<Block> forge(String path) {
            return BlockTags
                .create(ResourceLocation.tryParse(new ResourceLocation("forge", path).toString()));
        }

        private static TagKey<Block> mod(String path) {
            return BlockTags.create(ResourceLocation
                .tryParse(new ResourceLocation(RealYusufIsmailCore.MOD_ID, path).toString()));
        }
    }

    public static final class Items {
        // ores
        public static final TagKey<Item> ORES_COPPER =
                ItemTags.create(ResourceLocation.tryParse("forge:ores/copper"));
        // blocks
        public static final TagKey<Item> STORAGE_COPPER =
                ItemTags.create(ResourceLocation.tryParse("forge:storage_blocks/copper"));
        // items
        public static final TagKey<Item> INGOTS_COPPER =
                ItemTags.create(ResourceLocation.tryParse("forge:ingots/copper"));


        private static TagKey<Item> forge(String path) {
            return ItemTags
                .create(ResourceLocation.tryParse(new ResourceLocation("forge", path).toString()));
        }

        private static TagKey<Item> mod(String path) {
            return ItemTags.create(ResourceLocation
                .tryParse(new ResourceLocation(RealYusufIsmailCore.MOD_ID, path).toString()));
        }
    }
}
