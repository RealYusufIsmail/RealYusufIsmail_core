package net.yusuf.realyusufismailcore.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.yusuf.realyusufismailcore.RealYusufIsmailCore;
import net.yusuf.realyusufismailcore.data.client.ModBlockStateProvider;
import net.yusuf.realyusufismailcore.data.client.ModItemModelProvider;
import net.yusuf.realyusufismailcore.data.lang.ModEnLangProvider;
import net.yusuf.realyusufismailcore.data.loot.ModLootTables;
import net.yusuf.realyusufismailcore.data.recipe.ModRecipeProvider;
import net.yusuf.realyusufismailcore.data.tags.ModBlockTagsProvider;
import net.yusuf.realyusufismailcore.data.tags.ModItemTagsProvider;


@Mod.EventBusSubscriber(modid = RealYusufIsmailCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    private DataGenerators() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(new ModBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, existingFileHelper);
        gen.addProvider(blockTags);
        gen.addProvider(new ModItemTagsProvider(gen, blockTags, existingFileHelper));
        gen.addProvider(new ModEnLangProvider(gen));
        gen.addProvider(new ModRecipeProvider(gen));
        gen.addProvider(new ModLootTables(gen));

    }
}
