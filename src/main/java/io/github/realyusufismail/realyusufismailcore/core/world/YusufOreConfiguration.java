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

package io.github.realyusufismail.realyusufismailcore.core.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Taken from
 *
 * @see OreConfiguration
 */
@SuppressWarnings("unused")
public class YusufOreConfiguration implements FeatureConfiguration {
    public static final Codec<YusufOreConfiguration> CODEC =
            RecordCodecBuilder.create(create -> create
                .group(Codec.list(TargetBlockState.CODEC)
                    .fieldOf("targets")
                    .forGetter(getter -> getter.targetStates),
                        Codec.intRange(0, 64).fieldOf("size").forGetter(getter -> getter.size),
                        Codec.floatRange(0.0F, 1.0F)
                            .fieldOf("discard_chance_on_air_exposure")
                            .forGetter(getter -> getter.discardChanceOnAirExposure))
                .apply(create, YusufOreConfiguration::new));
    public final List<YusufOreConfiguration.TargetBlockState> targetStates;
    public final int size;
    public final float discardChanceOnAirExposure;

    public YusufOreConfiguration(List<YusufOreConfiguration.TargetBlockState> targetStates,
            int size, float discardChanceOnAirExposure) {
        this.size = size;
        this.targetStates = targetStates;
        this.discardChanceOnAirExposure = discardChanceOnAirExposure;
    }

    public YusufOreConfiguration(List<YusufOreConfiguration.TargetBlockState> targetStates,
            int size) {
        this(targetStates, size, 0.0F);
    }

    public YusufOreConfiguration(RuleTest ruleTest, BlockState blockState, int size,
            float discardChanceOnAirExposure) {
        this(List.of(new YusufOreConfiguration.TargetBlockState(ruleTest, blockState)), size,
                discardChanceOnAirExposure);
    }

    public YusufOreConfiguration(RuleTest ruleTest, BlockState blockState, int size) {
        this(List.of(new YusufOreConfiguration.TargetBlockState(ruleTest, blockState)), size, 0.0F);
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static YusufOreConfiguration.@NotNull TargetBlockState target(RuleTest ruleTest,
            BlockState blockState) {
        return new YusufOreConfiguration.TargetBlockState(ruleTest, blockState);
    }

    public static class TargetBlockState {
        public static final Codec<YusufOreConfiguration.TargetBlockState> CODEC =
                RecordCodecBuilder.create(create -> create
                    .group(RuleTest.CODEC.fieldOf("target").forGetter(getter -> getter.target),
                            BlockState.CODEC.fieldOf("state").forGetter(getter -> getter.state))
                    .apply(create, TargetBlockState::new));
        public final RuleTest target;
        public final BlockState state;

        TargetBlockState(RuleTest ruleTest, BlockState blockState) {
            this.target = ruleTest;
            this.state = blockState;
        }
    }
}
