package io.rpg.config;

import com.google.gson.Gson;
import io.rpg.config.model.GameObjectConfig;
import io.rpg.config.model.GameWorldConfig;
import io.rpg.config.model.LocationConfig;
import io.rpg.util.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class exposes methods to load user specified configuration into
 * configuration objects:
 * {@link GameObjectConfig},
 * {@link GameWorldConfig},
 * {@link LocationConfig}.
 */
public class ConfigLoader {
  @NotNull
  private final Gson gson;

  /**
   * Path to the directory that contains configuration -
   * root.json file & locations directory
   */
  @NotNull
  private final Path configDirPath;

  /**
   * This filed is initialized basing on {@link io.rpg.config.ConfigLoader#configDirPath}.
   * It represents path to the root.json file which is entry point for {@link ConfigLoader}
   */
  @NotNull
  private final Path rootFilePath;

  /**
   * Similarly to {@link io.rpg.config.ConfigLoader#rootFilePath} this field is initialized basing on
   * {@link io.rpg.config.ConfigLoader#configDirPath}.
   * It represents path to the directory that holds locations configuration files.
   */
  @NotNull
  private final Path locationsDirPath;

  public static final String ERR_INVALID_CFG_DIR_PATH = "Could not resolve config directory."
      + " Make sure that the config dir path is correct";

  public static final String ERR_ROOT_FNF = ConfigConstants.ROOT
      + " file was not found inside config directory. Make sure that the file exists and is named properly";

  public static final String ERR_LOCATIONS_DIR_FNF = ConfigConstants.LOCATIONS_DIR
      + " directory was not found inside configuration directory";

  public static final String ERR_LOCATION_DIR_FNF_FOR_TAG =
      "Directory was not found for location with tag: ";

  public static final String ERR_LOCATION_CFG_NR_FOR_TAG =
      "Configuration file was not found for location: ";

  @NotNull
  private final Logger logger;

  /**
   * Creates {@link ConfigLoader} for configuration under configDirPath.
   *
   * @param configDirPath Path to the root directory.
   * @throws IllegalArgumentException with appropriate error message when path to config directory
   * path is invalid or configuration has invalid structure.
   *
   */
  public ConfigLoader(@NotNull String configDirPath) {
    this(Path.of(configDirPath));
  }

  /**
   * Creates {@link ConfigLoader} for configuration under configDirPath.
   *
   * @param configDirPath Path to the root directory.
   * @throws IllegalArgumentException with appropriate error message when path to config directory
   * path is invalid or configuration has invalid structure.
   *
   */
  public ConfigLoader(@NotNull Path configDirPath) {
    logger = LogManager.getLogger(ConfigLoader.class);

    logger.info("Initializing");

    this.configDirPath = configDirPath;
    rootFilePath = this.configDirPath.resolve(ConfigConstants.ROOT);
    locationsDirPath = this.configDirPath.resolve(ConfigConstants.LOCATIONS_DIR);
    gson = new Gson();

    logger.info("root file path: " + rootFilePath);

    validate();
  }

  /**
   * Loads {@link io.rpg.config.model.GameWorldConfig} from the configuration files specified
   * by the user.
   *
   * @return Valid {@link io.rpg.config.model.GameWorldConfig} or exception.
   */
  public Result<GameWorldConfig, Exception> load() {
    logger.info("Loading GameWorldConfig");

    Result<GameWorldConfig, Exception> configLoadResult = loadGameWorldConfig();

    if (configLoadResult.isError()) {
      logger.error("Error while loading GameWorldConfig");
      configLoadResult.getErrorValueOpt().ifPresent(ex -> logger.error(ex.getMessage()));
      return configLoadResult;
    } else if (configLoadResult.getOkValue() == null) {
      return Result.err(new RuntimeException("loadGameWorldConfig returned null config"));
    }

    GameWorldConfig gameWorldConfig = configLoadResult.getOkValue();

    logger.info("GameWorldConfig loaded");
    logger.info(gameWorldConfig.toString());

    // we assume here that gameWorldConfig was validated in loadGameWorldConfig method

    for (String locationTag : gameWorldConfig.getLocationTags()) {
      try {
        logger.info("Loading location config for tag: " + locationTag);

        Result<LocationConfig, Exception> locationConfigLoadingResult = loadLocationConfig(locationTag);

        if (locationConfigLoadingResult.isError()) {
          return Result.err(locationConfigLoadingResult.getErrValue());
        } else if (locationConfigLoadingResult.isOkValueNull()) {
          return Result.err(new RuntimeException("Null LocationConfig returned for location with tag: " + locationTag));
        }

        LocationConfig locationConfig = locationConfigLoadingResult.getOkValue();

        gameWorldConfig.addLocationConfig(locationConfig);

        logger.info("Location config loaded for tag: " + locationTag);
        // locationConfig can not be null here, we checked this case earlier
        logger.info(locationConfig.toString());

        assert locationConfig.getPath() != null : "Path to location dir must be set in its loader";
        Path objectsDir = locationConfig.getPath().resolve(ConfigConstants.OBJECTS_DIR);

        // TODO: @kkafar: Load objects from inside objects directory
        // TODO: consider extracting this code to method
        if (Files.isDirectory(objectsDir)) { // there is directory with additional object configurations
          for (GameObjectConfig gameObjectConfig : locationConfig.getObjects()) {
            Path gameObjectConfigSpec = objectsDir.resolve(gameObjectConfig.getTag() + ".json"); // TODO
            // there is additional spec for this game object
            if (Files.isReadable(gameObjectConfigSpec)) {
              logger.info("Detected additional spec for object with tag: " + gameObjectConfig.getTag());
              BufferedReader reader = new BufferedReader(new FileReader(gameObjectConfigSpec.toString()));
              GameObjectConfig config = gson.fromJson(reader, GameObjectConfig.class);
              gameObjectConfig.updateFrom(config);
            }
          }
        }

        // game configs validation
        // TODO: consider extracting this code to separate method
        for (GameObjectConfig gameObjectConfig : locationConfig.getObjects()) {
          Result<GameObjectConfig, Exception> result = gameObjectConfig.validate();

          if (result.isError()) {
            // TODO: consider returning loading error here
            result.getErrorValueOpt().ifPresentOrElse(ex -> {
              String exceptionMessage = ex.getMessage();
              logger.warn("Validation for game object config with tag: "
                  + gameObjectConfig.getTag() + " failed."
                  + (exceptionMessage != null ? "Reason: " + exceptionMessage : "No reason provided"));
            },
            () -> {
              logger.warn("Validation for game object config with tag: "
                  + gameObjectConfig.getTag() + " failed with null result.");
            });
          } else {
            logger.info("Loaded GameObjectConfig for tag: " + gameObjectConfig.getTag() + ":" + gameObjectConfig);
          }

        }


      } catch (FileNotFoundException e) {
        logger.warn("Failed to load location config for tag: " + locationTag);
        e.printStackTrace();
      }
    }

    Result<GameWorldConfig, Exception> validationResult = gameWorldConfig.validate();
    if (validationResult.isError()) {
      return Result.err(validationResult.getErrValue());
    }

    return Result.ok(validationResult.getOkValue());
  }

  @NotNull
  Result<GameWorldConfig, Exception> loadGameWorldConfig() {
    logger.info("Loading game world config");

    BufferedReader reader;

    try {
      reader = new BufferedReader(new FileReader(rootFilePath.toString()));
    } catch (FileNotFoundException exception) {
      return Result.err(exception);
    }

    // after loading a object from JSON we should always call the validate method
    GameWorldConfig gameWorldConfigShell = gson.fromJson(reader, GameWorldConfig.class);

    System.out.println(gameWorldConfigShell.toString());

    // GameWorldConfig is loaded in two stages right now
    // todo: fix this! Separate initial GameWorldConfig to different class
    Result<GameWorldConfig, Exception> configLoadResult = gameWorldConfigShell.validateStageOne();

    return configLoadResult;
  }

  Result<LocationConfig, Exception> loadLocationConfig(@NotNull String locationTag) throws FileNotFoundException {
    logger.info("Loading location: " + locationTag);

    Path locationDir = locationsDirPath.resolve(locationTag);

    if (!Files.isDirectory(locationDir)) {
      logger.error(ERR_LOCATION_DIR_FNF_FOR_TAG + locationTag);
      return Result.err(new FileNotFoundException(ERR_LOCATION_DIR_FNF_FOR_TAG + locationTag));
    }

    Path locationConfigJson = locationDir.resolve(locationTag + ".json");

    if (!Files.isReadable(locationConfigJson)) {
      logger.error(ERR_LOCATION_CFG_NR_FOR_TAG + locationTag);
      return Result.err(new RuntimeException(ERR_LOCATION_DIR_FNF_FOR_TAG + locationTag));
    }

    BufferedReader reader = new BufferedReader(new FileReader(locationConfigJson.toString()));
    LocationConfig config = gson.fromJson(reader, LocationConfig.class);

    Result<LocationConfig, Exception> locationConfigValidationResult =
        config.validate();

    if (locationConfigValidationResult.isError()) {
      return locationConfigValidationResult;
    } else {
      config.setPath(locationDir);
      return Result.ok(config);
    }
  }

  private void validate() {
    if (!Files.isDirectory(configDirPath)) {
      logger.error(ERR_INVALID_CFG_DIR_PATH);
      throw new IllegalArgumentException(ERR_INVALID_CFG_DIR_PATH);
    } else if (!Files.isReadable(rootFilePath)) {
      logger.error(ERR_ROOT_FNF);
      throw new IllegalArgumentException(ERR_ROOT_FNF);
    } else if (!Files.isDirectory(locationsDirPath)) {
      logger.error(ERR_LOCATIONS_DIR_FNF);
      throw new IllegalArgumentException(ERR_LOCATIONS_DIR_FNF);
    }
  }
}
