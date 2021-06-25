package net.yusuf.realyusufismailcore.core.init;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.yusuf.realyusufismailcore.block.IBlockProvider;
import net.yusuf.realyusufismailcore.registry.RegistryObjectWrapper;

public class BlockRegistryObject <T extends Block> extends RegistryObjectWrapper<T> implements IBlockProvider {
    public BlockRegistryObject(RegistryObject<T> block) {
        super(block);
    }

    @Override
    public Block asBlock() {
        return registryObject.get();
    }
}
