package io.rpg.model.actions;

import io.rpg.model.actions.condition.Condition;
import io.rpg.model.data.Position;
import javafx.geometry.Point2D;

/**
 * Class for storing local data needed to preform a location change action.
 */

public class LocationChangeAction extends ConditionalAction {
  public final String destinationLocationTag;
  public final Point2D playerPosition;

  public LocationChangeAction(String destinationLocationTag, Position playerPosition, Condition condition) {
    super(condition);
    this.destinationLocationTag = destinationLocationTag;
    this.playerPosition = new Point2D(playerPosition.col, playerPosition.row);
  }

  public LocationChangeAction(String destinationLocationTag, Point2D playerPosition, Condition condition) {
    super(condition);
    this.destinationLocationTag = destinationLocationTag;
    this.playerPosition = playerPosition;
  }

  @Override
  public void acceptActionEngine(ActionEngine engine) {
    engine.onAction(this);
  }
}
