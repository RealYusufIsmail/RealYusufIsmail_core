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
package io.github.realyusufismail.realyusufismailcore.data;

import io.github.realyusufismail.realyusufismailcore.data.client.ModBlockStateProvider;
import io.github.realyusufismail.realyusufismailcore.data.client.ModItemModelProvider;
import io.github.realyusufismail.realyusufismailcore.data.lang.ModEnLangProvider;
import io.github.realyusufismail.realyusufismailcore.data.loot.ModLootTables;
import io.github.realyusufismail.realyusufismailcore.data.pack.PackMetadataGenerator;
import io.github.realyusufismail.realyusufismailcore.data.recipe.ModRecipeProvider;
import io.github.realyusufismail.realyusufismailcore.data.tags.ModBlockTagsProvider;
import io.github.realyusufismail.realyusufismailcore.data.tags.ModItemTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.data.PackMetadataSection;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataGenerators {
    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(new ModBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, existingFileHelper);
        gen.addProvider(blockTags);
        gen.addProvider(new ModItemTagsProvider(gen, blockTags, existingFileHelper));
        gen.addProvider(new ModEnLangProvider(gen));
        gen.addProvider(new ModRecipeProvider(gen));
        gen.addProvider(new ModLootTables(gen));
        gen.addProvider(
                true,
                new PackMetadataGenerator(gen, existingFileHelper)
                        .add(
                                PackMetadataSection.SERIALIZER,
                                new PackMetadataSection(
                                        Component.literal("RealYusufIsmailCore Resources"),
                                        DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES),
                                        Arrays.stream(PackType.values())
                                                .collect(Collectors.toMap(
                                                        Function.identity(),
                                                        DetectedVersion.BUILT_IN::getPackVersion)))));
    }
}
