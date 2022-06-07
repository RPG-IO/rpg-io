package io.rpg.config;

import com.google.gson.Gson;
import io.rpg.config.model.GameObjectConfig;
import io.rpg.config.model.GameWorldConfig;
import io.rpg.config.model.LocationConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;

import com.kkafara.rt.Result;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

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

    Result<GameWorldConfig, Exception> configLoadResult = loadRootFile();

    if (configLoadResult.isErr()) {
      logger.error("Error while loading GameWorldConfig");
      configLoadResult.getErrOpt().ifPresent(ex -> logger.error(ex.getMessage()));
      return configLoadResult;
    }

    Optional<GameWorldConfig> gameWorldConfigOpt = configLoadResult.getOkOpt();

    if (gameWorldConfigOpt.isEmpty()) {
      return Result.err(new RuntimeException("loadGameWorldConfig returned null config"));
    }

    GameWorldConfig gameWorldConfig = gameWorldConfigOpt.get();
    logger.info("GameWorldConfig loaded: \n" + gameWorldConfig);

    // we assume here that gameWorldConfig was validated in loadGameWorldConfig method
    for (String locationTag : gameWorldConfig.getLocationTags()) {
      try {
        Result<LocationConfig, Exception> locationConfigLoadingResult = loadLocationConfig(locationTag);

        if (locationConfigLoadingResult.isErr()) {
          return Result.err(locationConfigLoadingResult.getErr());
        } else if (locationConfigLoadingResult.isOkValueNull()) {
          return Result.err(new RuntimeException("Null LocationConfig returned for location with tag: " + locationTag));
        }

        LocationConfig locationConfig = locationConfigLoadingResult.getOk();

        gameWorldConfig.addLocationConfig(locationConfig);

        logger.info("Location config loaded for tag: " + locationTag);
        // locationConfig can not be null here, we checked this case earlier
        logger.info(locationConfig.toString());

        Result<Void, Exception> result = loadGameObjectsForLocation(locationConfig);

        if (result.isErr()) {
          result.getErrOpt().ifPresentOrElse(err -> {
            logger.warn("An error occurred while loading config for location with tag: " + locationTag + "\n"
                + err.getMessage());
          }, () -> {
            logger.warn("An unknown error occurred while loading config for location with tag: " + locationTag);
          });
        }

      } catch (IOException e) {
        logger.warn("Failed to load location config for tag: " + locationTag + ": \n" + e.getMessage());
        e.printStackTrace();
      }
    }

    Result<Void, Exception> validationResult = gameWorldConfig.validate();
    if (validationResult.isErr()) {
      return Result.err(validationResult.getErr());
    }

    return Result.ok(gameWorldConfig);
  }

  @NotNull
  private Result<Void, Exception> loadGameObjectsForLocation(LocationConfig locationConfig) throws IOException {
    assert locationConfig.getPath() != null : "Path to location dir must be set in its loader";
    Path objectsDir = locationConfig.getPath().resolve(ConfigConstants.OBJECTS_DIR);

    if (Files.isDirectory(objectsDir)) { // there is directory with additional object configurations
      try (DirectoryStream<Path> objectConfigPathStream = Files.newDirectoryStream(objectsDir)) {
        for (Path objectConfigPath : objectConfigPathStream) {
          if (Files.isReadable(objectConfigPath)) {
            String objectTag = FilenameUtils.removeExtension(objectConfigPath.getFileName().toString());
            logger.info("Detected configuration for object with tag: " + objectTag);
            GameObjectConfig config = gson.fromJson(Files.newBufferedReader(objectConfigPath), GameObjectConfig.class);

            Result<Void, Exception> validationResult = config.validateBasic();

            if (validationResult.isErr()) {
              String error = "Validation for object with tag: " + objectTag + " failed."
                  + (validationResult.isErrValueNull() ? "No reason provided."
                  : "Reason: " + validationResult.getErr().getMessage());
              logger.error(error);
              return Result.err(new Exception(error));
            }

            if (!config.getTag().equals(objectTag)) {
              String error = "Object tag: " + config.getTag() + " does not match tag deduced from file name: " + objectTag;
              logger.error(error);
              return Result.err(new Exception(error));
            }

            locationConfig.getGameObjectConfigForTag(objectTag).ifPresentOrElse(existingConfig -> {
              logger.debug("Updating configuration for object with tag: " + objectTag);
              existingConfig.updateFrom(config);
            }, () -> {
              logger.debug("Added new configuration for object with tag: " + objectTag);
              locationConfig.addObjectConfig(config);
            });
          } else {
            logger.warn("Detected non-readable file: " + objectConfigPath + " inside objects directory");
          }
        }
      }
    }

    // game configs validation
    for (GameObjectConfig gameObjectConfig : locationConfig.getObjects()) {
      Result<Void, Exception> result = gameObjectConfig.validate();

      if (result.isErr()) {
        result.getErrOpt().ifPresentOrElse(ex -> {
          String exceptionMessage = ex.getMessage();
          logger.warn("Validation for game object config with tag: "
              + gameObjectConfig.getTag() + " failed."
              + (exceptionMessage != null ? "Reason: " + exceptionMessage : "No reason provided"));
          }, () -> {
            logger.warn("Validation for game object config with tag: "
                + gameObjectConfig.getTag() + " failed with null result.");
          });
        return Result.err(new Exception("Validation for game object config with tag: " + gameObjectConfig.getTag()
            + " failed."));
      } else {
        logger.info("Loaded GameObjectConfig for tag: " + gameObjectConfig.getTag());
      }
    }

    return Result.ok();
  }

  /**
   * Attempts to load {@link GameWorldConfig} with initial information provided in
   * configuration root file.
   * Notice that it does not fully initialise the {@link GameWorldConfig}.
   * Information it fulfills is: <br>
   * <ol>
   *   <li>location tags</li>
   *   <li>player config</li>
   *   <li>root location tag</li>
   *   <li>game tag</li>
   * </ol>
   * Location tags are loaded from configuration root file & also all locations that are not specified in
   * root file but their directories exist in `locations` directory are added. Player config must be specified inside
   * configuration root file, same for game tag & root location tag.
   *
   * @return Initially loaded {@link GameWorldConfig} or an exception.
   */
  @NotNull
  Result<GameWorldConfig, Exception> loadRootFile() {
    logger.info("Loading configuration from root file");

    BufferedReader reader;

    try {
      reader = new BufferedReader(new FileReader(rootFilePath.toString()));
    } catch (FileNotFoundException exception) {
      return Result.err(exception);
    }

    // after loading an object from JSON we should always call the validate method
    // load information from configuration root file
    GameWorldConfig gameWorldConfigShell = gson.fromJson(reader, GameWorldConfig.class);

    System.out.println(gameWorldConfigShell.toString());

    // GameWorldConfig is loaded in two stages right now
    // todo: fix this! Separate initial GameWorldConfig to different class
    gameWorldConfigShell.injectConfigDirPath(configDirPath);
    Result<Void, Exception> configLoadResult = gameWorldConfigShell.validateStageOne();

    if (configLoadResult.isErr()) {
      return Result.err(configLoadResult.getErrOrNull());
    }

    GameWorldConfig config = gameWorldConfigShell;

    // detect all locations not specified explicitly in configuration root file
    try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(locationsDirPath)) {
      for (Path path : directoryStream) {
        if (Files.isDirectory(path)) {
          String locationTag = path.getFileName().toString();
          logger.info("Detected location directory: " + locationTag);
          if (config.getLocationTags().contains(locationTag)) {
            logger.info(locationTag + " location already declared in root file");
          } else {
            logger.info(locationTag + " location added to locations list");
            config.getLocationTags().add(locationTag);
          }
        } else {
          logger.warn("Non-directory file of name " + path + " detected inside locations directory");
        }
      }
    } catch (IOException ex) {
      logger.error("IOException while iterating over locations dir content:\n" + ex.getMessage());
    }

    return Result.ok(config);
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

    if (locationConfigValidationResult.isErr()) {
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
