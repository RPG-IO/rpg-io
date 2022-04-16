package io.rpg.model.data;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

final public record KeyboardEvent(Scene source, KeyEvent payload) {
  public interface Observer {
    void onKeyboardEvent(KeyboardEvent event);
  }

  public interface Emitter {
    void addKeyboardEventObserver(KeyboardEvent.Observer observer);

    void removeKeyboardEventObserver(KeyboardEvent.Observer observer);

    void emitKeyboardEvent(KeyboardEvent event);
  }
}
