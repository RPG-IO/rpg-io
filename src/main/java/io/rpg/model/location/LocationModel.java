package io.rpg.model.location;

import io.rpg.model.object.Player;
import io.rpg.model.object.GameObject;
import io.rpg.view.ILocationModelStateChangeObserver;
import io.rpg.view.IObservable;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents single location in our game
 */
public class LocationModel implements IObservable<ILocationModelStateChangeObserver> {
  private String tag;
  private List<GameObject> gameObjects;
  private Player player;

  private final Set<ILocationModelStateChangeObserver> locationModelStateChangeListeners;

  public LocationModel(@NotNull String tag, @NotNull List<GameObject> gameObjects) {
    this.tag = tag;
    this.gameObjects = gameObjects;
    this.player = null;
    this.locationModelStateChangeListeners = new LinkedHashSet<>();
  }

  public void setPlayer(@NotNull Player player) {
    this.player = player;
  }

  public String getTag() {
    return tag;
  }

  @Override
  public void addListener(ILocationModelStateChangeObserver listener) {
    locationModelStateChangeListeners.add(listener);
  }

  @Override
  public void removeListener(ILocationModelStateChangeObserver listener) {
    locationModelStateChangeListeners.remove(listener);
  }
}
