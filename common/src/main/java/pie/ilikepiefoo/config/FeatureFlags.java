package pie.ilikepiefoo.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.architectury.platform.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo.KubeJSAdditions;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class FeatureFlags {
	public static final String CONFIG_FILE = "feature_flags.json";
	public static final FeatureFlags INSTANCE = new FeatureFlags();
	private static final Logger LOG = LogManager.getLogger();
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Map<String, Boolean> FLAGS = new TreeMap<>();

	private FeatureFlags() {
		load();
		LOG.info("Initialized Feature Flags. Loaded {} flags from config.", FLAGS.size());
	}

	public synchronized void load() {
		File configFile = createConfigFile();
		TreeMap<?, ?> flags;
		try (var reader = new FileReader(configFile)) {
			flags = GSON.fromJson(reader, TreeMap.class);
		} catch (IOException e) {
			LOG.error("Failed to read from Config File.", e);
			return;
		}
		FLAGS.clear();

		flags.entrySet().stream()
				.filter(Objects::nonNull)
				.filter((entry) -> entry.getKey() instanceof String && entry.getValue() instanceof Boolean)
				.map((entry) -> Map.entry((String) entry.getKey(), (Boolean) entry.getValue()))
				.forEachOrdered((entry) -> FLAGS.put(entry.getKey(), entry.getValue()));
	}

	private synchronized File createConfigFile() {
		LOG.info("Saving Feature Flags");
		var configFolder = Platform.getConfigFolder().resolve(KubeJSAdditions.MOD_ID).toFile();
		if (!configFolder.exists()) {
			if (configFolder.mkdirs()) {
				LOG.info("Created Config Folder");
			} else {
				LOG.error("Failed to create Config Folder");
			}
		}
		var configFile = configFolder.toPath().resolve(CONFIG_FILE).toFile();
		if (!configFile.exists()) {
			try {
				if (configFile.createNewFile()) {
					LOG.info("Created Config File");
				} else {
					LOG.error("Failed to create Config File.");
				}
			} catch (IOException e) {
				LOG.error("Failed to create Config File.", e);
			}
		}
		return configFile;
	}

	public static void feature(String name, Runnable runnable) {
		feature(name, true, runnable);
	}

	public synchronized Boolean getFlagDefault(String name, Boolean defaultValue) {
		return FLAGS.getOrDefault(name, defaultValue);
	}

	public static void feature(String name, Boolean defaultValue, Runnable runnable) {
		String packageName = runnable.getClass().getPackageName();
		if (packageName.contains("fabric")) {
			name += " (Fabric)";
		} else if (packageName.contains("forge")) {
			name += " (Forge)";
		} else {
			name += " (Common)";
		}

		if (INSTANCE.getFlagDefault(name, defaultValue)) {
			runnable.run();
		}
	}

	public synchronized void setFlag(String name, Boolean value) {
		FLAGS.put(name, value);
		save();
	}

	public synchronized void save() {
		File configFile = createConfigFile();
		try (var writer = new FileWriter(configFile)) {
			GSON.toJson(FLAGS, writer);
		} catch (IOException e) {
			LOG.error("Failed to write to Config File.", e);
		}
		LOG.info("KubeJS Additions Feature Flags have been saved to {}", configFile.getPath());
	}

	public synchronized Boolean getFlag(String name) {
		return FLAGS.getOrDefault(name, false);
	}

	public synchronized Map<String, Boolean> getFlags() {
		return Collections.unmodifiableMap(FLAGS);
	}


}

