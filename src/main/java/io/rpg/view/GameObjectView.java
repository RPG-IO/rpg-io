package io.rpg.view;

import com.sun.javafx.scene.ImageViewHelper;
import io.rpg.model.data.GameObjectStateChange;
import io.rpg.model.data.MouseClickedEvent;
import io.rpg.model.data.Position;
import io.rpg.model.object.GameObject;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

import static io.rpg.util.PathUtils.resolvePathToJFXFormat;

public class GameObjectView extends ImageView
    implements MouseClickedEvent.Emitter, GameObjectStateChange.Observer {
  private Path path;
  private final Set<MouseClickedEvent.Observer> onClickedObservers;
  private final SimpleObjectProperty<Point2D> position;
  GameObject boundObject;


  public GameObjectView(@NotNull Path assetPath, @NotNull Position position) {
    this.path = assetPath;
    this.setImage(new Image(resolvePathToJFXFormat(path.toString())));
    this.position = new SimpleObjectProperty<>(new Point2D(position.col, position.row));
    setLayoutUpdateOnPositionChange();
    this.onClickedObservers = new HashSet<>();
    this.setOnMouseClicked(event -> emitOnMouseClickedEvent(new MouseClickedEvent(this, event)));
  }

  private void setLayoutUpdateOnPositionChange() {
    this.position.addListener((observable, oldValue, newValue) -> {
      if (Objects.equals(oldValue, newValue)) {
        return;
      }
      ImageViewHelper.geomChanged(this);
    });
  }


  @Override
  public void emitOnMouseClickedEvent(MouseClickedEvent event) {
    onClickedObservers.forEach(listener -> listener.onMouseClickedEvent(event));
  }

  public void bindToGameObject(GameObject gameObject) {
      this.position.bind(gameObject.getExactPositionProperty());
      this.boundObject = gameObject;
  }

  public Point2D getPosition() {
    return position.get();
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

  public GameObject getBoundObject() {
    return boundObject;
  }
}
