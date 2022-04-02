package io.rpg.config;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

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

    pathToConfigDir = Path.of(configDirPath);
    pathToRootFile = pathToConfigDir.resolve(ConfigConstants.ROOT);
    gson = new Gson();

    validateState();
  }

  public void load() {

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
