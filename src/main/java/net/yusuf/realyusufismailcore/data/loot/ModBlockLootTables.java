package net.yusuf.realyusufismailcore.data.loot;

import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.registries.ForgeRegistries;
import net.yusuf.realyusufismailcore.RealYusufIsmailCore;

import java.util.stream.Collectors;

import static net.yusuf.realyusufismailcore.core.init.BlockInit.*;

public class ModBlockLootTables extends BlockLootTables {
    @Override
    protected void addTables() {
        //ores
        dropSelf(COPPER_ORE.get());
        //blocks
        dropSelf(COPPER_BLOCK.get());
    }
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> RealYusufIsmailCore.MOD_ID.equals(block.getRegistryName().getNamespace()))
                .collect(Collectors.toSet());
    }
}
