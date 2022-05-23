package io.rpg.config.model;


import io.rpg.util.Result;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LocationConfig {
  public static final int MAX_HEIGHT = 10;
  public static final int MAX_WIDTH = 10;
  @Nullable
  private String tag;

  @NotNull
  private ArrayList<GameObjectConfig> objects;

  @Nullable
  private Path path;

  @Nullable
  private String backgroundPath;

  private int width;
  private int height;

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

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Result<LocationConfig, Exception> validate() {
    if (tag == null) {
      return Result.error(new IllegalStateException("Null tag"));
    } else if (backgroundPath == null || backgroundPath.isBlank()) {
      // TODO: Validate the backgroundPath here
      return Result.error(new IllegalStateException("Empty string as background path"));
    } else if (width > MAX_WIDTH || height > MAX_HEIGHT) {
      return Result.error(new IllegalStateException("Size of location is greater than max size"));
    } else {
      return Result.ok(this);
    }
  }
}
