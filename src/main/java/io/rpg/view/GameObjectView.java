package io.rpg.view;

import io.rpg.model.data.Position;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class GameObjectView extends ImageView implements MouseClickedEvent.Emitter {
  private Path path;
  private final Set<MouseClickedEvent.Observer> onClickedObservers;

  public GameObjectView(@NotNull Path assetPath, @NotNull Position position) {
    path = assetPath;
    this.setImage(new Image(path.toString()));
    // todo: better position class
    this.setX(position.col);
    this.setY(position.row);
    this.onClickedObservers = new HashSet<>();
    this.setOnMouseClicked(event -> emitOnMouseClickedEvent(new MouseClickedEvent(this, event)));
  }

  @Override
  public void emitOnMouseClickedEvent(MouseClickedEvent event) {
    onClickedObservers.forEach(listener -> listener.onMouseClickedEvent(event));
  }

  @Override
  public void addOnClickedObserver(MouseClickedEvent.Observer observer) {
    onClickedObservers.add(observer);
  }

  @Override
  public void removeOnClickedObserver(MouseClickedEvent.Observer observer) {
    onClickedObservers.remove(observer);
  }
}
