package io.rpg.controller;

import io.rpg.model.object.Player;
import io.rpg.view.GameObjectView;

public class PlayerController {
  private final Player player;
  private final GameObjectView playerView;

  public PlayerController(Player player, GameObjectView playerView) {
    this.player = player;
    this.playerView = playerView;

    player.addGameObjectStateChangeObserver(playerView);
    player.setGameObjectView(playerView);
  }


}
