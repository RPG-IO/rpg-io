package io.rpg.model.object;

import io.rpg.model.data.GameObjectStateChange;
import io.rpg.model.data.Position;
import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Class representing common state properties for all
 * objects appearing in the game.
 */
public class GameObject implements GameObjectStateChange.Emitter {

  /**
   * Position of game object in model's representation of location.
   */
  @Nullable
  protected Point2D exactPosition;

  /**
   * Unique identifier of this game object.
   * This value is set in location
   */
  @NotNull
  private final String tag;

  @NotNull
  private final Set<GameObjectStateChange.Observer> stateChangeObservers;

  /**
   * Unique identifier of this game object.
   */
  @NotNull
  public String getTag() {
    return tag;
  }

  private Point2D positionBounds;

  /**
   * Position of game object in model's representation of location.
   *
   * @return initial position on object in the model (on the grid)
   */
  @Nullable
  public Position getPosition() {
    assert exactPosition != null;
    return new Position((int) Math.round(exactPosition.getY()), (int) Math.round(exactPosition.getX()));
  }

  public void setExactPosition(Point2D position) {
    Point2D boundPosition = accountForBounds(position);
    if (!boundPosition.equals(position)) {
      emitBoundCrossedEvent(position.subtract(boundPosition));
    }

    emitPositionChangeEvent(this.exactPosition, boundPosition);
    this.exactPosition = boundPosition;
  }

  private void emitBoundCrossedEvent(Point2D crossedBy) {
    // TODO: 09.05.2022
  }

  // TODO: 09.05.2022 I need a better name for this method
  private Point2D accountForBounds(Point2D pos) {
    double x = Math.max(0, Math.min(positionBounds. getX(), pos.getX()));
    double y = Math.max(0, Math.min(positionBounds. getY(), pos.getY()));
    return new Point2D(x, y);
  }

  private void emitPositionChangeEvent(Point2D oldPosition, Point2D newPosition) {
    // TODO: 08.05.2022
  }

  public Point2D getExactPosition() {
    return exactPosition;
  }

  public GameObject(@NotNull String tag, @NotNull Position position) {
    this.tag = tag;
    this.exactPosition = new Point2D(position.col, position.row);
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

  public void setPosition(Position playerPosition) {
    setExactPosition(new Point2D(playerPosition.col, playerPosition.row));
  }

  public void setPositionBounds(Point2D bounds) {
    this.positionBounds = bounds;
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
