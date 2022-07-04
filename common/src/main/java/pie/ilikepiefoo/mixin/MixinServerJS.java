package pie.ilikepiefoo.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import dev.latvian.kubejs.server.ServerJS;
import dev.latvian.kubejs.world.ServerWorldJS;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.CrashReport;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.ServerFunctionManager;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.bossevents.CustomBossEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerConnectionListener;
import net.minecraft.server.network.TextFilter;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.server.players.PlayerList;
import net.minecraft.tags.TagContainer;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Snooper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.storage.CommandStorage;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.PredicateManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.security.KeyPair;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Mixin(ServerJS.class)
public class MixinServerJS {
	@Shadow
	private MinecraftServer minecraftServer;

	@Shadow
	public static ServerJS instance;

	public GameType getDefaultGameType() {
		return minecraftServer.getDefaultGameType();
	}

	public boolean isHardcore() {
		return minecraftServer.isHardcore();
	}

	public int getOperatorUserPermissionLevel() {
		return minecraftServer.getOperatorUserPermissionLevel();
	}

	public int getFunctionCompilationLevel() {
		return minecraftServer.getFunctionCompilationLevel();
	}

	public boolean shouldRconBroadcast() {
		return minecraftServer.shouldRconBroadcast();
	}

	public boolean saveAllChunks(boolean bl, boolean bl2, boolean bl3) {
		return minecraftServer.saveAllChunks(bl, bl2, bl3);
	}

	public void close() {
		minecraftServer.close();
	}

	public String getLocalIp() {
		return minecraftServer.getLocalIp();
	}

	public void setLocalIp(String string) {
		minecraftServer.setLocalIp(string);
	}

	public boolean isRunning() {
		return minecraftServer.isRunning();
	}

	public void halt(boolean bl) {
		minecraftServer.halt(bl);
	}

	public boolean pollTask() {
		return minecraftServer.pollTask();
	}

	@Environment(EnvType.CLIENT)
	public boolean hasWorldScreenshot() {
		return minecraftServer.hasWorldScreenshot();
	}

	@Environment(EnvType.CLIENT)
	public File getWorldScreenshotFile() {
		return minecraftServer.getWorldScreenshotFile();
	}

	public File getServerDirectory() {
		return minecraftServer.getServerDirectory();
	}

	public boolean isNetherEnabled() {
		return minecraftServer.isNetherEnabled();
	}

	public void addTickable(Runnable runnable) {
		minecraftServer.addTickable(runnable);
	}

	@Environment(EnvType.CLIENT)
	public boolean isShutdown() {
		return minecraftServer.isShutdown();
	}

	public File getFile(String string) {
		return minecraftServer.getFile(string);
	}

	public ServerWorldJS overworld() {
		return new ServerWorldJS(instance, minecraftServer.overworld());
	}

	@Nullable
	public ServerWorldJS getLevel(ResourceKey<Level> resourceKey) {
		ServerLevel level = minecraftServer.getLevel(resourceKey);
		return level == null ? null : new ServerWorldJS(instance, level);
	}

	public Set<ResourceKey<Level>> levelKeys() {
		return minecraftServer.levelKeys();
	}

	public Iterable<ServerWorldJS> getAllLevels() {
		return StreamSupport.stream(minecraftServer.getAllLevels().spliterator(),true).map((level)->new ServerWorldJS(instance,level)).collect(Collectors.toList());
	}

	public String getServerVersion() {
		return minecraftServer.getServerVersion();
	}

	public int getPlayerCount() {
		return minecraftServer.getPlayerCount();
	}

	public int getMaxPlayers() {
		return minecraftServer.getMaxPlayers();
	}

	public String[] getPlayerNames() {
		return minecraftServer.getPlayerNames();
	}

	public String getServerModName() {
		return minecraftServer.getServerModName();
	}

	public CrashReport fillReport(CrashReport crashReport) {
		return minecraftServer.fillReport(crashReport);
	}

	public Optional<String> getModdedStatus() {
		return minecraftServer.getModdedStatus();
	}

	public void sendMessage(Component component, UUID uUID) {
		minecraftServer.sendMessage(component, uUID);
	}

	public KeyPair getKeyPair() {
		return minecraftServer.getKeyPair();
	}

	public int getPort() {
		return minecraftServer.getPort();
	}

	public void setPort(int i) {
		minecraftServer.setPort(i);
	}

	public String getSingleplayerName() {
		return minecraftServer.getSingleplayerName();
	}

	public void setSingleplayerName(String string) {
		minecraftServer.setSingleplayerName(string);
	}

	public boolean isSingleplayer() {
		return minecraftServer.isSingleplayer();
	}

	public void setDifficulty(Difficulty difficulty, boolean bl) {
		minecraftServer.setDifficulty(difficulty, bl);
	}

	public int getScaledTrackingDistance(int i) {
		return minecraftServer.getScaledTrackingDistance(i);
	}

	public void setDifficultyLocked(boolean bl) {
		minecraftServer.setDifficultyLocked(bl);
	}

	public boolean isDemo() {
		return minecraftServer.isDemo();
	}

	public void setDemo(boolean bl) {
		minecraftServer.setDemo(bl);
	}

	public String getResourcePack() {
		return minecraftServer.getResourcePack();
	}

	public String getResourcePackHash() {
		return minecraftServer.getResourcePackHash();
	}

	public void setResourcePack(String string, String string2) {
		minecraftServer.setResourcePack(string, string2);
	}

	public void populateSnooper(Snooper snooper) {
		minecraftServer.populateSnooper(snooper);
	}

	public boolean isDedicatedServer() {
		return minecraftServer.isDedicatedServer();
	}

	public int getRateLimitPacketsPerSecond() {
		return minecraftServer.getRateLimitPacketsPerSecond();
	}

	public boolean usesAuthentication() {
		return minecraftServer.usesAuthentication();
	}

	public void setUsesAuthentication(boolean bl) {
		minecraftServer.setUsesAuthentication(bl);
	}

	public boolean getPreventProxyConnections() {
		return minecraftServer.getPreventProxyConnections();
	}

	public void setPreventProxyConnections(boolean bl) {
		minecraftServer.setPreventProxyConnections(bl);
	}

	public boolean isSpawningAnimals() {
		return minecraftServer.isSpawningAnimals();
	}

	public boolean areNpcsEnabled() {
		return minecraftServer.areNpcsEnabled();
	}

	public boolean isEpollEnabled() {
		return minecraftServer.isEpollEnabled();
	}

	public boolean isPvpAllowed() {
		return minecraftServer.isPvpAllowed();
	}

	public void setPvpAllowed(boolean bl) {
		minecraftServer.setPvpAllowed(bl);
	}

	public boolean isFlightAllowed() {
		return minecraftServer.isFlightAllowed();
	}

	public void setFlightAllowed(boolean bl) {
		minecraftServer.setFlightAllowed(bl);
	}

	public boolean isCommandBlockEnabled() {
		return minecraftServer.isCommandBlockEnabled();
	}

	public String getMotd() {
		return minecraftServer.getMotd();
	}

	public void setMotd(String string) {
		minecraftServer.setMotd(string);
	}

	public int getMaxBuildHeight() {
		return minecraftServer.getMaxBuildHeight();
	}

	public void setMaxBuildHeight(int i) {
		minecraftServer.setMaxBuildHeight(i);
	}

	public boolean isStopped() {
		return minecraftServer.isStopped();
	}

	public PlayerList getPlayerList() {
		return minecraftServer.getPlayerList();
	}

	public void setPlayerList(PlayerList playerList) {
		minecraftServer.setPlayerList(playerList);
	}

	public boolean isPublished() {
		return minecraftServer.isPublished();
	}

	public void setDefaultGameType(GameType gameType) {
		minecraftServer.setDefaultGameType(gameType);
	}

	@Nullable
	public ServerConnectionListener getConnection() {
		return minecraftServer.getConnection();
	}

	@Environment(EnvType.CLIENT)
	public boolean isReady() {
		return minecraftServer.isReady();
	}

	public boolean hasGui() {
		return minecraftServer.hasGui();
	}

	public boolean publishServer(GameType gameType, boolean bl, int i) {
		return minecraftServer.publishServer(gameType, bl, i);
	}

	public int getTickCount() {
		return minecraftServer.getTickCount();
	}

	@Environment(EnvType.CLIENT)
	public Snooper getSnooper() {
		return minecraftServer.getSnooper();
	}

	public int getSpawnProtectionRadius() {
		return minecraftServer.getSpawnProtectionRadius();
	}

	public boolean isUnderSpawnProtection(ServerLevel serverLevel,
										  BlockPos blockPos,
										  Player player) {
		return minecraftServer.isUnderSpawnProtection(serverLevel, blockPos, player);
	}

	public void setForceGameType(boolean bl) {
		minecraftServer.setForceGameType(bl);
	}

	public boolean getForceGameType() {
		return minecraftServer.getForceGameType();
	}

	public boolean repliesToStatus() {
		return minecraftServer.repliesToStatus();
	}

	public int getPlayerIdleTimeout() {
		return minecraftServer.getPlayerIdleTimeout();
	}

	public void setPlayerIdleTimeout(int i) {
		minecraftServer.setPlayerIdleTimeout(i);
	}

	public MinecraftSessionService getSessionService() {
		return minecraftServer.getSessionService();
	}

	public GameProfileRepository getProfileRepository() {
		return minecraftServer.getProfileRepository();
	}

	public GameProfileCache getProfileCache() {
		return minecraftServer.getProfileCache();
	}

	public ServerStatus getStatus() {
		return minecraftServer.getStatus();
	}

	public void invalidateStatus() {
		minecraftServer.invalidateStatus();
	}

	public int getAbsoluteMaxWorldSize() {
		return minecraftServer.getAbsoluteMaxWorldSize();
	}

	public boolean scheduleExecutables() {
		return minecraftServer.scheduleExecutables();
	}

	public Thread getRunningThread() {
		return minecraftServer.getRunningThread();
	}

	public int getCompressionThreshold() {
		return minecraftServer.getCompressionThreshold();
	}

	public long getNextTickTime() {
		return minecraftServer.getNextTickTime();
	}

	public DataFixer getFixerUpper() {
		return minecraftServer.getFixerUpper();
	}

	public int getSpawnRadius(@Nullable ServerLevel serverLevel) {
		return minecraftServer.getSpawnRadius(serverLevel);
	}

	public ServerAdvancementManager getAdvancements() {
		return minecraftServer.getAdvancements();
	}

	public ServerFunctionManager getFunctions() {
		return minecraftServer.getFunctions();
	}

	public CompletableFuture<Void> reloadResources(Collection<String> collection) {
		return minecraftServer.reloadResources(collection);
	}

	public void kickUnlistedPlayers(CommandSourceStack commandSourceStack) {
		minecraftServer.kickUnlistedPlayers(commandSourceStack);
	}

	public PackRepository getPackRepository() {
		return minecraftServer.getPackRepository();
	}

	public Commands getCommands() {
		return minecraftServer.getCommands();
	}

	public CommandSourceStack createCommandSourceStack() {
		return minecraftServer.createCommandSourceStack();
	}

	public boolean acceptsSuccess() {
		return minecraftServer.acceptsSuccess();
	}

	public boolean acceptsFailure() {
		return minecraftServer.acceptsFailure();
	}

	public RecipeManager getRecipeManager() {
		return minecraftServer.getRecipeManager();
	}

	public TagContainer getTags() {
		return minecraftServer.getTags();
	}

	public ServerScoreboard getScoreboard() {
		return minecraftServer.getScoreboard();
	}

	public CommandStorage getCommandStorage() {
		return minecraftServer.getCommandStorage();
	}

	public LootTables getLootTables() {
		return minecraftServer.getLootTables();
	}

	public PredicateManager getPredicateManager() {
		return minecraftServer.getPredicateManager();
	}

	public GameRules getGameRules() {
		return minecraftServer.getGameRules();
	}

	public CustomBossEvents getCustomBossEvents() {
		return minecraftServer.getCustomBossEvents();
	}

	public boolean isEnforceWhitelist() {
		return minecraftServer.isEnforceWhitelist();
	}

	public void setEnforceWhitelist(boolean bl) {
		minecraftServer.setEnforceWhitelist(bl);
	}

	public float getAverageTickTime() {
		return minecraftServer.getAverageTickTime();
	}

	public int getProfilePermissions(GameProfile gameProfile) {
		return minecraftServer.getProfilePermissions(gameProfile);
	}

	@Environment(EnvType.CLIENT)
	public FrameTimer getFrameTimer() {
		return minecraftServer.getFrameTimer();
	}

	public ProfilerFiller getProfiler() {
		return minecraftServer.getProfiler();
	}

	public boolean isSingleplayerOwner(GameProfile gameProfile) {
		return minecraftServer.isSingleplayerOwner(gameProfile);
	}

	public void saveDebugReport(Path path) throws IOException {
		minecraftServer.saveDebugReport(path);
	}

	public boolean isProfiling() {
		return minecraftServer.isProfiling();
	}

	public void startProfiling() {
		minecraftServer.startProfiling();
	}

	public ProfileResults finishProfiling() {
		return minecraftServer.finishProfiling();
	}

	public Path getWorldPath(LevelResource levelResource) {
		return minecraftServer.getWorldPath(levelResource);
	}

	public boolean forceSynchronousWrites() {
		return minecraftServer.forceSynchronousWrites();
	}

	public StructureManager getStructureManager() {
		return minecraftServer.getStructureManager();
	}

	public WorldData getWorldData() {
		return minecraftServer.getWorldData();
	}

	public RegistryAccess registryAccess() {
		return minecraftServer.registryAccess();
	}

	@Nullable
	public TextFilter createTextFilterForPlayer(ServerPlayer serverPlayer) {
		return minecraftServer.createTextFilterForPlayer(serverPlayer);
	}
}
