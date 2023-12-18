package io.github.realyusufismail.realyusufismailcore.data.pack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.data.PackMetadataSection;
import net.minecraft.resources.data.PackMetadataSectionSerializer;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class PackMetadataGenerator extends BlockTagsProvider {
    private final Map<String, Supplier<JsonElement>> elements = new HashMap<>();

    public PackMetadataGenerator(DataGenerator p_i244820_1_,  @Nullable ExistingFileHelper p_i244820_3_) {
        super(p_i244820_1_, RealYusufIsmailCore.MOD_ID, p_i244820_3_);
    }

    public <T> PackMetadataGenerator add(PackMetadataSectionSerializer p_252067_, T p_249511_) {
        this.elements.put(p_252067_.getMetadataSectionName(), () -> {
            return p_252067_.fromJson(p_249511_);
        });
        return this;
    }

    public CompletableFuture<?> run(CachedOutput pOutput) {
        JsonObject $$1 = new JsonObject();
        this.elements.forEach((p_249290_, p_251317_) -> {
            $$1.add(p_249290_, (JsonElement)p_251317_.get());
        });
        return DataProvider.saveStable(pOutput, $$1, this.output.getOutputFolder().resolve("pack.mcmeta"));
    }

    public final String getName() {
        return "Pack Metadata";
    }

    public static PackMetadataGenerator forFeaturePack(DataGenerator generator,  @Nullable ExistingFileHelper existingFileHelper, Component pDescription) {
        return (new PackMetadataGenerator(generator, existingFileHelper)).add(PackMetadataSection.SERIALIZER, new PackMetadataSection(pDescription, DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA)));
    }

    public static PackMetadataGenerator forFeaturePack(PackOutput pOutput, Component pDescription, FeatureFlagSet pFlags) {
        return forFeaturePack(pOutput, pDescription).add(FeatureFlagsMetadataSection.TYPE, new FeatureFlagsMetadataSection(pFlags));
    }
}
