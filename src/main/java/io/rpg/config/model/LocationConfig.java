package io.rpg.config.model;


import io.rpg.util.ErrorMessageBuilder;
import io.rpg.util.Result;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Files;
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
    ErrorMessageBuilder builder = new ErrorMessageBuilder();
    if (tag == null) {
      builder.append("Null tag");
    }
    if (backgroundPath == null) {
      builder.append("No background path provided");
    } else if (backgroundPath.isBlank()) {
      builder.append("Blank background path");
    } else if (!Files.isRegularFile(Path.of(backgroundPath))) {
      builder.append("Provided background path: \"" + backgroundPath
          + "\" does not point to a regular file");
    }

    return builder.isEmpty() ? Result.ok(this) :
        Result.err(new IllegalStateException(builder.toString()));
  }
}
