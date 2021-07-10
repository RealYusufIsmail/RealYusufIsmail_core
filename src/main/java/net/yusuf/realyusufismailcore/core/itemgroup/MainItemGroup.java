package net.yusuf.realyusufismailcore.core.itemgroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.yusuf.realyusufismailcore.core.init.ItemInitCore;

public class MainItemGroup extends ItemGroup {

    public static final MainItemGroup MAIN = new MainItemGroup(ItemGroup.TABS.length, "main");

    public MainItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemInitCore.COPPER.get());
    }

}
