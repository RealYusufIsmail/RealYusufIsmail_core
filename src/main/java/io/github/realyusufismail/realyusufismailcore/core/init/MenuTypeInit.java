package io.github.realyusufismail.realyusufismailcore.core.init;

import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.blocks.OldSmithingMenu;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;

public class MenuTypeInit {
    public static DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, RealYusufIsmailCore.MOD_ID);

    public static final RegistryObject<MenuType<AbstractContainerMenu>> OLD_SMITHING_TABLE =
            register("old_smithing", OldSmithingMenu::new, null);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String name, MenuType.MenuSupplier<T> supplier, @Nullable FeatureFlagSet flagSet) {
        if (flagSet == null) {
            flagSet = FeatureFlags.REGISTRY.allFlags();
        }

        FeatureFlagSet finalFlagSet = flagSet;
        return MENU_TYPES.register(name, () -> new MenuType<>(supplier, finalFlagSet));
    }
}
