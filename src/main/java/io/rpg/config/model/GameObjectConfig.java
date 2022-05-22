package io.rpg.config.model;

import io.rpg.model.data.Position;
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
   * Position of game object in model's representation of location.
   */
  @Nullable
  protected Position position;

  /**
   * Unique identifier of this game object.
   * This value is set in location
   */
  @NotNull
  private String tag;

  /**
   * Path to the image representing this object.
   */
  @Nullable
  protected String assetPath;

  /**
   *
   */
  private String type;

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

  public String getTypeString() {
    assert type != null : "Attempt to access uninitialized \"type\" field!";
    return type;
  }

  @NotNull
  public String getTag() {
    return tag;
  }

  @Nullable
  public String getAssetPath() {
    return assetPath;
  }

  @Nullable
  public ActionConfig getOnApproach() {
    return onApproach;
  }

  @Nullable
  public ActionConfig getOnPress() {
    return onPress;
  }

  @Nullable
  public Position getPosition() {
    return position;
  }

  /**
   * Only validates presence of objects tag. Meant for use in some special cases in {@link io.rpg.config.ConfigLoader}.
   *
   * @return maybe-valid {@link GameObjectConfig} or exception.
   */
  public Result<GameObjectConfig, Exception> validateBasic() {
    if (tag == null || tag.isBlank()) {
      return Result.err(new Exception("Invalid or no tag provided"));
    }
    return Result.ok();
  }

  /**
   * Allows for validation of the object's state.
   *
   * @return Object in valid state or exception.
   */
  public Result<GameObjectConfig, Exception> validate() {
    ErrorMessageBuilder builder = new ErrorMessageBuilder();

    if (tag == null) {
      builder.append("No tag provided");
    } else if (tag.isBlank()) {
      builder.append("Blank tag");
    }
    if (assetPath == null || assetPath.isBlank()) {
      builder.append("Invalid path to asset");
    }
    if (position == null) {
      builder.append("No position provided");
    }

    return builder.isEmpty() ? Result.ok(this) : Result.err(new Exception(builder.toString()));
  }

  public void updateFrom(GameObjectConfig gameObjectConfig) {
    if (gameObjectConfig.position != null) {
      this.position = gameObjectConfig.position;
    }
    if (gameObjectConfig.getTypeString() != null) {
      this.type = gameObjectConfig.getTypeString();
    }
    if (gameObjectConfig.assetPath != null) {
      this.assetPath = gameObjectConfig.assetPath;
    }
  }

  @Override
  public String toString() {
    return DataObjectDescriptionProvider.combineDescriptions(
        DataObjectDescriptionProvider.getFieldDescription(this, GameObjectConfig.class)
    );
  }
}
