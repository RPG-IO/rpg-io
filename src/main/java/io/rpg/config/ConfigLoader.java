package io.rpg.config;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigLoader {
  private final Gson gson;
  private final Path pathToConfigDir;
  private final Path pathToRootFile;


  public ConfigLoader(@NotNull String configDirPath) {

    pathToConfigDir = Path.of(configDirPath);
    pathToRootFile = pathToConfigDir.resolve(ConfigConstants.ROOT);
    gson = new Gson();

    validateState();
  }

  public void load() {

  }

  void validateState() {
    if (!Files.isDirectory(pathToConfigDir)) {

      throw new IllegalArgumentException("Could not resolve " + pathToConfigDir + " directory. " +
          "Make sure that the config dir path is correct.");
    }
  }
}
