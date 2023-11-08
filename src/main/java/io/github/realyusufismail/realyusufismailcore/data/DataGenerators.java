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

package io.github.realyusufismail.realyusufismailcore.data;

import io.github.realyusufismail.realyusufismailcore.data.client.ModBlockStateProvider;
import io.github.realyusufismail.realyusufismailcore.data.lang.ModEnLangProvider;
import io.github.realyusufismail.realyusufismailcore.data.loot.ModLootTables;
import io.github.realyusufismail.realyusufismailcore.data.recipe.ModRecipeProvider;
import io.github.realyusufismail.realyusufismailcore.data.tags.ModBlockTagsProvider;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore.logger;


public class DataGenerators {
    private DataGenerators() {}

    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

        try {
            gen.addProvider(true, new ModBlockStateProvider(gen, existingFileHelper));
            ModBlockTagsProvider blockTags =
                    new ModBlockTagsProvider(gen, existingFileHelper, lookup);
            gen.addProvider(true, blockTags);
            gen.addProvider(true, new ModEnLangProvider(gen));
            gen.addProvider(true, new ModRecipeProvider(gen, lookup));
            gen.addProvider(true, new ModLootTables(gen));
            gen.addProvider(true, new PackMetadataGenerator(gen.getPackOutput()).add(
                    PackMetadataSection.TYPE,
                    new PackMetadataSection(Component.literal("RealYusufIsmailCore Resources"),
                            DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES),
                            Arrays.stream(PackType.values())
                                .collect(Collectors.toMap(Function.identity(),
                                        DetectedVersion.BUILT_IN::getPackVersion)))));
        } catch (RuntimeException e) {
            logger.error("Error while generating data", e);
        }
    }
}
