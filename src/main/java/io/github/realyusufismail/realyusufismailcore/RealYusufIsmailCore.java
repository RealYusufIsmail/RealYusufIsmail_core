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

package io.github.realyusufismail.realyusufismailcore;

import io.github.realyusufismail.realyusufismailcore.blocks.LegacySmithingScreen;
import io.github.realyusufismail.realyusufismailcore.core.init.*;
import io.github.realyusufismail.realyusufismailcore.core.itemgroup.RealYusufIsmailCoreItemGroup;
import io.github.realyusufismail.realyusufismailcore.data.DataGenerators;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import java.util.Optional;

@Mod("realyusufismailcore")
public class RealYusufIsmailCore {
    public static final String MOD_ID = "realyusufismailcore";
    public static Logger logger = org.slf4j.LoggerFactory.getLogger(RealYusufIsmailCore.class);

    public RealYusufIsmailCore(IEventBus bus) {
        BlockInitCore.BLOCKS.register(bus);
        ItemInitCore.ITEMS.register(bus);
        MenuTypeInit.MENU_TYPES.register(bus);
        RecipeTypeInit.RECIPE_TYPES.register(bus);
        RecipeSerializerInit.SERIALIZERS.register(bus);
        RealYusufIsmailCoreItemGroup.CREATIVE_MODE_TABS.register(bus);

        bus.addListener(DataGenerators::gatherData);

        bus.addListener(FMLClientSetupEvent.class, (event) -> {
            event.enqueueWork(RealYusufIsmailCore::registerScreens);
        });

        FMLJavaModLoadingContext.get()
            .getModEventBus()
            .addListener(FMLClientSetupEvent.class, (event) -> {
                event.enqueueWork(() -> {
                    ModList.get().getModContainerById(MOD_ID).ifPresent((mod) -> {
                        logger.info("Loaded {} v{}", mod.getModInfo().getDisplayName(),
                                mod.getModInfo().getVersion());
                    });
                });
            });
    }

    private static void registerScreens() {
        MenuScreens.register(MenuTypeInit.LEGACY_SMITHING_TABLE.get(), LegacySmithingScreen::new);
    }

    public static String getVersion() {
        Optional<? extends ModContainer> o = ModList.get().getModContainerById(MOD_ID);
        if (o.isPresent()) {
            return o.get().getModInfo().getVersion().toString();
        }
        return "0.0.0";
    }

    public static boolean isDevBuild() {
        return "NONE".equals(getVersion());
    }

    public static ResourceLocation getId(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
