package io.rpg;

import io.rpg.controller.Controller;
import io.rpg.model.actions.Action;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game {
  private Controller controller;
  private Action onStart;

  private Game() {

  }

  public void setController(Controller controller) {
    this.controller = controller;
  }

  public void start(Stage stage) {
    stage.show();
    controller.setMainStage(stage);
    controller.onAction(onStart);
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
