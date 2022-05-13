package io.rpg.model.location;

import io.rpg.model.data.LocationModelStateChange;
import io.rpg.model.data.Position;
import io.rpg.model.object.Player;
import io.rpg.model.object.GameObject;
import io.rpg.util.Result;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.*;

/**
 * Represents single location in our game.
 */
public class LocationModel implements LocationModelStateChange.Emitter {
  private String tag;
  private List<GameObject> gameObjects;
  private HashMap<GameObject, ChangeListener<Point2D>> boundsCorrectors;
  private Player player;
  public final Point2D bounds;

  private final Set<LocationModelStateChange.Observer> locationModelStateChangeObservers;

  public LocationModel(@NotNull String tag, @NotNull List<GameObject> gameObjects) {
    this();
    this.tag = tag;
    this.gameObjects = gameObjects;

  }

  private LocationModel() {
    this.locationModelStateChangeObservers = new LinkedHashSet<>();
    this.boundsCorrectors = new HashMap<>();
    bounds = new Point2D(9.5, 9.5); // TODO: 09.05.2022 Implement loading from config
  }

  public void setPlayer(@NotNull Player player) {
    this.player = player;
  }

  public String getTag() {
    return tag;
  }

  public Player getPlayer() {
    return player;
  }

  public GameObject getObject(int row, int column) {
    GameObject object = gameObjects.stream().filter(gameObject -> gameObject.getPosition()
            .equals(new Position(row, column)))
        .findFirst().orElse(null);
    if (object == null) {
      throw new NullPointerException("No object found on (" + row + ", " + column + ")");
    }
    return object;
  }


//  @UnmodifiableView
//  public List<GameObject> getGameObjects() {
//    return Collections.unmodifiableList(gameObjects);
//  }

  /**
   * Private setter for Builder usage only. Notice that ownership of {@link GameObject}s is not
   * transferred to LocationModel.
   * TODO: Transfer ownership of objects to LoactionModel.
   *
   * @param gameObjects game object for location
   */
  private void setGameObjects(List<GameObject> gameObjects) {
    this.gameObjects = gameObjects;
    gameObjects.forEach(this::registerGameObject);
    gameObjects.forEach(g -> checkAndCorrectGameObjectPosition(g, g.getExactPosition(), g.getExactPosition()));
  }

  public void registerGameObject(GameObject gameObject) {
    ChangeListener<Point2D> boundCorrector =
        (observable, oldValue, newValue) -> checkAndCorrectGameObjectPosition(gameObject, oldValue, newValue);
    gameObject.getExactPositionProperty().addListener(boundCorrector);
    boundsCorrectors.put(gameObject, boundCorrector);
  }

  public void unRegisterGameObject(GameObject gameObject) {
    ChangeListener<Point2D> boundCorrector = boundsCorrectors.remove(gameObject);
    gameObject.getExactPositionProperty().removeListener(boundCorrector);
  }

  private void checkAndCorrectGameObjectPosition(GameObject gameObject, Point2D oldPosition, Point2D newPosition) {
    Point2D boundPosition = getBoundPosition(newPosition);
    if (boundPosition.equals(newPosition)) {
      return;
    }

    gameObject.setExactPosition(boundPosition);
    Point2D boundsCrossedDirection = newPosition.subtract(boundPosition).normalize();

    emitBoundCrossedEvent(boundsCrossedDirection);
  }

  private void emitBoundCrossedEvent(Point2D boundsCrossedDirection) {
    // TODO: 10.05.2022 Bound crossed action
    System.out.println(boundsCrossedDirection);
  }

  private Point2D getBoundPosition(Point2D pos) {
    double x = Math.max(0, Math.min(bounds.getX(), pos.getX()));
    double y = Math.max(0, Math.min(bounds.getY(), pos.getY()));
    return new Point2D(x, y);
  }

  @Override
  public void addOnLocationModelStateChangeObserver(LocationModelStateChange.Observer observer) {
    locationModelStateChangeObservers.add(observer);
  }

  @Override
  public void removeOnLocationModelStateChangeObserver(LocationModelStateChange.Observer observer) {
    locationModelStateChangeObservers.remove(observer);
  }

  @Override
  public void emitLocationModelStateChange(LocationModelStateChange event) {
    locationModelStateChangeObservers.forEach(observer -> {
      observer.onLocationModelStateChange(event);
    });
  }

  public Result<Void, Void> validate() {
    if (tag == null || gameObjects == null) {
      return Result.error(null);
    }
    return Result.ok(null);
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

    public Builder setTag(@NotNull String tag) {
      locationModel.tag = tag;
      return this;
    }

    public LocationModel build() {
      Result<Void, Void> result = locationModel.validate();

      // TODO(@kkafar): Handle this in better way. Consider returning the result.
      if (result.isError()) {
        logger.error("Error occurred while building LocationModel.");
      }

      return locationModel;
    }
  }

  public void update(float elapsed) {
    if (player != null) {
      player.update(elapsed);
    }
  }

}
