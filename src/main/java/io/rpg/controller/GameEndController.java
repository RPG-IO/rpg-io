package io.rpg.controller;

import io.rpg.view.GameEndView;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GameEndController {
  private final GameEndView view;
  private Runnable onEnd;

  public GameEndController() {
    view = GameEndView.load();
    onEnd = () -> {};
    view.setOnClick(this::end);
  }

  public void showGameEnd(Stage stage, String description) {
    view.setDescription(description);
    double prevWidth = stage.getWidth();
    double prevHeight = stage.getHeight();
    stage.setScene(view);
    stage.setWidth(prevWidth);
    stage.setHeight(prevHeight);
  }

  public void setOnEnd(Runnable onEnd) {
    this.onEnd = onEnd;
  }

  private void end() {
    onEnd.run();
  }
}
