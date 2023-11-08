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

package io.github.realyusufismail.realyusufismailcore.data.lang;

import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.core.init.BlockInitCore;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ModEnLangProvider extends LanguageProvider {

    public ModEnLangProvider(DataGenerator gen) {
        super(gen.getPackOutput(), RealYusufIsmailCore.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // block
        block(BlockInitCore.LEGACY_SMITHING_TABLE, "Legacy Smithing Table");

        // others
        add("creativetab.realyusufismailcore", "RealYusufIsmail Core Item Group");
        add("container.legacy_smithing", "Improve Gear");
    }

    private <T extends Item> void item(@NotNull RegistryObject<T> entry, String name) {
        add(entry.get(), name);
    }

    private <T extends Block> void block(@NotNull RegistryObject<T> entry, String name) {
        add(entry.get(), name);
    }

}
