package io.rpg;

import io.rpg.controller.Controller;
import javafx.scene.Scene;
import org.jetbrains.annotations.NotNull;

public class Game {
  private Controller controller;

  private Game() {

  }

  public void setLocationControllerForLocationTag(String tag) {
    // tutaj przyda sie hashmapa: nazwa lokacji -> kontroller
  }

  public Scene getWorldView() {
    return controller.getView();
  }

  public void setController(Controller controller) {
    this.controller = controller;
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
  }
}
