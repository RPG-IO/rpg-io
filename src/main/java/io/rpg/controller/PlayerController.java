package io.rpg.controller;

import io.rpg.model.data.KeyboardEvent;
import io.rpg.model.data.Position;
import io.rpg.model.location.LocationModel;
import io.rpg.model.object.Player;
import io.rpg.view.GameObjectView;
import io.rpg.view.LocationView;
import javafx.scene.input.KeyEvent;

public class PlayerController implements KeyboardEvent.Observer {
  private final Player player;
  private final GameObjectView playerView;
  private Runnable onChangeLocation;

  public PlayerController(Player player, GameObjectView playerView) {
    this.player = player;
    this.playerView = playerView;
    this.onChangeLocation = () -> {};

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

  /**
   * Changes player location. Removes listener from old location.
   *
   * @param model new location model.
   * @param view new location view.
   * @param playerPosition new player position.
   */
  public void teleportTo(LocationModel model, LocationView view, Position playerPosition) {
    onChangeLocation.run();
    updateOnChangeLocation(model, view);

    player.setPosition(playerPosition);
    model.setPlayer(player);
    view.addChild(playerView);
    view.addKeyboardEventObserver(this);
  }

  private void updateOnChangeLocation(LocationModel model, LocationView view) {
    this.onChangeLocation = () -> {
      view.removeKeyboardEventObserver(this);
      view.removeChild(this.playerView);
    };
  }

  public void addPoints(int pointsCount) {
    // TODO
  }
}
