package net.yusuf.realyusufismailcore.data.lang;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;
import net.yusuf.realyusufismailcore.RealYusufIsmailCore;
import net.yusuf.realyusufismailcore.core.init.BlockInitCore;
import net.yusuf.realyusufismailcore.core.init.ItemInitCore;
import net.yusuf.realyusufismailcore.core.itemgroup.MainItemGroup;

public class ModEnLangProvider extends LanguageProvider {

    public ModEnLangProvider(DataGenerator gen) {
        super(gen, RealYusufIsmailCore.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //block
        block(BlockInitCore.COPPER_BLOCK, "Copper Block");
        //ores
        block(BlockInitCore.COPPER_ORE, "Copper Ore");
        //ingots
        item(ItemInitCore.COPPER, "Copper");
        //others
        add(MainItemGroup.MAIN.getDisplayName().getString(), "RealYusufIsmail Core Item Group");
    }

    private <T extends Item> void item(RegistryObject<T> entry, String name) {
        add(entry.get(), name);
    }

    private <T extends Block> void block(RegistryObject<T> entry, String name) {
        add(entry.get(), name);
    }
}
