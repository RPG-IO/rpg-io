package io.rpg.model.data;

import io.rpg.model.object.GameObject;

public record GameObjectStateChange (
    GameObject source,
    Object payload // TODO: what kind of data do we want? What changed? What can change?
    // or maybe implement separate methods for observer & emitter for different kinds
    // of event
) {
  public interface Observer {
    void onGameObjectStateChange(GameObjectStateChange event);
  }

  public interface Emitter {
    void emitGameObjectStateChange(GameObjectStateChange event);

    void addGameObjectStateChangeObserver(Observer observer);

    void removeGameObjectStateChangeObserver(Observer observer);
  }
}
