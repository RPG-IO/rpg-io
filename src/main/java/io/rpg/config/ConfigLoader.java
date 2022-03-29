package io.rpg.config;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

public class ConfigLoader {
  private final Gson gson;
  private final String pathToConfigDir;

  public ConfigLoader(@NotNull String configDirPath) {
    pathToConfigDir = configDirPath;
    gson = new Gson();
  }

  public void load() {

  }
}
