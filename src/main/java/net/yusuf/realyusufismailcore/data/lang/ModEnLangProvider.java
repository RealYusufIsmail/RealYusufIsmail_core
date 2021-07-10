package net.yusuf.realyusufismailcore.data.lang;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;
import net.yusuf.realyusufismailcore.RealYusufIsmailCore;
import net.yusuf.realyusufismailcore.core.init.BlockInit;
import net.yusuf.realyusufismailcore.core.init.ItemInit;

public class ModEnLangProvider extends LanguageProvider {

    public ModEnLangProvider(DataGenerator gen) {
        super(gen, RealYusufIsmailCore.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //block
        block(BlockInit.COPPER_BLOCK, "Copper Block");
        //ores
        block(BlockInit.COPPER_ORE, "Copper Ore");
        //ingots
        item(ItemInit.COPPER, "Copper");
    }
    private <T extends Item> void item(RegistryObject<T> entry, String name) {
        add(entry.get(), name);
    }
    private <T extends Block> void block(RegistryObject<T> entry, String name) {
        add(entry.get(), name);
    }
}
