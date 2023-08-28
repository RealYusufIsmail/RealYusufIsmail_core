package io.github.realyusufismail.realyusufismailcore.core.init;

import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.blocks.LegacySmithingMenu;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

public class MenuTypeInit {
    public static DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, RealYusufIsmailCore.MOD_ID);

    public static final RegistryObject<MenuType<LegacySmithingMenu>> LEGACY_SMITHING_TABLE =
            register("legacy_smithing_table", LegacySmithingMenu::new,  FeatureFlags.REGISTRY.allFlags());

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(
           @NotNull String name, @NotNull MenuType.MenuSupplier<T> supplier, @NotNull FeatureFlagSet flagSet) {
        return MENU_TYPES.register(name, () -> new MenuType<>(supplier, flagSet));
    }
}
