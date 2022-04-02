package io.rpg.config;

import com.google.gson.Gson;
import io.rpg.model.Game;
import io.rpg.model.GameWorldConfig;
import io.rpg.model.object.GameObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigLoader {
  private final Gson gson;
  private final Path pathToConfigDir;
  private final Path pathToRootFile;

  public static final String ERR_INVALID_CFG_DIR_PATH = "Could not resolve config directory." +
      " Make sure that the config dir path is correct";

  public static final String ERR_ROOT_FNF = ConfigConstants.ROOT +
      " file was not found inside config directory. Make sure that the file exists and is named properly";

  private final Logger logger;

  public ConfigLoader(@NotNull String configDirPath) {
    logger = LogManager.getLogger(ConfigLoader.class);

    logger.info("Ctor");

    pathToConfigDir = Path.of(configDirPath);
    pathToRootFile = pathToConfigDir.resolve(ConfigConstants.ROOT);
    gson = new Gson();

    validateState();
  }

  public void load() {
    logger.info("Load");
    try {
      GameWorldConfig config = loadGameWorldConfig();

      logger.info("GameWorldConfig loaded");
      logger.info(config.toString());

    } catch (FileNotFoundException e) {
      throw new RuntimeException(ERR_ROOT_FNF);
    }
  }

  private GameWorldConfig loadGameWorldConfig() throws FileNotFoundException {
    logger.info("Loading game world config");
    BufferedReader reader = new BufferedReader(new FileReader(pathToRootFile.toString()));
    GameWorldConfig config = gson.fromJson(reader, GameWorldConfig.class);

    // todo: validate input

    return config;
  }

  void validateState() {
    if (!Files.isDirectory(pathToConfigDir)) {
      logger.error(ERR_INVALID_CFG_DIR_PATH);
      throw new IllegalArgumentException(ERR_INVALID_CFG_DIR_PATH);
    } else if (!Files.isReadable(pathToRootFile)) {
      logger.error(ERR_ROOT_FNF);
      throw new IllegalArgumentException(ERR_ROOT_FNF);
    }
  }
}
