package io.github.realyusufismail.realyusufismailcore.client;

import io.github.realyusufismail.realyusufismailcore.core.init.MenuTypeInit;
import io.github.realyusufismail.realyusufismailcore.blocks.LegacySmithingScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(ClientSetup::registerScreens);
    }

    private static void registerScreens() {
        MenuScreens.register(MenuTypeInit.LEGACY_SMITHING_TABLE.get(), LegacySmithingScreen::new);
    }
}
