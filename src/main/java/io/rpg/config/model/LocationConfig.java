package io.rpg.config.model;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LocationConfig {
  @Nullable
  private String tag;

  @NotNull
  private ArrayList<GameObjectConfig> objects;

  @Nullable
  private Path path;

  @Nullable
  private String backgroundPath;

  // This class is not meant to be instantiated
  // by hand. Only Gson should be able to do so
  private LocationConfig() {
    objects = new ArrayList<>();
  }

  @Nullable
  public String getTag() {
    return tag;
  }

  @Nullable
  public String getBackgroundPath() {
    return backgroundPath;
  }

  @NotNull
  public List<GameObjectConfig> getObjects() {
    return objects;
  }

  @Nullable
  public Path getPath() {
    return path;
  }

  public void setPath(@NotNull Path path) {
    this.path = path;
  }

  public void validate() {

  }
}
