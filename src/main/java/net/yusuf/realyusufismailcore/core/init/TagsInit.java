package net.yusuf.realyusufismailcore.core.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.yusuf.realyusufismailcore.RealYusufIsmailCore;

public class TagsInit {
    public static final class Blocks {
        //ores
        public static final ITag.INamedTag<Block> ORES_COPPER = BlockTags.bind("forge:ores/copper");
        //blocks
        public static final ITag.INamedTag<Block> STORAGE_COPPER = BlockTags.bind("forge:storage_blocks/copper");


        private static ITag.INamedTag<Block> forge(String path) {
            return BlockTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Block> mod(String path) {
            return BlockTags.bind(new ResourceLocation(RealYusufIsmailCore.MOD_ID, path).toString());
        }
    }
    public static final class Items {
        //ores
        public static final ITag.INamedTag<Item> ORES_COPPER = ItemTags.bind("forge:ores/copper");
        //blocks
        public static final ITag.INamedTag<Item> STORAGE_COPPER = ItemTags.bind("forge:storage_blocks/copper");
        //items
        public static final ITag.INamedTag<Item> INGOTS_COPPER = ItemTags.bind("forge:ingots/copper");




        private static ITag.INamedTag<Item> forge(String path) {
            return ItemTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Item> mod(String path) {
            return ItemTags.bind(new ResourceLocation(RealYusufIsmailCore.MOD_ID, path).toString());
        }
    }
}
