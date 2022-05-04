package io.rpg.controller;

import io.rpg.model.actions.Action;
import io.rpg.model.actions.LocationChangeAction;
import io.rpg.model.data.KeyboardEvent;
import io.rpg.model.data.Position;
import io.rpg.model.object.Player;
import io.rpg.view.GameObjectView;
import javafx.scene.input.KeyEvent;

public class PlayerController implements KeyboardEvent.Observer {
  private final Player player;
  private final GameObjectView playerView;

  public PlayerController(Player player, GameObjectView playerView) {
    this.player = player;
    this.playerView = playerView;

    player.addGameObjectStateChangeObserver(playerView);
    player.setGameObjectView(playerView);
  }

  @Override
  public void onKeyboardEvent(KeyboardEvent event) {
    KeyEvent payload = event.payload();
    if (payload.getEventType() == KeyEvent.KEY_PRESSED) {
      switch (payload.getCode()) {
        case A -> player.setLeftPressed(true);
        case D -> player.setRightPressed(true);
        case S -> player.setDownPressed(true);
        case W -> player.setUpPressed(true);
      }
    } else if (payload.getEventType() == KeyEvent.KEY_RELEASED) {
      switch (payload.getCode()) {
        case A -> player.setLeftPressed(false);
        case D -> player.setRightPressed(false);
        case S -> player.setDownPressed(false);
        case W -> player.setUpPressed(false);
      }
    }
  }

}
