package io.rpg.model.data;

import io.rpg.model.location.LocationModel;

/**
 * Event representing location model state change.
 */
public record LocationModelStateChange(
    LocationModel source,
    Object payload // TODO: Same considerations as for GameObjectStateChange
) {
  /**
   * Interface for {@link LocationModelStateChange} observer.
   */
  public interface Observer {
    void onLocationModelStateChange(LocationModelStateChange event);
  }

  /**
   * Interface for {@link LocationModelStateChange} event emitter.
   */
  public interface Emitter {
    void addOnLocationModelStateChangeObserver(Observer observer);

    void removeOnLocationModelStateChangeObserver(Observer observer);

    void emitLocationModelStateChange(LocationModelStateChange event);
  }
}
