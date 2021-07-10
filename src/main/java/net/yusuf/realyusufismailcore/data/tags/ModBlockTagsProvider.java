package net.yusuf.realyusufismailcore.data.tags;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.yusuf.realyusufismailcore.RealYusufIsmailCore;
import net.yusuf.realyusufismailcore.core.init.BlockInit;
import net.yusuf.realyusufismailcore.core.init.TagsInit;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, RealYusufIsmailCore.MOD_ID, existingFileHelper);
    }


    @Override
    protected void addTags() {
        //ores

        tag(TagsInit.Blocks.ORES_COPPER).add(BlockInit.COPPER_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_COPPER);

        //blocks
        tag(TagsInit.Blocks.STORAGE_COPPER).add(BlockInit.COPPER_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.STORAGE_COPPER);

    }
}

