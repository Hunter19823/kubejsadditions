package pie.ilikepiefoo.mixin;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.Window;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Function4;
import dev.latvian.kubejs.world.ClientWorldJS;
import net.minecraft.CrashReport;
import net.minecraft.client.Game;
import net.minecraft.client.HotbarManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.social.PlayerSocialManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.ClientPackSource;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.client.resources.PaintingTextureManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.searchtree.MutableSearchTree;
import net.minecraft.client.searchtree.SearchRegistry;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.Music;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Snooper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;
import java.net.Proxy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

@Mixin(value = ClientWorldJS.class, remap = false)
public abstract class MixinClientWorldJS {

	@Shadow
	@Final
	private Minecraft minecraft;

	@Shadow
	public abstract LocalPlayer getMinecraftPlayer();

	public void updateTitle() {
		minecraft.updateTitle();
	}

	public boolean isProbablyModded() {
		return minecraft.isProbablyModded();
	}

	public void clearResourcePacksOnError(Throwable throwable, @Nullable Component component) {
		minecraft.clearResourcePacksOnError(throwable, component);
	}

	public void run() {
		minecraft.run();
	}

	public RenderTarget getMainRenderTarget() {
		return minecraft.getMainRenderTarget();
	}

	public String getLaunchedVersion() {
		return minecraft.getLaunchedVersion();
	}

	public String getVersionType() {
		return minecraft.getVersionType();
	}

	public void delayCrash(CrashReport crashReport) {
		minecraft.delayCrash(crashReport);
	}

	public boolean isEnforceUnicode() {
		return minecraft.isEnforceUnicode();
	}

	public CompletableFuture<Void> reloadResourcePacks() {
		return minecraft.reloadResourcePacks();
	}

	public LevelStorageSource getLevelSource() {
		return minecraft.getLevelSource();
	}

	public void setScreen(@Nullable Screen screen) {
		minecraft.setScreen(screen);
	}

	public void setOverlay(@Nullable Overlay overlay) {
		minecraft.setOverlay(overlay);
	}

	public void destroy() {
		minecraft.destroy();
	}

	public void close() {
		minecraft.close();
	}

	public void resizeDisplay() {
		minecraft.resizeDisplay();
	}

	public void cursorEntered() {
		minecraft.cursorEntered();
	}

	public void emergencySave() {
		minecraft.emergencySave();
	}

	public void stop() {
		minecraft.stop();
	}

	public boolean isRunning() {
		return minecraft.isRunning();
	}

	public void pauseGame(boolean bl) {
		minecraft.pauseGame(bl);
	}

	public MusicManager getMusicManager() {
		return minecraft.getMusicManager();
	}

	public void tick() {
		minecraft.tick();
	}

	public void loadLevel(String string) {
		minecraft.loadLevel(string);
	}

	public void createLevel(String string,
							LevelSettings levelSettings,
							RegistryAccess.RegistryHolder registryHolder,
							WorldGenSettings worldGenSettings) {
		minecraft.createLevel(string, levelSettings, registryHolder, worldGenSettings);
	}

	public Minecraft.ServerStem makeServerStem(RegistryAccess.RegistryHolder registryHolder,
											   Function<LevelStorageSource.LevelStorageAccess, DataPackConfig> function,
											   Function4<LevelStorageSource.LevelStorageAccess, RegistryAccess.RegistryHolder, ResourceManager, DataPackConfig, WorldData> function4,
											   boolean bl,
											   LevelStorageSource.LevelStorageAccess levelStorageAccess) throws InterruptedException, ExecutionException {
		return minecraft.makeServerStem(registryHolder, function, function4, bl, levelStorageAccess);
	}

	public void setLevel(ClientLevel clientLevel) {
		minecraft.setLevel(clientLevel);
	}

	public void clearLevel() {
		minecraft.clearLevel();
	}

	public void clearLevel(Screen screen) {
		minecraft.clearLevel(screen);
	}

	public void forceSetScreen(Screen screen) {
		minecraft.forceSetScreen(screen);
	}

	public boolean allowsMultiplayer() {
		return minecraft.allowsMultiplayer();
	}

	public boolean isBlocked(UUID uUID) {
		return minecraft.isBlocked(uUID);
	}

	public boolean allowsChat() {
		return minecraft.allowsChat();
	}

	public boolean isDemo() {
		return minecraft.isDemo();
	}

	@Nullable
	public ClientPacketListener getConnection() {
		return minecraft.getConnection();
	}

	public CrashReport fillReport(CrashReport crashReport) {
		return minecraft.fillReport(crashReport);
	}

	public CompletableFuture<Void> delayTextureReload() {
		return minecraft.delayTextureReload();
	}

	public void populateSnooper(Snooper snooper) {
		minecraft.populateSnooper(snooper);
	}

	public void setCurrentServer(@Nullable ServerData serverData) {
		minecraft.setCurrentServer(serverData);
	}

	@Nullable
	public ServerData getCurrentServer() {
		return minecraft.getCurrentServer();
	}

	public boolean isLocalServer() {
		return minecraft.isLocalServer();
	}

	public boolean hasSingleplayerServer() {
		return minecraft.hasSingleplayerServer();
	}

	@Nullable
	public IntegratedServer getSingleplayerServer() {
		return minecraft.getSingleplayerServer();
	}

	public Snooper getSnooper() {
		return minecraft.getSnooper();
	}

	public User getUser() {
		return minecraft.getUser();
	}

	public PropertyMap getProfileProperties() {
		return minecraft.getProfileProperties();
	}

	public Proxy getProxy() {
		return minecraft.getProxy();
	}

	public TextureManager getTextureManager() {
		return minecraft.getTextureManager();
	}

	public ResourceManager getResourceManager() {
		return minecraft.getResourceManager();
	}

	public PackRepository getResourcePackRepository() {
		return minecraft.getResourcePackRepository();
	}

	public ClientPackSource getClientPackSource() {
		return minecraft.getClientPackSource();
	}

	public File getResourcePackDirectory() {
		return minecraft.getResourcePackDirectory();
	}

	public LanguageManager getLanguageManager() {
		return minecraft.getLanguageManager();
	}

	public Function<ResourceLocation, TextureAtlasSprite> getTextureAtlas(
			ResourceLocation resourceLocation) {
		return minecraft.getTextureAtlas(resourceLocation);
	}

	public boolean is64Bit() {
		return minecraft.is64Bit();
	}

	public boolean isPaused() {
		return minecraft.isPaused();
	}

	public GpuWarnlistManager getGpuWarnlistManager() {
		return minecraft.getGpuWarnlistManager();
	}

	public SoundManager getSoundManager() {
		return minecraft.getSoundManager();
	}

	public Music getSituationalMusic() {
		return minecraft.getSituationalMusic();
	}

	public MinecraftSessionService getMinecraftSessionService() {
		return minecraft.getMinecraftSessionService();
	}

	public SkinManager getSkinManager() {
		return minecraft.getSkinManager();
	}

	@Nullable
	public Entity getCameraEntity() {
		return minecraft.getCameraEntity();
	}

	public void setCameraEntity(Entity entity) {
		minecraft.setCameraEntity(entity);
	}

	public boolean shouldEntityAppearGlowing(Entity entity) {
		return minecraft.shouldEntityAppearGlowing(entity);
	}

	public BlockRenderDispatcher getBlockRenderer() {
		return minecraft.getBlockRenderer();
	}

	public EntityRenderDispatcher getEntityRenderDispatcher() {
		return minecraft.getEntityRenderDispatcher();
	}

	public ItemRenderer getItemRenderer() {
		return minecraft.getItemRenderer();
	}

	public ItemInHandRenderer getItemInHandRenderer() {
		return minecraft.getItemInHandRenderer();
	}

	public <T> MutableSearchTree<T> getSearchTree(SearchRegistry.Key<T> key) {
		return minecraft.getSearchTree(key);
	}

	public FrameTimer getFrameTimer() {
		return minecraft.getFrameTimer();
	}

	public boolean isConnectedToRealms() {
		return minecraft.isConnectedToRealms();
	}

	public void setConnectedToRealms(boolean bl) {
		minecraft.setConnectedToRealms(bl);
	}

	public DataFixer getFixerUpper() {
		return minecraft.getFixerUpper();
	}

	public float getFrameTime() {
		return minecraft.getFrameTime();
	}

	public float getDeltaFrameTime() {
		return minecraft.getDeltaFrameTime();
	}

	public BlockColors getBlockColors() {
		return minecraft.getBlockColors();
	}

	public boolean showOnlyReducedInfo() {
		return minecraft.showOnlyReducedInfo();
	}

	public ToastComponent getToasts() {
		return minecraft.getToasts();
	}

	public Tutorial getTutorial() {
		return minecraft.getTutorial();
	}

	public boolean isWindowActive() {
		return minecraft.isWindowActive();
	}

	public HotbarManager getHotbarManager() {
		return minecraft.getHotbarManager();
	}

	public ModelManager getModelManager() {
		return minecraft.getModelManager();
	}

	public PaintingTextureManager getPaintingTextures() {
		return minecraft.getPaintingTextures();
	}

	public MobEffectTextureManager getMobEffectTextures() {
		return minecraft.getMobEffectTextures();
	}

	public void setWindowActive(boolean bl) {
		minecraft.setWindowActive(bl);
	}

	public ProfilerFiller getProfiler() {
		return minecraft.getProfiler();
	}

	public Game getGame() {
		return minecraft.getGame();
	}

	public SplashManager getSplashManager() {
		return minecraft.getSplashManager();
	}

	@Nullable
	public Overlay getOverlay() {
		return minecraft.getOverlay();
	}

	public PlayerSocialManager getPlayerSocialManager() {
		return minecraft.getPlayerSocialManager();
	}

	public boolean renderOnThread() {
		return minecraft.renderOnThread();
	}

	public Window getWindow() {
		return minecraft.getWindow();
	}

	public RenderBuffers renderBuffers() {
		return minecraft.renderBuffers();
	}

	public void updateMaxMipLevel(int i) {
		minecraft.updateMaxMipLevel(i);
	}
}
