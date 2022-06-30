/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.realyusufismailcore.util;

import com.google.common.base.Preconditions;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.regex.Pattern;


public final class NameUtils {
    private static final Pattern PATTERN = Pattern.compile("([a-z0-9._-]+:)?[a-z0-9/._-]+");

    private NameUtils() {
        throw new IllegalAccessError("Utility class");
    }

    public static boolean isValid(CharSequence name) {
        return PATTERN.matcher(name).matches();
    }

    /**
     * Verify name is not null, throwing an exception if it is.
     *
     * @param name Possibly null ResourceLocation
     * @return name
     * @throws NullPointerException if name is null
     */
    public static ResourceLocation checkNotNull(@Nullable ResourceLocation name) {
        Preconditions.checkNotNull(name,
                "Name is null, make sure the object has been registered correctly");
        return name;
    }

    /**
     * Get a ResourceLocation with namespace "forge". Does not handle exceptions.
     *
     * @param path The path (must be /[a-z0-9/._-]+/)
     * @return A new ResourceLocation
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation forgeId(String path) {
        return new ResourceLocation("forge", path);
    }

    /**
     * Gets the registry name, throwing an exception if it is null
     *
     * @param entry The registry object
     * @return The registry name
     * @throws NullPointerException if registry name is null
     */
    public static ResourceLocation from(@NotNull IForgeRegistry<?> entry) {
        return checkNotNull(entry.getRegistryName());
    }

    /**
     * Gets the item's registry name, throwing an exception if it is null
     *
     * @param item The item
     * @return The registry name
     * @throws NullPointerException if registry name is null
     */
    public static ResourceLocation fromItem(@NotNull ItemLike item) {
        Preconditions.checkNotNull(item.asItem(),
                "asItem() is null, has object not been fully constructed?");
        return checkNotNull(ResourceLocation.tryParse(item.asItem().getDescription().toString()));
    }

    /**
     * Gets the registry name of the stack's item, throwing an exception if it is null
     *
     * @param stack The ItemStack
     * @return The registry name
     * @throws NullPointerException if registry name is null
     */
    public static ResourceLocation fromItem(@NotNull ItemStack stack) {
        return checkNotNull(ResourceLocation.tryParse(stack.getDisplayName().toString()));
    }
}
