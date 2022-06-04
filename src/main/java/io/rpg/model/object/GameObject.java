package io.rpg.model.object;

import io.rpg.model.actions.Action;
import io.rpg.model.actions.BaseActionEmitter;
import io.rpg.model.actions.QuizAction;
import io.rpg.model.data.GameObjectStateChange;
import io.rpg.model.data.Position;
import io.rpg.util.DataObjectDescriptionProvider;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashSet;
import java.util.Set;

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
  private Action onApproach;
  private boolean wasOnApproachFired;
  private int strength;

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
    this.onLeftClickAction = Action.VOID;
    this.onRightClickAction = Action.VOID;
    this.onApproach = Action.VOID;
    this.wasOnApproachFired = false;
  }

  public GameObject(@NotNull String tag, @NotNull Position position, int strength) {
    this(tag, position);
    this.strength = strength;
  }

  /**
   * Position of game object in model's representation of location.
   *
   * @return initial position on object in the model (on the grid)
   */
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

  public void setOnRightClickAction(@NotNull Action onRightClickAction) {
    this.onRightClickAction = onRightClickAction;
    setThisAsEmitter(this.onRightClickAction);
  }

  public void setOnLeftClickAction(@NotNull Action onLeftClickAction) {
    this.onLeftClickAction = onLeftClickAction;
    setThisAsEmitter(this.onLeftClickAction);
  }

  public void onRightClick() {
    emitAction(onRightClickAction);
  }

  public void onLeftClick() {
    emitAction(onLeftClickAction);
  }

  public int getStrength() {
    return strength;
  }

  public void onApproach() {
    if (wasOnApproachFired) {
      return;
    }
    wasOnApproachFired = true;
    Platform.runLater(() -> emitAction(onApproach));
  }

  public void setOnApproach(Action onApproach) {
    this.onApproach = onApproach;
    setThisAsEmitter(this.onApproach);
  }

  private void setThisAsEmitter(Action action) {
    if (action != null) {
      action.setEmitter(this);
      action.getAfterAction().ifPresent(a -> a.setEmitter(this));
      action.getBeforeAction().ifPresent(a -> a.setEmitter(this));
    }
  }
}
