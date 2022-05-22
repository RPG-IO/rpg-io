package io.rpg.model.actions;

import io.rpg.model.data.Position;
import javafx.geometry.Point2D;

/**
 * Class for storing local data needed to preform a location change action.
 */

public class LocationChangeAction implements Action {
  public final String destinationLocationTag;
  public final Point2D playerPosition;

  public LocationChangeAction(String destinationLocationTag, Position playerPosition) {
    this.destinationLocationTag = destinationLocationTag;
    this.playerPosition = new Point2D(playerPosition.col, playerPosition.row);
  }

  public LocationChangeAction(String destinationLocationTag, Point2D playerPosition) {
    this.destinationLocationTag = destinationLocationTag;
    this.playerPosition = playerPosition;
  }
}
