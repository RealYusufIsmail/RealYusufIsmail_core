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
package io.github.realyusufismail.realyusufismailcore.data.tags;

import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.core.init.ItemInitCore;
import io.github.realyusufismail.realyusufismailcore.core.init.TagsInit;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(
            DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, RealYusufIsmailCore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        // ores
        copy(TagsInit.Blocks.ORES_COPPER, TagsInit.Items.ORES_COPPER);

        // blocks
        copy(TagsInit.Blocks.STORAGE_COPPER, TagsInit.Items.STORAGE_COPPER);

        // ingots
        tag(TagsInit.Items.ORES_COPPER).add(ItemInitCore.COPPER.get());
    }
}
