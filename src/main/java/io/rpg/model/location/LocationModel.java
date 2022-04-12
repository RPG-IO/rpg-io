package io.rpg.model.location;

import io.rpg.model.object.Player;
import io.rpg.model.object.GameObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents single location in our game
 */
public class LocationModel {
  private String tag;
  private List<GameObject> gameObjects;
  private Player player;

  public LocationModel(@NotNull String tag, @NotNull List<GameObject> gameObjects) {
    this.tag = tag;
    this.gameObjects = gameObjects;
    player = null;
  }

  public void setPlayer(@NotNull Player player) {
    this.player = player;
  }

  public String getTag() {
    return tag;
  }
}
