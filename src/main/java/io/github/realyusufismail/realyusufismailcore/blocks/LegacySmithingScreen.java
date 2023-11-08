package io.github.realyusufismail.realyusufismailcore.blocks;

import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * @see net.minecraft.client.gui.screens.inventory.SmithingScreen
 */
public class LegacySmithingScreen extends ItemCombinerScreen<LegacySmithingMenu> {
    private static final ResourceLocation SMITHING_LOCATION =
            RealYusufIsmailCore.getId("textures/gui/container/legacy_smithing.png");

    public LegacySmithingScreen(LegacySmithingMenu pMenu, Inventory pPlayerInventory,
            Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, SMITHING_LOCATION);
        this.titleLabelX = 60;
        this.titleLabelY = 18;
    }

    @Override
    protected void renderErrorIcon(GuiGraphics pGuiGraphics, int pX, int pY) {
        if (this.hasRecipeError()) {
            pGuiGraphics.blit(SMITHING_LOCATION, pX + 99, pY + 45, this.imageWidth, 0, 28, 21);
        }
    }

    private boolean hasRecipeError() {
        return this.menu.getSlot(0).hasItem() && this.menu.getSlot(1).hasItem()
                && this.menu.getSlot(2).hasItem()
                && !this.menu.getSlot(this.menu.getResultSlot()).hasItem();
    }
}
