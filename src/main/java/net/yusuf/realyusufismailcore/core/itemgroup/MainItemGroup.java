package net.yusuf.realyusufismailcore.core.itemgroup;


import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.yusuf.realyusufismailcore.core.init.ItemInitCore;

public class MainItemGroup extends CreativeModeTab {

    public static final MainItemGroup MAIN = new MainItemGroup(CreativeModeTab.TABS.length, "main");

    public MainItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemInitCore.COPPER.get());
    }

}
