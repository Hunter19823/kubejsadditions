package pie.ilikepiefoo.compat.jade;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import pie.ilikepiefoo.compat.jade.builder.ServerDataProviderBuilder;
import pie.ilikepiefoo.compat.jade.builder.ServerExtensionProviderBuilder;
import pie.ilikepiefoo.compat.jade.impl.CustomServerDataProvider;
import pie.ilikepiefoo.compat.jade.impl.CustomServerExtensionProvider;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.view.IServerExtensionProvider;

import java.util.ArrayList;
import java.util.List;

public class WailaCommonRegistrationEventJS extends EventJS {
    private final IWailaCommonRegistration registration;
    private final List<Runnable> registrationCallbacks;

    public WailaCommonRegistrationEventJS(IWailaCommonRegistration registration) {
        this.registration = registration;
        this.registrationCallbacks = new ArrayList<>();
    }

    public IWailaCommonRegistration getRegistration() {
        return this.registration;
    }

    /**
     * Register an {@link IServerDataProvider<EntityAccessor>} instance for data syncing purposes.
     *
     * @param location The unique identifier used for the Jade Provider instance
     * @param block    The highest level class to apply to
     * @return A builder for the data provider
     */
    public ServerDataProviderBuilder<BlockAccessor> blockDataProvider(ResourceLocation location, Class<?> block) {
        ServerDataProviderBuilder<BlockAccessor> builder = new ServerDataProviderBuilder<>(location);
        registrationCallbacks.add(() -> registration.registerBlockDataProvider(new CustomServerDataProvider<>(builder), block));
        return builder;
    }

    /**
     * Register an {@link IServerDataProvider<EntityAccessor>} instance for data syncing purposes.
     *
     * @param location The unique identifier used for the Jade Provider instance.
     * @param entity   The highest level class to apply to
     * @return A builder for the data provider
     */
    public ServerDataProviderBuilder<EntityAccessor> entityDataProvider(ResourceLocation location, Class<? extends Entity> entity) {
        ServerDataProviderBuilder<EntityAccessor> builder = new ServerDataProviderBuilder<>(location);
        registrationCallbacks.add(() -> registration.registerEntityDataProvider(new CustomServerDataProvider<>(builder), entity));
        return builder;
    }

    /**
     * Register an {@link IServerExtensionProvider<T,ItemStack>} instance for data syncing purposes.
     *
     * @param location     The unique identifier used for the Jade Provider instance
     * @param highestClass The highest level class to apply to
     * @param <T>          Same as HighestClass, but needed to specify the type of the provider
     * @return A builder for the data provider
     */
    public <T> ServerExtensionProviderBuilder<T, ItemStack> itemStorage(ResourceLocation location, Class<? extends T> highestClass) {
        ServerExtensionProviderBuilder<T, ItemStack> builder = new ServerExtensionProviderBuilder<>(location);
        registrationCallbacks.add(() -> registration.registerItemStorage(new CustomServerExtensionProvider<>(builder), highestClass));
        return builder;
    }

    /**
     * Register an {@link IServerExtensionProvider<T,CompoundTag>} instance for data syncing purposes.
     *
     * @param location     The unique identifier used for the Jade Provider instance
     * @param highestClass The highest level class to apply to
     * @param <T>          The type of the provider
     * @return A builder for the data provider
     */
    public <T> ServerExtensionProviderBuilder<T, CompoundTag> fluidStorage(ResourceLocation location, Class<? extends T> highestClass) {
        ServerExtensionProviderBuilder<T, CompoundTag> builder = new ServerExtensionProviderBuilder<>(location);
        registrationCallbacks.add(() -> registration.registerFluidStorage(new CustomServerExtensionProvider<>(builder), highestClass));
        return builder;
    }

    /**
     * Register an {@link IServerExtensionProvider<T,CompoundTag>} instance for data syncing purposes.
     *
     * @param location     The unique identifier used for the Jade Provider instance
     * @param highestClass The highest level class to apply to
     * @param <T>          The type of the provider
     * @return A builder for the data provider
     */
    public <T> ServerExtensionProviderBuilder<T, CompoundTag> energyStorage(ResourceLocation location, Class<? extends T> highestClass) {
        ServerExtensionProviderBuilder<T, CompoundTag> builder = new ServerExtensionProviderBuilder<>(location);
        registrationCallbacks.add(() -> registration.registerEnergyStorage(new CustomServerExtensionProvider<>(builder), highestClass));
        return builder;
    }

    /**
     * Register an {@link IServerExtensionProvider<T,CompoundTag>} instance for data syncing purposes.
     *
     * @param location     The unique identifier used for the Jade Provider instance
     * @param highestClass The highest level class to apply to
     * @param <T>          The type of the provider
     * @return A builder for the data provider
     */
    public <T> ServerExtensionProviderBuilder<T, CompoundTag> progress(ResourceLocation location, Class<? extends T> highestClass) {
        ServerExtensionProviderBuilder<T, CompoundTag> builder = new ServerExtensionProviderBuilder<>(location);
        registrationCallbacks.add(() -> registration.registerProgress(new CustomServerExtensionProvider<>(builder), highestClass));
        return builder;
    }

    @HideFromJS
    public void register() {
        registrationCallbacks.forEach(Runnable::run);
    }
}
