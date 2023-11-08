package io.github.realyusufismail.realyusufismailcore.core.init;

import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.blocks.LegacySmithingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public class RecipeSerializerInit {
    public static DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, RealYusufIsmailCore.MOD_ID);

    public static final RegistryObject<RecipeSerializer<LegacySmithingRecipe>> LEGACY_SMITHING =
            simple("legacy_smithing", new LegacySmithingRecipe.Serializer());

    private static <S extends RecipeSerializer<T>, T extends Recipe<?>> RegistryObject<S> simple(
            String key, S serializer) {
        return SERIALIZERS.register(key, () -> serializer);
    }
}
