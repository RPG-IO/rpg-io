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

  private final TextPopupViewModel viewModel;

  public TextPopup(String text, String backgroundPath, String buttonPath) {
    this(text);
    viewModel.setBackgroundImage(backgroundPath);
    viewModel.setOkButtonImage(buttonPath);
  }

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
    this.setFill(Color.TRANSPARENT);
  }

  public void setButtonCallback(javafx.event.EventHandler<? super javafx.scene.input.MouseEvent> callback){
    this.viewModel.setButtonOnClick(callback);
  }
}
