package net.yusuf.realyusufismailcore.core.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.yusuf.realyusufismailcore.RealYusufIsmailCore;
import net.yusuf.realyusufismailcore.core.itemgroup.MainItemGroup;

import java.util.function.Supplier;
import static net.yusuf.realyusufismailcore.core.init.ItemInit.ITEMS;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RealYusufIsmailCore.MOD_ID);


    public static final RegistryObject<GeneralBlock> TEST_BLOCK = register("test_block", Blocks.NETHERITE_BLOCK);
    //helper methods for register both block and BlockItem at the same time.
    //Instead of put them in main class
    private static <T extends Block> RegistryObject<T> registerSpecial(String name, Supplier<T> supplier) {
        RegistryObject<T> blockReg = BLOCKS.register(name, supplier);
        ITEMS.register(name, () -> new BlockItem(blockReg.get(), new Item.Properties().tab(MainItemGroup.MAIN)));
        return blockReg;
    }

    private static RegistryObject<GeneralBlock> register(String name, Supplier<GeneralBlock> supplier) {
        RegistryObject<GeneralBlock> blockReg = BLOCKS.register(name, supplier);
        ITEMS.register(name, () -> new BlockItem(blockReg.get(), new Item.Properties().tab(MainItemGroup.MAIN)));
        return blockReg;
    }

    private static RegistryObject<GeneralBlock> register(String name, Block existingBlock) {
        return register(name, () -> new GeneralBlock(AbstractBlock.Properties.copy(existingBlock)));
    }
}
