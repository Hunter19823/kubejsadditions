package pie.ilikepiefoo.compat.jade;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import pie.ilikepiefoo.compat.jade.builder.BlockComponentProviderBuilder;
import pie.ilikepiefoo.compat.jade.builder.ClientExtensionProviderBuilder;
import pie.ilikepiefoo.compat.jade.builder.EntityComponentProviderBuilder;
import pie.ilikepiefoo.compat.jade.impl.CustomBlockComponentProvider;
import pie.ilikepiefoo.compat.jade.impl.CustomClientExtensionProvider;
import pie.ilikepiefoo.compat.jade.impl.CustomEntityComponentProvider;
import snownee.jade.api.Accessor;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IJadeProvider;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.callback.JadeAfterRenderCallback;
import snownee.jade.api.callback.JadeBeforeRenderCallback;
import snownee.jade.api.callback.JadeItemModNameCallback;
import snownee.jade.api.callback.JadeRayTraceCallback;
import snownee.jade.api.callback.JadeRenderBackgroundCallback;
import snownee.jade.api.callback.JadeTooltipCollectedCallback;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.platform.CustomEnchantPower;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.view.EnergyView;
import snownee.jade.api.view.FluidView;
import snownee.jade.api.view.IClientExtensionProvider;
import snownee.jade.api.view.ItemView;
import snownee.jade.api.view.ProgressView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class WailaClientRegistrationEventJS extends EventJS {
    private final IWailaClientRegistration registration;
    private final List<Runnable> registrationCallbacks;

    public WailaClientRegistrationEventJS(IWailaClientRegistration registration) {
        this.registration = registration;
        this.registrationCallbacks = new ArrayList<>();
    }

    /**
     * Register a namespaced config key to be accessed within data providers.
     *
     * @param key          the namespaced key
     * @param defaultValue the default value
     */
    public void addConfig(ResourceLocation key, boolean defaultValue) {
        registration.addConfig(key, defaultValue);
    }

    public void addConfig(ResourceLocation key, Enum<?> defaultValue) {
        registration.addConfig(key, defaultValue);
    }

    public void addConfig(ResourceLocation key, String defaultValue, Predicate<String> validator) {
        registration.addConfig(key, defaultValue, validator);
    }

    public void addConfig(ResourceLocation key, int defaultValue, int min, int max, boolean slider) {
        registration.addConfig(key, defaultValue, min, max, slider);
    }

    public void addConfig(ResourceLocation key, float defaultValue, float min, float max, boolean slider) {
        registration.addConfig(key, defaultValue, min, max, slider);
    }

    public void addConfigListener(ResourceLocation key, Consumer<ResourceLocation> listener) {
        registration.addConfigListener(key, listener);
    }


    /**
     * Register a {@link IBlockComponentProvider} instance for a block.
     * This method is used to register a custom block component provider that can be declared
     * in KubeJS scripts.
     * Using this builder you can define either the tooltip or the icon retriever.
     * You can also define both if you wish.
     *
     * @param location The unique identifier used for the Jade Provider instance.
     * @param block    The highest level class to apply to
     *                 (e.g. {@link Block} or {@link net.minecraft.world.level.block.CakeBlock})
     * @return A builder for the block component provider
     */
    public BlockComponentProviderBuilder block(ResourceLocation location, Class<? extends Block> block) {
        BlockComponentProviderBuilder builder = new BlockComponentProviderBuilder(location);
        registrationCallbacks.add(() -> {
            var provider = new CustomBlockComponentProvider(builder);
            if (builder.getIconRetriever() != null) {
                registration.registerBlockIcon(provider, block);
            }
            if (builder.getTooltipRetriever() != null) {
                registration.registerBlockComponent(provider, block);
            }
        });
        return builder;
    }

    /**
     * Register a {@link IEntityComponentProvider} instance for an entity.
     * This method is used to register a custom entity component provider that can be declared
     * in KubeJS scripts.
     * Using this builder you can define either the tooltip or the icon retriever.
     * You can also define both if you wish.
     *
     * @param location The unique identifier used for the Jade Provider instance.
     * @param entity   The highest level class to apply to
     *                 (e.g. {@link Entity} or {@link net.minecraft.world.entity.player.Player})
     * @return A builder for the entity component provider
     */
    public EntityComponentProviderBuilder entity(ResourceLocation location, Class<? extends Entity> entity) {
        EntityComponentProviderBuilder builder = new EntityComponentProviderBuilder(location);
        registrationCallbacks.add(() -> {
            var provider = new CustomEntityComponentProvider(builder);
            if (builder.getIconRetriever() != null) {
                registration.registerEntityIcon(provider, entity);
            }
            if (builder.getTooltipRetriever() != null) {
                registration.registerEntityComponent(provider, entity);
            }
        });
        return builder;
    }


    /**
     * Register an {@link IClientExtensionProvider<ItemStack, ItemView>} instance for handling item storage grouping.
     *
     * @param location The unique identifier used for the Jade Provider instance.
     * @return A builder for the client extension provider
     */
    public ClientExtensionProviderBuilder<ItemStack, ItemView> itemStorage(ResourceLocation location) {
        ClientExtensionProviderBuilder<ItemStack, ItemView> builder = new ClientExtensionProviderBuilder<>(location);
        registrationCallbacks.add(() -> registration.registerItemStorageClient(new CustomClientExtensionProvider<>(builder)));
        return builder;
    }

    /**
     * Register an {@link IClientExtensionProvider<CompoundTag, FluidView>} instance for handling fluid storage grouping.
     *
     * @param location The unique identifier used for the Jade Provider instance.
     * @return A builder for the client extension provider
     */
    public ClientExtensionProviderBuilder<CompoundTag, FluidView> fluidStorage(ResourceLocation location) {
        ClientExtensionProviderBuilder<CompoundTag, FluidView> builder = new ClientExtensionProviderBuilder<>(location);
        registrationCallbacks.add(() -> registration.registerFluidStorageClient(new CustomClientExtensionProvider<>(builder)));
        return builder;
    }

    /**
     * Register an {@link IClientExtensionProvider<CompoundTag, EnergyView>} instance for handling energy storage grouping.
     *
     * @param location The unique identifier used for the Jade Provider instance.
     * @return A builder for the client extension provider
     */
    public ClientExtensionProviderBuilder<CompoundTag, EnergyView> energyStorage(ResourceLocation location) {
        ClientExtensionProviderBuilder<CompoundTag, EnergyView> builder = new ClientExtensionProviderBuilder<>(location);
        registrationCallbacks.add(() -> registration.registerEnergyStorageClient(new CustomClientExtensionProvider<>(builder)));
        return builder;
    }

    /**
     * Register an {@link IClientExtensionProvider<CompoundTag, ProgressView>} instance for handling progress grouping.
     *
     * @param location The unique identifier used for the Jade Provider instance.
     * @return A builder for the client extension provider
     */
    public ClientExtensionProviderBuilder<CompoundTag, ProgressView> progress(ResourceLocation location) {
        ClientExtensionProviderBuilder<CompoundTag, ProgressView> builder = new ClientExtensionProviderBuilder<>(location);
        registrationCallbacks.add(() -> registration.registerProgressClient(new CustomClientExtensionProvider<>(builder)));
        return builder;
    }

    /**
     * Register an {@link IJadeProvider} instance to allow overriding the icon for a block via the
     * {@link IBlockComponentProvider#getIcon(BlockAccessor, IPluginConfig, IElement)} method.
     *
     * @param provider The data provider instance
     * @param block    The highest level class to apply to
     */
    public void registerBlockIcon(IBlockComponentProvider provider, Class<? extends Block> block) {
        registration.registerBlockIcon(provider, block);
    }

    /**
     * Register an {@link IJadeProvider} instance for appending informations to
     * the tooltip.
     *
     * @param provider The data provider instance
     * @param block    The highest level class to apply to
     */
    public void registerBlockComponent(IBlockComponentProvider provider, Class<? extends Block> block) {
        registration.registerBlockComponent(provider, block);
    }

    /**
     * Register an {@link IEntityComponentProvider} instance to allow overriding the icon for a entity via the
     * {@link IEntityComponentProvider#getIcon(EntityAccessor, IPluginConfig, IElement)} method.
     *
     * @param provider The data provider instance
     * @param entity   The highest level class to apply to
     */
    public void registerEntityIcon(IEntityComponentProvider provider, Class<? extends Entity> entity) {
        registration.registerEntityIcon(provider, entity);
    }

    /**
     * Register an {@link IEntityComponentProvider} instance for appending {@link Component}
     * to the tooltip.
     *
     * @param provider The data provider instance
     * @param entity   The highest level class to apply to
     */
    public void registerEntityComponent(IEntityComponentProvider provider, Class<? extends Entity> entity) {
        registration.registerEntityComponent(provider, entity);
    }

    /**
     * Mark a block as hidden in tooltip.
     *
     * @param block
     */
    public void hideTarget(Block block) {
        registration.hideTarget(block);
    }

    /**
     * Mark an entity as hidden in tooltip. If player is aiming to this entity, it will be ignored and
     * try to find next possible target.
     *
     * @param entityType
     */
    public void hideTarget(EntityType<?> entityType) {
        registration.hideTarget(entityType);
    }

    /**
     * Mark a block to show name of the picked result, rather than block name.
     *
     * @param block
     */
    public void usePickedResult(Block block) {
        registration.usePickedResult(block);
    }

    /**
     * Mark an entity type to show name of the picked result, rather than entity name.
     *
     * @param entityType
     */
    public void usePickedResult(EntityType<?> entityType) {
        registration.usePickedResult(entityType);
    }

    public BlockAccessor.Builder blockAccessor() {
        return registration.blockAccessor();
    }

    public EntityAccessor.Builder entityAccessor() {
        return registration.entityAccessor();
    }

    public boolean shouldHide(Entity target) {
        return registration.shouldHide(target);
    }

    public boolean shouldHide(BlockState state) {
        return registration.shouldHide(state);
    }

    public boolean shouldPick(Entity entity) {
        return registration.shouldPick(entity);
    }

    public boolean shouldPick(BlockState blockState) {
        return registration.shouldPick(blockState);
    }

    public void addAfterRenderCallback(JadeAfterRenderCallback callback) {
        registration.addAfterRenderCallback(callback);
    }

    public void addAfterRenderCallback(int priority, JadeAfterRenderCallback callback) {
        registration.addAfterRenderCallback(priority, callback);
    }

    public void addBeforeRenderCallback(JadeBeforeRenderCallback callback) {
        registration.addBeforeRenderCallback(callback);
    }

    public void addBeforeRenderCallback(int priority, JadeBeforeRenderCallback callback) {
        registration.addBeforeRenderCallback(priority, callback);
    }

    public void addRayTraceCallback(JadeRayTraceCallback callback) {
        registration.addRayTraceCallback(callback);
    }

    public void addRayTraceCallback(int priority, JadeRayTraceCallback callback) {
        registration.addRayTraceCallback(priority, callback);
    }

    public void addTooltipCollectedCallback(JadeTooltipCollectedCallback callback) {
        registration.addTooltipCollectedCallback(callback);
    }

    public void addTooltipCollectedCallback(int priority, JadeTooltipCollectedCallback callback) {
        registration.addTooltipCollectedCallback(priority, callback);
    }

    public void addItemModNameCallback(JadeItemModNameCallback callback) {
        registration.addItemModNameCallback(callback);
    }

    public void addItemModNameCallback(int priority, JadeItemModNameCallback callback) {
        registration.addItemModNameCallback(priority, callback);
    }

    public void addRenderBackgroundCallback(JadeRenderBackgroundCallback callback) {
        registration.addRenderBackgroundCallback(callback);
    }

    public void addRenderBackgroundCallback(int priority, JadeRenderBackgroundCallback callback) {
        registration.addRenderBackgroundCallback(priority, callback);
    }

    public Screen createPluginConfigScreen(@Nullable Screen parent, @Nullable String namespace) {
        return registration.createPluginConfigScreen(parent, namespace);
    }

    public void registerItemStorageClient(IClientExtensionProvider<ItemStack, ItemView> provider) {
        registration.registerItemStorageClient(provider);
    }

    public void registerFluidStorageClient(IClientExtensionProvider<CompoundTag, FluidView> provider) {
        registration.registerFluidStorageClient(provider);
    }

    public void registerEnergyStorageClient(IClientExtensionProvider<CompoundTag, EnergyView> provider) {
        registration.registerEnergyStorageClient(provider);
    }

    public void registerProgressClient(IClientExtensionProvider<CompoundTag, ProgressView> provider) {
        registration.registerProgressClient(provider);
    }

    public boolean isServerConnected() {
        return registration.isServerConnected();
    }

    public boolean isShowDetailsPressed() {
        return registration.isShowDetailsPressed();
    }

    public boolean maybeLowVisionUser() {
        return registration.maybeLowVisionUser();
    }

    public CompoundTag getServerData() {
        return registration.getServerData();
    }

    public void setServerData(CompoundTag tag) {
        registration.setServerData(tag);
    }

    public ItemStack getBlockCamouflage(LevelAccessor level, BlockPos pos) {
        return registration.getBlockCamouflage(level, pos);
    }

    public void markAsClientFeature(ResourceLocation uid) {
        registration.markAsClientFeature(uid);
    }

    public void markAsServerFeature(ResourceLocation uid) {
        registration.markAsServerFeature(uid);
    }

    public boolean isClientFeature(ResourceLocation uid) {
        return registration.isClientFeature(uid);
    }

    public <T extends Accessor<?>> void registerAccessorHandler(Class<T> clazz, Accessor.ClientHandler<T> handler) {
        registration.registerAccessorHandler(clazz, handler);
    }

    public Accessor.ClientHandler<Accessor<?>> getAccessorHandler(Class<? extends Accessor<?>> clazz) {
        return registration.getAccessorHandler(clazz);
    }

    public void registerCustomEnchantPower(Block block, CustomEnchantPower customEnchantPower) {
        registration.registerCustomEnchantPower(block, customEnchantPower);
    }

    @HideFromJS
    public void register() {
        registrationCallbacks.forEach(Runnable::run);
    }
}
