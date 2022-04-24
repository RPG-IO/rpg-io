package io.rpg.model.data;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

/**
 * Represents a keyboard clicked event.
 */
public final record KeyboardEvent(Scene source, KeyEvent payload) {
  /**
   * Interface for {@link KeyboardEvent} observer.
   */
  public interface Observer {
    void onKeyboardEvent(KeyboardEvent event);
  }

  /**
   * Interface for {@link KeyboardEvent} emitter.
   */
  public interface Emitter {
    void addKeyboardEventObserver(KeyboardEvent.Observer observer);

    void removeKeyboardEventObserver(KeyboardEvent.Observer observer);

    void emitKeyboardEvent(KeyboardEvent event);
  }
}
