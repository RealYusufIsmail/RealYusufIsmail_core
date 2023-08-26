package io.github.realyusufismail.realyusufismailcore.core.init;

import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.blocks.ILegacySmithingRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeTypeInit {
    public static DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, RealYusufIsmailCore.MOD_ID);

    public static final RegistryObject<RecipeType<ILegacySmithingRecipe>> LEGACY_SMITHING =
            RECIPE_TYPES.register("legacy_smithing", () -> RecipeType
                .simple(new ResourceLocation(RealYusufIsmailCore.MOD_ID, "legacy_smithing")));

}
