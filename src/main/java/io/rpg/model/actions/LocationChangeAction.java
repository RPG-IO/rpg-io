package io.rpg.model.actions;

/**
 * Class for storing local data needed to preform a location change action
 */

public class LocationChangeAction {
  public final String destinationLocationTag;

  public LocationChangeAction(String destinationLocationTag) {
    this.destinationLocationTag = destinationLocationTag;
  }
}
