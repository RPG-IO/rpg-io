package io.rpg.view;

import io.rpg.model.data.Position;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class GameObjectView extends ImageView implements IObservable<IOnClickedObserver> {
  private Path path;
  private final Set<IOnClickedObserver> onClickedListeners;

  public GameObjectView(@NotNull Path assetPath, @NotNull Position position) {
    path = assetPath;
    this.setImage(new Image(path.toString()));
    // todo: better position class
    this.setX(position.col);
    this.setY(position.row);
    this.onClickedListeners = new HashSet<>();
    this.setOnMouseClicked(this::notifyOnClickListeners);
  }

  private void notifyOnClickListeners(MouseEvent e) {
    onClickedListeners.forEach(listener -> listener.onClick(this));
  }

  @Override
  public void addListener(IOnClickedObserver listener) {
    onClickedListeners.add(listener);
  }

  @Override
  public void removeListener(IOnClickedObserver listener) {
    onClickedListeners.remove(listener);
  }
}
