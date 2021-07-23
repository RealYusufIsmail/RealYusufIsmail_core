package net.yusuf.realyusufismailcore.util;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;


public class OutputSlot extends Slot {
    public OutputSlot(Inventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }

    @Override
    public boolean mayPickup(Player p_82869_1_) {
        return true;
    }

    @Override
    public boolean mayPlace(ItemStack p_75214_1_) {
        return false;
    }
}
