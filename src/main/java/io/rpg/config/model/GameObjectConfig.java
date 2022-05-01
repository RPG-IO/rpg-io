package io.rpg.config.model;

import io.rpg.model.data.Position;
import io.rpg.model.object.GameObject;
import io.rpg.model.object.GameObjects;
import io.rpg.util.DataObjectDescriptionProvider;
import io.rpg.util.ErrorMessageBuilder;
import io.rpg.util.Result;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents {@link io.rpg.model.object.GameObject} configuration provided by user
 * in configuration files.
 */
public class GameObjectConfig {

  /**
   *
   */
  private String type;
  private String tag;
  private Position position;
  private String assetPath;

  /**
   * Config for the action triggered when object is pressed.
   */
  @Nullable
  private ActionConfig onPress;

  /**
   * Config for the action triggered when player approaches
   * the object.
   */
  @Nullable
  private ActionConfig onApproach;

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
    ErrorMessageBuilder builder = new ErrorMessageBuilder();

    if (!GameObjects.isValidType(type)) {
      // TODO: remove this validation as there is no longer
      // bounding between object type and action.

      builder.append("Invalid object type: " + type);
    }

    return builder.isEmpty() ? Result.ok(this) :
        Result.error(new IllegalStateException(builder.toString()));
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
