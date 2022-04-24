package io.rpg.model.data;

import io.rpg.view.GameObjectView;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Represents mouse click event.
 */
public record MouseClickedEvent(
    @NotNull GameObjectView source,
    @NotNull MouseEvent payload
) {
  /**
   * Interface for {@link MouseClickedEvent} observer.
   */
  public interface Observer {
    void onMouseClickedEvent(MouseClickedEvent event);
  }

  /**
   * Interface for {@link MouseClickedEvent} emitter.
   */
  public interface Emitter {
    void emitOnMouseClickedEvent(MouseClickedEvent event);

    void addOnClickedObserver(MouseClickedEvent.Observer observer);

    void removeOnClickedObserver(MouseClickedEvent.Observer observer);
  }
}


