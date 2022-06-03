package io.rpg;

import io.rpg.controller.Controller;
import io.rpg.model.actions.Action;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

public class Game {
  private Controller controller;
  private Action onStart;
  private AnimationTimer timer;

  private Game() {

  }

  public void setController(Controller controller) {
    this.controller = controller;
  }

  public void start(Stage stage) {
    controller.setMainStage(stage);
    controller.consumeAction(onStart);
    startAnimationTimer();
    stage.show();
  }

  private void startAnimationTimer() {
    timer = new AnimationTimer() {
      long lastUpdate = -1;
      @Override
      public void handle(long now) {
        if (lastUpdate != -1) {
          float difference = (now - lastUpdate) / 1e6f;

          getController().getPlayerController()
                         .getPlayer()
                         .update(difference);
        }
        lastUpdate = now;
      }
    };
    timer.start();
  }

  public static class Builder {
    private final Game game;

    public Builder() {
      game = new Game();
    }

    public Game build() {
      assert game.controller != null : "Attempt to build Game object without controller";
      return game;
    }

    public Builder setController(Controller controller) {
      assert controller != null : "Attempt to set null controller";
      game.setController(controller);
      return this;
    }

    public Builder setOnStartAction(Action onStart) {
      game.onStart = onStart;
      return this;
    }
  }

  public Controller getController() {
    return controller;
  }
}
