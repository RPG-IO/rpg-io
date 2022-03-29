package io.rpg.model.object;

import io.rpg.model.data.Position;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract class representing common state properties for all
 * objects appearing in the game
 */
abstract public class GameObject {
  /**
   * Position of game object in model's representation of location
   */
  @NotNull
  private final Position mPosition;

  /**
   * Unique identifier of this game object
   */
  @NotNull
  private final String mTag;

  /**
   * Unique identifier of this game object
   */
  public String getTag() {
    return mTag;
  }

  /**
   * Position of game object in model's representation of location
   */
  public Position getPosition() {
    return mPosition;
  }

  public GameObject(@NotNull String tag, @NotNull Position position) {
    mTag = tag;
    mPosition = position;
  }
}
