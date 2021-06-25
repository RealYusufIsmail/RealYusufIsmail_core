package net.yusuf.realyusufismailcore.block;

import com.google.gson.JsonElement;
import net.minecraft.data.BlockModelProvider;
import net.minecraft.data.IFinishedBlockState;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface IModelProvider extends IItemProvider {

    static final class BlockModels extends BlockModelProvider {
        private final Consumer<IFinishedBlockState> blockStateOutput;
        private final BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;

        public BlockModels(Consumer<IFinishedBlockState> p_i232514_1_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_i232514_2_, Consumer<net.minecraft.item.Item> p_i232514_3_) {
            super(p_i232514_1_, p_i232514_2_, p_i232514_3_);

            blockStateOutput = p_i232514_1_;
            modelOutput = p_i232514_2_;
        }
    }
}
