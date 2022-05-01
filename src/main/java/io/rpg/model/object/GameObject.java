package io.rpg.model.object;

import io.rpg.model.actions.Action;
import io.rpg.model.actions.BaseActionEmitter;
import io.rpg.model.actions.DialogueAction;
import io.rpg.model.actions.QuizAction;
import io.rpg.model.data.GameObjectStateChange;
import io.rpg.model.data.Position;
import java.lang.reflect.Field;
import io.rpg.util.DataObjectDescriptionProvider;

import java.util.LinkedHashSet;
import java.util.Set;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Class representing common state properties for all
 * objects appearing in the game.
 */
public class GameObject extends BaseActionEmitter implements GameObjectStateChange.Emitter {

  /**
   * Position of game object in model's representation of location.
   */
  private final SimpleObjectProperty<Point2D> exactPositionProperty;
  private Action onRightClickAction;
  private Action onLeftClickAction;

  /**
   * Unique identifier of this game object.
   * This value is set in location
   */
  @NotNull
  private final String tag;

  @NotNull
  private final Set<GameObjectStateChange.Observer> stateChangeObservers;

  /**
   * Unique identifier of this game object.
   */
  @NotNull
  public String getTag() {
    return tag;
  }


  public GameObject(@NotNull String tag, @NotNull Position position) {
    this.tag = tag;
    this.stateChangeObservers = new LinkedHashSet<>();
    this.exactPositionProperty = new SimpleObjectProperty<>(new Point2D(position.col, position.row));
    this.onLeftClickAction = new QuizAction(new Question("How many bits are there in one byte?", new String[]{"1/8", "1024", "8", "256"}, 'C'));
    this.onRightClickAction = Action.VOID;
  }

  /**
   * Position of game object in model's representation of location.
   *
   * @return initial position on object in the model (on the grid)
   */
  @Nullable
  public Position getPosition() {
    Point2D exactPosition = getExactPosition();
    return new Position(exactPosition);
  }

  public void setExactPosition(Point2D position) {
    exactPositionProperty.setValue(position);
  }

  public Point2D getExactPosition() {
    return exactPositionProperty.getValue();
  }

  public ObservableValue<Point2D> getExactPositionProperty() {
    return exactPositionProperty;
  }

  @Override
  public void emitGameObjectStateChange(GameObjectStateChange event) {
    // TODO
    // noop for now
  }

  @Override
  public void addGameObjectStateChangeObserver(GameObjectStateChange.Observer observer) {
    this.stateChangeObservers.add(observer);
  }

  @Override
  public void removeGameObjectStateChangeObserver(GameObjectStateChange.Observer observer) {
    this.stateChangeObservers.remove(observer);
  }

  @Override
  public String toString() {
    return DataObjectDescriptionProvider.combineDescriptions(
        DataObjectDescriptionProvider.getFieldDescription(this, GameObject.class)
    );
  }

  public void setPosition(Position playerPosition) {
    setExactPosition(new Point2D(playerPosition.col, playerPosition.row));
  }

  public void setOnRightClickAction(Action onRightClickAction) {
    this.onRightClickAction = onRightClickAction;
  }

  public void setOnLeftClickAction(Action onLeftClickAction) {
    this.onLeftClickAction = onLeftClickAction;
  }

  public void onRightClick() {
    emitAction(onRightClickAction);
  }

  public void onLeftClick() {
    emitAction(onLeftClickAction);
  }

  public enum Type {
    NAVIGABLE("navigable"),
    DIALOG("dialog"),
    PLAYER("player"),
    QUIZ("quiz"),
    COLLECTIBLE("collectible");

    private final String asString;

    Type(String asString) {
      this.asString = asString;
    }
  }
}
