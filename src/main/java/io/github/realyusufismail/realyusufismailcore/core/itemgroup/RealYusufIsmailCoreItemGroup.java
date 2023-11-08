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

package io.github.realyusufismail.realyusufismailcore.core.itemgroup;


import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.core.init.BlockInitCore;
import io.github.realyusufismail.realyusufismailcore.core.init.ItemInitCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryObject;

public class RealYusufIsmailCoreItemGroup {
    public static DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RealYusufIsmailCore.MOD_ID);

    public static final RegistryObject<CreativeModeTab> REALYUSUFISMAILCORE =
            CREATIVE_MODE_TABS.register("realyusufismailcore_tab",
                    () -> createCreativeTabBuilder(CreativeModeTab.builder()));

    private static CreativeModeTab createCreativeTabBuilder(CreativeModeTab.Builder builder) {
        builder.displayItems((itemDisplayParameters, output) -> {
            BlockInitCore.BLOCKS.getEntries()
                .stream()
                .map(block -> block.get().asItem())
                .forEach(output::accept);

            ItemInitCore.ITEMS.getEntries()
                .stream()
                .map(item -> item.get().asItem())
                .forEach(output::accept);
        });
        builder.icon(() -> new ItemStack(BlockInitCore.LEGACY_SMITHING_TABLE.get()));
        builder.title(Component.translatable("creativetab.realyusufismailcore"));

        return builder.build();
    }
}
