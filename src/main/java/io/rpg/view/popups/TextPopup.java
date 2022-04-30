package io.rpg.view.popups;

import io.rpg.viewmodel.TextPopupViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Objects;

public class TextPopup extends Scene {

  private final TextPopupViewModel controller;

  public TextPopup(String text, String backgroundPath) {
    super(new Group(), Color.TRANSPARENT);

    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TextPopupViewModel.class.getResource("text-popup-view.fxml")));
    Parent root = null;

    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.setRoot(root);

    controller = loader.getController();

    controller.setBackgroundImage(backgroundPath);
    controller.setText(text);
  }

  public TextPopup(String text) {
    this(text, "file:assets/popup-background.png");
  }
}
