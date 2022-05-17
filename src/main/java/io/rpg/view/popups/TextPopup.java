package io.rpg.view.popups;

import io.rpg.viewmodel.TextPopupViewModel;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Objects;

public class TextPopup extends Scene {

  private final TextPopupViewModel viewModel;
  private static String backgroundPath;
  private static String buttonPath;

  public TextPopup(String text) {
    super(new Group(), Color.TRANSPARENT);

    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TextPopupViewModel.class.getResource("text-popup-view.fxml")));
    Parent root = null;

    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.setRoot(root);

    viewModel = loader.getController();
    viewModel.setText(text);

    viewModel.setBackgroundImage(backgroundPath);
    viewModel.setOkButtonImage(buttonPath);
    this.setFill(Color.TRANSPARENT);
  }

  public static void setBackgroundPath(String backgroundPath) {
    TextPopup.backgroundPath = backgroundPath;
  }

  public static void setButtonPath(String buttonPath) {
    TextPopup.buttonPath = buttonPath;
  }

  public void setButtonCallback(EventHandler<? super MouseEvent> callback) {
    this.viewModel.setButtonOnClick(callback);
  }
}
