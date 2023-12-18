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
import io.github.realyusufismail.realyusufismailcore.core.init.BlockInitCore;
import io.github.realyusufismail.realyusufismailcore.core.init.TagsInit;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, RealYusufIsmailCore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        // ores

        tag(TagsInit.Blocks.ORES_COPPER).add(BlockInitCore.COPPER_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_COPPER);

        // blocks
        tag(TagsInit.Blocks.STORAGE_COPPER).add(BlockInitCore.COPPER_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.STORAGE_COPPER);
    }
}
