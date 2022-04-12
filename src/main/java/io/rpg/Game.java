package io.rpg;

import io.rpg.controller.Controller;
import javafx.scene.Scene;

public class Game {
  private Controller controller;

  public Game() {

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
}
