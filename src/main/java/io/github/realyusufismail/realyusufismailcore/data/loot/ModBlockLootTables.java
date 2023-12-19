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
package io.github.realyusufismail.realyusufismailcore.data.loot;

import static io.github.realyusufismail.realyusufismailcore.core.init.BlockInitCore.COPPER_BLOCK;
import static io.github.realyusufismail.realyusufismailcore.core.init.BlockInitCore.COPPER_ORE;

import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockLootTables extends BlockLootTables {
    @Override
    protected void addTables() {
        // ores
        dropSelf(COPPER_ORE.get());
        // blocks
        dropSelf(COPPER_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> RealYusufIsmailCore.MOD_ID.equals(
                        block.getRegistryName().getNamespace()))
                .collect(Collectors.toSet());
    }
}
