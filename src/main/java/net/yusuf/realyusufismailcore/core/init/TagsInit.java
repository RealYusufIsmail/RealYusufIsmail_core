package net.yusuf.realyusufismailcore.core.init;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;

import net.minecraft.tags.ItemTags;

import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.yusuf.realyusufismailcore.RealYusufIsmailCore;

public class TagsInit {
    public static final class Blocks {
        //ores
        public static final Tag.Named<Block> ORES_COPPER = BlockTags.bind("forge:ores/copper");
        //blocks
        public static final Tag.Named<Block> STORAGE_COPPER = BlockTags.bind("forge:storage_blocks/copper");


        private static Tag.Named<Block> forge(String path) {
            return BlockTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static Tag.Named<Block> mod(String path) {
            return BlockTags.bind(new ResourceLocation(RealYusufIsmailCore.MOD_ID, path).toString());
        }
    }

    public static final class Items {
        //ores
        public static final Tag.Named<Item> ORES_COPPER = ItemTags.bind("forge:ores/copper");
        //blocks
        public static final Tag.Named<Item> STORAGE_COPPER = ItemTags.bind("forge:storage_blocks/copper");
        //items
        public static final Tag.Named<Item> INGOTS_COPPER = ItemTags.bind("forge:ingots/copper");


        private static Tag.Named<Item> forge(String path) {
            return ItemTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static Tag.Named<Item> mod(String path) {
            return ItemTags.bind(new ResourceLocation(RealYusufIsmailCore.MOD_ID, path).toString());
        }
    }
}
