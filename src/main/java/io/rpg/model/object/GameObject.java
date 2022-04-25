package io.rpg.model.object;

import io.rpg.config.model.GameObjectConfig;
import io.rpg.model.data.GameObjectStateChange;
import io.rpg.model.data.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Class representing common state properties for all
 * objects appearing in the game.
 */
public class GameObject implements GameObjectStateChange.Emitter {
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
  private final String tag;

  /**
   *
   */
  @Nullable
  protected String assetPath;

  @NotNull
  private final Set<GameObjectStateChange.Observer> stateChangeObservers;

  @Nullable
  public String getAssetPath() {
    return assetPath;
  }

  /**
   * Unique identifier of this game object.
   */
  @NotNull
  public String getTag() {
    return tag;
  }

  /**
   * Position of game object in model's representation of location.
   *
   * @return initial position on object in the model (on the grid)
   */
  @Nullable
  public Position getPosition() {
    return position;
  }

  public GameObject(@NotNull String tag, @NotNull Position position) {
    this(tag, position, "");
  }

  public GameObject(@NotNull String tag, @NotNull Position position, @NotNull String assetPath) {
    this.tag = tag;
    this.position = position;
    this.assetPath = assetPath;
    this.stateChangeObservers = new LinkedHashSet<>();
  }

  @Override
  public void emitGameObjectStateChange(GameObjectStateChange event) {
    // TODO
    // noop for now
  }

  @Override
  public void addGameObjectStateChangeObserver(GameObjectStateChange.Observer observer) {
    this.stateChangeObservers.add(observer);
  }

  @Override
  public void removeGameObjectStateChangeObserver(GameObjectStateChange.Observer observer) {
    this.stateChangeObservers.remove(observer);
  }

  public enum Type {
    NAVIGABLE("navigable"),
    DIALOG("dialog"),
    PLAYER("player"),
    COLLECTIBLE("collectible");

    private final String asString;

    Type(String asString) {
      this.asString = asString;
    }
  }
}
