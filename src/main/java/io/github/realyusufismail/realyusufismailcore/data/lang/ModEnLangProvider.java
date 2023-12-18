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
package io.github.realyusufismail.realyusufismailcore.data.lang;

import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.core.init.BlockInitCore;
import io.github.realyusufismail.realyusufismailcore.core.init.ItemInitCore;
import io.github.realyusufismail.realyusufismailcore.core.itemgroup.MainItemGroup;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

public class ModEnLangProvider extends LanguageProvider {

    public ModEnLangProvider(DataGenerator gen) {
        super(gen, RealYusufIsmailCore.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // block
        block(BlockInitCore.COPPER_BLOCK, "Copper Block");
        // ores
        block(BlockInitCore.COPPER_ORE, "Copper Ore");
        // ingots
        item(ItemInitCore.COPPER, "Copper");
        // others
        add(MainItemGroup.MAIN.getDisplayName().getString(), "RealYusufIsmail Core Item Group");
    }

    private <T extends Item> void item(RegistryObject<T> entry, String name) {
        add(entry.get(), name);
    }

    private <T extends Block> void block(RegistryObject<T> entry, String name) {
        add(entry.get(), name);
    }
}
