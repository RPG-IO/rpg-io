package io.rpg.config.model;

import io.rpg.model.data.Position;
import io.rpg.model.object.GameObject;
import io.rpg.model.object.GameObjects;
import io.rpg.util.DataObjectDescriptionProvider;
import io.rpg.util.Result;
import org.jetbrains.annotations.NotNull;

/**
 * Represents {@link io.rpg.model.object.GameObject} configuration provided by user
 * in configuration files.
 */
public class GameObjectConfig {

  private String type;
  private String tag;
  private Position position;
  private String assetPath;

  public GameObjectConfig(@NotNull String tag, @NotNull Position position) {
    this.tag = tag;
    this.position = position;
  }

  public String getTag() {
    return tag;
  }

  public Position getPosition() {
    return position;
  }

  public String getAssetPath() {
    return assetPath;
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
    this.position = gameObjectConfig.position;
    if (gameObjectConfig.getTypeString() != null) {
      this.type = gameObjectConfig.getTypeString();
    }
  }

  @Override
  public String toString() {
    return DataObjectDescriptionProvider.combineDescriptions(
        DataObjectDescriptionProvider.getFieldDescription(this, GameObject.class),
        DataObjectDescriptionProvider.getFieldDescription(this, GameObjectConfig.class)
    );
  }
}
