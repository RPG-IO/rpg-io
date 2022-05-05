package io.rpg.model.actions;

import io.rpg.model.data.Position;

/**
 * Class for storing local data needed to preform a location change action.
 */

public class LocationChangeAction implements Action {
  public final String destinationLocationTag;
  public final Position playerPosition;

  public LocationChangeAction(String destinationLocationTag, Position playerPosition) {
    this.destinationLocationTag = destinationLocationTag;
    this.playerPosition = playerPosition;
  }
}