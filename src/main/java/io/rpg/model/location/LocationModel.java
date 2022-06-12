package io.rpg.model.location;

import com.kkafara.rt.Result;
import io.rpg.model.actions.ActionConsumer;
import io.rpg.model.actions.BaseActionEmitter;
import io.rpg.model.actions.LocationChangeAction;
import io.rpg.model.data.MapDirection;
import io.rpg.model.data.Position;
import io.rpg.model.object.GameObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Point2D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Represents single location in our game.
 */
public class LocationModel extends BaseActionEmitter {
  private String tag;
  private List<GameObject> gameObjects;
  private final HashMap<GameObject, ChangeListener<Point2D>> positionListeners;
  private final HashMap<Position, GameObject> positionGameObjectMap;
  private HashMap<MapDirection, String> directionToLocationMap;
  public Position bounds;

  @SuppressWarnings("unused")
  public LocationModel(@NotNull String tag, @NotNull List<GameObject> gameObjects) {
    this();
    this.tag = tag;
    this.gameObjects = gameObjects;
  }

  private LocationModel() {
    this.positionListeners = new HashMap<>();
    this.positionGameObjectMap = new HashMap<>();
    this.directionToLocationMap = new HashMap<>();
  }

  public String getTag() {
    return tag;
  }

  public Optional<GameObject> getObject(Position position) {
    return Optional.ofNullable(positionGameObjectMap.get(position));
  }

  /**
   * Private setter for Builder usage only. Notice that ownership of {@link GameObject}s is not
   * transferred to LocationModel.
   * TODO: Transfer ownership of objects to LocationModel.
   *
   * @param gameObjects game object for location
   */
  private void setGameObjects(List<GameObject> gameObjects) {
    this.gameObjects = gameObjects;
    gameObjects.forEach(this::placeGameObject);
    gameObjects.forEach(this::registerGameObject);
    gameObjects.forEach(g -> positionGameObjectMap.put(g.getPosition(), g));
  }

  public void addGameObject(GameObject gameObject) {
    gameObjects.add(gameObject);
    placeGameObject(gameObject);
    registerGameObject(gameObject);

  }

  private void registerGameObject(GameObject gameObject) {
    ChangeListener<Point2D> positionListener =
        (observable, oldValue, newValue) -> onGameObjectPositionChange(gameObject, oldValue, newValue);
    gameObject.getExactPositionProperty()
              .addListener(positionListener);
    positionListeners.put(gameObject, positionListener);
  }

  private void placeGameObject(GameObject gameObject) {
    Point2D boundPos = getBoundPosition(gameObject.getExactPosition());
    Position position = new Position(boundPos);
    if (!positionGameObjectMap.containsKey(position)) {
      gameObject.setExactPosition(boundPos);
      positionGameObjectMap.put(position, gameObject);
      return;
    }

    Iterator<Position> it = position.getBfsIter(bounds);
    while (it.hasNext()) {
      Position p = it.next();
      if (positionGameObjectMap.containsKey(p)) {
        continue;
      }

      gameObject.setPosition(p);
      positionGameObjectMap.put(p, gameObject);
      return;
    }

    throw new RuntimeException("No place to put gameObject"  + gameObject);
  }

  public void removeGameObject(GameObject gameObject) {
    gameObjects.remove(gameObject);
    positionGameObjectMap.values().remove(gameObject);
    unRegisterGameObject(gameObject);
  }

  private void unRegisterGameObject(GameObject gameObject) {
    ChangeListener<Point2D> positionListener = positionListeners.remove(gameObject);
    gameObject.getExactPositionProperty()
              .removeListener(positionListener);
  }

  private void onGameObjectPositionChange(GameObject gameObject, Point2D oldPosition, Point2D newPosition) {
    Position newPos = new Position(newPosition);
    Position oldPos = new Position(oldPosition);

    if (oldPos.equals(newPos)) {
      return;
    }

    // When previous position was not accepted
    if (gameObject.equals(positionGameObjectMap.get(newPos))) {
      return;
    }

    // Collision check
    if (positionGameObjectMap.containsKey(newPos)) {
      gameObject.setExactPosition(oldPosition);
      return;
    }

    if (!newPos.isInside(bounds)) {
      Position delta = newPos.subtract(oldPos);
      // Don't try to teleport diagonally
      if (delta.row * delta.col == 0) {
        boolean hasBeenTeleported = tryToTeleport(gameObject, delta);
        if (hasBeenTeleported) {
          return;
        }
      }

      gameObject.setExactPosition(oldPosition);
      return;
    }

    changeField(gameObject, oldPos, newPos);
    notifyApproachOf(gameObject);
  }

  private boolean tryToTeleport(GameObject gameObject, Position crossingDirection) {
    MapDirection teleportDirection = crossingDirection.getDirection();
    String nextLocation  = directionToLocationMap.get(teleportDirection);
    if (nextLocation == null) {
      return false;
    }

    Point2D currentPosition = gameObject.getExactPosition();
    Point2D nextPosition = currentPosition.subtract(teleportDirection.toVector().multiply(20));
    LocationChangeAction action = new LocationChangeAction(nextLocation, nextPosition, null);
    emitAction(action);
    return true;
  }

  private void notifyApproachOf(GameObject gameObject) {
    Position position = gameObject.getPosition();
    List<GameObject> neighbors = new ArrayList<>(8);

    for (Iterator<Position> it = position.getNeighborhoodIter(bounds); it.hasNext(); ) {
      Position p = it.next();
      GameObject neighbor = positionGameObjectMap.get(p);
      if (neighbor == null) {
        continue;
      }

      neighbors.add(neighbor);
    }

    neighbors.forEach(GameObject::onApproach);
  }

  private void changeField(GameObject gameObject, Position oldPos, Position newPos) {
    positionGameObjectMap.remove(oldPos);
    positionGameObjectMap.put(newPos, gameObject);
  }

  private Point2D getBoundPosition(Point2D pos) {
    double offset = 0.3; // it should be less than 0.5
    double x = Math.max(-offset, Math.min(bounds.col - 1 + offset, pos.getX()));
    double y = Math.max(-offset, Math.min(bounds.row - 1 + offset, pos.getY()));
    return new Point2D(x, y);
  }

  @Override
  public void setActionConsumer(ActionConsumer actionConsumer) {
    super.setActionConsumer(actionConsumer);
    gameObjects.forEach((g) -> g.setActionConsumer(actionConsumer));
  }

  public Result<Void, Void> validate() {
    if (tag == null || gameObjects == null) {
      return Result.err();
    }
    return Result.ok();
  }

  public static class Builder {
    private final LocationModel locationModel;

    private final Logger logger;

    public Builder() {
      logger = LogManager.getLogger(Builder.class);
      locationModel = new LocationModel();
    }

    public Builder setGameObjects(@NotNull List<GameObject> gameObjects) {
      locationModel.setGameObjects(gameObjects);
      return this;
    }

    public Builder setBounds(Point2D bounds) {
      locationModel.bounds = new Position(bounds);
      return this;
    }

    public Builder setTag(@NotNull String tag) {
      locationModel.tag = tag;
      return this;
    }

    public Builder setDirectionToLocationMap(@NotNull HashMap<MapDirection, String> directionToLocationMap) {
      locationModel.directionToLocationMap = directionToLocationMap;
      return this;
    }

    public LocationModel build() {
      Result<Void, Void> result = locationModel.validate();

      // TODO(@kkafar): Handle this in better way. Consider returning the result.
      if (result.isErr()) {
        logger.error("Error occurred while building LocationModel.");
      }

      return locationModel;
    }
  }

}
