package io.github.realyusufismail.realyusufismailcore.data.support.oregen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public abstract class ModOreFeaturesSupport {
    protected RuleTest ruleTest1 = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    protected RuleTest ruleTest2 = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    protected RuleTest ruleTest3 = new BlockMatchTest(Blocks.NETHERRACK);
    protected RuleTest ruleTest4 = new TagMatchTest(BlockTags.BASE_STONE_NETHER);


    public abstract void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context);

    private static void registerOre(BootstapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> ore,
            List<OreConfiguration.TargetBlockState> targetBlockStates, int size) {
        context.register(ore, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(targetBlockStates, size)));
    }

    private static void registerOre(BootstapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> ore, RuleTest ruleTest, BlockState blockState,
            int size) {
        context.register(ore, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(ruleTest, blockState, size)));
    }

    protected static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name, String modId) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,
                new ResourceLocation(modId, name.toLowerCase()));
    }
}
