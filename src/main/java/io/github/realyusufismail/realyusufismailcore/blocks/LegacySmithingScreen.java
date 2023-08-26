package io.github.realyusufismail.realyusufismailcore.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.blocks.LegacySmithingMenu;
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
            Component pTitle, ResourceLocation pMenuResource) {
        super(pMenu, pPlayerInventory, pTitle, pMenuResource);
        this.titleLabelX = 60;
        this.titleLabelY = 18;
    }

    @Override
    protected void renderErrorIcon(PoseStack stack, int p_267270_, int p_266714_) {
        if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem())
                && !this.menu.getSlot(this.menu.getResultSlot()).hasItem()) {
            blit(stack, p_267270_ + 99, p_266714_ + 45, this.imageWidth, 0, 28, 21);
        }
    }
}
