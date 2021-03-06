package io.rpg.view.popups;

import io.rpg.viewmodel.TextImagePopupViewModel;
import io.rpg.viewmodel.TextPopupViewModel;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Objects;

public class TextImagePopup extends Scene {

  private final TextImagePopupViewModel viewModel;
  private static String backgroundPath;
  private static String buttonPath;

  public TextImagePopup(String text, Image image) {
    super(new Group(), Color.TRANSPARENT);

    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TextPopupViewModel.class.getResource("text-image-popup-view.fxml")));
    Parent root = null;

    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.setRoot(root);

    viewModel = loader.getController();
    viewModel.setText(text);
    viewModel.setImage(image);

    viewModel.setBackgroundImage(backgroundPath);
    viewModel.setOkButtonImage(buttonPath);

    this.setFill(Color.TRANSPARENT);
  }

  public static void setBackgroundPath(String backgroundPath) {
    TextImagePopup.backgroundPath = backgroundPath;
  }

  public static void setButtonPath(String buttonPath) {
    TextImagePopup.buttonPath = buttonPath;
  }

  public void setButtonCallback(EventHandler<? super MouseEvent> callback) {
    this.viewModel.setButtonOnClick(callback);
  }
}
