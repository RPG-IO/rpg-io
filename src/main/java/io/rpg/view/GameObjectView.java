package io.rpg.view;

import io.rpg.model.data.GameObjectStateChange;
import io.rpg.model.data.MouseClickedEvent;
import io.rpg.model.data.Position;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class GameObjectView extends ImageView
    implements MouseClickedEvent.Emitter, GameObjectStateChange.Observer {
  private Path path;
  private final Set<MouseClickedEvent.Observer> onClickedObservers;

  public GameObjectView(@NotNull Path assetPath, @NotNull Position position) {
    this.path = assetPath;
//    String xdpath =
    this.setImage(new Image(resolvePathToJFXFormat(path.toString())));
    // todo: better position class
    this.setX(position.col);
    this.setY(position.row);
    this.onClickedObservers = new HashSet<>();
    this.setOnMouseClicked(event -> emitOnMouseClickedEvent(new MouseClickedEvent(this, event)));
  }

  public static String resolvePathToJFXFormat(String path) {
    return "file:" + path;
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

  @Override
  public void onGameObjectStateChange(GameObjectStateChange event) {
    // TODO: implement update logic here or create view model class but it
    // is even more boilerplate
  }
}
