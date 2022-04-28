package io.rpg.model.location;

import io.rpg.model.data.LocationModelStateChange;
import io.rpg.model.data.Position;
import io.rpg.model.object.Player;
import io.rpg.model.object.GameObject;
import io.rpg.util.Result;
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
  private Player player;

  private final Set<LocationModelStateChange.Observer> locationModelStateChangeObservers;

  public LocationModel(@NotNull String tag, @NotNull List<GameObject> gameObjects) {
    this();
    this.tag = tag;
    this.gameObjects = gameObjects;
  }

  private LocationModel() {
    this.locationModelStateChangeObservers = new LinkedHashSet<>();
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
    GameObject object =  gameObjects.stream().filter(gameObject -> gameObject.getPosition()
                    .equals(new Position(row, column)))
                    .findFirst().orElse(null);
    if (object == null) {
      throw new NullPointerException("No object found on (" + row + ", " + column + ")");
    }
    return object;
  }
  
  /**
   * Private setter for Builder usage only.
   *
   * @param tag tag for the location
   */
  private void setTag(String tag) {
    this.tag = tag;
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
      assert tag != null : "Location tag must not be null!";
      locationModel.setTag(tag);
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
  public List<GameObject> getGameObjects() {
    return gameObjects;
  }

  public void update(float elapsed){
    if(player!=null){
      player.update(elapsed);
    }
  }

}
