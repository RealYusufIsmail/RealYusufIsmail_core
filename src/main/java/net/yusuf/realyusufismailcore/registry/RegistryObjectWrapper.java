package net.yusuf.realyusufismailcore.registry;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.yusuf.realyusufismailcore.RealYusufIsmailCore;

import java.util.Objects;
import java.util.function.Supplier;

public class RegistryObjectWrapper<T extends IForgeRegistryEntry<? super T>> implements Supplier<T> {
    protected final RegistryObject<T> registryObject;

    public RegistryObjectWrapper(RegistryObject<T> registryObject) {
        this.registryObject = registryObject;
    }

    public RegistryObject<T> getRegistryObject() {
        return registryObject;
    }

    @Override
    public T get() {
        return registryObject.get();
    }

}
