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

package io.github.realyusufismail.realyusufismailcore.common.events;


import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;

public class CraftingEventTrigger extends PlayerEvent {
    @Nonnull
    private final ItemStack left; // The left side of the input
    @Nonnull
    private final ItemStack right; // The right side of the input
    @Nonnull
    private final ItemStack output; // Set this to set the output stack

    public CraftingEventTrigger(Player player, @Nonnull ItemStack left, @Nonnull ItemStack right,
            @Nonnull ItemStack output) {
        super(player);
        this.output = output;
        this.left = left;
        this.right = right;
    }

    @Nonnull
    public ItemStack getItemResult() {
        return output;
    }

    /**
     * Get the first item input into the anvil
     *
     * @return the first input slot
     */
    @Nonnull
    public ItemStack getItemInput() {
        return left;
    }

    /**
     * Get the second item input into the anvil
     *
     * @return the second input slot
     */
    @Nonnull
    public ItemStack getIngredientInput() {
        return right;
    }

}

