package io.github.realyusufismail.realyusufismailcore.data.support.oregen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public abstract class ModOrePlacementsSupport {

    // example :
    // public static final ResourceKey<PlacedFeature> LEGENDARY_ORE = createKey("legendary_ore");

    public abstract void bootstrap(BootstapContext<PlacedFeature> context);

    private static void register(BootstapContext<PlacedFeature> context,
            ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> holder,
            List<PlacementModifier> placement) {
        context.register(key, new PlacedFeature(holder, placement));
    }

    protected static List<PlacementModifier> orePlacement(PlacementModifier modifier,
            PlacementModifier modifier1) {
        return List.of(modifier, InSquarePlacement.spread(), modifier1, BiomeFilter.biome());
    }

    protected static List<PlacementModifier> commonOrePlacement(int pCount,
            PlacementModifier pHeightRange) {
        return orePlacement(CountPlacement.of(pCount), pHeightRange);
    }

    protected static List<PlacementModifier> rareOrePlacement(int pChance,
            PlacementModifier pHeightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
    }

    protected static ResourceKey<PlacedFeature> createKey(String name, String modId) {
        return ResourceKey.create(Registries.PLACED_FEATURE,
                new ResourceLocation(modId, name.toLowerCase()));
    }
}
