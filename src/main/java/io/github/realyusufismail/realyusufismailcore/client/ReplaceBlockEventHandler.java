package io.github.realyusufismail.realyusufismailcore.client;

import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.common.events.CraftingEventTrigger;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = RealYusufIsmailCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ReplaceBlockEventHandler {

    // Replace the smithing table with the custom one
    @ObjectHolder(registryName = "minecraft", value = "smithing_table")
    public static final Block SMITHING_TABLE = null;

    @SubscribeEvent
    public static void onRegisterBlocks(CraftingEventTrigger event) {

    }
}
