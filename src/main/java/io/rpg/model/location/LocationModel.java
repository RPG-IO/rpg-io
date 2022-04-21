package io.rpg.model.location;

import io.rpg.model.data.LocationModelStateChange;
import io.rpg.model.data.Position;
import io.rpg.model.object.Player;
import io.rpg.model.object.GameObject;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents single location in our game
 */
public class LocationModel implements LocationModelStateChange.Emitter {
  private String tag;
  private List<GameObject> gameObjects;
  private Player player;

  private final Set<LocationModelStateChange.Observer> locationModelStateChangeObservers;

  public LocationModel(@NotNull String tag, @NotNull List<GameObject> gameObjects) {
    this.tag = tag;
    this.gameObjects = gameObjects;
    this.player = null;
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
}
