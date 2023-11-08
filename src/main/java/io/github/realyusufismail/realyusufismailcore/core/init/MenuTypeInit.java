package io.github.realyusufismail.realyusufismailcore.core.init;

import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.blocks.LegacySmithingMenu;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.Objects;

public class MenuTypeInit {
    public static DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, RealYusufIsmailCore.MOD_ID);

    public static final RegistryObject<MenuType<LegacySmithingMenu>> LEGACY_SMITHING_TABLE =
            register("legacy_smithing_table", LegacySmithingMenu::new,
                    FeatureFlags.REGISTRY.allFlags());


    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(
            String name, MenuType.MenuSupplier<T> supplier, @Nullable FeatureFlagSet flagSet) {
        FeatureFlagSet finalFlagSet =
                Objects.requireNonNullElseGet(flagSet, FeatureFlags.REGISTRY::allFlags);
        return MENU_TYPES.register(name, () -> new MenuType<>(supplier, finalFlagSet));
    }
}
