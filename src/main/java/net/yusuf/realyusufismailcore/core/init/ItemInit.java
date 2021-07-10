package net.yusuf.realyusufismailcore.core.init;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.yusuf.realyusufismailcore.RealYusufIsmailCore;
import net.yusuf.realyusufismailcore.core.itemgroup.MainItemGroup;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RealYusufIsmailCore.MOD_ID);
    public static final RegistryObject<Item> COPPER = ITEMS.register("copper",
            () -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));

}
