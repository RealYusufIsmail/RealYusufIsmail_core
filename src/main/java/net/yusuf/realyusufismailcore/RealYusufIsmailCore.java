package net.yusuf.realyusufismailcore;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.yusuf.realyusufismailcore.core.init.BlockInit;
import net.yusuf.realyusufismailcore.core.init.ItemInit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.Random;

@Mod("realyusufismailcore")
@Mod.EventBusSubscriber(modid = RealYusufIsmailCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RealYusufIsmailCore {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Random RANDOM = new Random();
    public static final String MOD_ID = "realyusufismailcore";
    private static RealYusufIsmailCore INSTANCE;
    public static SideProxy PROXY;

    public RealYusufIsmailCore() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        INSTANCE = this;
        PROXY = DistExecutor.safeRunForDist(() -> SideProxy.Client::new, () -> SideProxy.Server::new);
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
    }
    public static String getVersion() {
        Optional<? extends ModContainer> o = ModList.get().getModContainerById(MOD_ID);
        if (o.isPresent()) {
            return o.get().getModInfo().getVersion().toString();
        }
        return "0.0.0";
    }

    public static boolean isDevBuild() {
        return "NONE".equals(getVersion());
    }

    public static ResourceLocation getId(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
