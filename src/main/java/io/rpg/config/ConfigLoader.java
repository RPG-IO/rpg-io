package io.rpg.config;

import com.google.gson.Gson;

import io.rpg.model.GameWorldConfig;
import io.rpg.model.location.LocationConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigLoader {
  @NotNull
  private final Gson gson;

  @NotNull
  private final Path pathToConfigDir;

  @NotNull
  private final Path pathToRootFile;

  @NotNull
  private final Path pathToLocationsDir;

  public static final String ERR_INVALID_CFG_DIR_PATH = "Could not resolve config directory." +
      " Make sure that the config dir path is correct";

  public static final String ERR_ROOT_FNF = ConfigConstants.ROOT +
      " file was not found inside config directory. Make sure that the file exists and is named properly";

  public static final String ERR_LOCATIONS_DIR_FNF = ConfigConstants.LOCATIONS_DIR +
      " directory was not found inside configuration directory";

  public static final String ERR_LOCATION_DIR_FNF_FOR_TAG =
      "Directory was not found for location with tag: ";

  public static final String ERR_LOCATION_CFG_NR_FOR_TAG =
      "Configuration file was not found for location: ";

  @NotNull
  private final Logger logger;

  public ConfigLoader(@NotNull String configDirPath) {
    logger = LogManager.getLogger(ConfigLoader.class);

    logger.info("Initializing");

    pathToConfigDir = Path.of(configDirPath);
    pathToRootFile = pathToConfigDir.resolve(ConfigConstants.ROOT);
    pathToLocationsDir = pathToConfigDir.resolve(ConfigConstants.LOCATIONS_DIR);
    gson = new Gson();

    validateState();
  }

  public void load() {
    logger.info("Load");

    GameWorldConfig config;
    try {
      config = loadGameWorldConfig();

      logger.info("GameWorldConfig loaded");
      logger.info(config.toString());

    } catch (FileNotFoundException e) {
      throw new RuntimeException(ERR_ROOT_FNF);
    }

    for (String locationTag : config.getLocations()) {
      try {
        loadLocation(locationTag);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  @NotNull
  GameWorldConfig loadGameWorldConfig() throws FileNotFoundException {
    logger.info("Loading game world config");
    BufferedReader reader = new BufferedReader(new FileReader(pathToRootFile.toString()));
    GameWorldConfig config = gson.fromJson(reader, GameWorldConfig.class);

    // todo: validate input

    return config;
  }

  @Nullable
  LocationConfig loadLocation(@NotNull String locationTag) throws FileNotFoundException {
    logger.info("Loading location: " + locationTag);

    Path locationDir = pathToLocationsDir.resolve(locationTag);

    if (!Files.isDirectory(locationDir)) {
      logger.error(ERR_LOCATION_DIR_FNF_FOR_TAG + locationTag);
      throw new FileNotFoundException(ERR_LOCATION_DIR_FNF_FOR_TAG + locationTag);
    }

    Path locationConfigJson = locationDir.resolve(locationTag + ".json");

    if (!Files.isReadable(locationConfigJson)) {
      logger.error(ERR_LOCATION_CFG_NR_FOR_TAG + locationTag);
      throw new RuntimeException(ERR_LOCATION_DIR_FNF_FOR_TAG + locationTag);
    }

    BufferedReader reader = new BufferedReader(new FileReader(locationConfigJson.toString()));
    LocationConfig config = gson.fromJson(reader, LocationConfig.class);
    return null;
  }

  private void validateState() {
    if (!Files.isDirectory(pathToConfigDir)) {
      logger.error(ERR_INVALID_CFG_DIR_PATH);
      throw new IllegalArgumentException(ERR_INVALID_CFG_DIR_PATH);
    } else if (!Files.isReadable(pathToRootFile)) {
      logger.error(ERR_ROOT_FNF);
      throw new IllegalArgumentException(ERR_ROOT_FNF);
    } else if (!Files.isDirectory(pathToLocationsDir)) {
      logger.error(ERR_LOCATIONS_DIR_FNF);
      throw new IllegalArgumentException(ERR_LOCATIONS_DIR_FNF);
    }
  }
}
