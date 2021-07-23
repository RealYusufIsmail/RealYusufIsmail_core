package net.yusuf.realyusufismailcore.block;


import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface IBlockProvider extends IItemProvider {
    /**
     * Get the block this object represents.
     *
     * @return The block, which may be newly constructed
     */
    Block asBlock();

    /**
     * Shortcut for getting the default state of the block.
     *
     * @return Default block state
     */
    default BlockState asBlockState() {
        return asBlock().defaultBlockState();
    }

    @Override
    default Item asItem() {
        return asBlock().asItem();
    }
}
