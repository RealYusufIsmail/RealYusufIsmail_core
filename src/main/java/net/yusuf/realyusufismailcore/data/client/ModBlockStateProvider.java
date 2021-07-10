package net.yusuf.realyusufismailcore.data.client;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.yusuf.realyusufismailcore.RealYusufIsmailCore;

import static net.yusuf.realyusufismailcore.core.init.BlockInitCore.COPPER_BLOCK;
import static net.yusuf.realyusufismailcore.core.init.BlockInitCore.COPPER_ORE;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, RealYusufIsmailCore.MOD_ID, exFileHelper);

    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(COPPER_ORE.get());
        simpleBlock(COPPER_BLOCK.get());
    }

}
