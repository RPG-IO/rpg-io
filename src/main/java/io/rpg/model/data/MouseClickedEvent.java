package io.rpg.model.data;

import io.rpg.view.GameObjectView;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
final public record MouseClickedEvent(
    @NotNull GameObjectView source,
    @NotNull MouseEvent payload
) {
  public interface Observer {
    void onMouseClickedEvent(MouseClickedEvent event);
  }

  public interface Emitter {
    void emitOnMouseClickedEvent(MouseClickedEvent event);

    void addOnClickedObserver(MouseClickedEvent.Observer observer);

    void removeOnClickedObserver(MouseClickedEvent.Observer observer);
  }
}


