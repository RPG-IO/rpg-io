package io.rpg.config.model;

import io.rpg.model.data.Position;
import io.rpg.model.object.GameObject;
import io.rpg.model.object.GameObjects;
import io.rpg.util.Result;
import org.jetbrains.annotations.NotNull;

/**
 * Represents {@link io.rpg.model.object.GameObject} configuration provided by user
 * in configuration files.
 */
public class GameObjectConfig extends GameObject {

  private String type;

  public GameObjectConfig(@NotNull String tag, @NotNull Position position) {
    super(tag, position);
  }

  public String getTypeString() {
    assert type != null : "Attempt to access uninitialized \"type\" field!";
    return type;
  }

  /**
   * Allows for validation of the object's state.
   *
   * @return Object in valid state or exception.
   */
  public Result<GameObjectConfig, Exception> validate() {
    if (!GameObjects.isValidType(type)) {
      return Result.error(new IllegalStateException("Invalid object type: " + type));
    }
    return Result.ok(this);
  }

  public void updateFrom(GameObjectConfig gameObjectConfig) {
    if (gameObjectConfig.getPosition() != null) {
      this.position = gameObjectConfig.getPosition();
    }
    if (gameObjectConfig.getTypeString() != null) {
      this.type = gameObjectConfig.getTypeString();
    }
    if (gameObjectConfig.getAssetPath() != null) {
      this.assetPath = gameObjectConfig.assetPath;
    }
  }
}
