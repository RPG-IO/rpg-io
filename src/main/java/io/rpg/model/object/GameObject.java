package io.rpg.model.object;

import io.rpg.model.data.Position;
import org.jetbrains.annotations.NotNull;

/**
 * Class representing common state properties for all
 * objects appearing in the game
 */
public class GameObject {
  /**
   * Position of game object in model's representation of location
   */
  @NotNull
  private final Position position;

  /**
   * Unique identifier of this game object
   */
  @NotNull
  private final String tag;

  /**
   * Unique identifier of this game object
   */
  @NotNull
  public String getTag() {
    return tag;
  }

  /**
   * Position of game object in model's representation of location
   */
  @NotNull
  public Position getPosition() {
    return position;
  }

  public void validate() {
    // nothing to validate for now
  }

  public GameObject(@NotNull String tag, @NotNull Position position) {
    this.tag = tag;
    this.position = position;
  }
}
