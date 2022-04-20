package io.rpg.model.data;

import io.rpg.model.location.LocationModel;

public record LocationModelStateChange(
    LocationModel source,
    Object payload // TODO: Same considerations as for GameObjectStateChange
) {
  public interface Observer {
    void onLocationModelStateChange(LocationModelStateChange event);
  }

  public interface Emitter {
    void addOnLocationModelStateChangeObserver(Observer observer);

    void removeOnLocationModelStateChangeObserver(Observer observer);

    void emitLocationModelStateChange(LocationModelStateChange event);
  }
}
