package io.rpg.config;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

public class ConfigLoader {
  private final Gson mGson;
  private final String mPathToConfigDir;

  public ConfigLoader(@NotNull String configDirPath) {
    mPathToConfigDir = configDirPath;
    mGson = new Gson();
  }

  public void load() {

  }
}
