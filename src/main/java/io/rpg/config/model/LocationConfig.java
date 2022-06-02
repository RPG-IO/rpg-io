package io.rpg.config.model;


import com.kkafara.rt.Result;
import io.rpg.util.ErrorMessageBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class LocationConfig {
  public static final int MAX_HEIGHT = 10;
  public static final int MAX_WIDTH = 10;
  @Nullable
  private String tag;

  @NotNull
  private Set<GameObjectConfig> objects;

  @Nullable
  private Path path;

  @Nullable
  private String backgroundPath;

  private int width;
  private int height;

  // This class is not meant to be instantiated
  // by hand. Only Gson should be able to do so
  private LocationConfig() {
    objects = new LinkedHashSet<>();
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
  public Set<GameObjectConfig> getObjects() {
    // objects actually might be null when this class is instantiated via Gson &
    // there is no objects param in JSON
    if (objects == null) {
      objects = new LinkedHashSet<>();
    }
    return objects;
  }

  public void addObjectConfig(@NotNull GameObjectConfig config) {
    objects.add(config);
  }

  /**
   * Checks whether object with given tag has already been initialized.
   *
   * @param tag of a object
   * @return true if {@link GameObjectConfig} for object with given tag already exists.
   */
  public Optional<GameObjectConfig> getGameObjectConfigForTag(@Nullable String tag) {
    if (tag == null) {
      return Optional.empty();
    }
    for (GameObjectConfig config : objects) {
      if (config.getTag().equals(tag)) {
        return Optional.of(config);
      }
    }
    return Optional.empty();
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
    } else if (objects == null) {
      builder.append("No objects specified");
    }

    return builder.isEmpty() ? Result.ok(this) :
        Result.err(new IllegalStateException(builder.toString()));
  }
}
