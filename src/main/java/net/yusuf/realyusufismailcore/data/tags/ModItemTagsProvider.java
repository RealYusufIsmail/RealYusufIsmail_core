package net.yusuf.realyusufismailcore.data.tags;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.yusuf.realyusufismailcore.RealYusufIsmailCore;
import net.yusuf.realyusufismailcore.core.init.ItemInitCore;
import net.yusuf.realyusufismailcore.core.init.TagsInit;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, RealYusufIsmailCore.MOD_ID, existingFileHelper);
    }
    @Override
    protected void addTags() {
        //ores
        copy(TagsInit.Blocks.ORES_COPPER, TagsInit.Items.ORES_COPPER);

        //blocks
        copy(TagsInit.Blocks.STORAGE_COPPER, TagsInit.Items.STORAGE_COPPER);


        //ingots
        tag(TagsInit.Items.ORES_COPPER).add(ItemInitCore.COPPER.get());




    }
}

