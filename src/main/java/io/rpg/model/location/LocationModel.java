package io.rpg.model.location;

import com.kkafara.rt.Result;
import io.rpg.model.actions.ActionConsumer;
import io.rpg.model.actions.BaseActionEmitter;
import io.rpg.model.actions.LocationChangeAction;
import io.rpg.model.data.MapDirection;
import io.rpg.model.data.Position;
import io.rpg.model.object.GameObject;

import java.util.*;

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
  public Point2D bounds;


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
    gameObjects.forEach(this::registerGameObject);
    gameObjects.forEach(g -> checkAndCorrectBoundsCrossing(g, g.getExactPosition(), false));
    gameObjects.forEach(g -> positionGameObjectMap.put(g.getPosition(), g));
  }

  public void addGameObject(GameObject gameObject) {
    gameObjects.add(gameObject);
    correctGameObjectPosition(gameObject);
    positionGameObjectMap.put(gameObject.getPosition(), gameObject);
    registerGameObject(gameObject);

  }

  private void correctGameObjectPosition(GameObject gameObject) {
    Point2D exactBoundPosition = getBoundPosition(gameObject.getExactPosition());
    Position boundPosition = new Position(exactBoundPosition);
    if (!positionGameObjectMap.containsKey(boundPosition)) {
      gameObject.setExactPosition(exactBoundPosition);
      return;
    }

    Position freePosition = findNearestFreePosition(boundPosition);
    gameObject.setPosition(freePosition);
  }

  private Position findNearestFreePosition(Position position) {
    if (!positionGameObjectMap.containsKey(position)) {
      return position;
    }

    var it = position.getNeighborhoodIter(new Position(bounds));
    while (it.hasNext()) {
      Position pos = it.next();
      if (!positionGameObjectMap.containsKey(pos)) {
        return pos;
      }
    }

    // Last resort
    for (int i = 0; i < bounds.getX() * bounds.getY(); i++) {
      int row =  i / ((int) bounds.getX());
      int col = i % ((int) bounds.getX());

      Position pos = new Position(row, col);
      if (!positionGameObjectMap.containsKey(pos)) {
        return pos;
      }
    }

    throw new IllegalStateException("No free field for new GameObject");
  }

  private void registerGameObject(GameObject gameObject) {
    ChangeListener<Point2D> positionListener =
        (observable, oldValue, newValue) -> onGameObjectPositionChange(gameObject, oldValue, newValue);
    gameObject.getExactPositionProperty()
              .addListener(positionListener);
    positionListeners.put(gameObject, positionListener);
  }

  public void removeGameObject(GameObject gameObject) {
    gameObjects.remove(gameObject);
    positionGameObjectMap.remove(gameObject.getPosition());
    unRegisterGameObject(gameObject);
  }

  private void unRegisterGameObject(GameObject gameObject) {
    ChangeListener<Point2D> positionListener = positionListeners.remove(gameObject);
    gameObject.getExactPositionProperty()
              .removeListener(positionListener);
  }

  private void onGameObjectPositionChange(GameObject gameObject, Point2D oldPosition, Point2D newPosition) {


    boolean changeOccurred = checkAndCorrectBoundsCrossing(gameObject, newPosition, true);

    if (changeOccurred) {
      return;
    }

    Position newPos = new Position(newPosition);
    Position oldPos = new Position(oldPosition);

    positionGameObjectMap.values().remove(gameObject);

    // Collision check
    if (positionGameObjectMap.containsKey(newPos)) {
      if (!positionGameObjectMap.containsKey(oldPos)) {
        gameObject.setExactPosition(oldPosition);
      } else {
        gameObject.setPosition(findNearestFreePosition(oldPos));
      }
      return;
    }

//    if (gameObject.equals(positionGameObjectMap.get(oldPos))) {
//      changeField(gameObject, oldPos, newPos);
//    }

    positionGameObjectMap.put(newPos, gameObject);
    notifyApproachOf(gameObject);
  }

  private void notifyApproachOf(GameObject gameObject) {
    Position position = gameObject.getPosition();
    List<GameObject> neighbors = new ArrayList<>(8);

    for (Iterator<Position> it = position.getNeighborhoodIter(new Position(bounds)); it.hasNext(); ) {
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


  private boolean checkAndCorrectBoundsCrossing(GameObject gameObject, Point2D newPosition, boolean emitAction) {
    Point2D boundPosition = getBoundPosition(newPosition);
    if (boundPosition.equals(newPosition)) {
      return false;
    }

    gameObject.setExactPosition(boundPosition);
    Point2D boundsCrossedDirection = newPosition.subtract(boundPosition)
                                                .normalize();

    if (emitAction) {
      emitBoundCrossedAction(gameObject, boundsCrossedDirection);
    }

    return true;
  }

  private void emitBoundCrossedAction(GameObject gameObject, Point2D boundsCrossedDirection) {
    // guard against non cardinal directions vector
    if ((boundsCrossedDirection.angle(1, 0) % 90) != 0) {
      return;
    }
    MapDirection direction = MapDirection.fromDirectionVector(boundsCrossedDirection);
    Point2D position = gameObject.getExactPosition();
    Point2D nextPosition = position.subtract(boundsCrossedDirection.multiply(20));
    if (!directionToLocationMap.containsKey(direction)) {
      return;
    }

    String location = directionToLocationMap.get(direction);
    LocationChangeAction action = new LocationChangeAction(location, nextPosition, null);
    emitAction(action);
  }


  private Point2D getBoundPosition(Point2D pos) {
    double offset = 0.3; // it should be less than 0.5
    double x = Math.max(-offset, Math.min(bounds.getX() - 1 + offset, pos.getX()));
    double y = Math.max(-offset, Math.min(bounds.getY() - 1 + offset, pos.getY()));
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
      locationModel.bounds = bounds;
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
