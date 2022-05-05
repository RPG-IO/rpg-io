package io.rpg.model.object;

import io.rpg.config.model.GameObjectConfig;
import io.rpg.model.data.GameObjectStateChange;
import io.rpg.model.data.Position;
import io.rpg.model.data.Vector;
import io.rpg.view.GameObjectView;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Class representing common state properties for all
 * objects appearing in the game.
 */
public class GameObject implements GameObjectStateChange.Emitter {

//  protected Vector currentPosition;

//  public GameObjectView view;

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

  private int strength;

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

  // TODO: remove this ctor when generic properties are implemented
  public GameObject(@Nullable Position position, @NotNull String tag, @Nullable String assetPath, int strength) {
    this.position = position;
    this.tag = tag;
    this.assetPath = assetPath;
    this.strength = strength;
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

  public String getFieldDescription() {
    StringBuilder builder = new StringBuilder();
    for (Field field : GameObject.class.getDeclaredFields()) {
      try {
        Optional<Object> fieldValue = Optional.ofNullable(field.get(this));
        fieldValue.ifPresent(_fieldValue -> builder.append('\t')
            .append(field.getName())
            .append(": ")
            .append(_fieldValue)
            .append(",\n")
        );
      } catch (IllegalAccessException ignored) { /* noop */ }
    }
    return builder.toString();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("\n{\n");
    builder.append(getFieldDescription());
    return builder.append("}").toString();
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

  // TODO: remove this getter when generic properties are implemented
  public int getStrength() {
    return strength;
  }
}
