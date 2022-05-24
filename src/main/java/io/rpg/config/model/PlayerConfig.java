package io.rpg.config.model;

import com.google.gson.annotations.SerializedName;
import io.rpg.model.data.Position;
import org.jetbrains.annotations.NotNull;

/**
 * Represents player configuration.
 */
public class PlayerConfig extends GameObjectConfig {
  @SerializedName("location")
  private String initialLocationTag;

  private int strength;

  public PlayerConfig(@NotNull String tag, @NotNull Position position) {
    super(tag, position);
  }

  public String getInitialLocationTag() {
    return initialLocationTag;
  }
}
